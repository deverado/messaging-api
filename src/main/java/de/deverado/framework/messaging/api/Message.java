package de.deverado.framework.messaging.api;

import com.google.common.util.concurrent.ListenableFuture;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.ByteBuffer;
import java.util.Map;

@ParametersAreNonnullByDefault
public interface Message {

    String getId();

    String getTopic();

    ListenableFuture<Boolean> acknowledge() throws RuntimeException;

    /**
     * Declare a message as not acknowledgeable.
     * 
     * @param retryAllowed
     *            if retrying might make sense (after a timeout or on a
     *            different system). Setting this to true does not mean that
     *            message will be retried - the system might for example decide
     *            that it failed too often and send it to dead letters or even
     *            drop it depending on the implmentation.
     * @throws RuntimeException
     */
    ListenableFuture<Boolean> reject(boolean retryAllowed, @Nullable Long optionalScheduledTime);

    /**
     * Might need to create the map on the fly depending on the implementation.
     *
     */
    Map<String, String> getPropsMap();

    Message setProperty(String name, @Nullable String content);

    String getProperty(String name);

    ByteBuffer getContent();

    String getContentParsedAsUtf8();

    boolean isHavingScheduledTime();

    long getScheduledTime();

}
