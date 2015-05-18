package de.deverado.framework.messaging.api;/*
 * Copyright Georg Koester 2012-15. All rights reserved.
 */

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.ByteBuffer;

@ParametersAreNonnullByDefault
public interface MessageBuilder extends Cloneable {

    MessageBuilder setTopic(String topic);

    /**
     * Used for equality.
     */
    MessageBuilder setId(String id);

    MessageBuilder setProperty(String name, String val);

    /**
     * Optional.
     * @param time Meant to schedule future delivery. If this is before now then the message might never be delivered.
     */
    MessageBuilder setScheduledTime(long time);

    MessageBuilder setContent(ByteBuffer content);

    MessageBuilder setContentEncodedToUtf8(String content);

    Message build();

}
