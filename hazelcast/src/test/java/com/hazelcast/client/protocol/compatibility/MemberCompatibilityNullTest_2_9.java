/*
 * Copyright (c) 2008-, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.client.protocol.compatibility;

import com.hazelcast.client.HazelcastClientUtil;
import com.hazelcast.client.impl.protocol.ClientMessage;
import com.hazelcast.client.impl.protocol.ClientMessageReader;
import com.hazelcast.client.impl.protocol.codec.*;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.annotation.ParallelJVMTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hazelcast.client.impl.protocol.ClientMessage.IS_FINAL_FLAG;
import static com.hazelcast.client.protocol.compatibility.ReferenceObjects.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@SuppressWarnings("unused")
@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelJVMTest.class})
public class MemberCompatibilityNullTest_2_9 {
    private final List<ClientMessage> clientMessages = new ArrayList<>();

    @Before
    public void setUp() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/2.9.protocol.compatibility.null.binary")) {
            assert inputStream != null;
            byte[] data = inputStream.readAllBytes();
            ByteBuffer buffer = ByteBuffer.wrap(data);
            ClientMessageReader reader = new ClientMessageReader(0);
            while (reader.readFrom(buffer, true)) {
                clientMessages.add(reader.getClientMessage());
                reader.reset();
            }
        }
    }

    @Test
    public void test_ClientAuthenticationCodec_decodeRequest() {
        int fileClientMessageIndex = 0;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientAuthenticationCodec.RequestParameters parameters = ClientAuthenticationCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.clusterName));
        assertTrue(isEqual(null, parameters.username));
        assertTrue(isEqual(null, parameters.password));
        assertTrue(isEqual(null, parameters.uuid));
        assertTrue(isEqual(aString, parameters.clientType));
        assertTrue(isEqual(aByte, parameters.serializationVersion));
        assertTrue(isEqual(aString, parameters.clientHazelcastVersion));
        assertTrue(isEqual(aString, parameters.clientName));
        assertTrue(isEqual(aListOfStrings, parameters.labels));
        assertTrue(parameters.isRoutingModeExists);
        assertTrue(isEqual(aByte, parameters.routingMode));
        assertTrue(parameters.isCpDirectToLeaderRoutingExists);
        assertTrue(isEqual(aBoolean, parameters.cpDirectToLeaderRouting));
    }

    @Test
    public void test_ClientAuthenticationCodec_encodeResponse() {
        int fileClientMessageIndex = 1;
        ClientMessage encoded = ClientAuthenticationCodec.encodeResponse(aByte, null, null, aByte, aString, anInt, aUUID, aBoolean, null, null, anInt, aListOfMemberInfos, anInt, aListOfUUIDToListOfIntegers, aMapOfStringToString);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAuthenticationCustomCodec_decodeRequest() {
        int fileClientMessageIndex = 2;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientAuthenticationCustomCodec.RequestParameters parameters = ClientAuthenticationCustomCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.clusterName));
        assertTrue(isEqual(aByteArray, parameters.credentials));
        assertTrue(isEqual(null, parameters.uuid));
        assertTrue(isEqual(aString, parameters.clientType));
        assertTrue(isEqual(aByte, parameters.serializationVersion));
        assertTrue(isEqual(aString, parameters.clientHazelcastVersion));
        assertTrue(isEqual(aString, parameters.clientName));
        assertTrue(isEqual(aListOfStrings, parameters.labels));
        assertTrue(parameters.isRoutingModeExists);
        assertTrue(isEqual(aByte, parameters.routingMode));
        assertTrue(parameters.isCpDirectToLeaderRoutingExists);
        assertTrue(isEqual(aBoolean, parameters.cpDirectToLeaderRouting));
    }

    @Test
    public void test_ClientAuthenticationCustomCodec_encodeResponse() {
        int fileClientMessageIndex = 3;
        ClientMessage encoded = ClientAuthenticationCustomCodec.encodeResponse(aByte, null, null, aByte, aString, anInt, aUUID, aBoolean, null, null, anInt, null, anInt, null, aMapOfStringToString);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddClusterViewListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 4;
    }

    @Test
    public void test_ClientAddClusterViewListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 5;
        ClientMessage encoded = ClientAddClusterViewListenerCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddClusterViewListenerCodec_encodeMembersViewEvent() {
        int fileClientMessageIndex = 6;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ClientAddClusterViewListenerCodec.encodeMembersViewEvent(anInt, aListOfMemberInfos);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddClusterViewListenerCodec_encodePartitionsViewEvent() {
        int fileClientMessageIndex = 7;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ClientAddClusterViewListenerCodec.encodePartitionsViewEvent(anInt, aListOfUUIDToListOfIntegers);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddClusterViewListenerCodec_encodeMemberGroupsViewEvent() {
        int fileClientMessageIndex = 8;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ClientAddClusterViewListenerCodec.encodeMemberGroupsViewEvent(anInt, aListOfListOfUUIDs);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddClusterViewListenerCodec_encodeClusterVersionEvent() {
        int fileClientMessageIndex = 9;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ClientAddClusterViewListenerCodec.encodeClusterVersionEvent(aVersion);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientCreateProxyCodec_decodeRequest() {
        int fileClientMessageIndex = 10;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientCreateProxyCodec.RequestParameters parameters = ClientCreateProxyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aString, parameters.serviceName));
    }

    @Test
    public void test_ClientCreateProxyCodec_encodeResponse() {
        int fileClientMessageIndex = 11;
        ClientMessage encoded = ClientCreateProxyCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientDestroyProxyCodec_decodeRequest() {
        int fileClientMessageIndex = 12;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientDestroyProxyCodec.RequestParameters parameters = ClientDestroyProxyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aString, parameters.serviceName));
    }

    @Test
    public void test_ClientDestroyProxyCodec_encodeResponse() {
        int fileClientMessageIndex = 13;
        ClientMessage encoded = ClientDestroyProxyCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddPartitionLostListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 14;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aBoolean, ClientAddPartitionLostListenerCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ClientAddPartitionLostListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 15;
        ClientMessage encoded = ClientAddPartitionLostListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddPartitionLostListenerCodec_encodePartitionLostEvent() {
        int fileClientMessageIndex = 16;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ClientAddPartitionLostListenerCodec.encodePartitionLostEvent(anInt, anInt, null);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientRemovePartitionLostListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 17;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aUUID, ClientRemovePartitionLostListenerCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ClientRemovePartitionLostListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 18;
        ClientMessage encoded = ClientRemovePartitionLostListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientGetDistributedObjectsCodec_decodeRequest() {
        int fileClientMessageIndex = 19;
    }

    @Test
    public void test_ClientGetDistributedObjectsCodec_encodeResponse() {
        int fileClientMessageIndex = 20;
        ClientMessage encoded = ClientGetDistributedObjectsCodec.encodeResponse(aListOfDistributedObjectInfo);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddDistributedObjectListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 21;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aBoolean, ClientAddDistributedObjectListenerCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ClientAddDistributedObjectListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 22;
        ClientMessage encoded = ClientAddDistributedObjectListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddDistributedObjectListenerCodec_encodeDistributedObjectEvent() {
        int fileClientMessageIndex = 23;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ClientAddDistributedObjectListenerCodec.encodeDistributedObjectEvent(aString, aString, aString, aUUID);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientRemoveDistributedObjectListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 24;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aUUID, ClientRemoveDistributedObjectListenerCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ClientRemoveDistributedObjectListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 25;
        ClientMessage encoded = ClientRemoveDistributedObjectListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientPingCodec_decodeRequest() {
        int fileClientMessageIndex = 26;
    }

    @Test
    public void test_ClientPingCodec_encodeResponse() {
        int fileClientMessageIndex = 27;
        ClientMessage encoded = ClientPingCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientStatisticsCodec_decodeRequest() {
        int fileClientMessageIndex = 28;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientStatisticsCodec.RequestParameters parameters = ClientStatisticsCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aLong, parameters.timestamp));
        assertTrue(isEqual(aString, parameters.clientAttributes));
        assertTrue(isEqual(aByteArray, parameters.metricsBlob));
    }

    @Test
    public void test_ClientStatisticsCodec_encodeResponse() {
        int fileClientMessageIndex = 29;
        ClientMessage encoded = ClientStatisticsCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientDeployClassesCodec_decodeRequest() {
        int fileClientMessageIndex = 30;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aListOfStringToByteArray, ClientDeployClassesCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ClientDeployClassesCodec_encodeResponse() {
        int fileClientMessageIndex = 31;
        ClientMessage encoded = ClientDeployClassesCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientCreateProxiesCodec_decodeRequest() {
        int fileClientMessageIndex = 32;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aListOfStringToString, ClientCreateProxiesCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ClientCreateProxiesCodec_encodeResponse() {
        int fileClientMessageIndex = 33;
        ClientMessage encoded = ClientCreateProxiesCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientLocalBackupListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 34;
    }

    @Test
    public void test_ClientLocalBackupListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 35;
        ClientMessage encoded = ClientLocalBackupListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientLocalBackupListenerCodec_encodeBackupEvent() {
        int fileClientMessageIndex = 36;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ClientLocalBackupListenerCodec.encodeBackupEvent(aLong);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientTriggerPartitionAssignmentCodec_decodeRequest() {
        int fileClientMessageIndex = 37;
    }

    @Test
    public void test_ClientTriggerPartitionAssignmentCodec_encodeResponse() {
        int fileClientMessageIndex = 38;
        ClientMessage encoded = ClientTriggerPartitionAssignmentCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddMigrationListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 39;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aBoolean, ClientAddMigrationListenerCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ClientAddMigrationListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 40;
        ClientMessage encoded = ClientAddMigrationListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddMigrationListenerCodec_encodeMigrationEvent() {
        int fileClientMessageIndex = 41;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ClientAddMigrationListenerCodec.encodeMigrationEvent(aMigrationState, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddMigrationListenerCodec_encodeReplicaMigrationEvent() {
        int fileClientMessageIndex = 42;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ClientAddMigrationListenerCodec.encodeReplicaMigrationEvent(aMigrationState, anInt, anInt, null, null, aBoolean, aLong);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientRemoveMigrationListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 43;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aUUID, ClientRemoveMigrationListenerCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ClientRemoveMigrationListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 44;
        ClientMessage encoded = ClientRemoveMigrationListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientSendSchemaCodec_decodeRequest() {
        int fileClientMessageIndex = 45;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aSchema, ClientSendSchemaCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ClientSendSchemaCodec_encodeResponse() {
        int fileClientMessageIndex = 46;
        ClientMessage encoded = ClientSendSchemaCodec.encodeResponse(aSetOfUUIDs);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientFetchSchemaCodec_decodeRequest() {
        int fileClientMessageIndex = 47;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aLong, ClientFetchSchemaCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ClientFetchSchemaCodec_encodeResponse() {
        int fileClientMessageIndex = 48;
        ClientMessage encoded = ClientFetchSchemaCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientSendAllSchemasCodec_decodeRequest() {
        int fileClientMessageIndex = 49;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aListOfSchemas, ClientSendAllSchemasCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ClientSendAllSchemasCodec_encodeResponse() {
        int fileClientMessageIndex = 50;
        ClientMessage encoded = ClientSendAllSchemasCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientTpcAuthenticationCodec_decodeRequest() {
        int fileClientMessageIndex = 51;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientTpcAuthenticationCodec.RequestParameters parameters = ClientTpcAuthenticationCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aUUID, parameters.uuid));
        assertTrue(isEqual(aByteArray, parameters.token));
    }

    @Test
    public void test_ClientTpcAuthenticationCodec_encodeResponse() {
        int fileClientMessageIndex = 52;
        ClientMessage encoded = ClientTpcAuthenticationCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddCPGroupViewListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 53;
    }

    @Test
    public void test_ClientAddCPGroupViewListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 54;
        ClientMessage encoded = ClientAddCPGroupViewListenerCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ClientAddCPGroupViewListenerCodec_encodeGroupsViewEvent() {
        int fileClientMessageIndex = 55;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ClientAddCPGroupViewListenerCodec.encodeGroupsViewEvent(aLong, aListOfRaftGroupInfo, aListOfUUIDToUUID);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapPutCodec_decodeRequest() {
        int fileClientMessageIndex = 56;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapPutCodec.RequestParameters parameters = MapPutCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.ttl));
    }

    @Test
    public void test_MapPutCodec_encodeResponse() {
        int fileClientMessageIndex = 57;
        ClientMessage encoded = MapPutCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapGetCodec_decodeRequest() {
        int fileClientMessageIndex = 58;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapGetCodec.RequestParameters parameters = MapGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MapGetCodec_encodeResponse() {
        int fileClientMessageIndex = 59;
        ClientMessage encoded = MapGetCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 60;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapRemoveCodec.RequestParameters parameters = MapRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MapRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 61;
        ClientMessage encoded = MapRemoveCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapReplaceCodec_decodeRequest() {
        int fileClientMessageIndex = 62;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapReplaceCodec.RequestParameters parameters = MapReplaceCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MapReplaceCodec_encodeResponse() {
        int fileClientMessageIndex = 63;
        ClientMessage encoded = MapReplaceCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapReplaceIfSameCodec_decodeRequest() {
        int fileClientMessageIndex = 64;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapReplaceIfSameCodec.RequestParameters parameters = MapReplaceIfSameCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.testValue));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MapReplaceIfSameCodec_encodeResponse() {
        int fileClientMessageIndex = 65;
        ClientMessage encoded = MapReplaceIfSameCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapContainsKeyCodec_decodeRequest() {
        int fileClientMessageIndex = 66;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapContainsKeyCodec.RequestParameters parameters = MapContainsKeyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MapContainsKeyCodec_encodeResponse() {
        int fileClientMessageIndex = 67;
        ClientMessage encoded = MapContainsKeyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapContainsValueCodec_decodeRequest() {
        int fileClientMessageIndex = 68;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapContainsValueCodec.RequestParameters parameters = MapContainsValueCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_MapContainsValueCodec_encodeResponse() {
        int fileClientMessageIndex = 69;
        ClientMessage encoded = MapContainsValueCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapRemoveIfSameCodec_decodeRequest() {
        int fileClientMessageIndex = 70;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapRemoveIfSameCodec.RequestParameters parameters = MapRemoveIfSameCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MapRemoveIfSameCodec_encodeResponse() {
        int fileClientMessageIndex = 71;
        ClientMessage encoded = MapRemoveIfSameCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapDeleteCodec_decodeRequest() {
        int fileClientMessageIndex = 72;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapDeleteCodec.RequestParameters parameters = MapDeleteCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MapDeleteCodec_encodeResponse() {
        int fileClientMessageIndex = 73;
        ClientMessage encoded = MapDeleteCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapFlushCodec_decodeRequest() {
        int fileClientMessageIndex = 74;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MapFlushCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MapFlushCodec_encodeResponse() {
        int fileClientMessageIndex = 75;
        ClientMessage encoded = MapFlushCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapTryRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 76;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapTryRemoveCodec.RequestParameters parameters = MapTryRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.timeout));
    }

    @Test
    public void test_MapTryRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 77;
        ClientMessage encoded = MapTryRemoveCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapTryPutCodec_decodeRequest() {
        int fileClientMessageIndex = 78;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapTryPutCodec.RequestParameters parameters = MapTryPutCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.timeout));
    }

    @Test
    public void test_MapTryPutCodec_encodeResponse() {
        int fileClientMessageIndex = 79;
        ClientMessage encoded = MapTryPutCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapPutTransientCodec_decodeRequest() {
        int fileClientMessageIndex = 80;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapPutTransientCodec.RequestParameters parameters = MapPutTransientCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.ttl));
    }

    @Test
    public void test_MapPutTransientCodec_encodeResponse() {
        int fileClientMessageIndex = 81;
        ClientMessage encoded = MapPutTransientCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapPutIfAbsentCodec_decodeRequest() {
        int fileClientMessageIndex = 82;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapPutIfAbsentCodec.RequestParameters parameters = MapPutIfAbsentCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.ttl));
    }

    @Test
    public void test_MapPutIfAbsentCodec_encodeResponse() {
        int fileClientMessageIndex = 83;
        ClientMessage encoded = MapPutIfAbsentCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapSetCodec_decodeRequest() {
        int fileClientMessageIndex = 84;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapSetCodec.RequestParameters parameters = MapSetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.ttl));
    }

    @Test
    public void test_MapSetCodec_encodeResponse() {
        int fileClientMessageIndex = 85;
        ClientMessage encoded = MapSetCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapLockCodec_decodeRequest() {
        int fileClientMessageIndex = 86;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapLockCodec.RequestParameters parameters = MapLockCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.ttl));
        assertTrue(isEqual(aLong, parameters.referenceId));
    }

    @Test
    public void test_MapLockCodec_encodeResponse() {
        int fileClientMessageIndex = 87;
        ClientMessage encoded = MapLockCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapTryLockCodec_decodeRequest() {
        int fileClientMessageIndex = 88;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapTryLockCodec.RequestParameters parameters = MapTryLockCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.lease));
        assertTrue(isEqual(aLong, parameters.timeout));
        assertTrue(isEqual(aLong, parameters.referenceId));
    }

    @Test
    public void test_MapTryLockCodec_encodeResponse() {
        int fileClientMessageIndex = 89;
        ClientMessage encoded = MapTryLockCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapIsLockedCodec_decodeRequest() {
        int fileClientMessageIndex = 90;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapIsLockedCodec.RequestParameters parameters = MapIsLockedCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_MapIsLockedCodec_encodeResponse() {
        int fileClientMessageIndex = 91;
        ClientMessage encoded = MapIsLockedCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapUnlockCodec_decodeRequest() {
        int fileClientMessageIndex = 92;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapUnlockCodec.RequestParameters parameters = MapUnlockCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.referenceId));
    }

    @Test
    public void test_MapUnlockCodec_encodeResponse() {
        int fileClientMessageIndex = 93;
        ClientMessage encoded = MapUnlockCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddInterceptorCodec_decodeRequest() {
        int fileClientMessageIndex = 94;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapAddInterceptorCodec.RequestParameters parameters = MapAddInterceptorCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.interceptor));
    }

    @Test
    public void test_MapAddInterceptorCodec_encodeResponse() {
        int fileClientMessageIndex = 95;
        ClientMessage encoded = MapAddInterceptorCodec.encodeResponse(aString);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapRemoveInterceptorCodec_decodeRequest() {
        int fileClientMessageIndex = 96;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapRemoveInterceptorCodec.RequestParameters parameters = MapRemoveInterceptorCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aString, parameters.id));
    }

    @Test
    public void test_MapRemoveInterceptorCodec_encodeResponse() {
        int fileClientMessageIndex = 97;
        ClientMessage encoded = MapRemoveInterceptorCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddEntryListenerToKeyWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 98;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapAddEntryListenerToKeyWithPredicateCodec.RequestParameters parameters = MapAddEntryListenerToKeyWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.predicate));
        assertTrue(isEqual(aBoolean, parameters.includeValue));
        assertTrue(isEqual(anInt, parameters.listenerFlags));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_MapAddEntryListenerToKeyWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 99;
        ClientMessage encoded = MapAddEntryListenerToKeyWithPredicateCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddEntryListenerToKeyWithPredicateCodec_encodeEntryEvent() {
        int fileClientMessageIndex = 100;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = MapAddEntryListenerToKeyWithPredicateCodec.encodeEntryEvent(null, null, null, null, anInt, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddEntryListenerWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 101;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapAddEntryListenerWithPredicateCodec.RequestParameters parameters = MapAddEntryListenerWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.predicate));
        assertTrue(isEqual(aBoolean, parameters.includeValue));
        assertTrue(isEqual(anInt, parameters.listenerFlags));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_MapAddEntryListenerWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 102;
        ClientMessage encoded = MapAddEntryListenerWithPredicateCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddEntryListenerWithPredicateCodec_encodeEntryEvent() {
        int fileClientMessageIndex = 103;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = MapAddEntryListenerWithPredicateCodec.encodeEntryEvent(null, null, null, null, anInt, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddEntryListenerToKeyCodec_decodeRequest() {
        int fileClientMessageIndex = 104;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapAddEntryListenerToKeyCodec.RequestParameters parameters = MapAddEntryListenerToKeyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aBoolean, parameters.includeValue));
        assertTrue(isEqual(anInt, parameters.listenerFlags));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_MapAddEntryListenerToKeyCodec_encodeResponse() {
        int fileClientMessageIndex = 105;
        ClientMessage encoded = MapAddEntryListenerToKeyCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddEntryListenerToKeyCodec_encodeEntryEvent() {
        int fileClientMessageIndex = 106;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = MapAddEntryListenerToKeyCodec.encodeEntryEvent(null, null, null, null, anInt, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddEntryListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 107;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapAddEntryListenerCodec.RequestParameters parameters = MapAddEntryListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.includeValue));
        assertTrue(isEqual(anInt, parameters.listenerFlags));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_MapAddEntryListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 108;
        ClientMessage encoded = MapAddEntryListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddEntryListenerCodec_encodeEntryEvent() {
        int fileClientMessageIndex = 109;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = MapAddEntryListenerCodec.encodeEntryEvent(null, null, null, null, anInt, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapRemoveEntryListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 110;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapRemoveEntryListenerCodec.RequestParameters parameters = MapRemoveEntryListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_MapRemoveEntryListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 111;
        ClientMessage encoded = MapRemoveEntryListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddPartitionLostListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 112;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapAddPartitionLostListenerCodec.RequestParameters parameters = MapAddPartitionLostListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_MapAddPartitionLostListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 113;
        ClientMessage encoded = MapAddPartitionLostListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddPartitionLostListenerCodec_encodeMapPartitionLostEvent() {
        int fileClientMessageIndex = 114;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = MapAddPartitionLostListenerCodec.encodeMapPartitionLostEvent(anInt, aUUID);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapRemovePartitionLostListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 115;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapRemovePartitionLostListenerCodec.RequestParameters parameters = MapRemovePartitionLostListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_MapRemovePartitionLostListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 116;
        ClientMessage encoded = MapRemovePartitionLostListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapGetEntryViewCodec_decodeRequest() {
        int fileClientMessageIndex = 117;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapGetEntryViewCodec.RequestParameters parameters = MapGetEntryViewCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MapGetEntryViewCodec_encodeResponse() {
        int fileClientMessageIndex = 118;
        ClientMessage encoded = MapGetEntryViewCodec.encodeResponse(null, aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapEvictCodec_decodeRequest() {
        int fileClientMessageIndex = 119;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapEvictCodec.RequestParameters parameters = MapEvictCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MapEvictCodec_encodeResponse() {
        int fileClientMessageIndex = 120;
        ClientMessage encoded = MapEvictCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapEvictAllCodec_decodeRequest() {
        int fileClientMessageIndex = 121;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MapEvictAllCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MapEvictAllCodec_encodeResponse() {
        int fileClientMessageIndex = 122;
        ClientMessage encoded = MapEvictAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapLoadAllCodec_decodeRequest() {
        int fileClientMessageIndex = 123;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapLoadAllCodec.RequestParameters parameters = MapLoadAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.replaceExistingValues));
    }

    @Test
    public void test_MapLoadAllCodec_encodeResponse() {
        int fileClientMessageIndex = 124;
        ClientMessage encoded = MapLoadAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapLoadGivenKeysCodec_decodeRequest() {
        int fileClientMessageIndex = 125;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapLoadGivenKeysCodec.RequestParameters parameters = MapLoadGivenKeysCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.keys));
        assertTrue(isEqual(aBoolean, parameters.replaceExistingValues));
    }

    @Test
    public void test_MapLoadGivenKeysCodec_encodeResponse() {
        int fileClientMessageIndex = 126;
        ClientMessage encoded = MapLoadGivenKeysCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapKeySetCodec_decodeRequest() {
        int fileClientMessageIndex = 127;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MapKeySetCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MapKeySetCodec_encodeResponse() {
        int fileClientMessageIndex = 128;
        ClientMessage encoded = MapKeySetCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapGetAllCodec_decodeRequest() {
        int fileClientMessageIndex = 129;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapGetAllCodec.RequestParameters parameters = MapGetAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.keys));
    }

    @Test
    public void test_MapGetAllCodec_encodeResponse() {
        int fileClientMessageIndex = 130;
        ClientMessage encoded = MapGetAllCodec.encodeResponse(aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapValuesCodec_decodeRequest() {
        int fileClientMessageIndex = 131;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MapValuesCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MapValuesCodec_encodeResponse() {
        int fileClientMessageIndex = 132;
        ClientMessage encoded = MapValuesCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapEntrySetCodec_decodeRequest() {
        int fileClientMessageIndex = 133;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MapEntrySetCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MapEntrySetCodec_encodeResponse() {
        int fileClientMessageIndex = 134;
        ClientMessage encoded = MapEntrySetCodec.encodeResponse(aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapKeySetWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 135;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapKeySetWithPredicateCodec.RequestParameters parameters = MapKeySetWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.predicate));
    }

    @Test
    public void test_MapKeySetWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 136;
        ClientMessage encoded = MapKeySetWithPredicateCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapValuesWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 137;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapValuesWithPredicateCodec.RequestParameters parameters = MapValuesWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.predicate));
    }

    @Test
    public void test_MapValuesWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 138;
        ClientMessage encoded = MapValuesWithPredicateCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapEntriesWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 139;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapEntriesWithPredicateCodec.RequestParameters parameters = MapEntriesWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.predicate));
    }

    @Test
    public void test_MapEntriesWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 140;
        ClientMessage encoded = MapEntriesWithPredicateCodec.encodeResponse(aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddIndexCodec_decodeRequest() {
        int fileClientMessageIndex = 141;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapAddIndexCodec.RequestParameters parameters = MapAddIndexCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anIndexConfig, parameters.indexConfig));
    }

    @Test
    public void test_MapAddIndexCodec_encodeResponse() {
        int fileClientMessageIndex = 142;
        ClientMessage encoded = MapAddIndexCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 143;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MapSizeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MapSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 144;
        ClientMessage encoded = MapSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapIsEmptyCodec_decodeRequest() {
        int fileClientMessageIndex = 145;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MapIsEmptyCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MapIsEmptyCodec_encodeResponse() {
        int fileClientMessageIndex = 146;
        ClientMessage encoded = MapIsEmptyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapPutAllCodec_decodeRequest() {
        int fileClientMessageIndex = 147;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapPutAllCodec.RequestParameters parameters = MapPutAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfDataToData, parameters.entries));
        assertTrue(parameters.isTriggerMapLoaderExists);
        assertTrue(isEqual(aBoolean, parameters.triggerMapLoader));
    }

    @Test
    public void test_MapPutAllCodec_encodeResponse() {
        int fileClientMessageIndex = 148;
        ClientMessage encoded = MapPutAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapClearCodec_decodeRequest() {
        int fileClientMessageIndex = 149;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MapClearCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MapClearCodec_encodeResponse() {
        int fileClientMessageIndex = 150;
        ClientMessage encoded = MapClearCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapExecuteOnKeyCodec_decodeRequest() {
        int fileClientMessageIndex = 151;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapExecuteOnKeyCodec.RequestParameters parameters = MapExecuteOnKeyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.entryProcessor));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MapExecuteOnKeyCodec_encodeResponse() {
        int fileClientMessageIndex = 152;
        ClientMessage encoded = MapExecuteOnKeyCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapSubmitToKeyCodec_decodeRequest() {
        int fileClientMessageIndex = 153;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapSubmitToKeyCodec.RequestParameters parameters = MapSubmitToKeyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.entryProcessor));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MapSubmitToKeyCodec_encodeResponse() {
        int fileClientMessageIndex = 154;
        ClientMessage encoded = MapSubmitToKeyCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapExecuteOnAllKeysCodec_decodeRequest() {
        int fileClientMessageIndex = 155;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapExecuteOnAllKeysCodec.RequestParameters parameters = MapExecuteOnAllKeysCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.entryProcessor));
    }

    @Test
    public void test_MapExecuteOnAllKeysCodec_encodeResponse() {
        int fileClientMessageIndex = 156;
        ClientMessage encoded = MapExecuteOnAllKeysCodec.encodeResponse(aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapExecuteWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 157;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapExecuteWithPredicateCodec.RequestParameters parameters = MapExecuteWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.entryProcessor));
        assertTrue(isEqual(aData, parameters.predicate));
    }

    @Test
    public void test_MapExecuteWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 158;
        ClientMessage encoded = MapExecuteWithPredicateCodec.encodeResponse(aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapExecuteOnKeysCodec_decodeRequest() {
        int fileClientMessageIndex = 159;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapExecuteOnKeysCodec.RequestParameters parameters = MapExecuteOnKeysCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.entryProcessor));
        assertTrue(isEqual(aListOfData, parameters.keys));
    }

    @Test
    public void test_MapExecuteOnKeysCodec_encodeResponse() {
        int fileClientMessageIndex = 160;
        ClientMessage encoded = MapExecuteOnKeysCodec.encodeResponse(aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapForceUnlockCodec_decodeRequest() {
        int fileClientMessageIndex = 161;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapForceUnlockCodec.RequestParameters parameters = MapForceUnlockCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.referenceId));
    }

    @Test
    public void test_MapForceUnlockCodec_encodeResponse() {
        int fileClientMessageIndex = 162;
        ClientMessage encoded = MapForceUnlockCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapKeySetWithPagingPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 163;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapKeySetWithPagingPredicateCodec.RequestParameters parameters = MapKeySetWithPagingPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aPagingPredicateHolder, parameters.predicate));
    }

    @Test
    public void test_MapKeySetWithPagingPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 164;
        ClientMessage encoded = MapKeySetWithPagingPredicateCodec.encodeResponse(aListOfData, anAnchorDataListHolder);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapValuesWithPagingPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 165;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapValuesWithPagingPredicateCodec.RequestParameters parameters = MapValuesWithPagingPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aPagingPredicateHolder, parameters.predicate));
    }

    @Test
    public void test_MapValuesWithPagingPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 166;
        ClientMessage encoded = MapValuesWithPagingPredicateCodec.encodeResponse(aListOfData, anAnchorDataListHolder);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapEntriesWithPagingPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 167;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapEntriesWithPagingPredicateCodec.RequestParameters parameters = MapEntriesWithPagingPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aPagingPredicateHolder, parameters.predicate));
    }

    @Test
    public void test_MapEntriesWithPagingPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 168;
        ClientMessage encoded = MapEntriesWithPagingPredicateCodec.encodeResponse(aListOfDataToData, anAnchorDataListHolder);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapFetchKeysCodec_decodeRequest() {
        int fileClientMessageIndex = 169;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapFetchKeysCodec.RequestParameters parameters = MapFetchKeysCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfIntegerToInteger, parameters.iterationPointers));
        assertTrue(isEqual(anInt, parameters.batch));
    }

    @Test
    public void test_MapFetchKeysCodec_encodeResponse() {
        int fileClientMessageIndex = 170;
        ClientMessage encoded = MapFetchKeysCodec.encodeResponse(aListOfIntegerToInteger, aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapFetchEntriesCodec_decodeRequest() {
        int fileClientMessageIndex = 171;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapFetchEntriesCodec.RequestParameters parameters = MapFetchEntriesCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfIntegerToInteger, parameters.iterationPointers));
        assertTrue(isEqual(anInt, parameters.batch));
    }

    @Test
    public void test_MapFetchEntriesCodec_encodeResponse() {
        int fileClientMessageIndex = 172;
        ClientMessage encoded = MapFetchEntriesCodec.encodeResponse(aListOfIntegerToInteger, aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAggregateCodec_decodeRequest() {
        int fileClientMessageIndex = 173;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapAggregateCodec.RequestParameters parameters = MapAggregateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.aggregator));
    }

    @Test
    public void test_MapAggregateCodec_encodeResponse() {
        int fileClientMessageIndex = 174;
        ClientMessage encoded = MapAggregateCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAggregateWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 175;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapAggregateWithPredicateCodec.RequestParameters parameters = MapAggregateWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.aggregator));
        assertTrue(isEqual(aData, parameters.predicate));
    }

    @Test
    public void test_MapAggregateWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 176;
        ClientMessage encoded = MapAggregateWithPredicateCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapProjectCodec_decodeRequest() {
        int fileClientMessageIndex = 177;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapProjectCodec.RequestParameters parameters = MapProjectCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.projection));
    }

    @Test
    public void test_MapProjectCodec_encodeResponse() {
        int fileClientMessageIndex = 178;
        ClientMessage encoded = MapProjectCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapProjectWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 179;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapProjectWithPredicateCodec.RequestParameters parameters = MapProjectWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.projection));
        assertTrue(isEqual(aData, parameters.predicate));
    }

    @Test
    public void test_MapProjectWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 180;
        ClientMessage encoded = MapProjectWithPredicateCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapFetchNearCacheInvalidationMetadataCodec_decodeRequest() {
        int fileClientMessageIndex = 181;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapFetchNearCacheInvalidationMetadataCodec.RequestParameters parameters = MapFetchNearCacheInvalidationMetadataCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aListOfStrings, parameters.names));
        assertTrue(isEqual(aUUID, parameters.uuid));
    }

    @Test
    public void test_MapFetchNearCacheInvalidationMetadataCodec_encodeResponse() {
        int fileClientMessageIndex = 182;
        ClientMessage encoded = MapFetchNearCacheInvalidationMetadataCodec.encodeResponse(aListOfStringToListOfIntegerToLong, aListOfIntegerToUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapRemoveAllCodec_decodeRequest() {
        int fileClientMessageIndex = 183;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapRemoveAllCodec.RequestParameters parameters = MapRemoveAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.predicate));
    }

    @Test
    public void test_MapRemoveAllCodec_encodeResponse() {
        int fileClientMessageIndex = 184;
        ClientMessage encoded = MapRemoveAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddNearCacheInvalidationListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 185;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapAddNearCacheInvalidationListenerCodec.RequestParameters parameters = MapAddNearCacheInvalidationListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.listenerFlags));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_MapAddNearCacheInvalidationListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 186;
        ClientMessage encoded = MapAddNearCacheInvalidationListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddNearCacheInvalidationListenerCodec_encodeIMapInvalidationEvent() {
        int fileClientMessageIndex = 187;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = MapAddNearCacheInvalidationListenerCodec.encodeIMapInvalidationEvent(null, aUUID, aUUID, aLong);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapAddNearCacheInvalidationListenerCodec_encodeIMapBatchInvalidationEvent() {
        int fileClientMessageIndex = 188;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = MapAddNearCacheInvalidationListenerCodec.encodeIMapBatchInvalidationEvent(aListOfData, aListOfUUIDs, aListOfUUIDs, aListOfLongs);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapFetchWithQueryCodec_decodeRequest() {
        int fileClientMessageIndex = 189;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapFetchWithQueryCodec.RequestParameters parameters = MapFetchWithQueryCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfIntegerToInteger, parameters.iterationPointers));
        assertTrue(isEqual(anInt, parameters.batch));
        assertTrue(isEqual(aData, parameters.projection));
        assertTrue(isEqual(aData, parameters.predicate));
    }

    @Test
    public void test_MapFetchWithQueryCodec_encodeResponse() {
        int fileClientMessageIndex = 190;
        ClientMessage encoded = MapFetchWithQueryCodec.encodeResponse(aListOfData, aListOfIntegerToInteger);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapEventJournalSubscribeCodec_decodeRequest() {
        int fileClientMessageIndex = 191;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MapEventJournalSubscribeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MapEventJournalSubscribeCodec_encodeResponse() {
        int fileClientMessageIndex = 192;
        ClientMessage encoded = MapEventJournalSubscribeCodec.encodeResponse(aLong, aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapEventJournalReadCodec_decodeRequest() {
        int fileClientMessageIndex = 193;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapEventJournalReadCodec.RequestParameters parameters = MapEventJournalReadCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.startSequence));
        assertTrue(isEqual(anInt, parameters.minSize));
        assertTrue(isEqual(anInt, parameters.maxSize));
        assertTrue(isEqual(null, parameters.predicate));
        assertTrue(isEqual(null, parameters.projection));
    }

    @Test
    public void test_MapEventJournalReadCodec_encodeResponse() {
        int fileClientMessageIndex = 194;
        ClientMessage encoded = MapEventJournalReadCodec.encodeResponse(anInt, aListOfData, null, aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapSetTtlCodec_decodeRequest() {
        int fileClientMessageIndex = 195;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapSetTtlCodec.RequestParameters parameters = MapSetTtlCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.ttl));
    }

    @Test
    public void test_MapSetTtlCodec_encodeResponse() {
        int fileClientMessageIndex = 196;
        ClientMessage encoded = MapSetTtlCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapPutWithMaxIdleCodec_decodeRequest() {
        int fileClientMessageIndex = 197;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapPutWithMaxIdleCodec.RequestParameters parameters = MapPutWithMaxIdleCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.ttl));
        assertTrue(isEqual(aLong, parameters.maxIdle));
    }

    @Test
    public void test_MapPutWithMaxIdleCodec_encodeResponse() {
        int fileClientMessageIndex = 198;
        ClientMessage encoded = MapPutWithMaxIdleCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapPutTransientWithMaxIdleCodec_decodeRequest() {
        int fileClientMessageIndex = 199;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapPutTransientWithMaxIdleCodec.RequestParameters parameters = MapPutTransientWithMaxIdleCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.ttl));
        assertTrue(isEqual(aLong, parameters.maxIdle));
    }

    @Test
    public void test_MapPutTransientWithMaxIdleCodec_encodeResponse() {
        int fileClientMessageIndex = 200;
        ClientMessage encoded = MapPutTransientWithMaxIdleCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapPutIfAbsentWithMaxIdleCodec_decodeRequest() {
        int fileClientMessageIndex = 201;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapPutIfAbsentWithMaxIdleCodec.RequestParameters parameters = MapPutIfAbsentWithMaxIdleCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.ttl));
        assertTrue(isEqual(aLong, parameters.maxIdle));
    }

    @Test
    public void test_MapPutIfAbsentWithMaxIdleCodec_encodeResponse() {
        int fileClientMessageIndex = 202;
        ClientMessage encoded = MapPutIfAbsentWithMaxIdleCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapSetWithMaxIdleCodec_decodeRequest() {
        int fileClientMessageIndex = 203;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapSetWithMaxIdleCodec.RequestParameters parameters = MapSetWithMaxIdleCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.ttl));
        assertTrue(isEqual(aLong, parameters.maxIdle));
    }

    @Test
    public void test_MapSetWithMaxIdleCodec_encodeResponse() {
        int fileClientMessageIndex = 204;
        ClientMessage encoded = MapSetWithMaxIdleCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapReplaceAllCodec_decodeRequest() {
        int fileClientMessageIndex = 205;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapReplaceAllCodec.RequestParameters parameters = MapReplaceAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.function));
    }

    @Test
    public void test_MapReplaceAllCodec_encodeResponse() {
        int fileClientMessageIndex = 206;
        ClientMessage encoded = MapReplaceAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MapPutAllWithMetadataCodec_decodeRequest() {
        int fileClientMessageIndex = 207;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MapPutAllWithMetadataCodec.RequestParameters parameters = MapPutAllWithMetadataCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfSimpleEntryViews, parameters.entries));
    }

    @Test
    public void test_MapPutAllWithMetadataCodec_encodeResponse() {
        int fileClientMessageIndex = 208;
        ClientMessage encoded = MapPutAllWithMetadataCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapPutCodec_decodeRequest() {
        int fileClientMessageIndex = 209;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapPutCodec.RequestParameters parameters = MultiMapPutCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MultiMapPutCodec_encodeResponse() {
        int fileClientMessageIndex = 210;
        ClientMessage encoded = MultiMapPutCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapGetCodec_decodeRequest() {
        int fileClientMessageIndex = 211;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapGetCodec.RequestParameters parameters = MultiMapGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MultiMapGetCodec_encodeResponse() {
        int fileClientMessageIndex = 212;
        ClientMessage encoded = MultiMapGetCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 213;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapRemoveCodec.RequestParameters parameters = MultiMapRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MultiMapRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 214;
        ClientMessage encoded = MultiMapRemoveCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapKeySetCodec_decodeRequest() {
        int fileClientMessageIndex = 215;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MultiMapKeySetCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MultiMapKeySetCodec_encodeResponse() {
        int fileClientMessageIndex = 216;
        ClientMessage encoded = MultiMapKeySetCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapValuesCodec_decodeRequest() {
        int fileClientMessageIndex = 217;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MultiMapValuesCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MultiMapValuesCodec_encodeResponse() {
        int fileClientMessageIndex = 218;
        ClientMessage encoded = MultiMapValuesCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapEntrySetCodec_decodeRequest() {
        int fileClientMessageIndex = 219;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MultiMapEntrySetCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MultiMapEntrySetCodec_encodeResponse() {
        int fileClientMessageIndex = 220;
        ClientMessage encoded = MultiMapEntrySetCodec.encodeResponse(aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapContainsKeyCodec_decodeRequest() {
        int fileClientMessageIndex = 221;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapContainsKeyCodec.RequestParameters parameters = MultiMapContainsKeyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MultiMapContainsKeyCodec_encodeResponse() {
        int fileClientMessageIndex = 222;
        ClientMessage encoded = MultiMapContainsKeyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapContainsValueCodec_decodeRequest() {
        int fileClientMessageIndex = 223;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapContainsValueCodec.RequestParameters parameters = MultiMapContainsValueCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_MultiMapContainsValueCodec_encodeResponse() {
        int fileClientMessageIndex = 224;
        ClientMessage encoded = MultiMapContainsValueCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapContainsEntryCodec_decodeRequest() {
        int fileClientMessageIndex = 225;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapContainsEntryCodec.RequestParameters parameters = MultiMapContainsEntryCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MultiMapContainsEntryCodec_encodeResponse() {
        int fileClientMessageIndex = 226;
        ClientMessage encoded = MultiMapContainsEntryCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 227;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MultiMapSizeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MultiMapSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 228;
        ClientMessage encoded = MultiMapSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapClearCodec_decodeRequest() {
        int fileClientMessageIndex = 229;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MultiMapClearCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MultiMapClearCodec_encodeResponse() {
        int fileClientMessageIndex = 230;
        ClientMessage encoded = MultiMapClearCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapValueCountCodec_decodeRequest() {
        int fileClientMessageIndex = 231;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapValueCountCodec.RequestParameters parameters = MultiMapValueCountCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MultiMapValueCountCodec_encodeResponse() {
        int fileClientMessageIndex = 232;
        ClientMessage encoded = MultiMapValueCountCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapAddEntryListenerToKeyCodec_decodeRequest() {
        int fileClientMessageIndex = 233;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapAddEntryListenerToKeyCodec.RequestParameters parameters = MultiMapAddEntryListenerToKeyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aBoolean, parameters.includeValue));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_MultiMapAddEntryListenerToKeyCodec_encodeResponse() {
        int fileClientMessageIndex = 234;
        ClientMessage encoded = MultiMapAddEntryListenerToKeyCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapAddEntryListenerToKeyCodec_encodeEntryEvent() {
        int fileClientMessageIndex = 235;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = MultiMapAddEntryListenerToKeyCodec.encodeEntryEvent(null, null, null, null, anInt, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapAddEntryListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 236;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapAddEntryListenerCodec.RequestParameters parameters = MultiMapAddEntryListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.includeValue));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_MultiMapAddEntryListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 237;
        ClientMessage encoded = MultiMapAddEntryListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapAddEntryListenerCodec_encodeEntryEvent() {
        int fileClientMessageIndex = 238;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = MultiMapAddEntryListenerCodec.encodeEntryEvent(null, null, null, null, anInt, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapRemoveEntryListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 239;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapRemoveEntryListenerCodec.RequestParameters parameters = MultiMapRemoveEntryListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_MultiMapRemoveEntryListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 240;
        ClientMessage encoded = MultiMapRemoveEntryListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapLockCodec_decodeRequest() {
        int fileClientMessageIndex = 241;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapLockCodec.RequestParameters parameters = MultiMapLockCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.ttl));
        assertTrue(isEqual(aLong, parameters.referenceId));
    }

    @Test
    public void test_MultiMapLockCodec_encodeResponse() {
        int fileClientMessageIndex = 242;
        ClientMessage encoded = MultiMapLockCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapTryLockCodec_decodeRequest() {
        int fileClientMessageIndex = 243;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapTryLockCodec.RequestParameters parameters = MultiMapTryLockCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.lease));
        assertTrue(isEqual(aLong, parameters.timeout));
        assertTrue(isEqual(aLong, parameters.referenceId));
    }

    @Test
    public void test_MultiMapTryLockCodec_encodeResponse() {
        int fileClientMessageIndex = 244;
        ClientMessage encoded = MultiMapTryLockCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapIsLockedCodec_decodeRequest() {
        int fileClientMessageIndex = 245;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapIsLockedCodec.RequestParameters parameters = MultiMapIsLockedCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_MultiMapIsLockedCodec_encodeResponse() {
        int fileClientMessageIndex = 246;
        ClientMessage encoded = MultiMapIsLockedCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapUnlockCodec_decodeRequest() {
        int fileClientMessageIndex = 247;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapUnlockCodec.RequestParameters parameters = MultiMapUnlockCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.referenceId));
    }

    @Test
    public void test_MultiMapUnlockCodec_encodeResponse() {
        int fileClientMessageIndex = 248;
        ClientMessage encoded = MultiMapUnlockCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapForceUnlockCodec_decodeRequest() {
        int fileClientMessageIndex = 249;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapForceUnlockCodec.RequestParameters parameters = MultiMapForceUnlockCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.referenceId));
    }

    @Test
    public void test_MultiMapForceUnlockCodec_encodeResponse() {
        int fileClientMessageIndex = 250;
        ClientMessage encoded = MultiMapForceUnlockCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapRemoveEntryCodec_decodeRequest() {
        int fileClientMessageIndex = 251;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapRemoveEntryCodec.RequestParameters parameters = MultiMapRemoveEntryCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MultiMapRemoveEntryCodec_encodeResponse() {
        int fileClientMessageIndex = 252;
        ClientMessage encoded = MultiMapRemoveEntryCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapDeleteCodec_decodeRequest() {
        int fileClientMessageIndex = 253;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapDeleteCodec.RequestParameters parameters = MultiMapDeleteCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_MultiMapDeleteCodec_encodeResponse() {
        int fileClientMessageIndex = 254;
        ClientMessage encoded = MultiMapDeleteCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MultiMapPutAllCodec_decodeRequest() {
        int fileClientMessageIndex = 255;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MultiMapPutAllCodec.RequestParameters parameters = MultiMapPutAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfDataToListOfData, parameters.entries));
    }

    @Test
    public void test_MultiMapPutAllCodec_encodeResponse() {
        int fileClientMessageIndex = 256;
        ClientMessage encoded = MultiMapPutAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueOfferCodec_decodeRequest() {
        int fileClientMessageIndex = 257;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueueOfferCodec.RequestParameters parameters = QueueOfferCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.timeoutMillis));
    }

    @Test
    public void test_QueueOfferCodec_encodeResponse() {
        int fileClientMessageIndex = 258;
        ClientMessage encoded = QueueOfferCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueuePutCodec_decodeRequest() {
        int fileClientMessageIndex = 259;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueuePutCodec.RequestParameters parameters = QueuePutCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_QueuePutCodec_encodeResponse() {
        int fileClientMessageIndex = 260;
        ClientMessage encoded = QueuePutCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 261;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, QueueSizeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_QueueSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 262;
        ClientMessage encoded = QueueSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 263;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueueRemoveCodec.RequestParameters parameters = QueueRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_QueueRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 264;
        ClientMessage encoded = QueueRemoveCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueuePollCodec_decodeRequest() {
        int fileClientMessageIndex = 265;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueuePollCodec.RequestParameters parameters = QueuePollCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.timeoutMillis));
    }

    @Test
    public void test_QueuePollCodec_encodeResponse() {
        int fileClientMessageIndex = 266;
        ClientMessage encoded = QueuePollCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueTakeCodec_decodeRequest() {
        int fileClientMessageIndex = 267;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, QueueTakeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_QueueTakeCodec_encodeResponse() {
        int fileClientMessageIndex = 268;
        ClientMessage encoded = QueueTakeCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueuePeekCodec_decodeRequest() {
        int fileClientMessageIndex = 269;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, QueuePeekCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_QueuePeekCodec_encodeResponse() {
        int fileClientMessageIndex = 270;
        ClientMessage encoded = QueuePeekCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueIteratorCodec_decodeRequest() {
        int fileClientMessageIndex = 271;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, QueueIteratorCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_QueueIteratorCodec_encodeResponse() {
        int fileClientMessageIndex = 272;
        ClientMessage encoded = QueueIteratorCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueDrainToCodec_decodeRequest() {
        int fileClientMessageIndex = 273;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, QueueDrainToCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_QueueDrainToCodec_encodeResponse() {
        int fileClientMessageIndex = 274;
        ClientMessage encoded = QueueDrainToCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueDrainToMaxSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 275;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueueDrainToMaxSizeCodec.RequestParameters parameters = QueueDrainToMaxSizeCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.maxSize));
    }

    @Test
    public void test_QueueDrainToMaxSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 276;
        ClientMessage encoded = QueueDrainToMaxSizeCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueContainsCodec_decodeRequest() {
        int fileClientMessageIndex = 277;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueueContainsCodec.RequestParameters parameters = QueueContainsCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_QueueContainsCodec_encodeResponse() {
        int fileClientMessageIndex = 278;
        ClientMessage encoded = QueueContainsCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueContainsAllCodec_decodeRequest() {
        int fileClientMessageIndex = 279;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueueContainsAllCodec.RequestParameters parameters = QueueContainsAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.dataList));
    }

    @Test
    public void test_QueueContainsAllCodec_encodeResponse() {
        int fileClientMessageIndex = 280;
        ClientMessage encoded = QueueContainsAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueCompareAndRemoveAllCodec_decodeRequest() {
        int fileClientMessageIndex = 281;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueueCompareAndRemoveAllCodec.RequestParameters parameters = QueueCompareAndRemoveAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.dataList));
    }

    @Test
    public void test_QueueCompareAndRemoveAllCodec_encodeResponse() {
        int fileClientMessageIndex = 282;
        ClientMessage encoded = QueueCompareAndRemoveAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueCompareAndRetainAllCodec_decodeRequest() {
        int fileClientMessageIndex = 283;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueueCompareAndRetainAllCodec.RequestParameters parameters = QueueCompareAndRetainAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.dataList));
    }

    @Test
    public void test_QueueCompareAndRetainAllCodec_encodeResponse() {
        int fileClientMessageIndex = 284;
        ClientMessage encoded = QueueCompareAndRetainAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueClearCodec_decodeRequest() {
        int fileClientMessageIndex = 285;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, QueueClearCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_QueueClearCodec_encodeResponse() {
        int fileClientMessageIndex = 286;
        ClientMessage encoded = QueueClearCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueAddAllCodec_decodeRequest() {
        int fileClientMessageIndex = 287;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueueAddAllCodec.RequestParameters parameters = QueueAddAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.dataList));
    }

    @Test
    public void test_QueueAddAllCodec_encodeResponse() {
        int fileClientMessageIndex = 288;
        ClientMessage encoded = QueueAddAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueAddListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 289;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueueAddListenerCodec.RequestParameters parameters = QueueAddListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.includeValue));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_QueueAddListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 290;
        ClientMessage encoded = QueueAddListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueAddListenerCodec_encodeItemEvent() {
        int fileClientMessageIndex = 291;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = QueueAddListenerCodec.encodeItemEvent(null, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueRemoveListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 292;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        QueueRemoveListenerCodec.RequestParameters parameters = QueueRemoveListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_QueueRemoveListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 293;
        ClientMessage encoded = QueueRemoveListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueRemainingCapacityCodec_decodeRequest() {
        int fileClientMessageIndex = 294;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, QueueRemainingCapacityCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_QueueRemainingCapacityCodec_encodeResponse() {
        int fileClientMessageIndex = 295;
        ClientMessage encoded = QueueRemainingCapacityCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_QueueIsEmptyCodec_decodeRequest() {
        int fileClientMessageIndex = 296;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, QueueIsEmptyCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_QueueIsEmptyCodec_encodeResponse() {
        int fileClientMessageIndex = 297;
        ClientMessage encoded = QueueIsEmptyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TopicPublishCodec_decodeRequest() {
        int fileClientMessageIndex = 298;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TopicPublishCodec.RequestParameters parameters = TopicPublishCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.message));
    }

    @Test
    public void test_TopicPublishCodec_encodeResponse() {
        int fileClientMessageIndex = 299;
        ClientMessage encoded = TopicPublishCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TopicAddMessageListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 300;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TopicAddMessageListenerCodec.RequestParameters parameters = TopicAddMessageListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_TopicAddMessageListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 301;
        ClientMessage encoded = TopicAddMessageListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TopicAddMessageListenerCodec_encodeTopicEvent() {
        int fileClientMessageIndex = 302;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = TopicAddMessageListenerCodec.encodeTopicEvent(aData, aLong, aUUID);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TopicRemoveMessageListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 303;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TopicRemoveMessageListenerCodec.RequestParameters parameters = TopicRemoveMessageListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_TopicRemoveMessageListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 304;
        ClientMessage encoded = TopicRemoveMessageListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TopicPublishAllCodec_decodeRequest() {
        int fileClientMessageIndex = 305;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TopicPublishAllCodec.RequestParameters parameters = TopicPublishAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.messages));
    }

    @Test
    public void test_TopicPublishAllCodec_encodeResponse() {
        int fileClientMessageIndex = 306;
        ClientMessage encoded = TopicPublishAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 307;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ListSizeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ListSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 308;
        ClientMessage encoded = ListSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListContainsCodec_decodeRequest() {
        int fileClientMessageIndex = 309;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListContainsCodec.RequestParameters parameters = ListContainsCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_ListContainsCodec_encodeResponse() {
        int fileClientMessageIndex = 310;
        ClientMessage encoded = ListContainsCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListContainsAllCodec_decodeRequest() {
        int fileClientMessageIndex = 311;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListContainsAllCodec.RequestParameters parameters = ListContainsAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.values));
    }

    @Test
    public void test_ListContainsAllCodec_encodeResponse() {
        int fileClientMessageIndex = 312;
        ClientMessage encoded = ListContainsAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListAddCodec_decodeRequest() {
        int fileClientMessageIndex = 313;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListAddCodec.RequestParameters parameters = ListAddCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_ListAddCodec_encodeResponse() {
        int fileClientMessageIndex = 314;
        ClientMessage encoded = ListAddCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 315;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListRemoveCodec.RequestParameters parameters = ListRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_ListRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 316;
        ClientMessage encoded = ListRemoveCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListAddAllCodec_decodeRequest() {
        int fileClientMessageIndex = 317;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListAddAllCodec.RequestParameters parameters = ListAddAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.valueList));
    }

    @Test
    public void test_ListAddAllCodec_encodeResponse() {
        int fileClientMessageIndex = 318;
        ClientMessage encoded = ListAddAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListCompareAndRemoveAllCodec_decodeRequest() {
        int fileClientMessageIndex = 319;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListCompareAndRemoveAllCodec.RequestParameters parameters = ListCompareAndRemoveAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.values));
    }

    @Test
    public void test_ListCompareAndRemoveAllCodec_encodeResponse() {
        int fileClientMessageIndex = 320;
        ClientMessage encoded = ListCompareAndRemoveAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListCompareAndRetainAllCodec_decodeRequest() {
        int fileClientMessageIndex = 321;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListCompareAndRetainAllCodec.RequestParameters parameters = ListCompareAndRetainAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.values));
    }

    @Test
    public void test_ListCompareAndRetainAllCodec_encodeResponse() {
        int fileClientMessageIndex = 322;
        ClientMessage encoded = ListCompareAndRetainAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListClearCodec_decodeRequest() {
        int fileClientMessageIndex = 323;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ListClearCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ListClearCodec_encodeResponse() {
        int fileClientMessageIndex = 324;
        ClientMessage encoded = ListClearCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListGetAllCodec_decodeRequest() {
        int fileClientMessageIndex = 325;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ListGetAllCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ListGetAllCodec_encodeResponse() {
        int fileClientMessageIndex = 326;
        ClientMessage encoded = ListGetAllCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListAddListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 327;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListAddListenerCodec.RequestParameters parameters = ListAddListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.includeValue));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_ListAddListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 328;
        ClientMessage encoded = ListAddListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListAddListenerCodec_encodeItemEvent() {
        int fileClientMessageIndex = 329;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ListAddListenerCodec.encodeItemEvent(null, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListRemoveListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 330;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListRemoveListenerCodec.RequestParameters parameters = ListRemoveListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_ListRemoveListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 331;
        ClientMessage encoded = ListRemoveListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListIsEmptyCodec_decodeRequest() {
        int fileClientMessageIndex = 332;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ListIsEmptyCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ListIsEmptyCodec_encodeResponse() {
        int fileClientMessageIndex = 333;
        ClientMessage encoded = ListIsEmptyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListAddAllWithIndexCodec_decodeRequest() {
        int fileClientMessageIndex = 334;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListAddAllWithIndexCodec.RequestParameters parameters = ListAddAllWithIndexCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.index));
        assertTrue(isEqual(aListOfData, parameters.valueList));
    }

    @Test
    public void test_ListAddAllWithIndexCodec_encodeResponse() {
        int fileClientMessageIndex = 335;
        ClientMessage encoded = ListAddAllWithIndexCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListGetCodec_decodeRequest() {
        int fileClientMessageIndex = 336;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListGetCodec.RequestParameters parameters = ListGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.index));
    }

    @Test
    public void test_ListGetCodec_encodeResponse() {
        int fileClientMessageIndex = 337;
        ClientMessage encoded = ListGetCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListSetCodec_decodeRequest() {
        int fileClientMessageIndex = 338;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListSetCodec.RequestParameters parameters = ListSetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.index));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_ListSetCodec_encodeResponse() {
        int fileClientMessageIndex = 339;
        ClientMessage encoded = ListSetCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListAddWithIndexCodec_decodeRequest() {
        int fileClientMessageIndex = 340;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListAddWithIndexCodec.RequestParameters parameters = ListAddWithIndexCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.index));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_ListAddWithIndexCodec_encodeResponse() {
        int fileClientMessageIndex = 341;
        ClientMessage encoded = ListAddWithIndexCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListRemoveWithIndexCodec_decodeRequest() {
        int fileClientMessageIndex = 342;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListRemoveWithIndexCodec.RequestParameters parameters = ListRemoveWithIndexCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.index));
    }

    @Test
    public void test_ListRemoveWithIndexCodec_encodeResponse() {
        int fileClientMessageIndex = 343;
        ClientMessage encoded = ListRemoveWithIndexCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListLastIndexOfCodec_decodeRequest() {
        int fileClientMessageIndex = 344;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListLastIndexOfCodec.RequestParameters parameters = ListLastIndexOfCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_ListLastIndexOfCodec_encodeResponse() {
        int fileClientMessageIndex = 345;
        ClientMessage encoded = ListLastIndexOfCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListIndexOfCodec_decodeRequest() {
        int fileClientMessageIndex = 346;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListIndexOfCodec.RequestParameters parameters = ListIndexOfCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_ListIndexOfCodec_encodeResponse() {
        int fileClientMessageIndex = 347;
        ClientMessage encoded = ListIndexOfCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListSubCodec_decodeRequest() {
        int fileClientMessageIndex = 348;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListSubCodec.RequestParameters parameters = ListSubCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.from));
        assertTrue(isEqual(anInt, parameters.to));
    }

    @Test
    public void test_ListSubCodec_encodeResponse() {
        int fileClientMessageIndex = 349;
        ClientMessage encoded = ListSubCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListIteratorCodec_decodeRequest() {
        int fileClientMessageIndex = 350;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ListIteratorCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ListIteratorCodec_encodeResponse() {
        int fileClientMessageIndex = 351;
        ClientMessage encoded = ListIteratorCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ListListIteratorCodec_decodeRequest() {
        int fileClientMessageIndex = 352;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ListListIteratorCodec.RequestParameters parameters = ListListIteratorCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.index));
    }

    @Test
    public void test_ListListIteratorCodec_encodeResponse() {
        int fileClientMessageIndex = 353;
        ClientMessage encoded = ListListIteratorCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 354;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, SetSizeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_SetSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 355;
        ClientMessage encoded = SetSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetContainsCodec_decodeRequest() {
        int fileClientMessageIndex = 356;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SetContainsCodec.RequestParameters parameters = SetContainsCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_SetContainsCodec_encodeResponse() {
        int fileClientMessageIndex = 357;
        ClientMessage encoded = SetContainsCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetContainsAllCodec_decodeRequest() {
        int fileClientMessageIndex = 358;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SetContainsAllCodec.RequestParameters parameters = SetContainsAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.items));
    }

    @Test
    public void test_SetContainsAllCodec_encodeResponse() {
        int fileClientMessageIndex = 359;
        ClientMessage encoded = SetContainsAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetAddCodec_decodeRequest() {
        int fileClientMessageIndex = 360;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SetAddCodec.RequestParameters parameters = SetAddCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_SetAddCodec_encodeResponse() {
        int fileClientMessageIndex = 361;
        ClientMessage encoded = SetAddCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 362;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SetRemoveCodec.RequestParameters parameters = SetRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_SetRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 363;
        ClientMessage encoded = SetRemoveCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetAddAllCodec_decodeRequest() {
        int fileClientMessageIndex = 364;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SetAddAllCodec.RequestParameters parameters = SetAddAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.valueList));
    }

    @Test
    public void test_SetAddAllCodec_encodeResponse() {
        int fileClientMessageIndex = 365;
        ClientMessage encoded = SetAddAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetCompareAndRemoveAllCodec_decodeRequest() {
        int fileClientMessageIndex = 366;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SetCompareAndRemoveAllCodec.RequestParameters parameters = SetCompareAndRemoveAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.values));
    }

    @Test
    public void test_SetCompareAndRemoveAllCodec_encodeResponse() {
        int fileClientMessageIndex = 367;
        ClientMessage encoded = SetCompareAndRemoveAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetCompareAndRetainAllCodec_decodeRequest() {
        int fileClientMessageIndex = 368;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SetCompareAndRetainAllCodec.RequestParameters parameters = SetCompareAndRetainAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.values));
    }

    @Test
    public void test_SetCompareAndRetainAllCodec_encodeResponse() {
        int fileClientMessageIndex = 369;
        ClientMessage encoded = SetCompareAndRetainAllCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetClearCodec_decodeRequest() {
        int fileClientMessageIndex = 370;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, SetClearCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_SetClearCodec_encodeResponse() {
        int fileClientMessageIndex = 371;
        ClientMessage encoded = SetClearCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetGetAllCodec_decodeRequest() {
        int fileClientMessageIndex = 372;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, SetGetAllCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_SetGetAllCodec_encodeResponse() {
        int fileClientMessageIndex = 373;
        ClientMessage encoded = SetGetAllCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetAddListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 374;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SetAddListenerCodec.RequestParameters parameters = SetAddListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.includeValue));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_SetAddListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 375;
        ClientMessage encoded = SetAddListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetAddListenerCodec_encodeItemEvent() {
        int fileClientMessageIndex = 376;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = SetAddListenerCodec.encodeItemEvent(null, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetRemoveListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 377;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SetRemoveListenerCodec.RequestParameters parameters = SetRemoveListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_SetRemoveListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 378;
        ClientMessage encoded = SetRemoveListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SetIsEmptyCodec_decodeRequest() {
        int fileClientMessageIndex = 379;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, SetIsEmptyCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_SetIsEmptyCodec_encodeResponse() {
        int fileClientMessageIndex = 380;
        ClientMessage encoded = SetIsEmptyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_FencedLockLockCodec_decodeRequest() {
        int fileClientMessageIndex = 381;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        FencedLockLockCodec.RequestParameters parameters = FencedLockLockCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.sessionId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aUUID, parameters.invocationUid));
    }

    @Test
    public void test_FencedLockLockCodec_encodeResponse() {
        int fileClientMessageIndex = 382;
        ClientMessage encoded = FencedLockLockCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_FencedLockTryLockCodec_decodeRequest() {
        int fileClientMessageIndex = 383;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        FencedLockTryLockCodec.RequestParameters parameters = FencedLockTryLockCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.sessionId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aUUID, parameters.invocationUid));
        assertTrue(isEqual(aLong, parameters.timeoutMs));
    }

    @Test
    public void test_FencedLockTryLockCodec_encodeResponse() {
        int fileClientMessageIndex = 384;
        ClientMessage encoded = FencedLockTryLockCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_FencedLockUnlockCodec_decodeRequest() {
        int fileClientMessageIndex = 385;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        FencedLockUnlockCodec.RequestParameters parameters = FencedLockUnlockCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.sessionId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aUUID, parameters.invocationUid));
    }

    @Test
    public void test_FencedLockUnlockCodec_encodeResponse() {
        int fileClientMessageIndex = 386;
        ClientMessage encoded = FencedLockUnlockCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_FencedLockGetLockOwnershipCodec_decodeRequest() {
        int fileClientMessageIndex = 387;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        FencedLockGetLockOwnershipCodec.RequestParameters parameters = FencedLockGetLockOwnershipCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
    }

    @Test
    public void test_FencedLockGetLockOwnershipCodec_encodeResponse() {
        int fileClientMessageIndex = 388;
        ClientMessage encoded = FencedLockGetLockOwnershipCodec.encodeResponse(aLong, anInt, aLong, aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ExecutorServiceShutdownCodec_decodeRequest() {
        int fileClientMessageIndex = 389;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ExecutorServiceShutdownCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ExecutorServiceShutdownCodec_encodeResponse() {
        int fileClientMessageIndex = 390;
        ClientMessage encoded = ExecutorServiceShutdownCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ExecutorServiceIsShutdownCodec_decodeRequest() {
        int fileClientMessageIndex = 391;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ExecutorServiceIsShutdownCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ExecutorServiceIsShutdownCodec_encodeResponse() {
        int fileClientMessageIndex = 392;
        ClientMessage encoded = ExecutorServiceIsShutdownCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ExecutorServiceCancelOnPartitionCodec_decodeRequest() {
        int fileClientMessageIndex = 393;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ExecutorServiceCancelOnPartitionCodec.RequestParameters parameters = ExecutorServiceCancelOnPartitionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aUUID, parameters.uuid));
        assertTrue(isEqual(aBoolean, parameters.interrupt));
    }

    @Test
    public void test_ExecutorServiceCancelOnPartitionCodec_encodeResponse() {
        int fileClientMessageIndex = 394;
        ClientMessage encoded = ExecutorServiceCancelOnPartitionCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ExecutorServiceCancelOnMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 395;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ExecutorServiceCancelOnMemberCodec.RequestParameters parameters = ExecutorServiceCancelOnMemberCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aUUID, parameters.uuid));
        assertTrue(isEqual(aUUID, parameters.memberUUID));
        assertTrue(isEqual(aBoolean, parameters.interrupt));
    }

    @Test
    public void test_ExecutorServiceCancelOnMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 396;
        ClientMessage encoded = ExecutorServiceCancelOnMemberCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ExecutorServiceSubmitToPartitionCodec_decodeRequest() {
        int fileClientMessageIndex = 397;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ExecutorServiceSubmitToPartitionCodec.RequestParameters parameters = ExecutorServiceSubmitToPartitionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.uuid));
        assertTrue(isEqual(aData, parameters.callable));
    }

    @Test
    public void test_ExecutorServiceSubmitToPartitionCodec_encodeResponse() {
        int fileClientMessageIndex = 398;
        ClientMessage encoded = ExecutorServiceSubmitToPartitionCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ExecutorServiceSubmitToMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 399;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ExecutorServiceSubmitToMemberCodec.RequestParameters parameters = ExecutorServiceSubmitToMemberCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.uuid));
        assertTrue(isEqual(aData, parameters.callable));
        assertTrue(isEqual(aUUID, parameters.memberUUID));
    }

    @Test
    public void test_ExecutorServiceSubmitToMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 400;
        ClientMessage encoded = ExecutorServiceSubmitToMemberCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicLongApplyCodec_decodeRequest() {
        int fileClientMessageIndex = 401;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicLongApplyCodec.RequestParameters parameters = AtomicLongApplyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.function));
    }

    @Test
    public void test_AtomicLongApplyCodec_encodeResponse() {
        int fileClientMessageIndex = 402;
        ClientMessage encoded = AtomicLongApplyCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicLongAlterCodec_decodeRequest() {
        int fileClientMessageIndex = 403;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicLongAlterCodec.RequestParameters parameters = AtomicLongAlterCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.function));
        assertTrue(isEqual(anInt, parameters.returnValueType));
    }

    @Test
    public void test_AtomicLongAlterCodec_encodeResponse() {
        int fileClientMessageIndex = 404;
        ClientMessage encoded = AtomicLongAlterCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicLongAddAndGetCodec_decodeRequest() {
        int fileClientMessageIndex = 405;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicLongAddAndGetCodec.RequestParameters parameters = AtomicLongAddAndGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.delta));
    }

    @Test
    public void test_AtomicLongAddAndGetCodec_encodeResponse() {
        int fileClientMessageIndex = 406;
        ClientMessage encoded = AtomicLongAddAndGetCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicLongCompareAndSetCodec_decodeRequest() {
        int fileClientMessageIndex = 407;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicLongCompareAndSetCodec.RequestParameters parameters = AtomicLongCompareAndSetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.expected));
        assertTrue(isEqual(aLong, parameters.updated));
    }

    @Test
    public void test_AtomicLongCompareAndSetCodec_encodeResponse() {
        int fileClientMessageIndex = 408;
        ClientMessage encoded = AtomicLongCompareAndSetCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicLongGetCodec_decodeRequest() {
        int fileClientMessageIndex = 409;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicLongGetCodec.RequestParameters parameters = AtomicLongGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
    }

    @Test
    public void test_AtomicLongGetCodec_encodeResponse() {
        int fileClientMessageIndex = 410;
        ClientMessage encoded = AtomicLongGetCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicLongGetAndAddCodec_decodeRequest() {
        int fileClientMessageIndex = 411;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicLongGetAndAddCodec.RequestParameters parameters = AtomicLongGetAndAddCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.delta));
    }

    @Test
    public void test_AtomicLongGetAndAddCodec_encodeResponse() {
        int fileClientMessageIndex = 412;
        ClientMessage encoded = AtomicLongGetAndAddCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicLongGetAndSetCodec_decodeRequest() {
        int fileClientMessageIndex = 413;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicLongGetAndSetCodec.RequestParameters parameters = AtomicLongGetAndSetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.newValue));
    }

    @Test
    public void test_AtomicLongGetAndSetCodec_encodeResponse() {
        int fileClientMessageIndex = 414;
        ClientMessage encoded = AtomicLongGetAndSetCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicRefApplyCodec_decodeRequest() {
        int fileClientMessageIndex = 415;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicRefApplyCodec.RequestParameters parameters = AtomicRefApplyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.function));
        assertTrue(isEqual(anInt, parameters.returnValueType));
        assertTrue(isEqual(aBoolean, parameters.alter));
    }

    @Test
    public void test_AtomicRefApplyCodec_encodeResponse() {
        int fileClientMessageIndex = 416;
        ClientMessage encoded = AtomicRefApplyCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicRefCompareAndSetCodec_decodeRequest() {
        int fileClientMessageIndex = 417;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicRefCompareAndSetCodec.RequestParameters parameters = AtomicRefCompareAndSetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(null, parameters.oldValue));
        assertTrue(isEqual(null, parameters.newValue));
    }

    @Test
    public void test_AtomicRefCompareAndSetCodec_encodeResponse() {
        int fileClientMessageIndex = 418;
        ClientMessage encoded = AtomicRefCompareAndSetCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicRefContainsCodec_decodeRequest() {
        int fileClientMessageIndex = 419;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicRefContainsCodec.RequestParameters parameters = AtomicRefContainsCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(null, parameters.value));
    }

    @Test
    public void test_AtomicRefContainsCodec_encodeResponse() {
        int fileClientMessageIndex = 420;
        ClientMessage encoded = AtomicRefContainsCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicRefGetCodec_decodeRequest() {
        int fileClientMessageIndex = 421;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicRefGetCodec.RequestParameters parameters = AtomicRefGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
    }

    @Test
    public void test_AtomicRefGetCodec_encodeResponse() {
        int fileClientMessageIndex = 422;
        ClientMessage encoded = AtomicRefGetCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_AtomicRefSetCodec_decodeRequest() {
        int fileClientMessageIndex = 423;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        AtomicRefSetCodec.RequestParameters parameters = AtomicRefSetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(null, parameters.newValue));
        assertTrue(isEqual(aBoolean, parameters.returnOldValue));
    }

    @Test
    public void test_AtomicRefSetCodec_encodeResponse() {
        int fileClientMessageIndex = 424;
        ClientMessage encoded = AtomicRefSetCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CountDownLatchTrySetCountCodec_decodeRequest() {
        int fileClientMessageIndex = 425;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CountDownLatchTrySetCountCodec.RequestParameters parameters = CountDownLatchTrySetCountCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.count));
    }

    @Test
    public void test_CountDownLatchTrySetCountCodec_encodeResponse() {
        int fileClientMessageIndex = 426;
        ClientMessage encoded = CountDownLatchTrySetCountCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CountDownLatchAwaitCodec_decodeRequest() {
        int fileClientMessageIndex = 427;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CountDownLatchAwaitCodec.RequestParameters parameters = CountDownLatchAwaitCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.invocationUid));
        assertTrue(isEqual(aLong, parameters.timeoutMs));
    }

    @Test
    public void test_CountDownLatchAwaitCodec_encodeResponse() {
        int fileClientMessageIndex = 428;
        ClientMessage encoded = CountDownLatchAwaitCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CountDownLatchCountDownCodec_decodeRequest() {
        int fileClientMessageIndex = 429;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CountDownLatchCountDownCodec.RequestParameters parameters = CountDownLatchCountDownCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.invocationUid));
        assertTrue(isEqual(anInt, parameters.expectedRound));
    }

    @Test
    public void test_CountDownLatchCountDownCodec_encodeResponse() {
        int fileClientMessageIndex = 430;
        ClientMessage encoded = CountDownLatchCountDownCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CountDownLatchGetCountCodec_decodeRequest() {
        int fileClientMessageIndex = 431;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CountDownLatchGetCountCodec.RequestParameters parameters = CountDownLatchGetCountCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
    }

    @Test
    public void test_CountDownLatchGetCountCodec_encodeResponse() {
        int fileClientMessageIndex = 432;
        ClientMessage encoded = CountDownLatchGetCountCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CountDownLatchGetRoundCodec_decodeRequest() {
        int fileClientMessageIndex = 433;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CountDownLatchGetRoundCodec.RequestParameters parameters = CountDownLatchGetRoundCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
    }

    @Test
    public void test_CountDownLatchGetRoundCodec_encodeResponse() {
        int fileClientMessageIndex = 434;
        ClientMessage encoded = CountDownLatchGetRoundCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SemaphoreInitCodec_decodeRequest() {
        int fileClientMessageIndex = 435;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SemaphoreInitCodec.RequestParameters parameters = SemaphoreInitCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.permits));
    }

    @Test
    public void test_SemaphoreInitCodec_encodeResponse() {
        int fileClientMessageIndex = 436;
        ClientMessage encoded = SemaphoreInitCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SemaphoreAcquireCodec_decodeRequest() {
        int fileClientMessageIndex = 437;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SemaphoreAcquireCodec.RequestParameters parameters = SemaphoreAcquireCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.sessionId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aUUID, parameters.invocationUid));
        assertTrue(isEqual(anInt, parameters.permits));
        assertTrue(isEqual(aLong, parameters.timeoutMs));
    }

    @Test
    public void test_SemaphoreAcquireCodec_encodeResponse() {
        int fileClientMessageIndex = 438;
        ClientMessage encoded = SemaphoreAcquireCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SemaphoreReleaseCodec_decodeRequest() {
        int fileClientMessageIndex = 439;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SemaphoreReleaseCodec.RequestParameters parameters = SemaphoreReleaseCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.sessionId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aUUID, parameters.invocationUid));
        assertTrue(isEqual(anInt, parameters.permits));
    }

    @Test
    public void test_SemaphoreReleaseCodec_encodeResponse() {
        int fileClientMessageIndex = 440;
        ClientMessage encoded = SemaphoreReleaseCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SemaphoreDrainCodec_decodeRequest() {
        int fileClientMessageIndex = 441;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SemaphoreDrainCodec.RequestParameters parameters = SemaphoreDrainCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.sessionId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aUUID, parameters.invocationUid));
    }

    @Test
    public void test_SemaphoreDrainCodec_encodeResponse() {
        int fileClientMessageIndex = 442;
        ClientMessage encoded = SemaphoreDrainCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SemaphoreChangeCodec_decodeRequest() {
        int fileClientMessageIndex = 443;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SemaphoreChangeCodec.RequestParameters parameters = SemaphoreChangeCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.sessionId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aUUID, parameters.invocationUid));
        assertTrue(isEqual(anInt, parameters.permits));
    }

    @Test
    public void test_SemaphoreChangeCodec_encodeResponse() {
        int fileClientMessageIndex = 444;
        ClientMessage encoded = SemaphoreChangeCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SemaphoreAvailablePermitsCodec_decodeRequest() {
        int fileClientMessageIndex = 445;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SemaphoreAvailablePermitsCodec.RequestParameters parameters = SemaphoreAvailablePermitsCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
    }

    @Test
    public void test_SemaphoreAvailablePermitsCodec_encodeResponse() {
        int fileClientMessageIndex = 446;
        ClientMessage encoded = SemaphoreAvailablePermitsCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SemaphoreGetSemaphoreTypeCodec_decodeRequest() {
        int fileClientMessageIndex = 447;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, SemaphoreGetSemaphoreTypeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_SemaphoreGetSemaphoreTypeCodec_encodeResponse() {
        int fileClientMessageIndex = 448;
        ClientMessage encoded = SemaphoreGetSemaphoreTypeCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapPutCodec_decodeRequest() {
        int fileClientMessageIndex = 449;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapPutCodec.RequestParameters parameters = ReplicatedMapPutCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.ttl));
    }

    @Test
    public void test_ReplicatedMapPutCodec_encodeResponse() {
        int fileClientMessageIndex = 450;
        ClientMessage encoded = ReplicatedMapPutCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 451;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ReplicatedMapSizeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ReplicatedMapSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 452;
        ClientMessage encoded = ReplicatedMapSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapIsEmptyCodec_decodeRequest() {
        int fileClientMessageIndex = 453;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ReplicatedMapIsEmptyCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ReplicatedMapIsEmptyCodec_encodeResponse() {
        int fileClientMessageIndex = 454;
        ClientMessage encoded = ReplicatedMapIsEmptyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapContainsKeyCodec_decodeRequest() {
        int fileClientMessageIndex = 455;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapContainsKeyCodec.RequestParameters parameters = ReplicatedMapContainsKeyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_ReplicatedMapContainsKeyCodec_encodeResponse() {
        int fileClientMessageIndex = 456;
        ClientMessage encoded = ReplicatedMapContainsKeyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapContainsValueCodec_decodeRequest() {
        int fileClientMessageIndex = 457;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapContainsValueCodec.RequestParameters parameters = ReplicatedMapContainsValueCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_ReplicatedMapContainsValueCodec_encodeResponse() {
        int fileClientMessageIndex = 458;
        ClientMessage encoded = ReplicatedMapContainsValueCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapGetCodec_decodeRequest() {
        int fileClientMessageIndex = 459;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapGetCodec.RequestParameters parameters = ReplicatedMapGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_ReplicatedMapGetCodec_encodeResponse() {
        int fileClientMessageIndex = 460;
        ClientMessage encoded = ReplicatedMapGetCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 461;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapRemoveCodec.RequestParameters parameters = ReplicatedMapRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_ReplicatedMapRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 462;
        ClientMessage encoded = ReplicatedMapRemoveCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapPutAllCodec_decodeRequest() {
        int fileClientMessageIndex = 463;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapPutAllCodec.RequestParameters parameters = ReplicatedMapPutAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfDataToData, parameters.entries));
    }

    @Test
    public void test_ReplicatedMapPutAllCodec_encodeResponse() {
        int fileClientMessageIndex = 464;
        ClientMessage encoded = ReplicatedMapPutAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapClearCodec_decodeRequest() {
        int fileClientMessageIndex = 465;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ReplicatedMapClearCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ReplicatedMapClearCodec_encodeResponse() {
        int fileClientMessageIndex = 466;
        ClientMessage encoded = ReplicatedMapClearCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerToKeyWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 467;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapAddEntryListenerToKeyWithPredicateCodec.RequestParameters parameters = ReplicatedMapAddEntryListenerToKeyWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.predicate));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerToKeyWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 468;
        ClientMessage encoded = ReplicatedMapAddEntryListenerToKeyWithPredicateCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerToKeyWithPredicateCodec_encodeEntryEvent() {
        int fileClientMessageIndex = 469;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ReplicatedMapAddEntryListenerToKeyWithPredicateCodec.encodeEntryEvent(null, null, null, null, anInt, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 470;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapAddEntryListenerWithPredicateCodec.RequestParameters parameters = ReplicatedMapAddEntryListenerWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.predicate));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 471;
        ClientMessage encoded = ReplicatedMapAddEntryListenerWithPredicateCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerWithPredicateCodec_encodeEntryEvent() {
        int fileClientMessageIndex = 472;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ReplicatedMapAddEntryListenerWithPredicateCodec.encodeEntryEvent(null, null, null, null, anInt, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerToKeyCodec_decodeRequest() {
        int fileClientMessageIndex = 473;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapAddEntryListenerToKeyCodec.RequestParameters parameters = ReplicatedMapAddEntryListenerToKeyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerToKeyCodec_encodeResponse() {
        int fileClientMessageIndex = 474;
        ClientMessage encoded = ReplicatedMapAddEntryListenerToKeyCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerToKeyCodec_encodeEntryEvent() {
        int fileClientMessageIndex = 475;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ReplicatedMapAddEntryListenerToKeyCodec.encodeEntryEvent(null, null, null, null, anInt, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 476;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapAddEntryListenerCodec.RequestParameters parameters = ReplicatedMapAddEntryListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 477;
        ClientMessage encoded = ReplicatedMapAddEntryListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapAddEntryListenerCodec_encodeEntryEvent() {
        int fileClientMessageIndex = 478;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ReplicatedMapAddEntryListenerCodec.encodeEntryEvent(null, null, null, null, anInt, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapRemoveEntryListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 479;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapRemoveEntryListenerCodec.RequestParameters parameters = ReplicatedMapRemoveEntryListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_ReplicatedMapRemoveEntryListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 480;
        ClientMessage encoded = ReplicatedMapRemoveEntryListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapKeySetCodec_decodeRequest() {
        int fileClientMessageIndex = 481;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ReplicatedMapKeySetCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ReplicatedMapKeySetCodec_encodeResponse() {
        int fileClientMessageIndex = 482;
        ClientMessage encoded = ReplicatedMapKeySetCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapValuesCodec_decodeRequest() {
        int fileClientMessageIndex = 483;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ReplicatedMapValuesCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ReplicatedMapValuesCodec_encodeResponse() {
        int fileClientMessageIndex = 484;
        ClientMessage encoded = ReplicatedMapValuesCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapEntrySetCodec_decodeRequest() {
        int fileClientMessageIndex = 485;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ReplicatedMapEntrySetCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ReplicatedMapEntrySetCodec_encodeResponse() {
        int fileClientMessageIndex = 486;
        ClientMessage encoded = ReplicatedMapEntrySetCodec.encodeResponse(aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapAddNearCacheEntryListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 487;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapAddNearCacheEntryListenerCodec.RequestParameters parameters = ReplicatedMapAddNearCacheEntryListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.includeValue));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_ReplicatedMapAddNearCacheEntryListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 488;
        ClientMessage encoded = ReplicatedMapAddNearCacheEntryListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapAddNearCacheEntryListenerCodec_encodeEntryEvent() {
        int fileClientMessageIndex = 489;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ReplicatedMapAddNearCacheEntryListenerCodec.encodeEntryEvent(null, null, null, null, anInt, aUUID, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapPutAllWithMetadataCodec_decodeRequest() {
        int fileClientMessageIndex = 490;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapPutAllWithMetadataCodec.RequestParameters parameters = ReplicatedMapPutAllWithMetadataCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfReplicatedMapEntryViewHolders, parameters.entries));
        assertTrue(isEqual(anInt, parameters.partitionId));
    }

    @Test
    public void test_ReplicatedMapPutAllWithMetadataCodec_encodeResponse() {
        int fileClientMessageIndex = 491;
        ClientMessage encoded = ReplicatedMapPutAllWithMetadataCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapFetchEntryViewsCodec_decodeRequest() {
        int fileClientMessageIndex = 492;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapFetchEntryViewsCodec.RequestParameters parameters = ReplicatedMapFetchEntryViewsCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.cursorId));
        assertTrue(isEqual(aBoolean, parameters.newIteration));
        assertTrue(isEqual(anInt, parameters.partitionId));
        assertTrue(isEqual(anInt, parameters.batchSize));
    }

    @Test
    public void test_ReplicatedMapFetchEntryViewsCodec_encodeResponse() {
        int fileClientMessageIndex = 493;
        ClientMessage encoded = ReplicatedMapFetchEntryViewsCodec.encodeResponse(aUUID, aListOfReplicatedMapEntryViewHolders);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ReplicatedMapEndEntryViewIterationCodec_decodeRequest() {
        int fileClientMessageIndex = 494;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ReplicatedMapEndEntryViewIterationCodec.RequestParameters parameters = ReplicatedMapEndEntryViewIterationCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(null, parameters.cursorId));
    }

    @Test
    public void test_ReplicatedMapEndEntryViewIterationCodec_encodeResponse() {
        int fileClientMessageIndex = 495;
        ClientMessage encoded = ReplicatedMapEndEntryViewIterationCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapContainsKeyCodec_decodeRequest() {
        int fileClientMessageIndex = 496;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapContainsKeyCodec.RequestParameters parameters = TransactionalMapContainsKeyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_TransactionalMapContainsKeyCodec_encodeResponse() {
        int fileClientMessageIndex = 497;
        ClientMessage encoded = TransactionalMapContainsKeyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapGetCodec_decodeRequest() {
        int fileClientMessageIndex = 498;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapGetCodec.RequestParameters parameters = TransactionalMapGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_TransactionalMapGetCodec_encodeResponse() {
        int fileClientMessageIndex = 499;
        ClientMessage encoded = TransactionalMapGetCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapGetForUpdateCodec_decodeRequest() {
        int fileClientMessageIndex = 500;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapGetForUpdateCodec.RequestParameters parameters = TransactionalMapGetForUpdateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_TransactionalMapGetForUpdateCodec_encodeResponse() {
        int fileClientMessageIndex = 501;
        ClientMessage encoded = TransactionalMapGetForUpdateCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 502;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapSizeCodec.RequestParameters parameters = TransactionalMapSizeCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionalMapSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 503;
        ClientMessage encoded = TransactionalMapSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapIsEmptyCodec_decodeRequest() {
        int fileClientMessageIndex = 504;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapIsEmptyCodec.RequestParameters parameters = TransactionalMapIsEmptyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionalMapIsEmptyCodec_encodeResponse() {
        int fileClientMessageIndex = 505;
        ClientMessage encoded = TransactionalMapIsEmptyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapPutCodec_decodeRequest() {
        int fileClientMessageIndex = 506;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapPutCodec.RequestParameters parameters = TransactionalMapPutCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(aLong, parameters.ttl));
    }

    @Test
    public void test_TransactionalMapPutCodec_encodeResponse() {
        int fileClientMessageIndex = 507;
        ClientMessage encoded = TransactionalMapPutCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapSetCodec_decodeRequest() {
        int fileClientMessageIndex = 508;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapSetCodec.RequestParameters parameters = TransactionalMapSetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_TransactionalMapSetCodec_encodeResponse() {
        int fileClientMessageIndex = 509;
        ClientMessage encoded = TransactionalMapSetCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapPutIfAbsentCodec_decodeRequest() {
        int fileClientMessageIndex = 510;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapPutIfAbsentCodec.RequestParameters parameters = TransactionalMapPutIfAbsentCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_TransactionalMapPutIfAbsentCodec_encodeResponse() {
        int fileClientMessageIndex = 511;
        ClientMessage encoded = TransactionalMapPutIfAbsentCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapReplaceCodec_decodeRequest() {
        int fileClientMessageIndex = 512;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapReplaceCodec.RequestParameters parameters = TransactionalMapReplaceCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_TransactionalMapReplaceCodec_encodeResponse() {
        int fileClientMessageIndex = 513;
        ClientMessage encoded = TransactionalMapReplaceCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapReplaceIfSameCodec_decodeRequest() {
        int fileClientMessageIndex = 514;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapReplaceIfSameCodec.RequestParameters parameters = TransactionalMapReplaceIfSameCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.oldValue));
        assertTrue(isEqual(aData, parameters.newValue));
    }

    @Test
    public void test_TransactionalMapReplaceIfSameCodec_encodeResponse() {
        int fileClientMessageIndex = 515;
        ClientMessage encoded = TransactionalMapReplaceIfSameCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 516;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapRemoveCodec.RequestParameters parameters = TransactionalMapRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_TransactionalMapRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 517;
        ClientMessage encoded = TransactionalMapRemoveCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapDeleteCodec_decodeRequest() {
        int fileClientMessageIndex = 518;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapDeleteCodec.RequestParameters parameters = TransactionalMapDeleteCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_TransactionalMapDeleteCodec_encodeResponse() {
        int fileClientMessageIndex = 519;
        ClientMessage encoded = TransactionalMapDeleteCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapRemoveIfSameCodec_decodeRequest() {
        int fileClientMessageIndex = 520;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapRemoveIfSameCodec.RequestParameters parameters = TransactionalMapRemoveIfSameCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_TransactionalMapRemoveIfSameCodec_encodeResponse() {
        int fileClientMessageIndex = 521;
        ClientMessage encoded = TransactionalMapRemoveIfSameCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapKeySetCodec_decodeRequest() {
        int fileClientMessageIndex = 522;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapKeySetCodec.RequestParameters parameters = TransactionalMapKeySetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionalMapKeySetCodec_encodeResponse() {
        int fileClientMessageIndex = 523;
        ClientMessage encoded = TransactionalMapKeySetCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapKeySetWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 524;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapKeySetWithPredicateCodec.RequestParameters parameters = TransactionalMapKeySetWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.predicate));
    }

    @Test
    public void test_TransactionalMapKeySetWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 525;
        ClientMessage encoded = TransactionalMapKeySetWithPredicateCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapValuesCodec_decodeRequest() {
        int fileClientMessageIndex = 526;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapValuesCodec.RequestParameters parameters = TransactionalMapValuesCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionalMapValuesCodec_encodeResponse() {
        int fileClientMessageIndex = 527;
        ClientMessage encoded = TransactionalMapValuesCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapValuesWithPredicateCodec_decodeRequest() {
        int fileClientMessageIndex = 528;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapValuesWithPredicateCodec.RequestParameters parameters = TransactionalMapValuesWithPredicateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.predicate));
    }

    @Test
    public void test_TransactionalMapValuesWithPredicateCodec_encodeResponse() {
        int fileClientMessageIndex = 529;
        ClientMessage encoded = TransactionalMapValuesWithPredicateCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMapContainsValueCodec_decodeRequest() {
        int fileClientMessageIndex = 530;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMapContainsValueCodec.RequestParameters parameters = TransactionalMapContainsValueCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_TransactionalMapContainsValueCodec_encodeResponse() {
        int fileClientMessageIndex = 531;
        ClientMessage encoded = TransactionalMapContainsValueCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMultiMapPutCodec_decodeRequest() {
        int fileClientMessageIndex = 532;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMultiMapPutCodec.RequestParameters parameters = TransactionalMultiMapPutCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_TransactionalMultiMapPutCodec_encodeResponse() {
        int fileClientMessageIndex = 533;
        ClientMessage encoded = TransactionalMultiMapPutCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMultiMapGetCodec_decodeRequest() {
        int fileClientMessageIndex = 534;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMultiMapGetCodec.RequestParameters parameters = TransactionalMultiMapGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_TransactionalMultiMapGetCodec_encodeResponse() {
        int fileClientMessageIndex = 535;
        ClientMessage encoded = TransactionalMultiMapGetCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMultiMapRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 536;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMultiMapRemoveCodec.RequestParameters parameters = TransactionalMultiMapRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_TransactionalMultiMapRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 537;
        ClientMessage encoded = TransactionalMultiMapRemoveCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMultiMapRemoveEntryCodec_decodeRequest() {
        int fileClientMessageIndex = 538;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMultiMapRemoveEntryCodec.RequestParameters parameters = TransactionalMultiMapRemoveEntryCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_TransactionalMultiMapRemoveEntryCodec_encodeResponse() {
        int fileClientMessageIndex = 539;
        ClientMessage encoded = TransactionalMultiMapRemoveEntryCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMultiMapValueCountCodec_decodeRequest() {
        int fileClientMessageIndex = 540;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMultiMapValueCountCodec.RequestParameters parameters = TransactionalMultiMapValueCountCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_TransactionalMultiMapValueCountCodec_encodeResponse() {
        int fileClientMessageIndex = 541;
        ClientMessage encoded = TransactionalMultiMapValueCountCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalMultiMapSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 542;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalMultiMapSizeCodec.RequestParameters parameters = TransactionalMultiMapSizeCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionalMultiMapSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 543;
        ClientMessage encoded = TransactionalMultiMapSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalSetAddCodec_decodeRequest() {
        int fileClientMessageIndex = 544;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalSetAddCodec.RequestParameters parameters = TransactionalSetAddCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.item));
    }

    @Test
    public void test_TransactionalSetAddCodec_encodeResponse() {
        int fileClientMessageIndex = 545;
        ClientMessage encoded = TransactionalSetAddCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalSetRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 546;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalSetRemoveCodec.RequestParameters parameters = TransactionalSetRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.item));
    }

    @Test
    public void test_TransactionalSetRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 547;
        ClientMessage encoded = TransactionalSetRemoveCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalSetSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 548;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalSetSizeCodec.RequestParameters parameters = TransactionalSetSizeCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionalSetSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 549;
        ClientMessage encoded = TransactionalSetSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalListAddCodec_decodeRequest() {
        int fileClientMessageIndex = 550;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalListAddCodec.RequestParameters parameters = TransactionalListAddCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.item));
    }

    @Test
    public void test_TransactionalListAddCodec_encodeResponse() {
        int fileClientMessageIndex = 551;
        ClientMessage encoded = TransactionalListAddCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalListRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 552;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalListRemoveCodec.RequestParameters parameters = TransactionalListRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.item));
    }

    @Test
    public void test_TransactionalListRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 553;
        ClientMessage encoded = TransactionalListRemoveCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalListSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 554;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalListSizeCodec.RequestParameters parameters = TransactionalListSizeCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionalListSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 555;
        ClientMessage encoded = TransactionalListSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalQueueOfferCodec_decodeRequest() {
        int fileClientMessageIndex = 556;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalQueueOfferCodec.RequestParameters parameters = TransactionalQueueOfferCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aData, parameters.item));
        assertTrue(isEqual(aLong, parameters.timeout));
    }

    @Test
    public void test_TransactionalQueueOfferCodec_encodeResponse() {
        int fileClientMessageIndex = 557;
        ClientMessage encoded = TransactionalQueueOfferCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalQueueTakeCodec_decodeRequest() {
        int fileClientMessageIndex = 558;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalQueueTakeCodec.RequestParameters parameters = TransactionalQueueTakeCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionalQueueTakeCodec_encodeResponse() {
        int fileClientMessageIndex = 559;
        ClientMessage encoded = TransactionalQueueTakeCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalQueuePollCodec_decodeRequest() {
        int fileClientMessageIndex = 560;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalQueuePollCodec.RequestParameters parameters = TransactionalQueuePollCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.timeout));
    }

    @Test
    public void test_TransactionalQueuePollCodec_encodeResponse() {
        int fileClientMessageIndex = 561;
        ClientMessage encoded = TransactionalQueuePollCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalQueuePeekCodec_decodeRequest() {
        int fileClientMessageIndex = 562;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalQueuePeekCodec.RequestParameters parameters = TransactionalQueuePeekCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
        assertTrue(isEqual(aLong, parameters.timeout));
    }

    @Test
    public void test_TransactionalQueuePeekCodec_encodeResponse() {
        int fileClientMessageIndex = 563;
        ClientMessage encoded = TransactionalQueuePeekCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionalQueueSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 564;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionalQueueSizeCodec.RequestParameters parameters = TransactionalQueueSizeCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.txnId));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionalQueueSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 565;
        ClientMessage encoded = TransactionalQueueSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheAddEntryListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 566;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheAddEntryListenerCodec.RequestParameters parameters = CacheAddEntryListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_CacheAddEntryListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 567;
        ClientMessage encoded = CacheAddEntryListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheAddEntryListenerCodec_encodeCacheEvent() {
        int fileClientMessageIndex = 568;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = CacheAddEntryListenerCodec.encodeCacheEvent(anInt, aListOfCacheEventData, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheClearCodec_decodeRequest() {
        int fileClientMessageIndex = 569;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, CacheClearCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_CacheClearCodec_encodeResponse() {
        int fileClientMessageIndex = 570;
        ClientMessage encoded = CacheClearCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheRemoveAllKeysCodec_decodeRequest() {
        int fileClientMessageIndex = 571;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheRemoveAllKeysCodec.RequestParameters parameters = CacheRemoveAllKeysCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.keys));
        assertTrue(isEqual(anInt, parameters.completionId));
    }

    @Test
    public void test_CacheRemoveAllKeysCodec_encodeResponse() {
        int fileClientMessageIndex = 572;
        ClientMessage encoded = CacheRemoveAllKeysCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheRemoveAllCodec_decodeRequest() {
        int fileClientMessageIndex = 573;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheRemoveAllCodec.RequestParameters parameters = CacheRemoveAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.completionId));
    }

    @Test
    public void test_CacheRemoveAllCodec_encodeResponse() {
        int fileClientMessageIndex = 574;
        ClientMessage encoded = CacheRemoveAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheContainsKeyCodec_decodeRequest() {
        int fileClientMessageIndex = 575;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheContainsKeyCodec.RequestParameters parameters = CacheContainsKeyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_CacheContainsKeyCodec_encodeResponse() {
        int fileClientMessageIndex = 576;
        ClientMessage encoded = CacheContainsKeyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheCreateConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 577;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheCreateConfigCodec.RequestParameters parameters = CacheCreateConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aCacheConfigHolder, parameters.cacheConfig));
        assertTrue(isEqual(aBoolean, parameters.createAlsoOnOthers));
    }

    @Test
    public void test_CacheCreateConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 578;
        ClientMessage encoded = CacheCreateConfigCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheDestroyCodec_decodeRequest() {
        int fileClientMessageIndex = 579;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, CacheDestroyCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_CacheDestroyCodec_encodeResponse() {
        int fileClientMessageIndex = 580;
        ClientMessage encoded = CacheDestroyCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheEntryProcessorCodec_decodeRequest() {
        int fileClientMessageIndex = 581;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheEntryProcessorCodec.RequestParameters parameters = CacheEntryProcessorCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.entryProcessor));
        assertTrue(isEqual(aListOfData, parameters.arguments));
        assertTrue(isEqual(anInt, parameters.completionId));
    }

    @Test
    public void test_CacheEntryProcessorCodec_encodeResponse() {
        int fileClientMessageIndex = 582;
        ClientMessage encoded = CacheEntryProcessorCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheGetAllCodec_decodeRequest() {
        int fileClientMessageIndex = 583;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheGetAllCodec.RequestParameters parameters = CacheGetAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.keys));
        assertTrue(isEqual(null, parameters.expiryPolicy));
    }

    @Test
    public void test_CacheGetAllCodec_encodeResponse() {
        int fileClientMessageIndex = 584;
        ClientMessage encoded = CacheGetAllCodec.encodeResponse(aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheGetAndRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 585;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheGetAndRemoveCodec.RequestParameters parameters = CacheGetAndRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(anInt, parameters.completionId));
    }

    @Test
    public void test_CacheGetAndRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 586;
        ClientMessage encoded = CacheGetAndRemoveCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheGetAndReplaceCodec_decodeRequest() {
        int fileClientMessageIndex = 587;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheGetAndReplaceCodec.RequestParameters parameters = CacheGetAndReplaceCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(null, parameters.expiryPolicy));
        assertTrue(isEqual(anInt, parameters.completionId));
    }

    @Test
    public void test_CacheGetAndReplaceCodec_encodeResponse() {
        int fileClientMessageIndex = 588;
        ClientMessage encoded = CacheGetAndReplaceCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheGetConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 589;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheGetConfigCodec.RequestParameters parameters = CacheGetConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aString, parameters.simpleName));
    }

    @Test
    public void test_CacheGetConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 590;
        ClientMessage encoded = CacheGetConfigCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheGetCodec_decodeRequest() {
        int fileClientMessageIndex = 591;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheGetCodec.RequestParameters parameters = CacheGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(null, parameters.expiryPolicy));
    }

    @Test
    public void test_CacheGetCodec_encodeResponse() {
        int fileClientMessageIndex = 592;
        ClientMessage encoded = CacheGetCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheIterateCodec_decodeRequest() {
        int fileClientMessageIndex = 593;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheIterateCodec.RequestParameters parameters = CacheIterateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfIntegerToInteger, parameters.iterationPointers));
        assertTrue(isEqual(anInt, parameters.batch));
    }

    @Test
    public void test_CacheIterateCodec_encodeResponse() {
        int fileClientMessageIndex = 594;
        ClientMessage encoded = CacheIterateCodec.encodeResponse(aListOfIntegerToInteger, aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheListenerRegistrationCodec_decodeRequest() {
        int fileClientMessageIndex = 595;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheListenerRegistrationCodec.RequestParameters parameters = CacheListenerRegistrationCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.listenerConfig));
        assertTrue(isEqual(aBoolean, parameters.shouldRegister));
        assertTrue(isEqual(aUUID, parameters.uuid));
    }

    @Test
    public void test_CacheListenerRegistrationCodec_encodeResponse() {
        int fileClientMessageIndex = 596;
        ClientMessage encoded = CacheListenerRegistrationCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheLoadAllCodec_decodeRequest() {
        int fileClientMessageIndex = 597;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheLoadAllCodec.RequestParameters parameters = CacheLoadAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.keys));
        assertTrue(isEqual(aBoolean, parameters.replaceExistingValues));
    }

    @Test
    public void test_CacheLoadAllCodec_encodeResponse() {
        int fileClientMessageIndex = 598;
        ClientMessage encoded = CacheLoadAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheManagementConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 599;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheManagementConfigCodec.RequestParameters parameters = CacheManagementConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.isStat));
        assertTrue(isEqual(aBoolean, parameters.enabled));
        assertTrue(isEqual(aUUID, parameters.uuid));
    }

    @Test
    public void test_CacheManagementConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 600;
        ClientMessage encoded = CacheManagementConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CachePutIfAbsentCodec_decodeRequest() {
        int fileClientMessageIndex = 601;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CachePutIfAbsentCodec.RequestParameters parameters = CachePutIfAbsentCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(null, parameters.expiryPolicy));
        assertTrue(isEqual(anInt, parameters.completionId));
    }

    @Test
    public void test_CachePutIfAbsentCodec_encodeResponse() {
        int fileClientMessageIndex = 602;
        ClientMessage encoded = CachePutIfAbsentCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CachePutCodec_decodeRequest() {
        int fileClientMessageIndex = 603;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CachePutCodec.RequestParameters parameters = CachePutCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
        assertTrue(isEqual(null, parameters.expiryPolicy));
        assertTrue(isEqual(aBoolean, parameters.get));
        assertTrue(isEqual(anInt, parameters.completionId));
    }

    @Test
    public void test_CachePutCodec_encodeResponse() {
        int fileClientMessageIndex = 604;
        ClientMessage encoded = CachePutCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheRemoveEntryListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 605;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheRemoveEntryListenerCodec.RequestParameters parameters = CacheRemoveEntryListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_CacheRemoveEntryListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 606;
        ClientMessage encoded = CacheRemoveEntryListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheRemoveInvalidationListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 607;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheRemoveInvalidationListenerCodec.RequestParameters parameters = CacheRemoveInvalidationListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_CacheRemoveInvalidationListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 608;
        ClientMessage encoded = CacheRemoveInvalidationListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 609;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheRemoveCodec.RequestParameters parameters = CacheRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(null, parameters.currentValue));
        assertTrue(isEqual(anInt, parameters.completionId));
    }

    @Test
    public void test_CacheRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 610;
        ClientMessage encoded = CacheRemoveCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheReplaceCodec_decodeRequest() {
        int fileClientMessageIndex = 611;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheReplaceCodec.RequestParameters parameters = CacheReplaceCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(null, parameters.oldValue));
        assertTrue(isEqual(aData, parameters.newValue));
        assertTrue(isEqual(null, parameters.expiryPolicy));
        assertTrue(isEqual(anInt, parameters.completionId));
    }

    @Test
    public void test_CacheReplaceCodec_encodeResponse() {
        int fileClientMessageIndex = 612;
        ClientMessage encoded = CacheReplaceCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 613;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, CacheSizeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_CacheSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 614;
        ClientMessage encoded = CacheSizeCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheAddPartitionLostListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 615;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheAddPartitionLostListenerCodec.RequestParameters parameters = CacheAddPartitionLostListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_CacheAddPartitionLostListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 616;
        ClientMessage encoded = CacheAddPartitionLostListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheAddPartitionLostListenerCodec_encodeCachePartitionLostEvent() {
        int fileClientMessageIndex = 617;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = CacheAddPartitionLostListenerCodec.encodeCachePartitionLostEvent(anInt, aUUID);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheRemovePartitionLostListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 618;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheRemovePartitionLostListenerCodec.RequestParameters parameters = CacheRemovePartitionLostListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_CacheRemovePartitionLostListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 619;
        ClientMessage encoded = CacheRemovePartitionLostListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CachePutAllCodec_decodeRequest() {
        int fileClientMessageIndex = 620;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CachePutAllCodec.RequestParameters parameters = CachePutAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfDataToData, parameters.entries));
        assertTrue(isEqual(null, parameters.expiryPolicy));
        assertTrue(isEqual(anInt, parameters.completionId));
    }

    @Test
    public void test_CachePutAllCodec_encodeResponse() {
        int fileClientMessageIndex = 621;
        ClientMessage encoded = CachePutAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheIterateEntriesCodec_decodeRequest() {
        int fileClientMessageIndex = 622;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheIterateEntriesCodec.RequestParameters parameters = CacheIterateEntriesCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfIntegerToInteger, parameters.iterationPointers));
        assertTrue(isEqual(anInt, parameters.batch));
    }

    @Test
    public void test_CacheIterateEntriesCodec_encodeResponse() {
        int fileClientMessageIndex = 623;
        ClientMessage encoded = CacheIterateEntriesCodec.encodeResponse(aListOfIntegerToInteger, aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheAddNearCacheInvalidationListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 624;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheAddNearCacheInvalidationListenerCodec.RequestParameters parameters = CacheAddNearCacheInvalidationListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_CacheAddNearCacheInvalidationListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 625;
        ClientMessage encoded = CacheAddNearCacheInvalidationListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheAddNearCacheInvalidationListenerCodec_encodeCacheInvalidationEvent() {
        int fileClientMessageIndex = 626;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = CacheAddNearCacheInvalidationListenerCodec.encodeCacheInvalidationEvent(aString, null, null, aUUID, aLong);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheAddNearCacheInvalidationListenerCodec_encodeCacheBatchInvalidationEvent() {
        int fileClientMessageIndex = 627;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = CacheAddNearCacheInvalidationListenerCodec.encodeCacheBatchInvalidationEvent(aString, aListOfData, aListOfUUIDs, aListOfUUIDs, aListOfLongs);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheFetchNearCacheInvalidationMetadataCodec_decodeRequest() {
        int fileClientMessageIndex = 628;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheFetchNearCacheInvalidationMetadataCodec.RequestParameters parameters = CacheFetchNearCacheInvalidationMetadataCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aListOfStrings, parameters.names));
        assertTrue(isEqual(aUUID, parameters.uuid));
    }

    @Test
    public void test_CacheFetchNearCacheInvalidationMetadataCodec_encodeResponse() {
        int fileClientMessageIndex = 629;
        ClientMessage encoded = CacheFetchNearCacheInvalidationMetadataCodec.encodeResponse(aListOfStringToListOfIntegerToLong, aListOfIntegerToUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheEventJournalSubscribeCodec_decodeRequest() {
        int fileClientMessageIndex = 630;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, CacheEventJournalSubscribeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_CacheEventJournalSubscribeCodec_encodeResponse() {
        int fileClientMessageIndex = 631;
        ClientMessage encoded = CacheEventJournalSubscribeCodec.encodeResponse(aLong, aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheEventJournalReadCodec_decodeRequest() {
        int fileClientMessageIndex = 632;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheEventJournalReadCodec.RequestParameters parameters = CacheEventJournalReadCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.startSequence));
        assertTrue(isEqual(anInt, parameters.minSize));
        assertTrue(isEqual(anInt, parameters.maxSize));
        assertTrue(isEqual(null, parameters.predicate));
        assertTrue(isEqual(null, parameters.projection));
    }

    @Test
    public void test_CacheEventJournalReadCodec_encodeResponse() {
        int fileClientMessageIndex = 633;
        ClientMessage encoded = CacheEventJournalReadCodec.encodeResponse(anInt, aListOfData, null, aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CacheSetExpiryPolicyCodec_decodeRequest() {
        int fileClientMessageIndex = 634;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CacheSetExpiryPolicyCodec.RequestParameters parameters = CacheSetExpiryPolicyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.keys));
        assertTrue(isEqual(aData, parameters.expiryPolicy));
    }

    @Test
    public void test_CacheSetExpiryPolicyCodec_encodeResponse() {
        int fileClientMessageIndex = 635;
        ClientMessage encoded = CacheSetExpiryPolicyCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_XATransactionClearRemoteCodec_decodeRequest() {
        int fileClientMessageIndex = 636;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(anXid, XATransactionClearRemoteCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_XATransactionClearRemoteCodec_encodeResponse() {
        int fileClientMessageIndex = 637;
        ClientMessage encoded = XATransactionClearRemoteCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_XATransactionCollectTransactionsCodec_decodeRequest() {
        int fileClientMessageIndex = 638;
    }

    @Test
    public void test_XATransactionCollectTransactionsCodec_encodeResponse() {
        int fileClientMessageIndex = 639;
        ClientMessage encoded = XATransactionCollectTransactionsCodec.encodeResponse(aListOfXids);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_XATransactionFinalizeCodec_decodeRequest() {
        int fileClientMessageIndex = 640;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        XATransactionFinalizeCodec.RequestParameters parameters = XATransactionFinalizeCodec.decodeRequest(fromFile);
        assertTrue(isEqual(anXid, parameters.xid));
        assertTrue(isEqual(aBoolean, parameters.isCommit));
    }

    @Test
    public void test_XATransactionFinalizeCodec_encodeResponse() {
        int fileClientMessageIndex = 641;
        ClientMessage encoded = XATransactionFinalizeCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_XATransactionCommitCodec_decodeRequest() {
        int fileClientMessageIndex = 642;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        XATransactionCommitCodec.RequestParameters parameters = XATransactionCommitCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aUUID, parameters.transactionId));
        assertTrue(isEqual(aBoolean, parameters.onePhase));
    }

    @Test
    public void test_XATransactionCommitCodec_encodeResponse() {
        int fileClientMessageIndex = 643;
        ClientMessage encoded = XATransactionCommitCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_XATransactionCreateCodec_decodeRequest() {
        int fileClientMessageIndex = 644;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        XATransactionCreateCodec.RequestParameters parameters = XATransactionCreateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(anXid, parameters.xid));
        assertTrue(isEqual(aLong, parameters.timeout));
    }

    @Test
    public void test_XATransactionCreateCodec_encodeResponse() {
        int fileClientMessageIndex = 645;
        ClientMessage encoded = XATransactionCreateCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_XATransactionPrepareCodec_decodeRequest() {
        int fileClientMessageIndex = 646;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aUUID, XATransactionPrepareCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_XATransactionPrepareCodec_encodeResponse() {
        int fileClientMessageIndex = 647;
        ClientMessage encoded = XATransactionPrepareCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_XATransactionRollbackCodec_decodeRequest() {
        int fileClientMessageIndex = 648;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aUUID, XATransactionRollbackCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_XATransactionRollbackCodec_encodeResponse() {
        int fileClientMessageIndex = 649;
        ClientMessage encoded = XATransactionRollbackCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionCommitCodec_decodeRequest() {
        int fileClientMessageIndex = 650;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionCommitCodec.RequestParameters parameters = TransactionCommitCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aUUID, parameters.transactionId));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionCommitCodec_encodeResponse() {
        int fileClientMessageIndex = 651;
        ClientMessage encoded = TransactionCommitCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionCreateCodec_decodeRequest() {
        int fileClientMessageIndex = 652;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionCreateCodec.RequestParameters parameters = TransactionCreateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aLong, parameters.timeout));
        assertTrue(isEqual(anInt, parameters.durability));
        assertTrue(isEqual(anInt, parameters.transactionType));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionCreateCodec_encodeResponse() {
        int fileClientMessageIndex = 653;
        ClientMessage encoded = TransactionCreateCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_TransactionRollbackCodec_decodeRequest() {
        int fileClientMessageIndex = 654;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        TransactionRollbackCodec.RequestParameters parameters = TransactionRollbackCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aUUID, parameters.transactionId));
        assertTrue(isEqual(aLong, parameters.threadId));
    }

    @Test
    public void test_TransactionRollbackCodec_encodeResponse() {
        int fileClientMessageIndex = 655;
        ClientMessage encoded = TransactionRollbackCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ContinuousQueryPublisherCreateWithValueCodec_decodeRequest() {
        int fileClientMessageIndex = 656;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ContinuousQueryPublisherCreateWithValueCodec.RequestParameters parameters = ContinuousQueryPublisherCreateWithValueCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.mapName));
        assertTrue(isEqual(aString, parameters.cacheName));
        assertTrue(isEqual(aData, parameters.predicate));
        assertTrue(isEqual(anInt, parameters.batchSize));
        assertTrue(isEqual(anInt, parameters.bufferSize));
        assertTrue(isEqual(aLong, parameters.delaySeconds));
        assertTrue(isEqual(aBoolean, parameters.populate));
        assertTrue(isEqual(aBoolean, parameters.coalesce));
    }

    @Test
    public void test_ContinuousQueryPublisherCreateWithValueCodec_encodeResponse() {
        int fileClientMessageIndex = 657;
        ClientMessage encoded = ContinuousQueryPublisherCreateWithValueCodec.encodeResponse(aListOfDataToData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ContinuousQueryPublisherCreateCodec_decodeRequest() {
        int fileClientMessageIndex = 658;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ContinuousQueryPublisherCreateCodec.RequestParameters parameters = ContinuousQueryPublisherCreateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.mapName));
        assertTrue(isEqual(aString, parameters.cacheName));
        assertTrue(isEqual(aData, parameters.predicate));
        assertTrue(isEqual(anInt, parameters.batchSize));
        assertTrue(isEqual(anInt, parameters.bufferSize));
        assertTrue(isEqual(aLong, parameters.delaySeconds));
        assertTrue(isEqual(aBoolean, parameters.populate));
        assertTrue(isEqual(aBoolean, parameters.coalesce));
    }

    @Test
    public void test_ContinuousQueryPublisherCreateCodec_encodeResponse() {
        int fileClientMessageIndex = 659;
        ClientMessage encoded = ContinuousQueryPublisherCreateCodec.encodeResponse(aListOfData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ContinuousQueryMadePublishableCodec_decodeRequest() {
        int fileClientMessageIndex = 660;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ContinuousQueryMadePublishableCodec.RequestParameters parameters = ContinuousQueryMadePublishableCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.mapName));
        assertTrue(isEqual(aString, parameters.cacheName));
    }

    @Test
    public void test_ContinuousQueryMadePublishableCodec_encodeResponse() {
        int fileClientMessageIndex = 661;
        ClientMessage encoded = ContinuousQueryMadePublishableCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ContinuousQueryAddListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 662;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ContinuousQueryAddListenerCodec.RequestParameters parameters = ContinuousQueryAddListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.listenerName));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_ContinuousQueryAddListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 663;
        ClientMessage encoded = ContinuousQueryAddListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ContinuousQueryAddListenerCodec_encodeQueryCacheSingleEvent() {
        int fileClientMessageIndex = 664;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ContinuousQueryAddListenerCodec.encodeQueryCacheSingleEvent(aQueryCacheEventData);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ContinuousQueryAddListenerCodec_encodeQueryCacheBatchEvent() {
        int fileClientMessageIndex = 665;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = ContinuousQueryAddListenerCodec.encodeQueryCacheBatchEvent(aListOfQueryCacheEventData, aString, anInt);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ContinuousQuerySetReadCursorCodec_decodeRequest() {
        int fileClientMessageIndex = 666;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ContinuousQuerySetReadCursorCodec.RequestParameters parameters = ContinuousQuerySetReadCursorCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.mapName));
        assertTrue(isEqual(aString, parameters.cacheName));
        assertTrue(isEqual(aLong, parameters.sequence));
    }

    @Test
    public void test_ContinuousQuerySetReadCursorCodec_encodeResponse() {
        int fileClientMessageIndex = 667;
        ClientMessage encoded = ContinuousQuerySetReadCursorCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ContinuousQueryDestroyCacheCodec_decodeRequest() {
        int fileClientMessageIndex = 668;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ContinuousQueryDestroyCacheCodec.RequestParameters parameters = ContinuousQueryDestroyCacheCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.mapName));
        assertTrue(isEqual(aString, parameters.cacheName));
    }

    @Test
    public void test_ContinuousQueryDestroyCacheCodec_encodeResponse() {
        int fileClientMessageIndex = 669;
        ClientMessage encoded = ContinuousQueryDestroyCacheCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_RingbufferSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 670;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, RingbufferSizeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_RingbufferSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 671;
        ClientMessage encoded = RingbufferSizeCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_RingbufferTailSequenceCodec_decodeRequest() {
        int fileClientMessageIndex = 672;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, RingbufferTailSequenceCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_RingbufferTailSequenceCodec_encodeResponse() {
        int fileClientMessageIndex = 673;
        ClientMessage encoded = RingbufferTailSequenceCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_RingbufferHeadSequenceCodec_decodeRequest() {
        int fileClientMessageIndex = 674;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, RingbufferHeadSequenceCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_RingbufferHeadSequenceCodec_encodeResponse() {
        int fileClientMessageIndex = 675;
        ClientMessage encoded = RingbufferHeadSequenceCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_RingbufferCapacityCodec_decodeRequest() {
        int fileClientMessageIndex = 676;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, RingbufferCapacityCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_RingbufferCapacityCodec_encodeResponse() {
        int fileClientMessageIndex = 677;
        ClientMessage encoded = RingbufferCapacityCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_RingbufferRemainingCapacityCodec_decodeRequest() {
        int fileClientMessageIndex = 678;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, RingbufferRemainingCapacityCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_RingbufferRemainingCapacityCodec_encodeResponse() {
        int fileClientMessageIndex = 679;
        ClientMessage encoded = RingbufferRemainingCapacityCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_RingbufferAddCodec_decodeRequest() {
        int fileClientMessageIndex = 680;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        RingbufferAddCodec.RequestParameters parameters = RingbufferAddCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.overflowPolicy));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_RingbufferAddCodec_encodeResponse() {
        int fileClientMessageIndex = 681;
        ClientMessage encoded = RingbufferAddCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_RingbufferReadOneCodec_decodeRequest() {
        int fileClientMessageIndex = 682;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        RingbufferReadOneCodec.RequestParameters parameters = RingbufferReadOneCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.sequence));
    }

    @Test
    public void test_RingbufferReadOneCodec_encodeResponse() {
        int fileClientMessageIndex = 683;
        ClientMessage encoded = RingbufferReadOneCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_RingbufferAddAllCodec_decodeRequest() {
        int fileClientMessageIndex = 684;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        RingbufferAddAllCodec.RequestParameters parameters = RingbufferAddAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfData, parameters.valueList));
        assertTrue(isEqual(anInt, parameters.overflowPolicy));
    }

    @Test
    public void test_RingbufferAddAllCodec_encodeResponse() {
        int fileClientMessageIndex = 685;
        ClientMessage encoded = RingbufferAddAllCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_RingbufferReadManyCodec_decodeRequest() {
        int fileClientMessageIndex = 686;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        RingbufferReadManyCodec.RequestParameters parameters = RingbufferReadManyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.startSequence));
        assertTrue(isEqual(anInt, parameters.minCount));
        assertTrue(isEqual(anInt, parameters.maxCount));
        assertTrue(isEqual(null, parameters.filter));
    }

    @Test
    public void test_RingbufferReadManyCodec_encodeResponse() {
        int fileClientMessageIndex = 687;
        ClientMessage encoded = RingbufferReadManyCodec.encodeResponse(anInt, aListOfData, null, aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DurableExecutorShutdownCodec_decodeRequest() {
        int fileClientMessageIndex = 688;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, DurableExecutorShutdownCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_DurableExecutorShutdownCodec_encodeResponse() {
        int fileClientMessageIndex = 689;
        ClientMessage encoded = DurableExecutorShutdownCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DurableExecutorIsShutdownCodec_decodeRequest() {
        int fileClientMessageIndex = 690;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, DurableExecutorIsShutdownCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_DurableExecutorIsShutdownCodec_encodeResponse() {
        int fileClientMessageIndex = 691;
        ClientMessage encoded = DurableExecutorIsShutdownCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DurableExecutorSubmitToPartitionCodec_decodeRequest() {
        int fileClientMessageIndex = 692;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DurableExecutorSubmitToPartitionCodec.RequestParameters parameters = DurableExecutorSubmitToPartitionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.callable));
    }

    @Test
    public void test_DurableExecutorSubmitToPartitionCodec_encodeResponse() {
        int fileClientMessageIndex = 693;
        ClientMessage encoded = DurableExecutorSubmitToPartitionCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DurableExecutorRetrieveResultCodec_decodeRequest() {
        int fileClientMessageIndex = 694;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DurableExecutorRetrieveResultCodec.RequestParameters parameters = DurableExecutorRetrieveResultCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.sequence));
    }

    @Test
    public void test_DurableExecutorRetrieveResultCodec_encodeResponse() {
        int fileClientMessageIndex = 695;
        ClientMessage encoded = DurableExecutorRetrieveResultCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DurableExecutorDisposeResultCodec_decodeRequest() {
        int fileClientMessageIndex = 696;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DurableExecutorDisposeResultCodec.RequestParameters parameters = DurableExecutorDisposeResultCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.sequence));
    }

    @Test
    public void test_DurableExecutorDisposeResultCodec_encodeResponse() {
        int fileClientMessageIndex = 697;
        ClientMessage encoded = DurableExecutorDisposeResultCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DurableExecutorRetrieveAndDisposeResultCodec_decodeRequest() {
        int fileClientMessageIndex = 698;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DurableExecutorRetrieveAndDisposeResultCodec.RequestParameters parameters = DurableExecutorRetrieveAndDisposeResultCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.sequence));
    }

    @Test
    public void test_DurableExecutorRetrieveAndDisposeResultCodec_encodeResponse() {
        int fileClientMessageIndex = 699;
        ClientMessage encoded = DurableExecutorRetrieveAndDisposeResultCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CardinalityEstimatorAddCodec_decodeRequest() {
        int fileClientMessageIndex = 700;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CardinalityEstimatorAddCodec.RequestParameters parameters = CardinalityEstimatorAddCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.hash));
    }

    @Test
    public void test_CardinalityEstimatorAddCodec_encodeResponse() {
        int fileClientMessageIndex = 701;
        ClientMessage encoded = CardinalityEstimatorAddCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CardinalityEstimatorEstimateCodec_decodeRequest() {
        int fileClientMessageIndex = 702;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, CardinalityEstimatorEstimateCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_CardinalityEstimatorEstimateCodec_encodeResponse() {
        int fileClientMessageIndex = 703;
        ClientMessage encoded = CardinalityEstimatorEstimateCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorShutdownCodec_decodeRequest() {
        int fileClientMessageIndex = 704;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorShutdownCodec.RequestParameters parameters = ScheduledExecutorShutdownCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aUUID, parameters.memberUuid));
    }

    @Test
    public void test_ScheduledExecutorShutdownCodec_encodeResponse() {
        int fileClientMessageIndex = 705;
        ClientMessage encoded = ScheduledExecutorShutdownCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorSubmitToPartitionCodec_decodeRequest() {
        int fileClientMessageIndex = 706;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorSubmitToPartitionCodec.RequestParameters parameters = ScheduledExecutorSubmitToPartitionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aByte, parameters.type));
        assertTrue(isEqual(aString, parameters.taskName));
        assertTrue(isEqual(aData, parameters.task));
        assertTrue(isEqual(aLong, parameters.initialDelayInMillis));
        assertTrue(isEqual(aLong, parameters.periodInMillis));
        assertTrue(parameters.isAutoDisposableExists);
        assertTrue(isEqual(aBoolean, parameters.autoDisposable));
    }

    @Test
    public void test_ScheduledExecutorSubmitToPartitionCodec_encodeResponse() {
        int fileClientMessageIndex = 707;
        ClientMessage encoded = ScheduledExecutorSubmitToPartitionCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorSubmitToMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 708;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorSubmitToMemberCodec.RequestParameters parameters = ScheduledExecutorSubmitToMemberCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aUUID, parameters.memberUuid));
        assertTrue(isEqual(aByte, parameters.type));
        assertTrue(isEqual(aString, parameters.taskName));
        assertTrue(isEqual(aData, parameters.task));
        assertTrue(isEqual(aLong, parameters.initialDelayInMillis));
        assertTrue(isEqual(aLong, parameters.periodInMillis));
        assertTrue(parameters.isAutoDisposableExists);
        assertTrue(isEqual(aBoolean, parameters.autoDisposable));
    }

    @Test
    public void test_ScheduledExecutorSubmitToMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 709;
        ClientMessage encoded = ScheduledExecutorSubmitToMemberCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorGetAllScheduledFuturesCodec_decodeRequest() {
        int fileClientMessageIndex = 710;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, ScheduledExecutorGetAllScheduledFuturesCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_ScheduledExecutorGetAllScheduledFuturesCodec_encodeResponse() {
        int fileClientMessageIndex = 711;
        ClientMessage encoded = ScheduledExecutorGetAllScheduledFuturesCodec.encodeResponse(aListOfScheduledTaskHandler);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorGetStatsFromPartitionCodec_decodeRequest() {
        int fileClientMessageIndex = 712;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorGetStatsFromPartitionCodec.RequestParameters parameters = ScheduledExecutorGetStatsFromPartitionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
    }

    @Test
    public void test_ScheduledExecutorGetStatsFromPartitionCodec_encodeResponse() {
        int fileClientMessageIndex = 713;
        ClientMessage encoded = ScheduledExecutorGetStatsFromPartitionCodec.encodeResponse(aLong, aLong, aLong, aLong, aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorGetStatsFromMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 714;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorGetStatsFromMemberCodec.RequestParameters parameters = ScheduledExecutorGetStatsFromMemberCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
        assertTrue(isEqual(aUUID, parameters.memberUuid));
    }

    @Test
    public void test_ScheduledExecutorGetStatsFromMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 715;
        ClientMessage encoded = ScheduledExecutorGetStatsFromMemberCodec.encodeResponse(aLong, aLong, aLong, aLong, aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorGetDelayFromPartitionCodec_decodeRequest() {
        int fileClientMessageIndex = 716;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorGetDelayFromPartitionCodec.RequestParameters parameters = ScheduledExecutorGetDelayFromPartitionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
    }

    @Test
    public void test_ScheduledExecutorGetDelayFromPartitionCodec_encodeResponse() {
        int fileClientMessageIndex = 717;
        ClientMessage encoded = ScheduledExecutorGetDelayFromPartitionCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorGetDelayFromMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 718;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorGetDelayFromMemberCodec.RequestParameters parameters = ScheduledExecutorGetDelayFromMemberCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
        assertTrue(isEqual(aUUID, parameters.memberUuid));
    }

    @Test
    public void test_ScheduledExecutorGetDelayFromMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 719;
        ClientMessage encoded = ScheduledExecutorGetDelayFromMemberCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorCancelFromPartitionCodec_decodeRequest() {
        int fileClientMessageIndex = 720;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorCancelFromPartitionCodec.RequestParameters parameters = ScheduledExecutorCancelFromPartitionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
        assertTrue(isEqual(aBoolean, parameters.mayInterruptIfRunning));
    }

    @Test
    public void test_ScheduledExecutorCancelFromPartitionCodec_encodeResponse() {
        int fileClientMessageIndex = 721;
        ClientMessage encoded = ScheduledExecutorCancelFromPartitionCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorCancelFromMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 722;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorCancelFromMemberCodec.RequestParameters parameters = ScheduledExecutorCancelFromMemberCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
        assertTrue(isEqual(aUUID, parameters.memberUuid));
        assertTrue(isEqual(aBoolean, parameters.mayInterruptIfRunning));
    }

    @Test
    public void test_ScheduledExecutorCancelFromMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 723;
        ClientMessage encoded = ScheduledExecutorCancelFromMemberCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorIsCancelledFromPartitionCodec_decodeRequest() {
        int fileClientMessageIndex = 724;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorIsCancelledFromPartitionCodec.RequestParameters parameters = ScheduledExecutorIsCancelledFromPartitionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
    }

    @Test
    public void test_ScheduledExecutorIsCancelledFromPartitionCodec_encodeResponse() {
        int fileClientMessageIndex = 725;
        ClientMessage encoded = ScheduledExecutorIsCancelledFromPartitionCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorIsCancelledFromMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 726;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorIsCancelledFromMemberCodec.RequestParameters parameters = ScheduledExecutorIsCancelledFromMemberCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
        assertTrue(isEqual(aUUID, parameters.memberUuid));
    }

    @Test
    public void test_ScheduledExecutorIsCancelledFromMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 727;
        ClientMessage encoded = ScheduledExecutorIsCancelledFromMemberCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorIsDoneFromPartitionCodec_decodeRequest() {
        int fileClientMessageIndex = 728;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorIsDoneFromPartitionCodec.RequestParameters parameters = ScheduledExecutorIsDoneFromPartitionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
    }

    @Test
    public void test_ScheduledExecutorIsDoneFromPartitionCodec_encodeResponse() {
        int fileClientMessageIndex = 729;
        ClientMessage encoded = ScheduledExecutorIsDoneFromPartitionCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorIsDoneFromMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 730;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorIsDoneFromMemberCodec.RequestParameters parameters = ScheduledExecutorIsDoneFromMemberCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
        assertTrue(isEqual(aUUID, parameters.memberUuid));
    }

    @Test
    public void test_ScheduledExecutorIsDoneFromMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 731;
        ClientMessage encoded = ScheduledExecutorIsDoneFromMemberCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorGetResultFromPartitionCodec_decodeRequest() {
        int fileClientMessageIndex = 732;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorGetResultFromPartitionCodec.RequestParameters parameters = ScheduledExecutorGetResultFromPartitionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
    }

    @Test
    public void test_ScheduledExecutorGetResultFromPartitionCodec_encodeResponse() {
        int fileClientMessageIndex = 733;
        ClientMessage encoded = ScheduledExecutorGetResultFromPartitionCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorGetResultFromMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 734;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorGetResultFromMemberCodec.RequestParameters parameters = ScheduledExecutorGetResultFromMemberCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
        assertTrue(isEqual(aUUID, parameters.memberUuid));
    }

    @Test
    public void test_ScheduledExecutorGetResultFromMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 735;
        ClientMessage encoded = ScheduledExecutorGetResultFromMemberCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorDisposeFromPartitionCodec_decodeRequest() {
        int fileClientMessageIndex = 736;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorDisposeFromPartitionCodec.RequestParameters parameters = ScheduledExecutorDisposeFromPartitionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
    }

    @Test
    public void test_ScheduledExecutorDisposeFromPartitionCodec_encodeResponse() {
        int fileClientMessageIndex = 737;
        ClientMessage encoded = ScheduledExecutorDisposeFromPartitionCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ScheduledExecutorDisposeFromMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 738;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ScheduledExecutorDisposeFromMemberCodec.RequestParameters parameters = ScheduledExecutorDisposeFromMemberCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.schedulerName));
        assertTrue(isEqual(aString, parameters.taskName));
        assertTrue(isEqual(aUUID, parameters.memberUuid));
    }

    @Test
    public void test_ScheduledExecutorDisposeFromMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 739;
        ClientMessage encoded = ScheduledExecutorDisposeFromMemberCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddMultiMapConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 740;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddMultiMapConfigCodec.RequestParameters parameters = DynamicConfigAddMultiMapConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aString, parameters.collectionType));
        assertTrue(isEqual(null, parameters.listenerConfigs));
        assertTrue(isEqual(aBoolean, parameters.binary));
        assertTrue(isEqual(anInt, parameters.backupCount));
        assertTrue(isEqual(anInt, parameters.asyncBackupCount));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(isEqual(aString, parameters.mergePolicy));
        assertTrue(isEqual(anInt, parameters.mergeBatchSize));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddMultiMapConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 741;
        ClientMessage encoded = DynamicConfigAddMultiMapConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddRingbufferConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 742;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddRingbufferConfigCodec.RequestParameters parameters = DynamicConfigAddRingbufferConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.capacity));
        assertTrue(isEqual(anInt, parameters.backupCount));
        assertTrue(isEqual(anInt, parameters.asyncBackupCount));
        assertTrue(isEqual(anInt, parameters.timeToLiveSeconds));
        assertTrue(isEqual(aString, parameters.inMemoryFormat));
        assertTrue(isEqual(null, parameters.ringbufferStoreConfig));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(isEqual(aString, parameters.mergePolicy));
        assertTrue(isEqual(anInt, parameters.mergeBatchSize));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddRingbufferConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 743;
        ClientMessage encoded = DynamicConfigAddRingbufferConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddCardinalityEstimatorConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 744;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddCardinalityEstimatorConfigCodec.RequestParameters parameters = DynamicConfigAddCardinalityEstimatorConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.backupCount));
        assertTrue(isEqual(anInt, parameters.asyncBackupCount));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(isEqual(aString, parameters.mergePolicy));
        assertTrue(isEqual(anInt, parameters.mergeBatchSize));
    }

    @Test
    public void test_DynamicConfigAddCardinalityEstimatorConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 745;
        ClientMessage encoded = DynamicConfigAddCardinalityEstimatorConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddListConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 746;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddListConfigCodec.RequestParameters parameters = DynamicConfigAddListConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(null, parameters.listenerConfigs));
        assertTrue(isEqual(anInt, parameters.backupCount));
        assertTrue(isEqual(anInt, parameters.asyncBackupCount));
        assertTrue(isEqual(anInt, parameters.maxSize));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(isEqual(aString, parameters.mergePolicy));
        assertTrue(isEqual(anInt, parameters.mergeBatchSize));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddListConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 747;
        ClientMessage encoded = DynamicConfigAddListConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddSetConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 748;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddSetConfigCodec.RequestParameters parameters = DynamicConfigAddSetConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(null, parameters.listenerConfigs));
        assertTrue(isEqual(anInt, parameters.backupCount));
        assertTrue(isEqual(anInt, parameters.asyncBackupCount));
        assertTrue(isEqual(anInt, parameters.maxSize));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(isEqual(aString, parameters.mergePolicy));
        assertTrue(isEqual(anInt, parameters.mergeBatchSize));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddSetConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 749;
        ClientMessage encoded = DynamicConfigAddSetConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddReplicatedMapConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 750;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddReplicatedMapConfigCodec.RequestParameters parameters = DynamicConfigAddReplicatedMapConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aString, parameters.inMemoryFormat));
        assertTrue(isEqual(aBoolean, parameters.asyncFillup));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(aString, parameters.mergePolicy));
        assertTrue(isEqual(null, parameters.listenerConfigs));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(isEqual(anInt, parameters.mergeBatchSize));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddReplicatedMapConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 751;
        ClientMessage encoded = DynamicConfigAddReplicatedMapConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddTopicConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 752;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddTopicConfigCodec.RequestParameters parameters = DynamicConfigAddTopicConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.globalOrderingEnabled));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(aBoolean, parameters.multiThreadingEnabled));
        assertTrue(isEqual(null, parameters.listenerConfigs));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddTopicConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 753;
        ClientMessage encoded = DynamicConfigAddTopicConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddExecutorConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 754;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddExecutorConfigCodec.RequestParameters parameters = DynamicConfigAddExecutorConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.poolSize));
        assertTrue(isEqual(anInt, parameters.queueCapacity));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddExecutorConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 755;
        ClientMessage encoded = DynamicConfigAddExecutorConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddDurableExecutorConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 756;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddDurableExecutorConfigCodec.RequestParameters parameters = DynamicConfigAddDurableExecutorConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.poolSize));
        assertTrue(isEqual(anInt, parameters.durability));
        assertTrue(isEqual(anInt, parameters.capacity));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(parameters.isStatisticsEnabledExists);
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddDurableExecutorConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 757;
        ClientMessage encoded = DynamicConfigAddDurableExecutorConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddScheduledExecutorConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 758;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddScheduledExecutorConfigCodec.RequestParameters parameters = DynamicConfigAddScheduledExecutorConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.poolSize));
        assertTrue(isEqual(anInt, parameters.durability));
        assertTrue(isEqual(anInt, parameters.capacity));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(isEqual(aString, parameters.mergePolicy));
        assertTrue(isEqual(anInt, parameters.mergeBatchSize));
        assertTrue(parameters.isStatisticsEnabledExists);
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(parameters.isCapacityPolicyExists);
        assertTrue(isEqual(aByte, parameters.capacityPolicy));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddScheduledExecutorConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 759;
        ClientMessage encoded = DynamicConfigAddScheduledExecutorConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddQueueConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 760;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddQueueConfigCodec.RequestParameters parameters = DynamicConfigAddQueueConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(null, parameters.listenerConfigs));
        assertTrue(isEqual(anInt, parameters.backupCount));
        assertTrue(isEqual(anInt, parameters.asyncBackupCount));
        assertTrue(isEqual(anInt, parameters.maxSize));
        assertTrue(isEqual(anInt, parameters.emptyQueueTtl));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(isEqual(null, parameters.queueStoreConfig));
        assertTrue(isEqual(aString, parameters.mergePolicy));
        assertTrue(isEqual(anInt, parameters.mergeBatchSize));
        assertTrue(parameters.isPriorityComparatorClassNameExists);
        assertTrue(isEqual(null, parameters.priorityComparatorClassName));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddQueueConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 761;
        ClientMessage encoded = DynamicConfigAddQueueConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddMapConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 762;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddMapConfigCodec.RequestParameters parameters = DynamicConfigAddMapConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.backupCount));
        assertTrue(isEqual(anInt, parameters.asyncBackupCount));
        assertTrue(isEqual(anInt, parameters.timeToLiveSeconds));
        assertTrue(isEqual(anInt, parameters.maxIdleSeconds));
        assertTrue(isEqual(null, parameters.evictionConfig));
        assertTrue(isEqual(aBoolean, parameters.readBackupData));
        assertTrue(isEqual(aString, parameters.cacheDeserializedValues));
        assertTrue(isEqual(aString, parameters.mergePolicy));
        assertTrue(isEqual(anInt, parameters.mergeBatchSize));
        assertTrue(isEqual(aString, parameters.inMemoryFormat));
        assertTrue(isEqual(null, parameters.listenerConfigs));
        assertTrue(isEqual(null, parameters.partitionLostListenerConfigs));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(isEqual(null, parameters.mapStoreConfig));
        assertTrue(isEqual(null, parameters.nearCacheConfig));
        assertTrue(isEqual(null, parameters.wanReplicationRef));
        assertTrue(isEqual(null, parameters.indexConfigs));
        assertTrue(isEqual(null, parameters.attributeConfigs));
        assertTrue(isEqual(null, parameters.queryCacheConfigs));
        assertTrue(isEqual(null, parameters.partitioningStrategyClassName));
        assertTrue(isEqual(null, parameters.partitioningStrategyImplementation));
        assertTrue(isEqual(null, parameters.hotRestartConfig));
        assertTrue(isEqual(null, parameters.eventJournalConfig));
        assertTrue(isEqual(null, parameters.merkleTreeConfig));
        assertTrue(isEqual(anInt, parameters.metadataPolicy));
        assertTrue(parameters.isPerEntryStatsEnabledExists);
        assertTrue(isEqual(aBoolean, parameters.perEntryStatsEnabled));
        assertTrue(parameters.isDataPersistenceConfigExists);
        assertTrue(isEqual(aDataPersistenceConfig, parameters.dataPersistenceConfig));
        assertTrue(parameters.isTieredStoreConfigExists);
        assertTrue(isEqual(aTieredStoreConfig, parameters.tieredStoreConfig));
        assertTrue(parameters.isPartitioningAttributeConfigsExists);
        assertTrue(isEqual(null, parameters.partitioningAttributeConfigs));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddMapConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 763;
        ClientMessage encoded = DynamicConfigAddMapConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddReliableTopicConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 764;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddReliableTopicConfigCodec.RequestParameters parameters = DynamicConfigAddReliableTopicConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(null, parameters.listenerConfigs));
        assertTrue(isEqual(anInt, parameters.readBatchSize));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(aString, parameters.topicOverloadPolicy));
        assertTrue(isEqual(null, parameters.executor));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddReliableTopicConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 765;
        ClientMessage encoded = DynamicConfigAddReliableTopicConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddCacheConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 766;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddCacheConfigCodec.RequestParameters parameters = DynamicConfigAddCacheConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(null, parameters.keyType));
        assertTrue(isEqual(null, parameters.valueType));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(aBoolean, parameters.managementEnabled));
        assertTrue(isEqual(aBoolean, parameters.readThrough));
        assertTrue(isEqual(aBoolean, parameters.writeThrough));
        assertTrue(isEqual(null, parameters.cacheLoaderFactory));
        assertTrue(isEqual(null, parameters.cacheWriterFactory));
        assertTrue(isEqual(null, parameters.cacheLoader));
        assertTrue(isEqual(null, parameters.cacheWriter));
        assertTrue(isEqual(anInt, parameters.backupCount));
        assertTrue(isEqual(anInt, parameters.asyncBackupCount));
        assertTrue(isEqual(aString, parameters.inMemoryFormat));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(isEqual(null, parameters.mergePolicy));
        assertTrue(isEqual(anInt, parameters.mergeBatchSize));
        assertTrue(isEqual(aBoolean, parameters.disablePerEntryInvalidationEvents));
        assertTrue(isEqual(null, parameters.partitionLostListenerConfigs));
        assertTrue(isEqual(null, parameters.expiryPolicyFactoryClassName));
        assertTrue(isEqual(null, parameters.timedExpiryPolicyFactoryConfig));
        assertTrue(isEqual(null, parameters.cacheEntryListeners));
        assertTrue(isEqual(null, parameters.evictionConfig));
        assertTrue(isEqual(null, parameters.wanReplicationRef));
        assertTrue(isEqual(null, parameters.eventJournalConfig));
        assertTrue(isEqual(null, parameters.hotRestartConfig));
        assertTrue(parameters.isMerkleTreeConfigExists);
        assertTrue(isEqual(null, parameters.merkleTreeConfig));
        assertTrue(parameters.isDataPersistenceConfigExists);
        assertTrue(isEqual(aDataPersistenceConfig, parameters.dataPersistenceConfig));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddCacheConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 767;
        ClientMessage encoded = DynamicConfigAddCacheConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddFlakeIdGeneratorConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 768;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddFlakeIdGeneratorConfigCodec.RequestParameters parameters = DynamicConfigAddFlakeIdGeneratorConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.prefetchCount));
        assertTrue(isEqual(aLong, parameters.prefetchValidity));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(aLong, parameters.nodeIdOffset));
        assertTrue(isEqual(aLong, parameters.epochStart));
        assertTrue(isEqual(anInt, parameters.bitsSequence));
        assertTrue(isEqual(anInt, parameters.bitsNodeId));
        assertTrue(isEqual(aLong, parameters.allowedFutureMillis));
    }

    @Test
    public void test_DynamicConfigAddFlakeIdGeneratorConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 769;
        ClientMessage encoded = DynamicConfigAddFlakeIdGeneratorConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddPNCounterConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 770;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddPNCounterConfigCodec.RequestParameters parameters = DynamicConfigAddPNCounterConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.replicaCount));
        assertTrue(isEqual(aBoolean, parameters.statisticsEnabled));
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
    }

    @Test
    public void test_DynamicConfigAddPNCounterConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 771;
        ClientMessage encoded = DynamicConfigAddPNCounterConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddDataConnectionConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 772;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddDataConnectionConfigCodec.RequestParameters parameters = DynamicConfigAddDataConnectionConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aString, parameters.type));
        assertTrue(isEqual(aBoolean, parameters.shared));
        assertTrue(isEqual(aMapOfStringToString, parameters.properties));
    }

    @Test
    public void test_DynamicConfigAddDataConnectionConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 773;
        ClientMessage encoded = DynamicConfigAddDataConnectionConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddWanReplicationConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 774;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddWanReplicationConfigCodec.RequestParameters parameters = DynamicConfigAddWanReplicationConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(null, parameters.consumerConfig));
        assertTrue(isEqual(aListOfWanCustomPublisherConfigsHolders, parameters.customPublisherConfigs));
        assertTrue(isEqual(aListOfWanBatchPublisherConfigHolders, parameters.batchPublisherConfigs));
    }

    @Test
    public void test_DynamicConfigAddWanReplicationConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 775;
        ClientMessage encoded = DynamicConfigAddWanReplicationConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddUserCodeNamespaceConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 776;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddUserCodeNamespaceConfigCodec.RequestParameters parameters = DynamicConfigAddUserCodeNamespaceConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfResourceDefinitionHolders, parameters.resources));
    }

    @Test
    public void test_DynamicConfigAddUserCodeNamespaceConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 777;
        ClientMessage encoded = DynamicConfigAddUserCodeNamespaceConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_DynamicConfigAddVectorCollectionConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 778;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        DynamicConfigAddVectorCollectionConfigCodec.RequestParameters parameters = DynamicConfigAddVectorCollectionConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aList_VectorIndexConfig, parameters.indexConfigs));
        assertTrue(parameters.isBackupCountExists);
        assertTrue(isEqual(anInt, parameters.backupCount));
        assertTrue(parameters.isAsyncBackupCountExists);
        assertTrue(isEqual(anInt, parameters.asyncBackupCount));
        assertTrue(parameters.isSplitBrainProtectionNameExists);
        assertTrue(isEqual(null, parameters.splitBrainProtectionName));
        assertTrue(parameters.isMergePolicyExists);
        assertTrue(isEqual(aString, parameters.mergePolicy));
        assertTrue(parameters.isMergeBatchSizeExists);
        assertTrue(isEqual(anInt, parameters.mergeBatchSize));
        assertTrue(parameters.isUserCodeNamespaceExists);
        assertTrue(isEqual(null, parameters.userCodeNamespace));
    }

    @Test
    public void test_DynamicConfigAddVectorCollectionConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 779;
        ClientMessage encoded = DynamicConfigAddVectorCollectionConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_FlakeIdGeneratorNewIdBatchCodec_decodeRequest() {
        int fileClientMessageIndex = 780;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        FlakeIdGeneratorNewIdBatchCodec.RequestParameters parameters = FlakeIdGeneratorNewIdBatchCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(anInt, parameters.batchSize));
    }

    @Test
    public void test_FlakeIdGeneratorNewIdBatchCodec_encodeResponse() {
        int fileClientMessageIndex = 781;
        ClientMessage encoded = FlakeIdGeneratorNewIdBatchCodec.encodeResponse(aLong, aLong, anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_PNCounterGetCodec_decodeRequest() {
        int fileClientMessageIndex = 782;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        PNCounterGetCodec.RequestParameters parameters = PNCounterGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aListOfUuidToLong, parameters.replicaTimestamps));
        assertTrue(isEqual(aUUID, parameters.targetReplicaUUID));
    }

    @Test
    public void test_PNCounterGetCodec_encodeResponse() {
        int fileClientMessageIndex = 783;
        ClientMessage encoded = PNCounterGetCodec.encodeResponse(aLong, aListOfUuidToLong, anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_PNCounterAddCodec_decodeRequest() {
        int fileClientMessageIndex = 784;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        PNCounterAddCodec.RequestParameters parameters = PNCounterAddCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aLong, parameters.delta));
        assertTrue(isEqual(aBoolean, parameters.getBeforeUpdate));
        assertTrue(isEqual(aListOfUuidToLong, parameters.replicaTimestamps));
        assertTrue(isEqual(aUUID, parameters.targetReplicaUUID));
    }

    @Test
    public void test_PNCounterAddCodec_encodeResponse() {
        int fileClientMessageIndex = 785;
        ClientMessage encoded = PNCounterAddCodec.encodeResponse(aLong, aListOfUuidToLong, anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_PNCounterGetConfiguredReplicaCountCodec_decodeRequest() {
        int fileClientMessageIndex = 786;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, PNCounterGetConfiguredReplicaCountCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_PNCounterGetConfiguredReplicaCountCodec_encodeResponse() {
        int fileClientMessageIndex = 787;
        ClientMessage encoded = PNCounterGetConfiguredReplicaCountCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPGroupCreateCPGroupCodec_decodeRequest() {
        int fileClientMessageIndex = 788;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, CPGroupCreateCPGroupCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_CPGroupCreateCPGroupCodec_encodeResponse() {
        int fileClientMessageIndex = 789;
        ClientMessage encoded = CPGroupCreateCPGroupCodec.encodeResponse(aRaftGroupId);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPGroupDestroyCPObjectCodec_decodeRequest() {
        int fileClientMessageIndex = 790;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPGroupDestroyCPObjectCodec.RequestParameters parameters = CPGroupDestroyCPObjectCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.serviceName));
        assertTrue(isEqual(aString, parameters.objectName));
    }

    @Test
    public void test_CPGroupDestroyCPObjectCodec_encodeResponse() {
        int fileClientMessageIndex = 791;
        ClientMessage encoded = CPGroupDestroyCPObjectCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSessionCreateSessionCodec_decodeRequest() {
        int fileClientMessageIndex = 792;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPSessionCreateSessionCodec.RequestParameters parameters = CPSessionCreateSessionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.endpointName));
    }

    @Test
    public void test_CPSessionCreateSessionCodec_encodeResponse() {
        int fileClientMessageIndex = 793;
        ClientMessage encoded = CPSessionCreateSessionCodec.encodeResponse(aLong, aLong, aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSessionCloseSessionCodec_decodeRequest() {
        int fileClientMessageIndex = 794;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPSessionCloseSessionCodec.RequestParameters parameters = CPSessionCloseSessionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aLong, parameters.sessionId));
    }

    @Test
    public void test_CPSessionCloseSessionCodec_encodeResponse() {
        int fileClientMessageIndex = 795;
        ClientMessage encoded = CPSessionCloseSessionCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSessionHeartbeatSessionCodec_decodeRequest() {
        int fileClientMessageIndex = 796;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPSessionHeartbeatSessionCodec.RequestParameters parameters = CPSessionHeartbeatSessionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aLong, parameters.sessionId));
    }

    @Test
    public void test_CPSessionHeartbeatSessionCodec_encodeResponse() {
        int fileClientMessageIndex = 797;
        ClientMessage encoded = CPSessionHeartbeatSessionCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSessionGenerateThreadIdCodec_decodeRequest() {
        int fileClientMessageIndex = 798;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aRaftGroupId, CPSessionGenerateThreadIdCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_CPSessionGenerateThreadIdCodec_encodeResponse() {
        int fileClientMessageIndex = 799;
        ClientMessage encoded = CPSessionGenerateThreadIdCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCReadMetricsCodec_decodeRequest() {
        int fileClientMessageIndex = 800;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCReadMetricsCodec.RequestParameters parameters = MCReadMetricsCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aUUID, parameters.uuid));
        assertTrue(isEqual(aLong, parameters.fromSequence));
    }

    @Test
    public void test_MCReadMetricsCodec_encodeResponse() {
        int fileClientMessageIndex = 801;
        ClientMessage encoded = MCReadMetricsCodec.encodeResponse(aListOfLongToByteArray, aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCChangeClusterStateCodec_decodeRequest() {
        int fileClientMessageIndex = 802;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(anInt, MCChangeClusterStateCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MCChangeClusterStateCodec_encodeResponse() {
        int fileClientMessageIndex = 803;
        ClientMessage encoded = MCChangeClusterStateCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCGetMapConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 804;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MCGetMapConfigCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MCGetMapConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 805;
        ClientMessage encoded = MCGetMapConfigCodec.encodeResponse(anInt, anInt, anInt, anInt, anInt, anInt, anInt, aBoolean, anInt, aString, aListOfIndexConfigs, null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCUpdateMapConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 806;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCUpdateMapConfigCodec.RequestParameters parameters = MCUpdateMapConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.mapName));
        assertTrue(isEqual(anInt, parameters.timeToLiveSeconds));
        assertTrue(isEqual(anInt, parameters.maxIdleSeconds));
        assertTrue(isEqual(anInt, parameters.evictionPolicy));
        assertTrue(isEqual(aBoolean, parameters.readBackupData));
        assertTrue(isEqual(anInt, parameters.maxSize));
        assertTrue(isEqual(anInt, parameters.maxSizePolicy));
        assertTrue(parameters.isWanReplicationRefExists);
        assertTrue(isEqual(null, parameters.wanReplicationRef));
    }

    @Test
    public void test_MCUpdateMapConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 807;
        ClientMessage encoded = MCUpdateMapConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCGetMemberConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 808;
    }

    @Test
    public void test_MCGetMemberConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 809;
        ClientMessage encoded = MCGetMemberConfigCodec.encodeResponse(aString);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCRunGcCodec_decodeRequest() {
        int fileClientMessageIndex = 810;
    }

    @Test
    public void test_MCRunGcCodec_encodeResponse() {
        int fileClientMessageIndex = 811;
        ClientMessage encoded = MCRunGcCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCGetThreadDumpCodec_decodeRequest() {
        int fileClientMessageIndex = 812;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aBoolean, MCGetThreadDumpCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MCGetThreadDumpCodec_encodeResponse() {
        int fileClientMessageIndex = 813;
        ClientMessage encoded = MCGetThreadDumpCodec.encodeResponse(aString);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCShutdownMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 814;
    }

    @Test
    public void test_MCShutdownMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 815;
        ClientMessage encoded = MCShutdownMemberCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCPromoteLiteMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 816;
    }

    @Test
    public void test_MCPromoteLiteMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 817;
        ClientMessage encoded = MCPromoteLiteMemberCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCGetSystemPropertiesCodec_decodeRequest() {
        int fileClientMessageIndex = 818;
    }

    @Test
    public void test_MCGetSystemPropertiesCodec_encodeResponse() {
        int fileClientMessageIndex = 819;
        ClientMessage encoded = MCGetSystemPropertiesCodec.encodeResponse(aListOfStringToString);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCGetTimedMemberStateCodec_decodeRequest() {
        int fileClientMessageIndex = 820;
    }

    @Test
    public void test_MCGetTimedMemberStateCodec_encodeResponse() {
        int fileClientMessageIndex = 821;
        ClientMessage encoded = MCGetTimedMemberStateCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCMatchMCConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 822;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MCMatchMCConfigCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MCMatchMCConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 823;
        ClientMessage encoded = MCMatchMCConfigCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCApplyMCConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 824;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCApplyMCConfigCodec.RequestParameters parameters = MCApplyMCConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.eTag));
        assertTrue(isEqual(anInt, parameters.clientBwListMode));
        assertTrue(isEqual(aListOfClientBwListEntries, parameters.clientBwListEntries));
    }

    @Test
    public void test_MCApplyMCConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 825;
        ClientMessage encoded = MCApplyMCConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCGetClusterMetadataCodec_decodeRequest() {
        int fileClientMessageIndex = 826;
    }

    @Test
    public void test_MCGetClusterMetadataCodec_encodeResponse() {
        int fileClientMessageIndex = 827;
        ClientMessage encoded = MCGetClusterMetadataCodec.encodeResponse(aByte, aString, null, aLong, aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCShutdownClusterCodec_decodeRequest() {
        int fileClientMessageIndex = 828;
    }

    @Test
    public void test_MCShutdownClusterCodec_encodeResponse() {
        int fileClientMessageIndex = 829;
        ClientMessage encoded = MCShutdownClusterCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCChangeClusterVersionCodec_decodeRequest() {
        int fileClientMessageIndex = 830;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCChangeClusterVersionCodec.RequestParameters parameters = MCChangeClusterVersionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aByte, parameters.majorVersion));
        assertTrue(isEqual(aByte, parameters.minorVersion));
    }

    @Test
    public void test_MCChangeClusterVersionCodec_encodeResponse() {
        int fileClientMessageIndex = 831;
        ClientMessage encoded = MCChangeClusterVersionCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCRunScriptCodec_decodeRequest() {
        int fileClientMessageIndex = 832;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCRunScriptCodec.RequestParameters parameters = MCRunScriptCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.engine));
        assertTrue(isEqual(aString, parameters.script));
    }

    @Test
    public void test_MCRunScriptCodec_encodeResponse() {
        int fileClientMessageIndex = 833;
        ClientMessage encoded = MCRunScriptCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCRunConsoleCommandCodec_decodeRequest() {
        int fileClientMessageIndex = 834;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCRunConsoleCommandCodec.RequestParameters parameters = MCRunConsoleCommandCodec.decodeRequest(fromFile);
        assertTrue(isEqual(null, parameters.namespace));
        assertTrue(isEqual(aString, parameters.command));
    }

    @Test
    public void test_MCRunConsoleCommandCodec_encodeResponse() {
        int fileClientMessageIndex = 835;
        ClientMessage encoded = MCRunConsoleCommandCodec.encodeResponse(aString);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCChangeWanReplicationStateCodec_decodeRequest() {
        int fileClientMessageIndex = 836;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCChangeWanReplicationStateCodec.RequestParameters parameters = MCChangeWanReplicationStateCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.wanReplicationName));
        assertTrue(isEqual(aString, parameters.wanPublisherId));
        assertTrue(isEqual(aByte, parameters.newState));
    }

    @Test
    public void test_MCChangeWanReplicationStateCodec_encodeResponse() {
        int fileClientMessageIndex = 837;
        ClientMessage encoded = MCChangeWanReplicationStateCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCClearWanQueuesCodec_decodeRequest() {
        int fileClientMessageIndex = 838;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCClearWanQueuesCodec.RequestParameters parameters = MCClearWanQueuesCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.wanReplicationName));
        assertTrue(isEqual(aString, parameters.wanPublisherId));
    }

    @Test
    public void test_MCClearWanQueuesCodec_encodeResponse() {
        int fileClientMessageIndex = 839;
        ClientMessage encoded = MCClearWanQueuesCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCAddWanBatchPublisherConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 840;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCAddWanBatchPublisherConfigCodec.RequestParameters parameters = MCAddWanBatchPublisherConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aString, parameters.targetCluster));
        assertTrue(isEqual(null, parameters.publisherId));
        assertTrue(isEqual(aString, parameters.endpoints));
        assertTrue(isEqual(anInt, parameters.queueCapacity));
        assertTrue(isEqual(anInt, parameters.batchSize));
        assertTrue(isEqual(anInt, parameters.batchMaxDelayMillis));
        assertTrue(isEqual(anInt, parameters.responseTimeoutMillis));
        assertTrue(isEqual(anInt, parameters.ackType));
        assertTrue(isEqual(anInt, parameters.queueFullBehavior));
        assertTrue(parameters.isConsistencyCheckStrategyExists);
        assertTrue(isEqual(aByte, parameters.consistencyCheckStrategy));
    }

    @Test
    public void test_MCAddWanBatchPublisherConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 841;
        ClientMessage encoded = MCAddWanBatchPublisherConfigCodec.encodeResponse(aListOfStrings, aListOfStrings);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCWanSyncMapCodec_decodeRequest() {
        int fileClientMessageIndex = 842;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCWanSyncMapCodec.RequestParameters parameters = MCWanSyncMapCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.wanReplicationName));
        assertTrue(isEqual(aString, parameters.wanPublisherId));
        assertTrue(isEqual(anInt, parameters.wanSyncType));
        assertTrue(isEqual(null, parameters.mapName));
    }

    @Test
    public void test_MCWanSyncMapCodec_encodeResponse() {
        int fileClientMessageIndex = 843;
        ClientMessage encoded = MCWanSyncMapCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCCheckWanConsistencyCodec_decodeRequest() {
        int fileClientMessageIndex = 844;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCCheckWanConsistencyCodec.RequestParameters parameters = MCCheckWanConsistencyCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.wanReplicationName));
        assertTrue(isEqual(aString, parameters.wanPublisherId));
        assertTrue(isEqual(null, parameters.mapName));
    }

    @Test
    public void test_MCCheckWanConsistencyCodec_encodeResponse() {
        int fileClientMessageIndex = 845;
        ClientMessage encoded = MCCheckWanConsistencyCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCPollMCEventsCodec_decodeRequest() {
        int fileClientMessageIndex = 846;
    }

    @Test
    public void test_MCPollMCEventsCodec_encodeResponse() {
        int fileClientMessageIndex = 847;
        ClientMessage encoded = MCPollMCEventsCodec.encodeResponse(aListOfMCEvents);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCGetCPMembersCodec_decodeRequest() {
        int fileClientMessageIndex = 848;
    }

    @Test
    public void test_MCGetCPMembersCodec_encodeResponse() {
        int fileClientMessageIndex = 849;
        ClientMessage encoded = MCGetCPMembersCodec.encodeResponse(aListOfUUIDToUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCPromoteToCPMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 850;
    }

    @Test
    public void test_MCPromoteToCPMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 851;
        ClientMessage encoded = MCPromoteToCPMemberCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCRemoveCPMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 852;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aUUID, MCRemoveCPMemberCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MCRemoveCPMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 853;
        ClientMessage encoded = MCRemoveCPMemberCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCResetCPSubsystemCodec_decodeRequest() {
        int fileClientMessageIndex = 854;
    }

    @Test
    public void test_MCResetCPSubsystemCodec_encodeResponse() {
        int fileClientMessageIndex = 855;
        ClientMessage encoded = MCResetCPSubsystemCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCTriggerPartialStartCodec_decodeRequest() {
        int fileClientMessageIndex = 856;
    }

    @Test
    public void test_MCTriggerPartialStartCodec_encodeResponse() {
        int fileClientMessageIndex = 857;
        ClientMessage encoded = MCTriggerPartialStartCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCTriggerForceStartCodec_decodeRequest() {
        int fileClientMessageIndex = 858;
    }

    @Test
    public void test_MCTriggerForceStartCodec_encodeResponse() {
        int fileClientMessageIndex = 859;
        ClientMessage encoded = MCTriggerForceStartCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCTriggerHotRestartBackupCodec_decodeRequest() {
        int fileClientMessageIndex = 860;
    }

    @Test
    public void test_MCTriggerHotRestartBackupCodec_encodeResponse() {
        int fileClientMessageIndex = 861;
        ClientMessage encoded = MCTriggerHotRestartBackupCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCInterruptHotRestartBackupCodec_decodeRequest() {
        int fileClientMessageIndex = 862;
    }

    @Test
    public void test_MCInterruptHotRestartBackupCodec_encodeResponse() {
        int fileClientMessageIndex = 863;
        ClientMessage encoded = MCInterruptHotRestartBackupCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCResetQueueAgeStatisticsCodec_decodeRequest() {
        int fileClientMessageIndex = 864;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MCResetQueueAgeStatisticsCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MCResetQueueAgeStatisticsCodec_encodeResponse() {
        int fileClientMessageIndex = 865;
        ClientMessage encoded = MCResetQueueAgeStatisticsCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCReloadConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 866;
    }

    @Test
    public void test_MCReloadConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 867;
        ClientMessage encoded = MCReloadConfigCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCUpdateConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 868;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, MCUpdateConfigCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_MCUpdateConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 869;
        ClientMessage encoded = MCUpdateConfigCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCForceCloseCPSessionCodec_decodeRequest() {
        int fileClientMessageIndex = 870;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCForceCloseCPSessionCodec.RequestParameters parameters = MCForceCloseCPSessionCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.groupName));
        assertTrue(isEqual(aLong, parameters.sessionId));
    }

    @Test
    public void test_MCForceCloseCPSessionCodec_encodeResponse() {
        int fileClientMessageIndex = 871;
        ClientMessage encoded = MCForceCloseCPSessionCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCDemoteDataMemberCodec_decodeRequest() {
        int fileClientMessageIndex = 872;
    }

    @Test
    public void test_MCDemoteDataMemberCodec_encodeResponse() {
        int fileClientMessageIndex = 873;
        ClientMessage encoded = MCDemoteDataMemberCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCGetConfigFileContentCodec_decodeRequest() {
        int fileClientMessageIndex = 874;
    }

    @Test
    public void test_MCGetConfigFileContentCodec_encodeResponse() {
        int fileClientMessageIndex = 875;
        ClientMessage encoded = MCGetConfigFileContentCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCSetDiagnosticsConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 876;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        MCSetDiagnosticsConfigCodec.RequestParameters parameters = MCSetDiagnosticsConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aBoolean, parameters.enabled));
        assertTrue(isEqual(aString, parameters.outputType));
        assertTrue(isEqual(aBoolean, parameters.includeEpochTime));
        assertTrue(isEqual(aFloat, parameters.maxRolledFileSizeInMB));
        assertTrue(isEqual(anInt, parameters.maxRolledFileCount));
        assertTrue(isEqual(aString, parameters.logDirectory));
        assertTrue(isEqual(null, parameters.fileNamePrefix));
        assertTrue(isEqual(null, parameters.properties));
        assertTrue(isEqual(anInt, parameters.autoOffDurationInMinutes));
    }

    @Test
    public void test_MCSetDiagnosticsConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 877;
        ClientMessage encoded = MCSetDiagnosticsConfigCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_MCGetDiagnosticsConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 878;
    }

    @Test
    public void test_MCGetDiagnosticsConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 879;
        ClientMessage encoded = MCGetDiagnosticsConfigCodec.encodeResponse(aBoolean, aString, aBoolean, aFloat, anInt, aString, null, null, anInt, aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SqlExecute_reservedCodec_decodeRequest() {
        int fileClientMessageIndex = 880;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SqlExecute_reservedCodec.RequestParameters parameters = SqlExecute_reservedCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.sql));
        assertTrue(isEqual(aListOfData, parameters.parameters));
        assertTrue(isEqual(aLong, parameters.timeoutMillis));
        assertTrue(isEqual(anInt, parameters.cursorBufferSize));
    }

    @Test
    public void test_SqlExecute_reservedCodec_encodeResponse() {
        int fileClientMessageIndex = 881;
        ClientMessage encoded = SqlExecute_reservedCodec.encodeResponse(null, null, null, aBoolean, aLong, null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SqlFetch_reservedCodec_decodeRequest() {
        int fileClientMessageIndex = 882;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SqlFetch_reservedCodec.RequestParameters parameters = SqlFetch_reservedCodec.decodeRequest(fromFile);
        assertTrue(isEqual(anSqlQueryId, parameters.queryId));
        assertTrue(isEqual(anInt, parameters.cursorBufferSize));
    }

    @Test
    public void test_SqlFetch_reservedCodec_encodeResponse() {
        int fileClientMessageIndex = 883;
        ClientMessage encoded = SqlFetch_reservedCodec.encodeResponse(null, aBoolean, null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SqlCloseCodec_decodeRequest() {
        int fileClientMessageIndex = 884;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(anSqlQueryId, SqlCloseCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_SqlCloseCodec_encodeResponse() {
        int fileClientMessageIndex = 885;
        ClientMessage encoded = SqlCloseCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SqlExecuteCodec_decodeRequest() {
        int fileClientMessageIndex = 886;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SqlExecuteCodec.RequestParameters parameters = SqlExecuteCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.sql));
        assertTrue(isEqual(aListOfData, parameters.parameters));
        assertTrue(isEqual(aLong, parameters.timeoutMillis));
        assertTrue(isEqual(anInt, parameters.cursorBufferSize));
        assertTrue(isEqual(null, parameters.schema));
        assertTrue(isEqual(aByte, parameters.expectedResultType));
        assertTrue(isEqual(anSqlQueryId, parameters.queryId));
        assertTrue(parameters.isSkipUpdateStatisticsExists);
        assertTrue(isEqual(aBoolean, parameters.skipUpdateStatistics));
    }

    @Test
    public void test_SqlExecuteCodec_encodeResponse() {
        int fileClientMessageIndex = 887;
        ClientMessage encoded = SqlExecuteCodec.encodeResponse(null, null, aLong, null, aBoolean, anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SqlFetchCodec_decodeRequest() {
        int fileClientMessageIndex = 888;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        SqlFetchCodec.RequestParameters parameters = SqlFetchCodec.decodeRequest(fromFile);
        assertTrue(isEqual(anSqlQueryId, parameters.queryId));
        assertTrue(isEqual(anInt, parameters.cursorBufferSize));
    }

    @Test
    public void test_SqlFetchCodec_encodeResponse() {
        int fileClientMessageIndex = 889;
        ClientMessage encoded = SqlFetchCodec.encodeResponse(null, null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_SqlMappingDdlCodec_decodeRequest() {
        int fileClientMessageIndex = 890;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, SqlMappingDdlCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_SqlMappingDdlCodec_encodeResponse() {
        int fileClientMessageIndex = 891;
        ClientMessage encoded = SqlMappingDdlCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSubsystemAddMembershipListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 892;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aBoolean, CPSubsystemAddMembershipListenerCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_CPSubsystemAddMembershipListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 893;
        ClientMessage encoded = CPSubsystemAddMembershipListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSubsystemAddMembershipListenerCodec_encodeMembershipEventEvent() {
        int fileClientMessageIndex = 894;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = CPSubsystemAddMembershipListenerCodec.encodeMembershipEventEvent(aCpMember, aByte);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSubsystemRemoveMembershipListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 895;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aUUID, CPSubsystemRemoveMembershipListenerCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_CPSubsystemRemoveMembershipListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 896;
        ClientMessage encoded = CPSubsystemRemoveMembershipListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSubsystemAddGroupAvailabilityListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 897;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aBoolean, CPSubsystemAddGroupAvailabilityListenerCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_CPSubsystemAddGroupAvailabilityListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 898;
        ClientMessage encoded = CPSubsystemAddGroupAvailabilityListenerCodec.encodeResponse(aUUID);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSubsystemAddGroupAvailabilityListenerCodec_encodeGroupAvailabilityEventEvent() {
        int fileClientMessageIndex = 899;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = CPSubsystemAddGroupAvailabilityListenerCodec.encodeGroupAvailabilityEventEvent(aRaftGroupId, aListOfCpMembers, aListOfCpMembers, aBoolean);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSubsystemRemoveGroupAvailabilityListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 900;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aUUID, CPSubsystemRemoveGroupAvailabilityListenerCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_CPSubsystemRemoveGroupAvailabilityListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 901;
        ClientMessage encoded = CPSubsystemRemoveGroupAvailabilityListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSubsystemGetCPGroupIdsCodec_decodeRequest() {
        int fileClientMessageIndex = 902;
    }

    @Test
    public void test_CPSubsystemGetCPGroupIdsCodec_encodeResponse() {
        int fileClientMessageIndex = 903;
        ClientMessage encoded = CPSubsystemGetCPGroupIdsCodec.encodeResponse(aListOfRaftGroupIds);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPSubsystemGetCPObjectInfosCodec_decodeRequest() {
        int fileClientMessageIndex = 904;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPSubsystemGetCPObjectInfosCodec.RequestParameters parameters = CPSubsystemGetCPObjectInfosCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.serviceName));
        assertTrue(isEqual(aBoolean, parameters.tombstone));
    }

    @Test
    public void test_CPSubsystemGetCPObjectInfosCodec_encodeResponse() {
        int fileClientMessageIndex = 905;
        ClientMessage encoded = CPSubsystemGetCPObjectInfosCodec.encodeResponse(aListOfStrings);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPMapGetCodec_decodeRequest() {
        int fileClientMessageIndex = 906;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPMapGetCodec.RequestParameters parameters = CPMapGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_CPMapGetCodec_encodeResponse() {
        int fileClientMessageIndex = 907;
        ClientMessage encoded = CPMapGetCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPMapPutCodec_decodeRequest() {
        int fileClientMessageIndex = 908;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPMapPutCodec.RequestParameters parameters = CPMapPutCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_CPMapPutCodec_encodeResponse() {
        int fileClientMessageIndex = 909;
        ClientMessage encoded = CPMapPutCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPMapSetCodec_decodeRequest() {
        int fileClientMessageIndex = 910;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPMapSetCodec.RequestParameters parameters = CPMapSetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_CPMapSetCodec_encodeResponse() {
        int fileClientMessageIndex = 911;
        ClientMessage encoded = CPMapSetCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPMapRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 912;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPMapRemoveCodec.RequestParameters parameters = CPMapRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_CPMapRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 913;
        ClientMessage encoded = CPMapRemoveCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPMapDeleteCodec_decodeRequest() {
        int fileClientMessageIndex = 914;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPMapDeleteCodec.RequestParameters parameters = CPMapDeleteCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_CPMapDeleteCodec_encodeResponse() {
        int fileClientMessageIndex = 915;
        ClientMessage encoded = CPMapDeleteCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPMapCompareAndSetCodec_decodeRequest() {
        int fileClientMessageIndex = 916;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPMapCompareAndSetCodec.RequestParameters parameters = CPMapCompareAndSetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.expectedValue));
        assertTrue(isEqual(aData, parameters.newValue));
    }

    @Test
    public void test_CPMapCompareAndSetCodec_encodeResponse() {
        int fileClientMessageIndex = 917;
        ClientMessage encoded = CPMapCompareAndSetCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_CPMapPutIfAbsentCodec_decodeRequest() {
        int fileClientMessageIndex = 918;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        CPMapPutIfAbsentCodec.RequestParameters parameters = CPMapPutIfAbsentCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aRaftGroupId, parameters.groupId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aData, parameters.value));
    }

    @Test
    public void test_CPMapPutIfAbsentCodec_encodeResponse() {
        int fileClientMessageIndex = 919;
        ClientMessage encoded = CPMapPutIfAbsentCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_VectorCollectionPutCodec_decodeRequest() {
        int fileClientMessageIndex = 920;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        VectorCollectionPutCodec.RequestParameters parameters = VectorCollectionPutCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aVectorDocument, parameters.value));
    }

    @Test
    public void test_VectorCollectionPutCodec_encodeResponse() {
        int fileClientMessageIndex = 921;
        ClientMessage encoded = VectorCollectionPutCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_VectorCollectionPutIfAbsentCodec_decodeRequest() {
        int fileClientMessageIndex = 922;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        VectorCollectionPutIfAbsentCodec.RequestParameters parameters = VectorCollectionPutIfAbsentCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aVectorDocument, parameters.value));
    }

    @Test
    public void test_VectorCollectionPutIfAbsentCodec_encodeResponse() {
        int fileClientMessageIndex = 923;
        ClientMessage encoded = VectorCollectionPutIfAbsentCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_VectorCollectionPutAllCodec_decodeRequest() {
        int fileClientMessageIndex = 924;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        VectorCollectionPutAllCodec.RequestParameters parameters = VectorCollectionPutAllCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aEntryList_Data_VectorDocument, parameters.entries));
    }

    @Test
    public void test_VectorCollectionPutAllCodec_encodeResponse() {
        int fileClientMessageIndex = 925;
        ClientMessage encoded = VectorCollectionPutAllCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_VectorCollectionGetCodec_decodeRequest() {
        int fileClientMessageIndex = 926;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        VectorCollectionGetCodec.RequestParameters parameters = VectorCollectionGetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_VectorCollectionGetCodec_encodeResponse() {
        int fileClientMessageIndex = 927;
        ClientMessage encoded = VectorCollectionGetCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_VectorCollectionRemoveCodec_decodeRequest() {
        int fileClientMessageIndex = 928;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        VectorCollectionRemoveCodec.RequestParameters parameters = VectorCollectionRemoveCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_VectorCollectionRemoveCodec_encodeResponse() {
        int fileClientMessageIndex = 929;
        ClientMessage encoded = VectorCollectionRemoveCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_VectorCollectionSetCodec_decodeRequest() {
        int fileClientMessageIndex = 930;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        VectorCollectionSetCodec.RequestParameters parameters = VectorCollectionSetCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
        assertTrue(isEqual(aVectorDocument, parameters.value));
    }

    @Test
    public void test_VectorCollectionSetCodec_encodeResponse() {
        int fileClientMessageIndex = 931;
        ClientMessage encoded = VectorCollectionSetCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_VectorCollectionDeleteCodec_decodeRequest() {
        int fileClientMessageIndex = 932;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        VectorCollectionDeleteCodec.RequestParameters parameters = VectorCollectionDeleteCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aData, parameters.key));
    }

    @Test
    public void test_VectorCollectionDeleteCodec_encodeResponse() {
        int fileClientMessageIndex = 933;
        ClientMessage encoded = VectorCollectionDeleteCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_VectorCollectionSearchNearVectorCodec_decodeRequest() {
        int fileClientMessageIndex = 934;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        VectorCollectionSearchNearVectorCodec.RequestParameters parameters = VectorCollectionSearchNearVectorCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aList_VectorPair, parameters.vectors));
        assertTrue(isEqual(aVectorSearchOptions, parameters.options));
    }

    @Test
    public void test_VectorCollectionSearchNearVectorCodec_encodeResponse() {
        int fileClientMessageIndex = 935;
        ClientMessage encoded = VectorCollectionSearchNearVectorCodec.encodeResponse(aList_VectorSearchResult);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_VectorCollectionOptimizeCodec_decodeRequest() {
        int fileClientMessageIndex = 936;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        VectorCollectionOptimizeCodec.RequestParameters parameters = VectorCollectionOptimizeCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(null, parameters.indexName));
        assertTrue(parameters.isUuidExists);
        assertTrue(isEqual(null, parameters.uuid));
    }

    @Test
    public void test_VectorCollectionOptimizeCodec_encodeResponse() {
        int fileClientMessageIndex = 937;
        ClientMessage encoded = VectorCollectionOptimizeCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_VectorCollectionClearCodec_decodeRequest() {
        int fileClientMessageIndex = 938;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, VectorCollectionClearCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_VectorCollectionClearCodec_encodeResponse() {
        int fileClientMessageIndex = 939;
        ClientMessage encoded = VectorCollectionClearCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_VectorCollectionSizeCodec_decodeRequest() {
        int fileClientMessageIndex = 940;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aString, VectorCollectionSizeCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_VectorCollectionSizeCodec_encodeResponse() {
        int fileClientMessageIndex = 941;
        ClientMessage encoded = VectorCollectionSizeCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_ExperimentalPipelineSubmitCodec_decodeRequest() {
        int fileClientMessageIndex = 942;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ExperimentalPipelineSubmitCodec.RequestParameters parameters = ExperimentalPipelineSubmitCodec.decodeRequest(fromFile);
        assertTrue(isEqual(null, parameters.jobName));
        assertTrue(isEqual(aString, parameters.pipelineDefinition));
        assertTrue(isEqual(null, parameters.resourceBundle));
        assertTrue(isEqual(anInt, parameters.resourceBundleChecksum));
    }

    @Test
    public void test_ExperimentalPipelineSubmitCodec_encodeResponse() {
        int fileClientMessageIndex = 943;
        ClientMessage encoded = ExperimentalPipelineSubmitCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetSubmitJobCodec_decodeRequest() {
        int fileClientMessageIndex = 944;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetSubmitJobCodec.RequestParameters parameters = JetSubmitJobCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aLong, parameters.jobId));
        assertTrue(isEqual(aData, parameters.dag));
        assertTrue(isEqual(null, parameters.jobConfig));
        assertTrue(parameters.isLightJobCoordinatorExists);
        assertTrue(isEqual(null, parameters.lightJobCoordinator));
    }

    @Test
    public void test_JetSubmitJobCodec_encodeResponse() {
        int fileClientMessageIndex = 945;
        ClientMessage encoded = JetSubmitJobCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetTerminateJobCodec_decodeRequest() {
        int fileClientMessageIndex = 946;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetTerminateJobCodec.RequestParameters parameters = JetTerminateJobCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aLong, parameters.jobId));
        assertTrue(isEqual(anInt, parameters.terminateMode));
        assertTrue(parameters.isLightJobCoordinatorExists);
        assertTrue(isEqual(null, parameters.lightJobCoordinator));
    }

    @Test
    public void test_JetTerminateJobCodec_encodeResponse() {
        int fileClientMessageIndex = 947;
        ClientMessage encoded = JetTerminateJobCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetGetJobStatusCodec_decodeRequest() {
        int fileClientMessageIndex = 948;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aLong, JetGetJobStatusCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_JetGetJobStatusCodec_encodeResponse() {
        int fileClientMessageIndex = 949;
        ClientMessage encoded = JetGetJobStatusCodec.encodeResponse(anInt);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetGetJobIdsCodec_decodeRequest() {
        int fileClientMessageIndex = 950;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetGetJobIdsCodec.RequestParameters parameters = JetGetJobIdsCodec.decodeRequest(fromFile);
        assertTrue(parameters.isOnlyNameExists);
        assertTrue(isEqual(null, parameters.onlyName));
        assertTrue(parameters.isOnlyJobIdExists);
        assertTrue(isEqual(aLong, parameters.onlyJobId));
    }

    @Test
    public void test_JetGetJobIdsCodec_encodeResponse() {
        int fileClientMessageIndex = 951;
        ClientMessage encoded = JetGetJobIdsCodec.encodeResponse(aData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetJoinSubmittedJobCodec_decodeRequest() {
        int fileClientMessageIndex = 952;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetJoinSubmittedJobCodec.RequestParameters parameters = JetJoinSubmittedJobCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aLong, parameters.jobId));
        assertTrue(parameters.isLightJobCoordinatorExists);
        assertTrue(isEqual(null, parameters.lightJobCoordinator));
    }

    @Test
    public void test_JetJoinSubmittedJobCodec_encodeResponse() {
        int fileClientMessageIndex = 953;
        ClientMessage encoded = JetJoinSubmittedJobCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetGetJobSubmissionTimeCodec_decodeRequest() {
        int fileClientMessageIndex = 954;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetGetJobSubmissionTimeCodec.RequestParameters parameters = JetGetJobSubmissionTimeCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aLong, parameters.jobId));
        assertTrue(parameters.isLightJobCoordinatorExists);
        assertTrue(isEqual(null, parameters.lightJobCoordinator));
    }

    @Test
    public void test_JetGetJobSubmissionTimeCodec_encodeResponse() {
        int fileClientMessageIndex = 955;
        ClientMessage encoded = JetGetJobSubmissionTimeCodec.encodeResponse(aLong);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetGetJobConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 956;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetGetJobConfigCodec.RequestParameters parameters = JetGetJobConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aLong, parameters.jobId));
        assertTrue(parameters.isLightJobCoordinatorExists);
        assertTrue(isEqual(null, parameters.lightJobCoordinator));
    }

    @Test
    public void test_JetGetJobConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 957;
        ClientMessage encoded = JetGetJobConfigCodec.encodeResponse(aData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetResumeJobCodec_decodeRequest() {
        int fileClientMessageIndex = 958;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aLong, JetResumeJobCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_JetResumeJobCodec_encodeResponse() {
        int fileClientMessageIndex = 959;
        ClientMessage encoded = JetResumeJobCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetExportSnapshotCodec_decodeRequest() {
        int fileClientMessageIndex = 960;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetExportSnapshotCodec.RequestParameters parameters = JetExportSnapshotCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aLong, parameters.jobId));
        assertTrue(isEqual(aString, parameters.name));
        assertTrue(isEqual(aBoolean, parameters.cancelJob));
    }

    @Test
    public void test_JetExportSnapshotCodec_encodeResponse() {
        int fileClientMessageIndex = 961;
        ClientMessage encoded = JetExportSnapshotCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetGetJobSummaryListCodec_decodeRequest() {
        int fileClientMessageIndex = 962;
    }

    @Test
    public void test_JetGetJobSummaryListCodec_encodeResponse() {
        int fileClientMessageIndex = 963;
        ClientMessage encoded = JetGetJobSummaryListCodec.encodeResponse(aData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetExistsDistributedObjectCodec_decodeRequest() {
        int fileClientMessageIndex = 964;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetExistsDistributedObjectCodec.RequestParameters parameters = JetExistsDistributedObjectCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aString, parameters.serviceName));
        assertTrue(isEqual(aString, parameters.objectName));
    }

    @Test
    public void test_JetExistsDistributedObjectCodec_encodeResponse() {
        int fileClientMessageIndex = 965;
        ClientMessage encoded = JetExistsDistributedObjectCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetGetJobMetricsCodec_decodeRequest() {
        int fileClientMessageIndex = 966;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aLong, JetGetJobMetricsCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_JetGetJobMetricsCodec_encodeResponse() {
        int fileClientMessageIndex = 967;
        ClientMessage encoded = JetGetJobMetricsCodec.encodeResponse(aData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetGetJobSuspensionCauseCodec_decodeRequest() {
        int fileClientMessageIndex = 968;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aLong, JetGetJobSuspensionCauseCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_JetGetJobSuspensionCauseCodec_encodeResponse() {
        int fileClientMessageIndex = 969;
        ClientMessage encoded = JetGetJobSuspensionCauseCodec.encodeResponse(aData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetGetJobAndSqlSummaryListCodec_decodeRequest() {
        int fileClientMessageIndex = 970;
    }

    @Test
    public void test_JetGetJobAndSqlSummaryListCodec_encodeResponse() {
        int fileClientMessageIndex = 971;
        ClientMessage encoded = JetGetJobAndSqlSummaryListCodec.encodeResponse(aListJobAndSqlSummary);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetIsJobUserCancelledCodec_decodeRequest() {
        int fileClientMessageIndex = 972;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        assertTrue(isEqual(aLong, JetIsJobUserCancelledCodec.decodeRequest(fromFile)));
    }

    @Test
    public void test_JetIsJobUserCancelledCodec_encodeResponse() {
        int fileClientMessageIndex = 973;
        ClientMessage encoded = JetIsJobUserCancelledCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetUploadJobMetaDataCodec_decodeRequest() {
        int fileClientMessageIndex = 974;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetUploadJobMetaDataCodec.RequestParameters parameters = JetUploadJobMetaDataCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aUUID, parameters.sessionId));
        assertTrue(isEqual(aBoolean, parameters.jarOnMember));
        assertTrue(isEqual(aString, parameters.fileName));
        assertTrue(isEqual(aString, parameters.sha256Hex));
        assertTrue(isEqual(null, parameters.snapshotName));
        assertTrue(isEqual(null, parameters.jobName));
        assertTrue(isEqual(null, parameters.mainClass));
        assertTrue(isEqual(aListOfStrings, parameters.jobParameters));
    }

    @Test
    public void test_JetUploadJobMetaDataCodec_encodeResponse() {
        int fileClientMessageIndex = 975;
        ClientMessage encoded = JetUploadJobMetaDataCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetUploadJobMultipartCodec_decodeRequest() {
        int fileClientMessageIndex = 976;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetUploadJobMultipartCodec.RequestParameters parameters = JetUploadJobMultipartCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aUUID, parameters.sessionId));
        assertTrue(isEqual(anInt, parameters.currentPartNumber));
        assertTrue(isEqual(anInt, parameters.totalPartNumber));
        assertTrue(isEqual(aByteArray, parameters.partData));
        assertTrue(isEqual(anInt, parameters.partSize));
        assertTrue(isEqual(aString, parameters.sha256Hex));
    }

    @Test
    public void test_JetUploadJobMultipartCodec_encodeResponse() {
        int fileClientMessageIndex = 977;
        ClientMessage encoded = JetUploadJobMultipartCodec.encodeResponse();
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetAddJobStatusListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 978;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetAddJobStatusListenerCodec.RequestParameters parameters = JetAddJobStatusListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aLong, parameters.jobId));
        assertTrue(isEqual(null, parameters.lightJobCoordinator));
        assertTrue(isEqual(aBoolean, parameters.localOnly));
    }

    @Test
    public void test_JetAddJobStatusListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 979;
        ClientMessage encoded = JetAddJobStatusListenerCodec.encodeResponse(null);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetAddJobStatusListenerCodec_encodeJobStatusEvent() {
        int fileClientMessageIndex = 980;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        ClientMessage encoded = JetAddJobStatusListenerCodec.encodeJobStatusEvent(aLong, anInt, anInt, null, aBoolean);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetRemoveJobStatusListenerCodec_decodeRequest() {
        int fileClientMessageIndex = 981;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetRemoveJobStatusListenerCodec.RequestParameters parameters = JetRemoveJobStatusListenerCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aLong, parameters.jobId));
        assertTrue(isEqual(aUUID, parameters.registrationId));
    }

    @Test
    public void test_JetRemoveJobStatusListenerCodec_encodeResponse() {
        int fileClientMessageIndex = 982;
        ClientMessage encoded = JetRemoveJobStatusListenerCodec.encodeResponse(aBoolean);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    @Test
    public void test_JetUpdateJobConfigCodec_decodeRequest() {
        int fileClientMessageIndex = 983;
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        JetUpdateJobConfigCodec.RequestParameters parameters = JetUpdateJobConfigCodec.decodeRequest(fromFile);
        assertTrue(isEqual(aLong, parameters.jobId));
        assertTrue(isEqual(aData, parameters.deltaConfig));
    }

    @Test
    public void test_JetUpdateJobConfigCodec_encodeResponse() {
        int fileClientMessageIndex = 984;
        ClientMessage encoded = JetUpdateJobConfigCodec.encodeResponse(aData);
        ClientMessage fromFile = clientMessages.get(fileClientMessageIndex);
        compareClientMessages(fromFile, encoded);
    }

    private void compareClientMessages(ClientMessage binaryMessage, ClientMessage encodedMessage) {
        ClientMessage.Frame binaryFrame, encodedFrame;

        ClientMessage.ForwardFrameIterator binaryFrameIterator = binaryMessage.frameIterator();
        ClientMessage.ForwardFrameIterator encodedFrameIterator = encodedMessage.frameIterator();
        assertTrue("Client message that is read from the binary file does not have any frames", binaryFrameIterator.hasNext());

        while (binaryFrameIterator.hasNext()) {
            binaryFrame = binaryFrameIterator.next();
            encodedFrame = encodedFrameIterator.next();
            assertNotNull("Encoded client message has less frames.", encodedFrame);

            if (binaryFrame.isEndFrame() && !encodedFrame.isEndFrame()) {
                if (encodedFrame.isBeginFrame()) {
                    HazelcastClientUtil.fastForwardToEndFrame(encodedFrameIterator);
                }
                encodedFrame = HazelcastClientUtil.fastForwardToEndFrame(encodedFrameIterator);
            }

            boolean isFinal = binaryFrameIterator.peekNext() == null;
            int flags = isFinal ? encodedFrame.flags | IS_FINAL_FLAG : encodedFrame.flags;
            assertArrayEquals("Frames have different contents", binaryFrame.content, Arrays.copyOf(encodedFrame.content, binaryFrame.content.length));
            assertEquals("Frames have different flags", binaryFrame.flags, flags);
        }
    }
}