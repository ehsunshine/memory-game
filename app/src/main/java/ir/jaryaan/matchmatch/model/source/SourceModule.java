package ir.jaryaan.matchmatch.model.source;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.jaryaan.matchmatch.BuildConfig;
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

    @Provides
    @Singleton
    public FirebaseDatabase provideFirebaseDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(false);
        firebaseDatabase.setLogLevel(BuildConfig.DEBUG ? Logger.Level.DEBUG : Logger.Level.NONE);
        return firebaseDatabase;
    }

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

}
