package ir.jaryaan.matchmatch.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by ehsun on 5/12/2017.
 */

public class ValidationUtil {
    public static boolean isValidEmail(String text) {
        return !TextUtils.isEmpty(text) && Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }

}
