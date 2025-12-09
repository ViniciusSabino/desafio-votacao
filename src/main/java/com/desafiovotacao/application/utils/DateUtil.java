package com.desafiovotacao.application.utils;

import com.desafiovotacao.domain.constants.DateConstants;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtil {

    public Instant createDateTime(String isoDate) {
        LocalDateTime ldt = LocalDateTime.parse(isoDate);

        return ldt.atZone(DateConstants.DEFAULT_TIME_ZONE).toInstant();
    }

    public Instant now() {
        return Instant.now();
    }
}