package ir.jaryaan.matchmatch.utils;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Created by ehsun on 5/17/2017.
 */

public class ConvertUtil {
    public static <T> T cast(Object obj, Class<T> clazz, T defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        try {
            return clazz.cast(obj);
        } catch (ClassCastException ex) {
            return defaultValue;
        }
    }

    public static int castInt(Object obj, int defaultValue) {
        if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof Long) {
            return (int) (long) obj;
        }
        return defaultValue;
    }

    public static long castLong(Object obj, long defaultValue) {
        if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof Long) {
            return (Long) obj;
        }
        return defaultValue;
    }

    public static String castString(Object obj, String defaultValue) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return defaultValue;
    }

    public static String convertMillisecondToMinutesAndSecond(long milliseconds) {
        Duration duration = new Duration(milliseconds);
        Period period = duration.toPeriod();
        PeriodFormatter minutesAndSeconds = new PeriodFormatterBuilder()
                .printZeroAlways()
                .appendMinutes()
                .appendSeparator(":")
                .appendSeconds()
                .toFormatter();
        return minutesAndSeconds.print(period);
    }

}
