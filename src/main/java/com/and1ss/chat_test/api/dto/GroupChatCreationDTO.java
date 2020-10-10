package com.and1ss.chat_test.api.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupChatCreationDTO {
    @NonNull
    private String title;

    private String about;

    @NonNull
    private UUID creator;

    @NonNull
    private List<UUID> participants;
}
