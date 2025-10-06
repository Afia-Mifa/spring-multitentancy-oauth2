package com.example.tm.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    public static final int HOURS_IN_DAY = 24;

    public static final int SECONDS_IN_HOUR = 3600;

    public static final int SECONDS_IN_MINUTE = 60;

    public static Date getNow() {
        return new Date();
    }

    public static Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
