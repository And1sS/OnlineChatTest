package com.and1ss.chat_test.api.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivateChatRetrievalDTO {
    @NonNull
    private UUID id;

    private AccountInfoRetrievalDTO user1;

    private AccountInfoRetrievalDTO user2;
}