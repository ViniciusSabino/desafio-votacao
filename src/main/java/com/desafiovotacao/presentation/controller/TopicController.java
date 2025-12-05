package com.desafiovotacao.presentation.controller;

import com.desafiovotacao.application.dto.TopicCreateDTO;
import com.desafiovotacao.application.dto.TopicDTO;
import com.desafiovotacao.domain.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Topic", description = "Endpoints from Managing Voting Topics")
public interface TopicController {

    @Operation(summary = "Create a Topic",
            description = "Create a Topic",
            tags = {"Topic"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TopicDTO.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<TopicDTO> create(TopicCreateDTO topicCreateDTO) throws BusinessException;
}