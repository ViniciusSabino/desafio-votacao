package com.desafiovotacao.application.response;

import com.desafiovotacao.domain.enums.SessionStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.Instant;

@Builder
public record SessionResponse(
        @Positive
        @NotNull
        @JsonProperty("id")
        Long id,

        @Positive
        @NotNull
        @JsonProperty("topicId")
        Long topicId,

        @NotNull
        @NotEmpty
        @JsonProperty("startAt")
        Instant startAt,

        @NotNull
        @NotEmpty
        @JsonProperty("endAt")
        Instant endAt,

        @NotNull
        @NotEmpty
        @JsonProperty("createdBy")
        String createdBy,

        @NotNull
        @NotEmpty
        @JsonProperty("status")
        SessionStatusEnum status,

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
