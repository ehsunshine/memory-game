package ir.jaryaan.matchmatch.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ehsun on 5/12/2017.
 */

public class GsonParser {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .create();
        }
        return gson;
    }

}
