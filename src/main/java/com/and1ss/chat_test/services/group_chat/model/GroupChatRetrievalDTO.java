package com.and1ss.chat_test.services.group_chat.model;

import com.and1ss.chat_test.services.auth.model.AccountInfoRetrievalDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatRetrievalDTO {
    @NonNull
    private UUID id;

    @NonNull
    private String title;

    private String about;

    private AccountInfoRetrievalDTO creator;

    @NonNull
    private List<AccountInfoRetrievalDTO> participants;
}

