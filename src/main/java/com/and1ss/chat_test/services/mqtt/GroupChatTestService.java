package com.and1ss.chat_test.services.mqtt;

import com.and1ss.chat_test.dto.mqtt.MyMqttMessage;
import com.and1ss.chat_test.dto.rest.GroupChatCreationDTO;
import com.and1ss.chat_test.dto.rest.GroupMessageCreationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;

public interface GroupChatTestService {
    void createGroupChat(MyMqttMessage<GroupChatCreationDTO> dto) throws JsonProcessingException, MqttException;
    void createMessage(MyMqttMessage<GroupMessageCreationDTO> dto) throws JsonProcessingException, MqttException;
    void disconnect() throws MqttException;
}
