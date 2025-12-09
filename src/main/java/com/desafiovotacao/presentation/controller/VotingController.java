package com.desafiovotacao.presentation.controller;

import com.desafiovotacao.application.response.CountingVotesResponse;
import com.desafiovotacao.application.dto.VoteCreateDTO;
import com.desafiovotacao.application.response.VotingResponse;
import com.desafiovotacao.domain.exception.BusinessException;
import com.desafiovotacao.presentation.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Voting", description = "Endpoints from Managing Voting")
public interface VotingController {

    @Operation(summary = "Take a Vote",
            description = "Take a Vote",
            tags = {"Voting"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VotingResponse.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
            }
    )
    ResponseEntity<VotingResponse> voting(String topicId, String associateFederalTaxId, VoteCreateDTO voteCreateDTO) throws BusinessException;

    @Operation(summary = "Counting Votes",
            description = "Counting votes in a session",
            tags = {"Voting"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CountingVotesResponse.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
            }
    )
    ResponseEntity<CountingVotesResponse> count(String sessionId) throws BusinessException;
}