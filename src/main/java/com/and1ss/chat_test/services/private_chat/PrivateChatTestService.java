package com.and1ss.chat_test.services.private_chat;

import com.and1ss.chat_test.services.private_chat.model.PrivateChatCreationDTO;
import com.and1ss.chat_test.services.private_chat.model.PrivateChatRetrievalDTO;
import com.and1ss.chat_test.services.private_chat.model.PrivateMessageCreationDTO;
import com.and1ss.chat_test.services.private_chat.model.PrivateMessageRetrievalDTO;
import org.springframework.web.reactive.function.BodyInserters;
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
