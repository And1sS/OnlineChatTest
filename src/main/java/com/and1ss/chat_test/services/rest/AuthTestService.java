package com.and1ss.chat_test.services.rest;

import com.and1ss.chat_test.dto.rest.AccessTokenRetrievalDTO;
import com.and1ss.chat_test.dto.rest.AccountInfoRetrievalDTO;
import com.and1ss.chat_test.dto.rest.LoginInfoDTO;
import com.and1ss.chat_test.dto.rest.RegisterInfoDTO;
import reactor.core.publisher.Mono;

public interface AuthTestService {
    Mono<AccountInfoRetrievalDTO> register(RegisterInfoDTO registerInfoDTO);

    Mono<AccessTokenRetrievalDTO> login(LoginInfoDTO loginInfoDTO);
}
