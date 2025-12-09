package com.desafiovotacao.presentation.controller.impl;

import com.desafiovotacao.application.dto.SessionCreateDTO;
import com.desafiovotacao.application.response.SessionResponse;
import com.desafiovotacao.application.service.SessionService;
import com.desafiovotacao.domain.exception.BusinessException;
import com.desafiovotacao.presentation.controller.SessionController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/session", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class SessionControllerImpl implements SessionController {

    private final SessionService service;

    @PostMapping
    @Override
    public ResponseEntity<SessionResponse> create(@Valid @RequestBody SessionCreateDTO sessionCreateDTO) throws BusinessException {
        log.trace("POST /session - Criando uma nova sessão para uma pauta");

        SessionResponse session = service.create(sessionCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }
}