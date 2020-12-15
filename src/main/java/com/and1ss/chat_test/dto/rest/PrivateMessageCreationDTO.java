package com.and1ss.chat_test.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateMessageCreationDTO {
    private UUID chatId;

    @NonNull
    private String contents;

    public PrivateMessageCreationDTO(String contents) {
        this.contents = contents;
    }
}
