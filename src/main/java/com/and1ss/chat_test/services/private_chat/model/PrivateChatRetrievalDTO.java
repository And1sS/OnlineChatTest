package com.and1ss.chat_test.services.private_chat.model;

import com.and1ss.chat_test.services.auth.model.AccountInfoRetrievalDTO;
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