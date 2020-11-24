package com.and1ss.chat_test.services.grpc;

import com.and1ss.user_service.GrpcAccessTokenOutgoingDTO;
import com.and1ss.user_service.GrpcAccountInfoRetrievalDTO;
import com.and1ss.user_service.GrpcLoginCredentialsDTO;
import com.and1ss.user_service.GrpcRegisterInfoDTO;

public interface AuthTestService {
    GrpcAccountInfoRetrievalDTO register(GrpcRegisterInfoDTO registerInfoDTO);

    GrpcAccessTokenOutgoingDTO login(GrpcLoginCredentialsDTO loginInfoDTO);

    public void shutdown();
}
