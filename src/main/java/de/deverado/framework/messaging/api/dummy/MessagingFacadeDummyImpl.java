package de.deverado.framework.messaging.api.dummy;/*
 * Copyright Georg Koester 2012-15. All rights reserved.
 */

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.SettableFuture;
import de.deverado.framework.core.problemreporting.ProblemReporter;
import de.deverado.framework.messaging.api.Message;
import de.deverado.framework.messaging.api.MessageBuilder;
import de.deverado.framework.messaging.api.MessageHandler;
import de.deverado.framework.messaging.api.MessageScanHandler;
import de.deverado.framework.messaging.api.MessagingFacade;
import de.deverado.framework.messaging.api.Subscription;
import de.deverado.framework.messaging.api.SubscriptionType;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.concurrent.Executor;

/**
 * Use this to provide messaging implementations without actual messaging happening (e.g. if there are other means of
 * communications that work as a fallback).
 */
@ParametersAreNonnullByDefault
public class MessagingFacadeDummyImpl implements MessagingFacade {
    @Override
    public ListenableFuture<Subscription> subscribeQueued(String topic, MessageHandler handler,
                                                          SubscriptionType subsType, ProblemReporter problemReporter,
                                                          ListeningExecutorService executor,
                                                          boolean lockQueueForExclusiveProcessing) {
        Subscription value = SubscriptionDummyImpl.create(subsType, topic,
                SettableFuture.<Subscription>create());
        return Futures.immediateFuture(value);
    }

    @Override
    public ListenableFuture<Subscription> subscribeEvents(String topic, MessageHandler handler,
                                                          SubscriptionType subsType, ProblemReporter problemReporter,
                                                          ListeningExecutorService handlerExecutor) {
        Subscription value = SubscriptionDummyImpl.create(subsType, topic,
                SettableFuture.<Subscription>create());
        return Futures.immediateFuture(value);
    }

    @Override
    public ListenableFuture<Long> sendMessage(Message msg) {
        return Futures.immediateFuture(System.currentTimeMillis());
    }

    @Override
    public MessageBuilder createMessageBuilder() {
        return new MessageBuilderDummyImpl();
    }

    @Override
    public String getDeadLettersTopicName(String topic) {
        return topic + "-deadLetters";
    }

    @Override
    public ListenableFuture<Boolean> listTopics(Function<String, ?> handler, Executor handlerExecutor,
                                                ProblemReporter problemReporter) {
        return Futures.immediateFuture(Boolean.FALSE);
    }

    @Override
    public ListenableFuture<Boolean> scanMessages(String topic, MessageScanHandler handler, long start, long end,
                                                  SubscriptionType subscriptionType, boolean scanOnlyUnprocessed,
                                                  ProblemReporter problemReporter,
                                                  ListeningExecutorService handlerExecutor) {
        return Futures.immediateFuture(Boolean.FALSE);
    }
}
