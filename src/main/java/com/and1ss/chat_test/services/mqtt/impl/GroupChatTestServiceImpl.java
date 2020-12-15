package com.and1ss.chat_test.services.mqtt.impl;

import com.and1ss.chat_test.dto.mqtt.MyMqttMessage;
import com.and1ss.chat_test.dto.rest.GroupChatCreationDTO;
import com.and1ss.chat_test.dto.rest.GroupMessageCreationDTO;
import com.and1ss.chat_test.services.mqtt.GroupChatTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

public class GroupChatTestServiceImpl implements GroupChatTestService {
    private static final String address = "tcp://localhost:1883";

    private final IMqttClient publisher;

    public GroupChatTestServiceImpl() throws MqttException {
        publisher = new MqttClient(address,
                UUID.randomUUID().toString(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        publisher.connect(options);
    }

    @Override
    public void createGroupChat(MyMqttMessage<GroupChatCreationDTO> dto) throws JsonProcessingException, MqttException {
        publisher.publish("/group-chat-service/new-message", mapToMqttMessage(dto));
    }

    @Override
    public void createMessage(MyMqttMessage<GroupMessageCreationDTO> dto) throws JsonProcessingException, MqttException {
        publisher.publish("/group-chat-service/new-chat", mapToMqttMessage(dto));
    }

    @Override
    public void disconnect() throws MqttException {
        publisher.disconnect();
    }

    private <T> MqttMessage mapToMqttMessage(MyMqttMessage<T> dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);
        MqttMessage message = new MqttMessage(json.getBytes());
        message.setRetained(false);
        message.setQos(0);

        return message;
    }
}
