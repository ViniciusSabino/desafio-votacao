package com.desafiovotacao.application.service.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    private static final DateTimeFormatter ISO_LOCAL_DATE_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private DateUtil() {}

    public static Instant now() {
        return Instant.now();
    }

    public static Instant createDateTime(String isoDateTime) {
        LocalDateTime localDateTime =
                LocalDateTime.parse(isoDateTime, ISO_LOCAL_DATE_TIME);

        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    public static String future(long seconds) {
        return LocalDateTime
                .now(ZoneOffset.UTC)
                .plusSeconds(seconds)
                .format(ISO_LOCAL_DATE_TIME);
    }

    public static String past(long seconds) {
        return LocalDateTime
                .now(ZoneOffset.UTC)
                .minusSeconds(seconds)
                .format(ISO_LOCAL_DATE_TIME);
    }

    public static String format(Instant instant) {
        return ISO_LOCAL_DATE_TIME
                .withZone(ZoneOffset.UTC)
                .format(instant);
    }
}
