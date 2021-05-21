package com.doodl6.springboot.cloud.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySource {

    String STREAM_MESSAGE_TOPIC = "stream-message-topic";

    String STREAM_RESULT_TOPIC = "stream-result-topic";

    @Output(STREAM_MESSAGE_TOPIC)
    MessageChannel messageChannel();

    @Output(STREAM_RESULT_TOPIC)
    MessageChannel resultChannel();

}