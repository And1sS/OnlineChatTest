package com.and1ss.chat_test.dto.mqtt;

import lombok.Data;

@Data
public class MyMqttMessage<T> {
    String token;

    T payload;

    public MyMqttMessage(String token, T payload) {
        this.token = token;
        this.payload = payload;
    }
}
