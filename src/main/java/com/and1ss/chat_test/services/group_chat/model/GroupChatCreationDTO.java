package com.and1ss.chat_test.services.group_chat.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatCreationDTO {
    @NonNull
    private String title;

    private String about;

    @NonNull
    private UUID creator;

    @NonNull
    private List<UUID> participants;
}
