package com.and1ss.chat_test.services.rest;

import com.and1ss.chat_test.dto.rest.PrivateChatCreationDTO;
import com.and1ss.chat_test.dto.rest.PrivateChatRetrievalDTO;
import com.and1ss.chat_test.dto.rest.PrivateMessageCreationDTO;
import com.and1ss.chat_test.dto.rest.PrivateMessageRetrievalDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PrivateChatTestService {
    Mono<PrivateChatRetrievalDTO> createPrivateChat(
            PrivateChatCreationDTO chatCreationDTO,
            String accessToken
    );

    Mono<PrivateChatRetrievalDTO[]> getAllPrivateChats(String accessToken);

    Mono<PrivateMessageRetrievalDTO> createMessage(
            PrivateMessageCreationDTO privateMessageCreationDTO,
            UUID privateChatId,
            String accessToken
    );

    Mono<PrivateMessageRetrievalDTO[]> getAllMessagesForChat(
            UUID privateChatId,
            String accessToken
    );
}
