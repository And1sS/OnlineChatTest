package com.and1ss.chat_test.services.impl;

import com.and1ss.chat_test.services.GroupChatTestService;
import com.and1ss.chat_test.api.dto.GroupChatCreationDTO;
import com.and1ss.chat_test.api.dto.GroupChatRetrievalDTO;
import com.and1ss.chat_test.api.dto.GroupMessageCreationDTO;
import com.and1ss.chat_test.api.dto.GroupMessageRetrievalDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class GroupChatTestServiceImpl implements GroupChatTestService {
    private final WebClient webClient;

    public GroupChatTestServiceImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/group-chat-service/chats")
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                ).build();
    }

    public Mono<GroupChatRetrievalDTO> createGroupChat(
            GroupChatCreationDTO chatCreationDTO,
            String accessToken
    ) {
        return webClient.post()
                .uri("/")
                .header("Authorization", "Bearer " + accessToken)
                .body(BodyInserters.fromValue(chatCreationDTO))
                .retrieve()
                .bodyToMono(GroupChatRetrievalDTO.class);
    }

    public Mono<GroupChatRetrievalDTO[]> getAllGroupChats(String accessToken) {
        return webClient.get()
                .uri("/all")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(GroupChatRetrievalDTO[].class);
    }

    public Mono<GroupMessageRetrievalDTO> createMessage(
            GroupMessageCreationDTO groupMessageCreationDTO,
            UUID groupChatId,
            String accessToken
    ) {
        return webClient.post()
                .uri("/{groupChatId}/messages", groupChatId)
                .header("Authorization", "Bearer " + accessToken)
                .body(BodyInserters.fromValue(groupMessageCreationDTO))
                .retrieve()
                .bodyToMono(GroupMessageRetrievalDTO.class);
    }

    public Mono<GroupMessageRetrievalDTO[]> getAllMessagesForChat(
            UUID groupChatId,
            String accessToken
    ) {
        return webClient.get()
                .uri("/{groupChatId}/messages", groupChatId)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(GroupMessageRetrievalDTO[].class);
    }
}
