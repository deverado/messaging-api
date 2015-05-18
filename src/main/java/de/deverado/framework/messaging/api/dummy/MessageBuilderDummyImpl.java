package de.deverado.framework.messaging.api.dummy;/*
 * Copyright Georg Koester 2012-15. All rights reserved.
 */

import com.google.common.base.Charsets;
import de.deverado.framework.messaging.api.Message;
import de.deverado.framework.messaging.api.MessageBuilder;

import javax.annotation.ParametersAreNonnullByDefault;

import java.nio.ByteBuffer;

@ParametersAreNonnullByDefault
public class MessageBuilderDummyImpl implements MessageBuilder {

    private MessageDummyImpl prototype = new MessageDummyImpl();

    @Override
    public MessageBuilder setTopic(String topic) {
        prototype.setTopic(topic);
        return this;
    }

    @Override
    public MessageBuilder setId(String id) {
        prototype.setId(id);
        return this;
    }

    @Override
    public MessageBuilder setProperty(String name, String val) {
        prototype.setProperty(name, val);
        return this;
    }

    @Override
    public MessageBuilder setScheduledTime(long time) {
        prototype.setScheduledTime(time);
        return this;
    }

    @Override
    public MessageBuilder setContent(ByteBuffer content) {
        prototype.setContent(content);
        return this;
    }

    @Override
    public MessageBuilder setContentEncodedToUtf8(String content) {
        prototype.setContent(ByteBuffer.wrap(content.getBytes(Charsets.UTF_8)));
        return this;
    }

    @Override
    public Message build() {
        return prototype.clone();
    }
}
