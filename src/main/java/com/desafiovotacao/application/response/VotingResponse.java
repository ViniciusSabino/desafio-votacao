package com.desafiovotacao.application.response;

import com.desafiovotacao.domain.enums.AssociateVoteStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VotingResponse(
        @NotNull
        @NotEmpty
        @JsonProperty("message")
        String message,

        @NotNull
        @NotEmpty
        @JsonProperty("status")
        AssociateVoteStatusEnum status
) {
}
