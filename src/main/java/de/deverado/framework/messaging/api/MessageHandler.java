package de.deverado.framework.messaging.api;

import com.google.common.util.concurrent.ListenableFuture;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface MessageHandler {

    /**
     * Handled in the executor given during subscription.
     * An exception during execution will lead to automatic rejection of the message
     * (see {@link Message#reject(boolean, Long)}. Such a failure will be logged as a warning - the handler
     * should handle this case.
     * <p>
     *     Async processing: In the async case as in sync handling an exception in the future will
     *     lead to rejection. If buckets are used then a message that hasn't finished async processing
     *     will make bucket processing wait until the async processing was finished.
     * </p>
     * 
     * @param msg must be acknowledged to indicate successful processing.
     * @return null if processing is finished (most performant option). Future when processing is
     * async. Return value of the future is ignored. The future should not fail, error cases should be handled in the
     * handler. If the future fails it will be logged as error and the message will be rejected.
     *
     */
    @Nullable
    ListenableFuture<?> handle(Message msg);
}
