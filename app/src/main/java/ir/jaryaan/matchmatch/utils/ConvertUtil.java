package ir.jaryaan.matchmatch.utils;

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

}
