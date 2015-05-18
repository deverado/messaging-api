package de.deverado.framework.messaging.api;/*
 * Copyright Georg Koester 2012-15. All rights reserved.
 */

public enum SubscriptionType {

    /**
     * Events that are only meaningful at the current point in time or due to performance considerations.
     */
    NO_ACK,

    /**
     * Message must be acknowledged.
     */
    ACK,

    /**
     * Messages must be persisted so that they survive a long time. Acknowledgement required.
     */
    DURABLE;
}
