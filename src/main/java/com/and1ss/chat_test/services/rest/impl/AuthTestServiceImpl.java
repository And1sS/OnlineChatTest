package com.and1ss.chat_test.services.rest.impl;

import com.and1ss.chat_test.api.dto.AccessTokenRetrievalDTO;
import com.and1ss.chat_test.api.dto.AccountInfoRetrievalDTO;
import com.and1ss.chat_test.api.dto.LoginInfoDTO;
import com.and1ss.chat_test.api.dto.RegisterInfoDTO;
import com.and1ss.chat_test.services.AuthTestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthTestServiceImpl implements AuthTestService {
    private final WebClient webClient;

    public AuthTestServiceImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/user-service/auth")
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                ).build();
    }

    public Mono<AccountInfoRetrievalDTO> register(RegisterInfoDTO registerInfoDTO) {
        return webClient.post()
                .uri("/register")
                .body(BodyInserters.fromValue(registerInfoDTO))
                .retrieve()
                .bodyToMono(AccountInfoRetrievalDTO.class);
    }

    public Mono<AccessTokenRetrievalDTO> login(LoginInfoDTO loginInfoDTO) {
        return webClient
                .method(HttpMethod.GET)
                .uri("/login")
                .body(BodyInserters.fromValue(loginInfoDTO))
                .retrieve()
                .bodyToMono(AccessTokenRetrievalDTO.class);
    }
}
