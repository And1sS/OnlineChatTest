package com.and1ss.chat_test.dto.rest;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInfoDTO {
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String login;
    @NonNull
    private String password;
}
