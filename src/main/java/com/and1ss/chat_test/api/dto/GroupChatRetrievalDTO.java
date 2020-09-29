package com.and1ss.chat_test.api.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

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

