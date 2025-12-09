package com.desafiovotacao.application.service;

import com.desafiovotacao.application.dto.SessionCreateDTO;
import com.desafiovotacao.application.response.SessionResponse;

public interface SessionService {
    SessionResponse create(SessionCreateDTO sessionCreateDTO);
}