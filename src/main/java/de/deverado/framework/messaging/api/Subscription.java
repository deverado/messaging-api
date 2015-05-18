package de.deverado.framework.messaging.api;/*
 * Copyright Georg Koester 2012-15. All rights reserved.
 */

import com.google.common.util.concurrent.ListenableFuture;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface Subscription {

    SubscriptionType getSubscriptionType();

    String getTopic();

    ListenableFuture<Subscription> cancel();

    boolean isCancelRequested();

    boolean isCanceled();

    ListenableFuture<Subscription> getCancellationFuture();
}
