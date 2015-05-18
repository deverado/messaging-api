package de.deverado.framework.messaging.api;/*
 * Copyright Georg Koester 2012-15. All rights reserved.
 */

import com.google.common.base.Function;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import de.deverado.framework.core.problemreporting.ProblemReporter;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.Executor;

@ParametersAreNonnullByDefault
public interface MessagingFacade {
    /**
     * Using this subscription with <code>lockQueueForExclusiveProcessing</code> makes pretty sure that only one
     * subscriber is active at a given time and messages marked
     * as processed will not be reprocessed if a different subscription is opened.
     * @param lockQueueForExclusiveProcessing normally true, maybe false if you want duplicate executions to ensure
     *                                        higher availability and more consistent response times.
     * @return future which might throw {@link de.deverado.framework.messaging.api.QueueLockNotAcquiredException} to
     * indicate a failure to set up a (exclusive) subscription
     */
    public ListenableFuture<Subscription> subscribeQueued(String topic,
                                                          MessageHandler handler,
                                                          SubscriptionType subsType,
                                                          @Nullable ProblemReporter problemReporter,
                                                          ListeningExecutorService executor,
                                                          boolean lockQueueForExclusiveProcessing);

    /**
     * Subscribe to new events published to the given queue. Events are published to every subscriber.
     * <p>New events means that events newer than subscription time are delivered.</p>
     * @param subsType ACK here (and DURABLE) are not the same as acknowledging messages via the method
     * {@link Message#acknowledge()}: The type referes to the transmission of messages - ACK and DURABLE should ensure
     *                 that messages are not missed. But if they were passed once to the handler messages are not
     *                 passed again if they were not acknowledged. Specifically events are not checked for processing
     *                 time. So you can acknowledge a message, but this will not inhibit other subscribers from getting
     *                 the message.
     */
    public ListenableFuture<Subscription> subscribeEvents(String topic, MessageHandler handler,
                                                          SubscriptionType subsType,
                                                          @Nullable ProblemReporter problemReporter,
                                                          ListeningExecutorService handlerExecutor);

    public ListenableFuture<Long> sendMessage(Message msg);

    public MessageBuilder createMessageBuilder();

    public String getDeadLettersTopicName(String topic);

    public ListenableFuture<Boolean> listTopics(Function<String, ?> handler, Executor handlerExecutor,
                                                @Nullable ProblemReporter problemReporter);

    /**
     *
     * @param handler gets all messages (usually in serial order).
     * @param subscriptionType determines if acknowledgements are delivered.
     * @param handlerExecutor (usually) gets message processing task packages - no parallel processing.
     *                        If you want parallel processing you will have to delegate each message further
     *                        in the handler.
     */
    public ListenableFuture<Boolean> scanMessages(String topic,
                                                  MessageScanHandler handler,
                                                  long start, long end,
                                                  SubscriptionType subscriptionType,
                                                  boolean scanOnlyUnprocessed,
                                                  @Nullable ProblemReporter problemReporter,
                                                  ListeningExecutorService handlerExecutor);
}
