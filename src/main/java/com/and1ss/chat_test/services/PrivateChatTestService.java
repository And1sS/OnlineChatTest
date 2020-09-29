package com.and1ss.chat_test.services;

import com.and1ss.chat_test.api.dto.PrivateChatCreationDTO;
import com.and1ss.chat_test.api.dto.PrivateChatRetrievalDTO;
import com.and1ss.chat_test.api.dto.PrivateMessageCreationDTO;
import com.and1ss.chat_test.api.dto.PrivateMessageRetrievalDTO;
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
