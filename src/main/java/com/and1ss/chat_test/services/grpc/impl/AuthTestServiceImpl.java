package com.and1ss.chat_test.services.grpc.impl;

import com.and1ss.chat_test.services.grpc.AuthTestService;
import com.and1ss.private_chat_service.GrpcPrivateChatServiceGrpc;
import com.and1ss.user_service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthTestServiceImpl implements AuthTestService {

    private final ManagedChannel channel;

    @Autowired
    public AuthTestServiceImpl() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext()
                .build();
    }

    @Override
    public GrpcAccountInfoRetrievalDTO register(GrpcRegisterInfoDTO registerInfoDTO) {
        GrpcAuthenticationServiceGrpc.GrpcAuthenticationServiceBlockingStub stub =
                GrpcAuthenticationServiceGrpc.newBlockingStub(channel);

        return stub.register(registerInfoDTO);
    }

    @Override
    public GrpcAccessTokenOutgoingDTO login(GrpcLoginCredentialsDTO loginInfoDTO) {
        GrpcAuthenticationServiceGrpc.GrpcAuthenticationServiceBlockingStub stub =
                GrpcAuthenticationServiceGrpc.newBlockingStub(channel);

        return stub.login(loginInfoDTO);
    }

    @Override
    public void shutdown() {
        channel.shutdown();
    }
}