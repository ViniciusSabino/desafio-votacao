package com.desafiovotacao.application.response;

import com.desafiovotacao.domain.enums.SessionStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record CountingVotesResponse(
        @NotNull
        @Positive
        @JsonProperty("sessionId")
        Long sessionId,

        @NotNull
        @Positive
        @JsonProperty("topicId")
        Long topicId,

        @NotNull
        @NotEmpty
        @JsonProperty("status")
        SessionStatusEnum status,

        @NotNull
        @Positive
        @JsonProperty("totalVotes")
        Integer totalVotes,

        @NotNull
        @Positive
        @JsonProperty("yes")
        Integer yes,

        @NotNull
        @Positive
        @JsonProperty("no")
        Integer no
) {
}