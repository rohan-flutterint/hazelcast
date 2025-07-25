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

package com.hazelcast.internal.diagnostics;

import com.hazelcast.logging.ILogger;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

/**
 * A {@link DiagnosticsPlugin} that displays the system properties.
 */
public class SystemPropertiesPlugin extends DiagnosticsPlugin {

    static final String JVM_ARGS = "java.vm.args";

    private final List keys = new ArrayList();
    private String inputArgs;

    public SystemPropertiesPlugin(ILogger logger) {
        super(logger);
    }

    @Override
    public long getPeriodMillis() {
        return RUN_ONCE_PERIOD_MS;
    }

    @Override
    public void onStart() {
        super.onStart();
        logger.info("Plugin:active");
        inputArgs = getInputArgs();
    }

    @Override
    public void onShutdown() {
        super.onShutdown();
        logger.info("Plugin:inactive");
    }

    @Override
    void readProperties() {
        // nothing to read
    }

    private static String getInputArgs() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();

        StringBuilder sb = new StringBuilder();
        for (String argument : arguments) {
            sb.append(argument);
            sb.append(' ');
        }
        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run(DiagnosticsLogWriter writer) {
        if (!isActive()) {
            return;
        }
        writer.startSection("SystemProperties");

        keys.clear();
        keys.addAll(System.getProperties().keySet());
        keys.add(JVM_ARGS);
        sort(keys);

        for (Object key : keys) {
            String keyString = (String) key;
            if (isIgnored(keyString)) {
                continue;
            }

            String value = getProperty(keyString);
            writer.writeKeyValueEntry(keyString, value);
        }
        writer.endSection();
    }

    private static boolean isIgnored(String systemProperty) {
        if (systemProperty.startsWith("java.awt")) {
            return true;
        }

        if (systemProperty.startsWith("java")
                || systemProperty.startsWith("hazelcast")
                || systemProperty.startsWith("sun")
                || systemProperty.startsWith("os")) {
            return false;
        }

        return true;
    }

    private String getProperty(String keyString) {
        if (keyString.equals(JVM_ARGS)) {
            return inputArgs;
        } else {
            return System.getProperty(keyString);
        }
    }
}
