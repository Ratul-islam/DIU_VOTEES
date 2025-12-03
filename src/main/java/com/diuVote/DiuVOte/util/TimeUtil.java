package com.diuVote.DiuVOte.util;

import java.time.Duration;

public class TimeUtil {

    private TimeUtil() {}

    public static String formatDuration(Duration duration) {
        long totalSeconds = duration.getSeconds();
        if (totalSeconds < 0) {
            totalSeconds = -totalSeconds;
        }

        long days = totalSeconds / (24 * 3600);
        long hours = (totalSeconds % (24 * 3600)) / 3600;
        long minutes = (totalSeconds % 3600) / 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append(" day").append(days > 1 ? "s" : "");
        }
        if (hours > 0) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(hours).append(" hour").append(hours > 1 ? "s" : "");
        }
        if (minutes > 0 || sb.isEmpty()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(minutes).append(" minute").append(minutes != 1 ? "s" : "");
        }
        return sb.toString();
    }
}