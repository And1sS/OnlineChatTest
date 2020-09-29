package com.and1ss.chat_test.services;

import com.and1ss.chat_test.api.dto.AccessTokenRetrievalDTO;
import com.and1ss.chat_test.api.dto.AccountInfoRetrievalDTO;
import com.and1ss.chat_test.api.dto.LoginInfoDTO;
import com.and1ss.chat_test.api.dto.RegisterInfoDTO;
import reactor.core.publisher.Mono;

public interface AuthTestService {
    Mono<AccountInfoRetrievalDTO> register(RegisterInfoDTO registerInfoDTO);

    Mono<AccessTokenRetrievalDTO> login(LoginInfoDTO loginInfoDTO);
}
