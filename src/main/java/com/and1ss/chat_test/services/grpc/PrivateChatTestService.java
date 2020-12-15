package com.and1ss.chat_test.services.grpc;

import com.and1ss.private_chat_service.*;

public interface PrivateChatTestService {
    GrpcPrivateChatDTO createPrivateChat(GrpcChatCreationDTO chatCreationDTO);

    GrpcPrivateChatsDTO getAllPrivateChats(GrpcAccessTokenIncomingDTO accessToken);

    GrpcPrivateMessageRetrievalDTO createMessage(GrpcPrivateMessageCreationDTO privateMessageCreationDTO);

    GrpcPrivateMessagesRetrievalDTO getAllMessagesForChat(GrpcChatRetrievalDTO grpcChatRetrievalDTO);

    public void shutdown();
}
