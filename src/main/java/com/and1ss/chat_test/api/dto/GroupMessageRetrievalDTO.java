package com.and1ss.chat_test.api.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageRetrievalDTO {
    @NonNull
    private UUID id;

    @NonNull
    private UUID authorId;

    @NonNull
    private UUID chatId;

    private String contents;

    private Timestamp createdAt;
}
