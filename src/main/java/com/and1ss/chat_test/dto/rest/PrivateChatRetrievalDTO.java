package com.and1ss.chat_test.dto.rest;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivateChatRetrievalDTO {
    @NonNull
    private UUID id;

    private UUID user1Id;

    private UUID user2Id;

}