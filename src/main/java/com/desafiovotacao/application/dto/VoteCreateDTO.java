package com.desafiovotacao.application.dto;

import com.desafiovotacao.domain.enums.VoteValueEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoteCreateDTO(
        @NotNull
        @NotEmpty
        @JsonProperty("value")
        String value
) {
}
