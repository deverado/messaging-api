package de.deverado.framework.messaging.api.dummy;/*
 * Copyright Georg Koester 2012-15. All rights reserved.
 */

import com.google.common.util.concurrent.ListenableFuture;
import de.deverado.framework.messaging.api.Subscription;
import de.deverado.framework.messaging.api.SubscriptionType;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SubscriptionDummyImpl implements Subscription {


    private SubscriptionType subscriptionType;
    private String topic;
    private ListenableFuture<Subscription> cancelFuture;

    public static SubscriptionDummyImpl create(SubscriptionType subscriptionType, String topic,
                                               ListenableFuture<Subscription> cancelFuture) {
        SubscriptionDummyImpl result = new SubscriptionDummyImpl();
        result.subscriptionType = subscriptionType;
        result.topic = topic;
        result.cancelFuture = cancelFuture;
        return result;
    }

    @Override
    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public ListenableFuture<Subscription> cancel() {
        return cancelFuture;
    }

    @Override
    public boolean isCancelRequested() {
        return cancelFuture.isCancelled();
    }

    @Override
    public boolean isCanceled() {
        return cancelFuture.isCancelled();
    }

    @Override
    public ListenableFuture<Subscription> getCancellationFuture() {
        return cancelFuture;
    }

    @Override
    public String toString() {
        return "SubscriptionDummyImpl{" +
                "topic='" + topic + '\'' +
                ", subscriptionType=" + subscriptionType +
                '}';
    }

}
