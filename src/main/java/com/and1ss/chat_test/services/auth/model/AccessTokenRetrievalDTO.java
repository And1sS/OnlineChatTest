package com.and1ss.chat_test.services.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenRetrievalDTO {
    @NonNull
    @JsonProperty("access_token")
    private UUID accessToken;

    @NonNull
    @JsonProperty("created_at")
    private Timestamp createdAt;
}
