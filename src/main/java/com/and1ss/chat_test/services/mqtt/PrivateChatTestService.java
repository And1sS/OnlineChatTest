package com.and1ss.chat_test.services.mqtt;

import com.and1ss.chat_test.dto.mqtt.MyMqttMessage;
import com.and1ss.chat_test.dto.rest.PrivateChatCreationDTO;
import com.and1ss.chat_test.dto.rest.PrivateMessageCreationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;

public interface PrivateChatTestService {
    void createPrivateChat(MyMqttMessage<PrivateChatCreationDTO> dto) throws JsonProcessingException, MqttException;
    void createMessage(MyMqttMessage<PrivateMessageCreationDTO> dto) throws JsonProcessingException, MqttException;
    void disconnect() throws MqttException;
}
