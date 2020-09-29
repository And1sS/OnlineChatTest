package com.and1ss.chat_test.services;

import com.and1ss.chat_test.api.dto.GroupChatCreationDTO;
import com.and1ss.chat_test.api.dto.GroupChatRetrievalDTO;
import com.and1ss.chat_test.api.dto.GroupMessageCreationDTO;
import com.and1ss.chat_test.api.dto.GroupMessageRetrievalDTO;
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
