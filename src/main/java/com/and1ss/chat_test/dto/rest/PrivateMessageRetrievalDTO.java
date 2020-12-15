package com.and1ss.chat_test.dto.rest;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessageRetrievalDTO {
    @NonNull
    private UUID id;

    private UUID authorId;

    @NonNull
    private UUID chatId;

    private String contents;

    private Timestamp createdAt;
}