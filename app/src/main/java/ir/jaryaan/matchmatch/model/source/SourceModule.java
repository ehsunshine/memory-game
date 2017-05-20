package ir.jaryaan.matchmatch.model.source;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.jaryaan.matchmatch.utils.DateTimeDeserializer;

/**
 * Created by ehsun on 5/12/2017.
 */

@Module
public class SourceModule {
    private static final String SHARED_PREF_MATCH = "MATCH_PREFS";

    private Context context;

    public SourceModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreference() {
        return context.getSharedPreferences(SHARED_PREF_MATCH, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                .create();
    }

}
