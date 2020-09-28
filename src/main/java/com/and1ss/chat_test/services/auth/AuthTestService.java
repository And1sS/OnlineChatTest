package com.and1ss.chat_test.services.auth;

import com.and1ss.chat_test.services.auth.model.AccessTokenRetrievalDTO;
import com.and1ss.chat_test.services.auth.model.AccountInfoRetrievalDTO;
import com.and1ss.chat_test.services.auth.model.LoginInfoDTO;
import com.and1ss.chat_test.services.auth.model.RegisterInfoDTO;
import reactor.core.publisher.Mono;

public interface AuthTestService {
    Mono<AccountInfoRetrievalDTO> register(RegisterInfoDTO registerInfoDTO);

    Mono<AccessTokenRetrievalDTO> login(LoginInfoDTO loginInfoDTO);
}
