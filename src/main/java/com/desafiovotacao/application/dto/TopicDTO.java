package com.desafiovotacao.application.dto;

import com.desafiovotacao.domain.enums.TopicStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record TopicDTO(
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

        @JsonProperty("status")
        TopicStatusEnum status,

        @NotNull
        @NotEmpty
        @JsonProperty("createdBy")
        String createdBy,

        @NotNull
        @NotEmpty
        @JsonProperty("createdAt")
        String createdAt,

        @NotNull
        @NotEmpty
        @JsonProperty("updatedAt")
        String updatedAt
) {
}
