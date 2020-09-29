package com.and1ss.chat_test.api.dto;

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

