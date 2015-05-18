package de.deverado.framework.messaging.api.dummy;/*
 * Copyright Georg Koester 2012-15. All rights reserved.
 */

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.deverado.framework.core.ExceptionsHelper;
import de.deverado.framework.messaging.api.Message;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class MessageDummyImpl implements Message, Cloneable {

    private Long scheduledTime;
    private String id;
    private String topic;
    private Map<String, String> props;
    private ByteBuffer content;

    public MessageDummyImpl() {
    }

    public MessageDummyImpl(Long scheduledTime, String id, String topic,
                            Map<String, String> props, ByteBuffer content) {
        this();
        this.scheduledTime = scheduledTime;
        this.id = id;
        this.topic = topic;
        this.props = props;
        this.content = content;
    }

    public void setScheduledTime(Long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    public void setContent(ByteBuffer content) {
        this.content = content;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public ListenableFuture<Boolean> acknowledge() throws RuntimeException {
        return Futures.immediateFuture(Boolean.TRUE);
    }

    @Override
    public ListenableFuture<Boolean> reject(boolean retryAllowed, Long optionalScheduledTime) {
        return Futures.immediateFuture(Boolean.TRUE);
    }

    @Override
    public Map<String, String> getPropsMap() {
        return props;
    }

    @Override
    public Message setProperty(String name, String content) {
        props.put(name, content);
        return this;
    }

    @Override
    public String getProperty(String name) {
        return props.get(name);
    }

    @Override
    public ByteBuffer getContent() {
        return content;
    }

    @Override
    public String getContentParsedAsUtf8() {
        return Charsets.UTF_8.decode(getContent()).toString();
    }

    @Override
    public boolean isHavingScheduledTime() {
        return scheduledTime != null;
    }

    @Override
    public long getScheduledTime() {
        return scheduledTime == null ? -1 : scheduledTime;
    }

    @Override
    public String toString() {
        return "MessageDummyImpl{" +
                "id='" + id + '\'' +
                ", topic='" + topic + '\'' +
                ", scheduledTime=" + scheduledTime +
                '}';
    }

    @Override
    public MessageDummyImpl clone() {
        MessageDummyImpl clone = null;
        try {
            clone = (MessageDummyImpl) super.clone();
            clone.props = new HashMap<>(props);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw ExceptionsHelper.newWithCause(RuntimeException.class, e);
        }
    }
}
