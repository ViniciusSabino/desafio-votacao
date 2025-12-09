package com.desafiovotacao.presentation.controller;

import com.desafiovotacao.application.dto.SessionCreateDTO;
import com.desafiovotacao.application.response.SessionResponse;
import com.desafiovotacao.domain.exception.BusinessException;
import com.desafiovotacao.presentation.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Session", description = "Endpoints from Managing Voting Sessions")
public interface SessionController {

    @Operation(summary = "Create a Session",
            description = "Create a Session",
            tags = {"Session"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = SessionResponse.class)
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
    ResponseEntity<SessionResponse> create(SessionCreateDTO sessionCreateDTO) throws BusinessException;
}