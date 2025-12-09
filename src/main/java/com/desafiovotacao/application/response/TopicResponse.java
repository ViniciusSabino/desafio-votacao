package com.desafiovotacao.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.Instant;

@Builder
public record TopicResponse(
        @Positive
        @NotNull
        @JsonProperty("id")
        Long id,

        @NotNull
        @JsonProperty("title")
        String title,

        @NotNull
        @JsonProperty("description")
        String description,

        @NotNull
        @NotEmpty
        @JsonProperty("createdBy")
        String createdBy,

        @NotNull
        @NotEmpty
        @JsonProperty("createdAt")
        Instant createdAt,

        @NotNull
        @NotEmpty
        @JsonProperty("updatedAt")
        Instant updatedAt
) {
}
