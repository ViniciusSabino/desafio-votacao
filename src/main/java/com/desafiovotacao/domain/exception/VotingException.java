package com.desafiovotacao.domain.exception;

import com.desafiovotacao.domain.enums.AssociateVoteStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VotingException extends BusinessException {
    public AssociateVoteStatusEnum status;

    public VotingException(String message, AssociateVoteStatusEnum status) {
        super(message);

        this.status = status;
    }

    public VotingException(String message) {
        super(message);
    }
}