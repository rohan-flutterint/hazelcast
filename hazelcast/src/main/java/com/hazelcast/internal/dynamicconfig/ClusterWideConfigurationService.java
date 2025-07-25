/*
 * Copyright (c) 2008-2025, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.internal.dynamicconfig;

import com.hazelcast.cache.impl.CacheService;
import com.hazelcast.cache.impl.ICacheService;
import com.hazelcast.config.CacheSimpleConfig;
import com.hazelcast.config.CardinalityEstimatorConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.ConfigPatternMatcher;
import com.hazelcast.config.DataConnectionConfig;
import com.hazelcast.internal.diagnostics.DiagnosticsConfig;
import com.hazelcast.config.DurableExecutorConfig;
import com.hazelcast.config.EventJournalConfig;
import com.hazelcast.config.ExecutorConfig;
import com.hazelcast.config.FlakeIdGeneratorConfig;
import com.hazelcast.config.InvalidConfigurationException;
import com.hazelcast.config.ListConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MerkleTreeConfig;
import com.hazelcast.config.MultiMapConfig;
import com.hazelcast.config.PNCounterConfig;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.config.ReliableTopicConfig;
import com.hazelcast.config.ReplicatedMapConfig;
import com.hazelcast.config.RingbufferConfig;
import com.hazelcast.config.ScheduledExecutorConfig;
import com.hazelcast.config.SetConfig;
import com.hazelcast.config.TopicConfig;
import com.hazelcast.config.UserCodeNamespaceAwareConfig;
import com.hazelcast.config.UserCodeNamespaceConfig;
import com.hazelcast.config.WanReplicationConfig;
import com.hazelcast.config.vector.VectorCollectionConfig;
import com.hazelcast.core.HazelcastException;
import com.hazelcast.internal.cluster.ClusterService;
import com.hazelcast.internal.cluster.ClusterVersionListener;
import com.hazelcast.internal.management.operation.UpdateTcpIpMemberListOperation;
import com.hazelcast.internal.namespace.NamespaceUtil;
import com.hazelcast.internal.serialization.Data;
import com.hazelcast.internal.serialization.SerializationService;
import com.hazelcast.internal.services.CoreService;
import com.hazelcast.internal.services.ManagedService;
import com.hazelcast.internal.services.PreJoinAwareService;
import com.hazelcast.internal.services.SplitBrainHandlerService;
import com.hazelcast.internal.util.FutureUtil;
import com.hazelcast.logging.ILogger;
import com.hazelcast.map.impl.MapService;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import com.hazelcast.spi.impl.InternalCompletableFuture;
import com.hazelcast.spi.impl.NodeEngine;
import com.hazelcast.spi.impl.NodeEngineImpl;
import com.hazelcast.version.Version;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

import static com.hazelcast.internal.cluster.Versions.V4_0;
import static com.hazelcast.internal.cluster.Versions.V5_2;
import static com.hazelcast.internal.cluster.Versions.V5_4;
import static com.hazelcast.internal.cluster.Versions.V5_5;
import static com.hazelcast.internal.cluster.Versions.V6_0;
import static com.hazelcast.internal.config.ConfigUtils.lookupByPattern;
import static com.hazelcast.internal.util.FutureUtil.waitForever;
import static com.hazelcast.internal.util.InvocationUtil.invokeOnStableClusterSerial;
import static java.lang.Boolean.getBoolean;
import static java.lang.String.format;
import static java.util.Collections.singleton;

@SuppressWarnings({"checkstyle:cyclomaticcomplexity", "checkstyle:methodcount", "checkstyle:classfanoutcomplexity"})
public class ClusterWideConfigurationService implements
        PreJoinAwareService<DynamicConfigPreJoinOperation>,
        CoreService,
        ClusterVersionListener,
        ManagedService,
        ConfigurationService,
        SplitBrainHandlerService {

    public static final int CONFIG_PUBLISH_MAX_ATTEMPT_COUNT = 100;

    // RU_COMPAT
    // maps config class to cluster version in which it was introduced
    static final Map<Class<? extends IdentifiedDataSerializable>, Version> CONFIG_TO_VERSION;

    //this is meant to be used as a workaround for buggy equals/hashcode implementations
    private static final boolean IGNORE_CONFLICTING_CONFIGS_WORKAROUND = getBoolean("hazelcast.dynamicconfig.ignore.conflicts");

    protected final NodeEngine nodeEngine;
    protected final ILogger logger;

    private final DynamicConfigListener listener;

    private final ConcurrentMap<String, MapConfig> mapConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, MultiMapConfig> multiMapConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, CardinalityEstimatorConfig> cardinalityEstimatorConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, PNCounterConfig> pnCounterConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, RingbufferConfig> ringbufferConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, ListConfig> listConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, SetConfig> setConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, ReplicatedMapConfig> replicatedMapConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, TopicConfig> topicConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, ExecutorConfig> executorConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, DurableExecutorConfig> durableExecutorConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, ScheduledExecutorConfig> scheduledExecutorConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, QueueConfig> queueConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, ReliableTopicConfig> reliableTopicConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, CacheSimpleConfig> cacheSimpleConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, FlakeIdGeneratorConfig> flakeIdGeneratorConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, DataConnectionConfig> dataConnectionConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, WanReplicationConfig> wanReplicationConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, UserCodeNamespaceConfig> namespaceConfigs = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, VectorCollectionConfig> vectorCollectionConfigs = new ConcurrentHashMap<>();
    private DiagnosticsConfig diagnosticsConfig;

    private final ConfigPatternMatcher configPatternMatcher;

    @SuppressWarnings("unchecked")
    private final Map<?, ? extends IdentifiedDataSerializable>[] allConfigurations = new Map[]{
            mapConfigs,
            multiMapConfigs,
            cardinalityEstimatorConfigs,
            ringbufferConfigs,
            listConfigs,
            setConfigs,
            replicatedMapConfigs,
            topicConfigs,
            executorConfigs,
            durableExecutorConfigs,
            scheduledExecutorConfigs,
            queueConfigs,
            reliableTopicConfigs,
            cacheSimpleConfigs,
            flakeIdGeneratorConfigs,
            pnCounterConfigs,
            dataConnectionConfigs,
            wanReplicationConfigs,
            namespaceConfigs,
            vectorCollectionConfigs,
    };

    private volatile Version version;

    static {
        CONFIG_TO_VERSION = initializeConfigToVersionMap();
    }

    public ClusterWideConfigurationService(
            NodeEngine nodeEngine,
            DynamicConfigListener dynamicConfigListener
    ) {
        this.nodeEngine = nodeEngine;
        this.listener = dynamicConfigListener;
        this.configPatternMatcher = nodeEngine.getConfig().getConfigPatternMatcher();
        this.logger = nodeEngine.getLogger(getClass());
    }

    @Override
    public DynamicConfigPreJoinOperation getPreJoinOperation() {
        IdentifiedDataSerializable[] allConfigurations = collectAllDynamicConfigs();
        if (noConfigurationExist(allConfigurations)) {
            // there is no dynamic configuration -> no need to send an empty operation
            return null;
        }
        return new DynamicConfigPreJoinOperation(allConfigurations, ConfigCheckMode.WARNING);
    }

    private boolean noConfigurationExist(IdentifiedDataSerializable[] configurations) {
        return configurations.length == 0;
    }

    private IdentifiedDataSerializable[] collectAllDynamicConfigs() {
        List<IdentifiedDataSerializable> all = new ArrayList<>();
        for (Map<?, ? extends IdentifiedDataSerializable> entry : allConfigurations) {
            Collection<? extends IdentifiedDataSerializable> values = entry.values();
            all.addAll(values);
        }
        return all.toArray(new IdentifiedDataSerializable[0]);
    }

    @Override
    public void onClusterVersionChange(Version newVersion) {
        version = newVersion;
    }

    @Override
    public void init(NodeEngine nodeEngine, Properties properties) {
        listener.onServiceInitialized(this);
    }

    @Override
    public void reset() {
        for (Map<?, ?> entry : allConfigurations) {
            entry.clear();
        }
    }

    @Override
    public void shutdown(boolean terminate) {
        //no-op
    }

    @Override
    public void broadcastConfig(IdentifiedDataSerializable config) {
        broadcastConfigAsync(config).joinInternal();
    }

    @Override
    public void updateLicense(String licenseKey) {
        throw new UnsupportedOperationException("Updating the license requires Hazelcast Enterprise");
    }

    @Override
    public CompletableFuture<Void> updateLicenseAsync(String licenseKey) {
        throw new UnsupportedOperationException("Updating the license requires Hazelcast Enterprise");
    }

    @Override
    public ConfigUpdateResult update(@Nullable Config newConfig) {
        throw new UnsupportedOperationException("Configuration Update requires Hazelcast Enterprise Edition");
    }

    @Override
    public UUID updateAsync(String configPatch) {
        throw new UnsupportedOperationException("Configuration Update requires Hazelcast Enterprise Edition");
    }

    public InternalCompletableFuture<Object> broadcastConfigAsync(IdentifiedDataSerializable config) {
        return broadcastConfigAsync(config, AddDynamicConfigOperationSupplier::new);
    }

    public InternalCompletableFuture<Object> broadcastConfigAsync(IdentifiedDataSerializable config,
                                                                  BiFunction<ClusterService, IdentifiedDataSerializable,
                                                                  DynamicConfigOperationSupplier> dynamicConfigOpGenerator) {
        checkConfigVersion(config);
        // we create a defensive copy as local operation execution might use a fast-path
        // and avoid config serialization altogether.
        // we certainly do not want the dynamic config service to reference object a user can mutate
        IdentifiedDataSerializable clonedConfig = cloneConfig(config);
        ClusterService clusterService = nodeEngine.getClusterService();
        return invokeOnStableClusterSerial(nodeEngine, dynamicConfigOpGenerator.apply(clusterService, clonedConfig),
                CONFIG_PUBLISH_MAX_ATTEMPT_COUNT);
    }

    private void checkConfigVersion(IdentifiedDataSerializable config) {
        Class<? extends IdentifiedDataSerializable> configClass = config.getClass();
        Version currentClusterVersion = version;
        Version introducedIn = CONFIG_TO_VERSION.get(configClass);
        if (currentClusterVersion.isLessThan(introducedIn)) {
            throw new UnsupportedOperationException(format("Config '%s' is available since version '%s'. "
                            + "Current cluster version '%s' does not allow dynamically modifying '%1$s'.",
                    configClass.getSimpleName(),
                    introducedIn.toString(),
                    currentClusterVersion
            ));
        }
    }

    private IdentifiedDataSerializable cloneConfig(IdentifiedDataSerializable config) {
        SerializationService serializationService = nodeEngine.getSerializationService();
        Data data = serializationService.toData(config);
        // Certain configs can contain definitions for UDFs (such as ItemListenerConfig), so
        //     we should wrap the deserialization in Namespace awareness where applicable
        if (config instanceof UserCodeNamespaceAwareConfig nsAware) {
            return NamespaceUtil.callWithNamespace(nodeEngine, nsAware.getUserCodeNamespace(),
                    () -> serializationService.toObject(data));
        }
        return serializationService.toObject(data);
    }

    /**
     * Register a dynamic configuration in a local member.
     *
     * @param newConfig       Configuration to register.
     * @param configCheckMode behaviour when a config is detected
     * @throws UnsupportedOperationException when given configuration type is not supported
     * @throws InvalidConfigurationException when conflict is detected and configCheckMode is on THROW_EXCEPTION
     */
    @SuppressWarnings("checkstyle:methodlength")
    public void registerConfigLocally(IdentifiedDataSerializable newConfig, ConfigCheckMode configCheckMode) {
        IdentifiedDataSerializable currentConfig;
        if (newConfig instanceof MultiMapConfig multiMapConfig) {
            currentConfig = multiMapConfigs.putIfAbsent(multiMapConfig.getName(), multiMapConfig);
        } else if (newConfig instanceof MapConfig newMapConfig) {
            currentConfig = mapConfigs.putIfAbsent(newMapConfig.getName(), newMapConfig);
            if (currentConfig == null) {
                UserCodeNamespaceConfig namespace = findPersistableNamespaceConfig(newMapConfig.getUserCodeNamespace());
                listener.onConfigRegistered(newMapConfig, namespace);
            }
        } else if (newConfig instanceof CardinalityEstimatorConfig cardinalityEstimatorConfig) {
            currentConfig = cardinalityEstimatorConfigs.putIfAbsent(
                    cardinalityEstimatorConfig.getName(),
                    cardinalityEstimatorConfig
            );
        } else if (newConfig instanceof RingbufferConfig ringbufferConfig) {
            currentConfig = ringbufferConfigs.putIfAbsent(ringbufferConfig.getName(), ringbufferConfig);
        } else if (newConfig instanceof ListConfig listConfig) {
            currentConfig = listConfigs.putIfAbsent(listConfig.getName(), listConfig);
        } else if (newConfig instanceof SetConfig setConfig) {
            currentConfig = setConfigs.putIfAbsent(setConfig.getName(), setConfig);
        } else if (newConfig instanceof ReplicatedMapConfig replicatedMapConfig) {
            currentConfig = replicatedMapConfigs.putIfAbsent(replicatedMapConfig.getName(), replicatedMapConfig);
        } else if (newConfig instanceof TopicConfig topicConfig) {
            currentConfig = topicConfigs.putIfAbsent(topicConfig.getName(), topicConfig);
        } else if (newConfig instanceof ExecutorConfig executorConfig) {
            currentConfig = executorConfigs.putIfAbsent(executorConfig.getName(), executorConfig);
        } else if (newConfig instanceof DurableExecutorConfig durableExecutorConfig) {
            currentConfig = durableExecutorConfigs.putIfAbsent(durableExecutorConfig.getName(), durableExecutorConfig);
        } else if (newConfig instanceof ScheduledExecutorConfig scheduledExecutorConfig) {
            currentConfig = scheduledExecutorConfigs.putIfAbsent(scheduledExecutorConfig.getName(), scheduledExecutorConfig);
        } else if (newConfig instanceof QueueConfig queueConfig) {
            currentConfig = queueConfigs.putIfAbsent(queueConfig.getName(), queueConfig);
        } else if (newConfig instanceof ReliableTopicConfig reliableTopicConfig) {
            currentConfig = reliableTopicConfigs.putIfAbsent(reliableTopicConfig.getName(), reliableTopicConfig);
        } else if (newConfig instanceof CacheSimpleConfig cacheSimpleConfig) {
            currentConfig = cacheSimpleConfigs.putIfAbsent(cacheSimpleConfig.getName(), cacheSimpleConfig);
            if (currentConfig == null) {
                UserCodeNamespaceConfig namespace = findPersistableNamespaceConfig(cacheSimpleConfig.getUserCodeNamespace());
                listener.onConfigRegistered(cacheSimpleConfig, namespace);
            }
        } else if (newConfig instanceof FlakeIdGeneratorConfig config) {
            currentConfig = flakeIdGeneratorConfigs.putIfAbsent(config.getName(), config);
        } else if (newConfig instanceof PNCounterConfig config) {
            currentConfig = pnCounterConfigs.putIfAbsent(config.getName(), config);
        } else if (newConfig instanceof DataConnectionConfig config) {
            currentConfig = dataConnectionConfigs.putIfAbsent(config.getName(), config);
            if (currentConfig == null) {
                nodeEngine.getDataConnectionService().createConfigDataConnection(config);
            }
        } else if (newConfig instanceof WanReplicationConfig config) {
            currentConfig = wanReplicationConfigs.putIfAbsent(config.getName(), config);
            if (currentConfig == null) {
                nodeEngine.getWanReplicationService().addWanReplicationConfig(config);
            }
        } else if (newConfig instanceof UserCodeNamespaceConfig config) {
            // Deliberately overwrite existing
            currentConfig = namespaceConfigs.put(config.getName(), config);
            // ensure that the namespace is registered and added to the config.
            nodeEngine.getNamespaceService().addNamespaceConfig(nodeEngine.getConfig().getNamespacesConfig(), config);
            if (isNamespaceReferencedWithHRPersistence(nodeEngine, config)) {
                listener.onConfigRegistered(config);
            }
        } else if (newConfig instanceof VectorCollectionConfig newVectorCollectionConfig) {
            currentConfig = vectorCollectionConfigs.putIfAbsent(newVectorCollectionConfig.getName(), newVectorCollectionConfig);
        } else if (newConfig instanceof DiagnosticsConfig newDiagnosticsConfig) {
            ((NodeEngineImpl) nodeEngine).getDiagnostics().setConfig(newDiagnosticsConfig);
            diagnosticsConfig = newDiagnosticsConfig;
            currentConfig = newDiagnosticsConfig;
        } else {
            throw new UnsupportedOperationException("Unsupported config type: " + newConfig);
        }
        checkCurrentConfigNullOrEqual(configCheckMode, currentConfig, newConfig);
        persist(newConfig);
    }

    protected void checkCurrentConfigNullOrEqual(ConfigCheckMode checkMode, Object currentConfig, Object newConfig) {
        if (IGNORE_CONFLICTING_CONFIGS_WORKAROUND) {
            return;
        }
        if (currentConfig == null) {
            return;
        }
        if (!currentConfig.equals(newConfig)) {
            String message = "Cannot add a dynamic configuration '" + newConfig + "' as there"
                    + " is already a conflicting configuration '" + currentConfig + "'";
            switch (checkMode) {
                case THROW_EXCEPTION:
                    throw new InvalidConfigurationException(message);
                case WARNING:
                    logger.warning(message);
                    break;
                case SILENT:
                    logger.finest(message);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown consistency check mode: " + checkMode);
            }
        }
    }

    /**
     * Retrieve a namespace from the list of dynamically added or updated
     * namespaces.
     * <p>
     * Only those namespaces that are not statically configured should be
     * persisted for hot restart enabled data structures.
     *
     * @param name namespace name.
     * @return the {@code UserCodeNamespaceConfig} or {@code null} if not found.
     */
    private UserCodeNamespaceConfig findPersistableNamespaceConfig(String name) {
        if (name == null) {
            return null;
        }
        return namespaceConfigs.get(name);
    }

    /**
     * Checks if the given namespace is referenced by a hot restart enabled
     * data structure.
     *
     * @param nodeEngine the node engine.
     * @param namespace  the namespace.
     * @return {@code true} if the namespace is referenced by a hot restart
     * enabled data structure, {@code false} otherwise.
     */
    private boolean isNamespaceReferencedWithHRPersistence(NodeEngine nodeEngine, UserCodeNamespaceConfig namespace) {
        CacheService cacheService = nodeEngine.getServiceOrNull(ICacheService.SERVICE_NAME);
        if (cacheService == null) {
            return MapService.isNamespaceReferencedWithHotRestart(nodeEngine, namespace.getName());
        } else {
            return MapService.isNamespaceReferencedWithHotRestart(nodeEngine, namespace.getName())
                    || cacheService.isNamespaceReferencedWithHotRestart(namespace.getName());
        }
    }


    @Override
    public void persist(Object subConfig) {
        if (nodeEngine.getConfig().getDynamicConfigurationConfig().isPersistenceEnabled()) {
            // Code should never come here. We should fast fail in
            // DefaultNodeExtension#checkDynamicConfigurationPersistenceAllowed()
            throw new UnsupportedOperationException("Dynamic Configuration Persistence requires Hazelcast Enterprise Edition");
        }
    }

    @Override
    public MultiMapConfig findMultiMapConfig(String name) {
        return lookupByPattern(configPatternMatcher, multiMapConfigs, name);
    }

    @Override
    public ConcurrentMap<String, MultiMapConfig> getMultiMapConfigs() {
        return multiMapConfigs;
    }

    @Override
    public MapConfig findMapConfig(String name) {
        return lookupByPattern(configPatternMatcher, mapConfigs, name);
    }

    @Override
    public Map<String, MapConfig> getMapConfigs() {
        return mapConfigs;
    }

    @Override
    public TopicConfig findTopicConfig(String name) {
        return lookupByPattern(configPatternMatcher, topicConfigs, name);
    }

    @Override
    public ConcurrentMap<String, TopicConfig> getTopicConfigs() {
        return topicConfigs;
    }

    @Override
    public CardinalityEstimatorConfig findCardinalityEstimatorConfig(String name) {
        return lookupByPattern(configPatternMatcher, cardinalityEstimatorConfigs, name);
    }

    @Override
    public ConcurrentMap<String, CardinalityEstimatorConfig> getCardinalityEstimatorConfigs() {
        return cardinalityEstimatorConfigs;
    }

    @Override
    public PNCounterConfig findPNCounterConfig(String name) {
        return lookupByPattern(configPatternMatcher, pnCounterConfigs, name);
    }

    @Override
    public ConcurrentMap<String, PNCounterConfig> getPNCounterConfigs() {
        return pnCounterConfigs;
    }

    @Override
    public ExecutorConfig findExecutorConfig(String name) {
        return lookupByPattern(configPatternMatcher, executorConfigs, name);
    }

    @Override
    public ConcurrentMap<String, ExecutorConfig> getExecutorConfigs() {
        return executorConfigs;
    }

    @Override
    public ScheduledExecutorConfig findScheduledExecutorConfig(String name) {
        return lookupByPattern(configPatternMatcher, scheduledExecutorConfigs, name);
    }

    @Override
    public ConcurrentMap<String, ScheduledExecutorConfig> getScheduledExecutorConfigs() {
        return scheduledExecutorConfigs;
    }

    @Override
    public DurableExecutorConfig findDurableExecutorConfig(String name) {
        return lookupByPattern(configPatternMatcher, durableExecutorConfigs, name);
    }

    @Override
    public ConcurrentMap<String, DurableExecutorConfig> getDurableExecutorConfigs() {
        return durableExecutorConfigs;
    }

    @Override
    public RingbufferConfig findRingbufferConfig(String name) {
        return lookupByPattern(configPatternMatcher, ringbufferConfigs, name);
    }

    @Override
    public ConcurrentMap<String, RingbufferConfig> getRingbufferConfigs() {
        return ringbufferConfigs;
    }

    @Override
    public ListConfig findListConfig(String name) {
        return lookupByPattern(configPatternMatcher, listConfigs, name);
    }

    @Override
    public ConcurrentMap<String, ListConfig> getListConfigs() {
        return listConfigs;
    }

    @Override
    public QueueConfig findQueueConfig(String name) {
        return lookupByPattern(configPatternMatcher, queueConfigs, name);
    }

    @Override
    public Map<String, QueueConfig> getQueueConfigs() {
        return queueConfigs;
    }

    @Override
    public SetConfig findSetConfig(String name) {
        return lookupByPattern(configPatternMatcher, setConfigs, name);
    }

    @Override
    public ConcurrentMap<String, SetConfig> getSetConfigs() {
        return setConfigs;
    }

    @Override
    public ReplicatedMapConfig findReplicatedMapConfig(String name) {
        return lookupByPattern(configPatternMatcher, replicatedMapConfigs, name);
    }

    @Override
    public ConcurrentMap<String, ReplicatedMapConfig> getReplicatedMapConfigs() {
        return replicatedMapConfigs;
    }

    @Override
    public ReliableTopicConfig findReliableTopicConfig(String name) {
        return lookupByPattern(configPatternMatcher, reliableTopicConfigs, name);
    }

    @Override
    public ConcurrentMap<String, ReliableTopicConfig> getReliableTopicConfigs() {
        return reliableTopicConfigs;
    }

    @Override
    public CacheSimpleConfig findCacheSimpleConfig(String name) {
        return lookupByPattern(configPatternMatcher, cacheSimpleConfigs, name);
    }

    @Override
    public Map<String, CacheSimpleConfig> getCacheSimpleConfigs() {
        return cacheSimpleConfigs;
    }

    @Override
    public FlakeIdGeneratorConfig findFlakeIdGeneratorConfig(String baseName) {
        return lookupByPattern(configPatternMatcher, flakeIdGeneratorConfigs, baseName);
    }

    @Override
    public Map<String, FlakeIdGeneratorConfig> getFlakeIdGeneratorConfigs() {
        return flakeIdGeneratorConfigs;
    }

    @Override
    public DataConnectionConfig findDataConnectionConfig(String baseName) {
        return lookupByPattern(configPatternMatcher, dataConnectionConfigs, baseName);
    }

    @Override
    public Map<String, DataConnectionConfig> getDataConnectionConfigs() {
        return dataConnectionConfigs;
    }

    @Override
    public WanReplicationConfig findWanReplicationConfig(String name) {
        return lookupByPattern(configPatternMatcher, wanReplicationConfigs, name);
    }

    @Override
    public Map<String, WanReplicationConfig> getWanReplicationConfigs() {
        return wanReplicationConfigs;
    }

    @Override
    public Map<String, UserCodeNamespaceConfig> getNamespaceConfigs() {
        return namespaceConfigs;
    }

    @Override
    public VectorCollectionConfig findVectorCollectionConfig(String name) {
        return lookupByPattern(configPatternMatcher, vectorCollectionConfigs, name);
    }

    @Override
    public Map<String, VectorCollectionConfig> getVectorCollectionConfigs() {
        return vectorCollectionConfigs;
    }

    @Override
    public DiagnosticsConfig getDiagnosticsConfig() {
        return diagnosticsConfig;
    }

    @Override
    public Runnable prepareMergeRunnable() {
        IdentifiedDataSerializable[] allConfigurations = collectAllDynamicConfigs();
        if (noConfigurationExist(allConfigurations)) {
            return null;
        }
        return new Merger(nodeEngine, allConfigurations);
    }

    @Override
    public CompletableFuture<Void> updateTcpIpConfigMemberListAsync(List<String> memberList) {
        return invokeOnStableClusterSerial(
                nodeEngine,
                () -> new UpdateTcpIpMemberListOperation(memberList), CONFIG_PUBLISH_MAX_ATTEMPT_COUNT);
    }

    @Override
    public void updateTcpIpConfigMemberList(List<String> memberList) {
        updateTcpIpConfigMemberListAsync(memberList).join();
    }

    public static class Merger implements Runnable {
        private final NodeEngine nodeEngine;
        private final IdentifiedDataSerializable[] allConfigurations;

        @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
        public Merger(NodeEngine nodeEngine, IdentifiedDataSerializable[] allConfigurations) {
            this.nodeEngine = nodeEngine;
            this.allConfigurations = allConfigurations;
        }

        @Override
        public void run() {
            try {
                Future<Object> future = invokeOnStableClusterSerial(
                        nodeEngine,
                        () -> new DynamicConfigPreJoinOperation(allConfigurations, ConfigCheckMode.SILENT),
                        CONFIG_PUBLISH_MAX_ATTEMPT_COUNT
                );
                waitForever(singleton(future), FutureUtil.RETHROW_EVERYTHING);
            } catch (Exception e) {
                throw new HazelcastException("Error while merging configurations", e);
            }
        }
    }

    private static Map<Class<? extends IdentifiedDataSerializable>, Version> initializeConfigToVersionMap() {
        Map<Class<? extends IdentifiedDataSerializable>, Version> configToVersion =
                new HashMap<>();

        configToVersion.put(MapConfig.class, V4_0);
        configToVersion.put(MultiMapConfig.class, V4_0);
        configToVersion.put(CardinalityEstimatorConfig.class, V4_0);
        configToVersion.put(RingbufferConfig.class, V4_0);
        configToVersion.put(ListConfig.class, V4_0);
        configToVersion.put(SetConfig.class, V4_0);
        configToVersion.put(ReplicatedMapConfig.class, V4_0);
        configToVersion.put(TopicConfig.class, V4_0);
        configToVersion.put(ExecutorConfig.class, V4_0);
        configToVersion.put(DurableExecutorConfig.class, V4_0);
        configToVersion.put(ScheduledExecutorConfig.class, V4_0);
        configToVersion.put(QueueConfig.class, V4_0);
        configToVersion.put(ReliableTopicConfig.class, V4_0);
        configToVersion.put(CacheSimpleConfig.class, V4_0);
        configToVersion.put(EventJournalConfig.class, V4_0);
        configToVersion.put(FlakeIdGeneratorConfig.class, V4_0);
        configToVersion.put(PNCounterConfig.class, V4_0);
        configToVersion.put(MerkleTreeConfig.class, V4_0);
        configToVersion.put(DataConnectionConfig.class, V5_2);
        configToVersion.put(WanReplicationConfig.class, V5_4);
        configToVersion.put(UserCodeNamespaceConfig.class, V5_4);
        configToVersion.put(VectorCollectionConfig.class, V5_5);
        configToVersion.put(DiagnosticsConfig.class, V6_0);

        return Collections.unmodifiableMap(configToVersion);
    }
}
