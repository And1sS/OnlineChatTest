package com.and1ss.chat_test.dto.rest;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoDTO {
    @NonNull
    private String login;
    @NonNull
    private String password;
}

