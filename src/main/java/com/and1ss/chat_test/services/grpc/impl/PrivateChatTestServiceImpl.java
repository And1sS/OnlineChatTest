package com.and1ss.chat_test.services.grpc.impl;

import com.and1ss.chat_test.services.grpc.PrivateChatTestService;
import com.and1ss.private_chat_service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class PrivateChatTestServiceImpl implements PrivateChatTestService {
    private final ManagedChannel channel;


    public PrivateChatTestServiceImpl() {
        this.channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext()
                .build();
    }
    @Override
    public GrpcPrivateChatDTO createPrivateChat(GrpcChatCreationDTO chatCreationDTO) {
        GrpcPrivateChatServiceGrpc.GrpcPrivateChatServiceBlockingStub stub =
                GrpcPrivateChatServiceGrpc.newBlockingStub(channel);

        return stub.createPrivateChat(chatCreationDTO);
    }

    @Override
    public GrpcPrivateChatsDTO getAllPrivateChats(GrpcAccessTokenIncomingDTO accessToken) {
        GrpcPrivateChatServiceGrpc.GrpcPrivateChatServiceBlockingStub stub =
                GrpcPrivateChatServiceGrpc.newBlockingStub(channel);

        return stub.getAllChats(accessToken);
    }

    @Override
    public GrpcPrivateMessageRetrievalDTO createMessage(GrpcPrivateMessageCreationDTO privateMessageCreationDTO) {
        GrpcPrivateChatServiceGrpc.GrpcPrivateChatServiceBlockingStub stub =
                GrpcPrivateChatServiceGrpc.newBlockingStub(channel);

        return stub.createMessage(privateMessageCreationDTO);
    }

    @Override
    public GrpcPrivateMessagesRetrievalDTO getAllMessagesForChat(GrpcChatRetrievalDTO grpcChatRetrievalDTO) {
        GrpcPrivateChatServiceGrpc.GrpcPrivateChatServiceBlockingStub stub =
                GrpcPrivateChatServiceGrpc.newBlockingStub(channel);

        return stub.getAllMessagesForChat(grpcChatRetrievalDTO);
    }

    @Override
    public void shutdown() {
        channel.shutdown();
    }
}
