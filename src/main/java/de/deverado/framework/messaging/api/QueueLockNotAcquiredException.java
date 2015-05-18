package de.deverado.framework.messaging.api;/*
 * Copyright Georg Koester 2012-15. All rights reserved.
 */

public class QueueLockNotAcquiredException extends Exception {

    public QueueLockNotAcquiredException() {
    }

    public QueueLockNotAcquiredException(String s) {
        super(s);
    }

    public QueueLockNotAcquiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueueLockNotAcquiredException(Throwable cause) {
        super(cause);
    }

}
