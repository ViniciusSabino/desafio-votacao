package com.desafiovotacao.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TopicCreateDTO(

        @NotNull
        @NotEmpty
        @JsonProperty("title")
        String title,

        @JsonProperty("description")
        String description,

        @NotNull
        @NotEmpty
        @JsonProperty("createdBy")
        String createdBy
) {
}
