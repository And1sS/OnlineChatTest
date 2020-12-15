package com.and1ss.chat_test.services.rest.impl;

import com.and1ss.chat_test.dto.rest.PrivateChatCreationDTO;
import com.and1ss.chat_test.dto.rest.PrivateChatRetrievalDTO;
import com.and1ss.chat_test.dto.rest.PrivateMessageCreationDTO;
import com.and1ss.chat_test.dto.rest.PrivateMessageRetrievalDTO;
import com.and1ss.chat_test.services.rest.PrivateChatTestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class PrivateChatTestServiceImpl implements PrivateChatTestService {
    private final WebClient webClient;

    public PrivateChatTestServiceImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/private-chat-service/chats/")
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                ).build();
    }

    public Mono<PrivateChatRetrievalDTO> createPrivateChat(
            PrivateChatCreationDTO chatCreationDTO,
            String accessToken
    ) {
        return webClient.post()
                .uri("/")
                .header("Authorization", "Bearer " + accessToken)
                .body(BodyInserters.fromValue(chatCreationDTO))
                .retrieve()
                .bodyToMono(PrivateChatRetrievalDTO.class);
    }

    public Mono<PrivateChatRetrievalDTO[]> getAllPrivateChats(String accessToken) {
        return webClient.get()
                .uri("/all")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(PrivateChatRetrievalDTO[].class);
    }

    public Mono<PrivateMessageRetrievalDTO> createMessage(
            PrivateMessageCreationDTO privateMessageCreationDTO,
            UUID privateChatId,
            String accessToken
    ) {
        return webClient.post()
                .uri("/{privateChatId}/messages", privateChatId)
                .header("Authorization", "Bearer " + accessToken)
                .body(BodyInserters.fromValue(privateMessageCreationDTO))
                .retrieve()
                .bodyToMono(PrivateMessageRetrievalDTO.class);
    }

    public Mono<PrivateMessageRetrievalDTO[]> getAllMessagesForChat(
            UUID privateChatId,
            String accessToken
    ) {
        return webClient.get()
                .uri("/{privateChatId}/messages", privateChatId)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(PrivateMessageRetrievalDTO[].class);
    }
}
