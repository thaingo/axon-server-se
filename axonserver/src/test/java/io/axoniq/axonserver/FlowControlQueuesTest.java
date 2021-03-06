/*
 * Copyright (c) 2017-2019 AxonIQ B.V. and/or licensed to AxonIQ B.V.
 * under one or more contributor license agreements.
 *
 *  Licensed under the AxonIQ Open Source License Agreement v1.0;
 *  you may not use this file except in compliance with the license.
 *
 */

package io.axoniq.axonserver;

import io.axoniq.axonserver.message.FlowControlQueues;
import org.junit.*;

import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * @author Marc Gathier
 */
public class FlowControlQueuesTest {
    private FlowControlQueues<QueueElement> testSubject;

    @Before
    public void setup() {
        testSubject = new FlowControlQueues<>( Comparator.comparing(QueueElement::getPrioKey));
    }

    @Test
    public void take() throws Exception {
        testSubject.put("one", new QueueElement("A"));
        testSubject.put("one", new QueueElement("B"));
        assertEquals("A", testSubject.take("one").prioKey);
        assertEquals("B", testSubject.take("one").prioKey);
    }

    @Test
    public void takeHigestPrio() throws Exception {
        testSubject.put("one", new QueueElement("B"));
        testSubject.put("one", new QueueElement("A"));
        assertEquals("A", testSubject.take("one").prioKey);
        assertEquals("B", testSubject.take("one").prioKey);
    }

    @Test
    public void samePrioMaintainOrder() throws Exception {
        testSubject.put("one", new QueueElement("A", "1"));
        testSubject.put("one", new QueueElement("A", "2"));
        testSubject.put("one", new QueueElement("A", "3"));
        assertEquals("1", testSubject.take("one").value);
        assertEquals("2", testSubject.take("one").value);
        assertEquals("3", testSubject.take("one").value);
    }

    @Test
    public void move() throws Exception {
        testSubject.put("one", new QueueElement("A"));
        testSubject.put("two", new QueueElement("C"));
        testSubject.put("one", new QueueElement("B"));
        testSubject.move("two", cmd -> "one");
        assertEquals("A", testSubject.take("one").prioKey);
        assertEquals("B", testSubject.take("one").prioKey);
        assertEquals("C", testSubject.take("one").prioKey);
    }

    @Test
    public void move2() throws Exception {
        testSubject.put("one", new QueueElement("A"));
        testSubject.put("one", new QueueElement("B"));
        testSubject.put("two", new QueueElement("C"));
        testSubject.move("two", cmd -> "one");
        assertEquals("A", testSubject.take("one").prioKey);
        assertEquals("B", testSubject.take("one").prioKey);
        assertEquals("C", testSubject.take("one").prioKey);
    }

    public static class QueueElement {
        private final String prioKey;
        private final String value;

        public QueueElement(String prioKey) {
            this(prioKey, "none");
        }
        public QueueElement(String prioKey, String value) {
            this.prioKey = prioKey;
            this.value = value;
        }

        public String getPrioKey() {
            return prioKey;
        }
    }

}
