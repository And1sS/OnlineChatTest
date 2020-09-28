package com.and1ss.chat_test.services.private_chat.model;

import com.and1ss.chat_test.services.auth.model.AccountInfoRetrievalDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessageRetrievalDTO {
    protected UUID id;

    private AccountInfoRetrievalDTO author;

    @NonNull
    private UUID chatId;

    private String contents;

    private Timestamp createdAt;
}