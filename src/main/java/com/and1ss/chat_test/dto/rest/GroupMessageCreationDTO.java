package com.and1ss.chat_test.dto.rest;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageCreationDTO {
    private UUID chatId;

    @NonNull
    private String contents;

    public GroupMessageCreationDTO(String contents) {
        this.contents = contents;
    }
}
