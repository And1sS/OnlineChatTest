package com.and1ss.chat_test.api.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageRetrievalDTO {
    protected UUID id;

    private AccountInfoRetrievalDTO author;

    @NonNull
    private UUID chatId;

    private String contents;

    private Timestamp createdAt;
}
