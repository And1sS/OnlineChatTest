package com.and1ss.chat_test.services.group_chat;

import com.and1ss.chat_test.services.group_chat.model.GroupChatCreationDTO;
import com.and1ss.chat_test.services.group_chat.model.GroupChatRetrievalDTO;
import com.and1ss.chat_test.services.group_chat.model.GroupMessageCreationDTO;
import com.and1ss.chat_test.services.group_chat.model.GroupMessageRetrievalDTO;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GroupChatTestService {
    Mono<GroupChatRetrievalDTO> createGroupChat(
            GroupChatCreationDTO chatCreationDTO,
            String accessToken
    );

    Mono<GroupChatRetrievalDTO[]> getAllGroupChats(String accessToken);

    Mono<GroupMessageRetrievalDTO> createMessage(
            GroupMessageCreationDTO groupMessageCreationDTO,
            UUID groupChatId,
            String accessToken
    );

    Mono<GroupMessageRetrievalDTO[]> getAllMessagesForChat(
            UUID groupChatId,
            String accessToken
    );
}
