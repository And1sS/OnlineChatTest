package com.and1ss.chat_test.services.grpc;

import com.and1ss.chat_test.api.dto.PrivateChatCreationDTO;
import com.and1ss.chat_test.api.dto.PrivateChatRetrievalDTO;
import com.and1ss.chat_test.api.dto.PrivateMessageCreationDTO;
import com.and1ss.chat_test.api.dto.PrivateMessageRetrievalDTO;
import com.and1ss.private_chat_service.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PrivateChatTestService {
    GrpcPrivateChatDTO createPrivateChat(GrpcChatCreationDTO chatCreationDTO);

    GrpcPrivateChatsDTO getAllPrivateChats(GrpcAccessTokenIncomingDTO accessToken);

    GrpcPrivateMessageRetrievalDTO createMessage(GrpcPrivateMessageCreationDTO privateMessageCreationDTO);

    GrpcPrivateMessagesRetrievalDTO getAllMessagesForChat(GrpcChatRetrievalDTO grpcChatRetrievalDTO);

    public void shutdown();
}
