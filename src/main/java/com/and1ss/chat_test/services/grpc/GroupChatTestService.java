package com.and1ss.chat_test.services.grpc;

import com.and1ss.group_chat_service.*;

public interface GroupChatTestService {
    GrpcGroupChatDTO createGroupChat(GrpcGroupChatCreationDTO chatCreationDTO);

    GrpcGroupChatsDTO getAllGroupChats(GrpcAccessTokenIncomingDTO accessToken);

    GrpcGroupMessageRetrievalDTO createMessage(GrpcGroupMessageCreationDTO groupMessageCreationDTO);

    GrpcGroupMessagesRetrievalDTO getAllMessagesForChat(GrpcChatRetrievalDTO grpcGroupMessagesRetrievalDTO);

    public void shutdown();
}
