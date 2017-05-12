package ir.jaryaan.matchmatch.utils;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by ehsun on 5/12/2017.
 */

public class DateTimeDeserializer implements JsonDeserializer<DateTime> {

    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private SimpleDateFormat simpleDateFormat;

    public DateTimeDeserializer() {
        this.simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
    }

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String text = json.getAsString();
        if (!TextUtils.isEmpty(text)) {
            try {
                return new DateTime(simpleDateFormat.parse(text).getTime());
            } catch (ParseException ex) {
                //Do nothing!
            }
        }

        return null;
    }
}
