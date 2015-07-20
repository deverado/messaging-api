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
     * Values T, t are recognised as true.
     */
    String DURABLE_HINT_PROP_NAME = "DUR_HNT";

    /**
     * Get (up to a limit) order guarantees by setting this to 't'.
     * If value starts with 't': Shards also messages without shardKey by enforcing shardKey "".
     * Else: default behavior. If no shardKey present: full parallel processing.
     */
    public static final String SHARDING_FORCED_PROPERTY = "shardForced";

    /**
     * By which messages are sharded when queuing (and processing) them. Messages with the same shardKey are
     * queued to the same queue, and are processed in queue-order, waiting for processing on the first to
     * finish before processing of the next with same shardKey is started.
     */
    public static final String SHARD_KEY_PROPERTY = "shardKey";

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

    /**
     * <p>
     * Async/Sharding: Depending on the queue listener configuration message ordering can be maintained (up to an
     * implementation-specific) extent. Sharding uses the 'shardKey' and 'alwaysShard' message properties:
     * <ul>
     * <li>No 'shardKey' property set: (configurable) Default behavior.
     * </li>
     * <li>'shardForced' set to 't' and no 'shardKey' set: Messages without shardKey get forced into shardKey "".
     * If 'shardKey' is not set and 'shardForced not set, then behavior depending on queue reader configuration.
     * Might be fully parallel (up to implementation-specific limits), no order guarantees. If set to 't' 'shardForced'
     * might override sharding config. This still might lead to duplicate processing if more than one reader is
     * working on a queue. For load balancing messages are sharded by message id into load balancing queues if they
     * don't have a shardkey set.</li>
     * <li>'shardKey' property set: Msgs with same shardKey values will be sharded to the same queue and processed in
     * order of appearance in queue, waiting for one message's processing is finished until starting processing
     * on the next with same shardKey.
     * </li>
     * </ul>
     * </p>
     *
     * @return future returning with the time the message was scheduled with.
     */
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
