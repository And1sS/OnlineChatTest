package com.and1ss.chat_test.services.grpc.impl;

import com.and1ss.chat_test.services.grpc.GroupChatTestService;
import com.and1ss.group_chat_service.*;
import com.and1ss.private_chat_service.GrpcPrivateChatServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GroupChatTestServiceImpl implements GroupChatTestService {

    private final ManagedChannel channel;

    public GroupChatTestServiceImpl() {
        this.channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext()
                .build();
    }

    @Override
    public GrpcGroupChatDTO createGroupChat(GrpcGroupChatCreationDTO chatCreationDTO) {
        GrpcGroupChatServiceGrpc.GrpcGroupChatServiceBlockingStub stub =
                GrpcGroupChatServiceGrpc.newBlockingStub(channel);

        return stub.createGroupChat(chatCreationDTO);
    }

    @Override
    public GrpcGroupChatsDTO getAllGroupChats(GrpcAccessTokenIncomingDTO accessToken) {
        GrpcGroupChatServiceGrpc.GrpcGroupChatServiceBlockingStub stub =
                GrpcGroupChatServiceGrpc.newBlockingStub(channel);

        return stub.getAllChats(accessToken);
    }

    @Override
    public GrpcGroupMessageRetrievalDTO createMessage(GrpcGroupMessageCreationDTO groupMessageCreationDTO) {
        GrpcGroupChatServiceGrpc.GrpcGroupChatServiceBlockingStub stub =
                GrpcGroupChatServiceGrpc.newBlockingStub(channel);

        return stub.createMessage(groupMessageCreationDTO);
    }

    @Override
    public GrpcGroupMessagesRetrievalDTO getAllMessagesForChat(GrpcChatRetrievalDTO grpcGroupMessagesRetrievalDTO) {
        GrpcGroupChatServiceGrpc.GrpcGroupChatServiceBlockingStub stub =
                GrpcGroupChatServiceGrpc.newBlockingStub(channel);

        return stub.getAllMessageForChat(grpcGroupMessagesRetrievalDTO);
    }

    @Override
    public void shutdown() {
        channel.shutdown();
    }
}
