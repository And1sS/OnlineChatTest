package com.and1ss.chat_test.dto.rest;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfoRetrievalDTO {
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private String surname;
}
