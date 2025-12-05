package com.desafiovotacao.application.utils;

import com.desafiovotacao.domain.constants.DateConstants;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtil {

    public String formatDate(Instant date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

        return fmt.format(date);
    }

    public String formatDateTime(Instant date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_TIME_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

        return fmt.format(date);
    }
}
