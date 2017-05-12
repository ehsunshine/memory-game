package ir.jaryaan.matchmatch.utils;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by ehsun on 5/12/2017.
 */

public class SharedPreferenceUtils {
    private SharedPreferenceUtils() {
    }

    public static boolean contains(@NonNull SharedPreferences prefs, String stringTag) {
        return prefs.contains(stringTag);
    }

    public static void clear(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    public static void remove(SharedPreferences prefs, String stringTag) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(stringTag);
        editor.apply();
    }

    public static void setString(SharedPreferences prefs, String stringTag, String stringToWrite) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(stringTag, stringToWrite);
        editor.apply();
    }

    public static String getString(SharedPreferences prefs, String stringTag, String stringDefault) {
        return prefs.getString(stringTag, stringDefault);
    }

    public static void setInt(SharedPreferences prefs, String stringTag, int intToWrite) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(stringTag, intToWrite);
        editor.apply();
    }

    public static int getInt(SharedPreferences prefs, String stringTag, int intDefault) {
        return prefs.getInt(stringTag, intDefault);
    }

    public static void setLong(SharedPreferences prefs, String stringTag, long longToWrite) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(stringTag, longToWrite);
        editor.apply();
    }

    public static long getLong(SharedPreferences prefs, String stringTag, long longDefault) {
        return prefs.getLong(stringTag, longDefault);
    }

    public static void setBoolean(SharedPreferences prefs, String stringTag, boolean booleanToWrite) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(stringTag, booleanToWrite);
        editor.apply();
    }

    public static boolean getBoolean(SharedPreferences prefs, String stringTag, boolean booleanDefault) {
        return prefs.getBoolean(stringTag, booleanDefault);
    }

}
