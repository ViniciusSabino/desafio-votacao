package com.desafiovotacao.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SessionCreateDTO(
        @NotNull
        @Positive
        @JsonProperty("topicId")
        Long topicId,

        @JsonProperty("startAt")
        String startAt,

        @JsonProperty("endAt")
        String endAt,

        @NotNull
        @NotEmpty
        @JsonProperty("createdBy")
        String createdBy
) {
}
