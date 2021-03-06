/*
 * Copyright (c) 2017-2019 AxonIQ B.V. and/or licensed to AxonIQ B.V.
 * under one or more contributor license agreements.
 *
 *  Licensed under the AxonIQ Open Source License Agreement v1.0;
 *  you may not use this file except in compliance with the license.
 *
 */

package io.axoniq.axonserver.topology;

import io.axoniq.axonserver.message.event.EventStore;

/**
 * Defines an interface to retrieve an event store for a context. Standard Edition only supports context "default", and
 * as it does not support clustering, the current node will always be master for the default context.

 * @author Marc Gathier
 * @since 4.0
 */
public interface EventStoreLocator {

    /**
     * Checks if a specific node is leader for the specified context.
     * @param nodeName the node to consider as leader
     * @param contextName the context name
     * @return true if node is leader
     */
    boolean isLeader(String nodeName, String contextName);

    /**
     * Retrieve an EventStore instance which can be used to store and retrieve events. Returns null when there is no leader for
     * the specified context.
     * @param context the context to get the eventstore for
     * @return an EventStore
     */
    EventStore getEventStore(String context);
}
