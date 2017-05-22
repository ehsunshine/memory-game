package ir.jaryaan.matchmatch.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.jaryaan.matchmatch.model.gateways.scoreboard.FirebaseScoreboardGateway;
import ir.jaryaan.matchmatch.model.manager.GameManager;
import ir.jaryaan.matchmatch.model.manager.GameManagerContract;
import ir.jaryaan.matchmatch.model.manager.ScoreManager;
import ir.jaryaan.matchmatch.model.manager.ScoreManagerContract;
import ir.jaryaan.matchmatch.model.repository.ImageRepository;
import ir.jaryaan.matchmatch.model.repository.ImageRepositoryContract;
import ir.jaryaan.matchmatch.model.repository.SettingRepository;
import ir.jaryaan.matchmatch.model.repository.SettingRepositoryContract;
import ir.jaryaan.matchmatch.network.ApiService;

/**
 * Created by ehsun on 5/12/2017.
 */
@Module
public class ManagerModule {
    private Context context;

    public ManagerModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public ImageRepositoryContract provideImageRepository(@NonNull ApiService apiService) {
        return new ImageRepository(apiService);
    }

    @Provides
    @Singleton
    public GameManagerContract provideGameManager(@NonNull SettingRepositoryContract settingRepository) {
        return new GameManager(settingRepository);
    }

    @Provides
    @Singleton
    public SettingRepositoryContract provideSettingRepository(@NonNull SharedPreferences sharedPreferences,
                                                              @NonNull Gson gson) {
        return new SettingRepository(sharedPreferences, gson);
    }

    @Provides
    @Singleton
    public ScoreManagerContract provideScoreManager(@NonNull FirebaseScoreboardGateway firebaseScoreboardGateway) {
        return new ScoreManager(firebaseScoreboardGateway);
    }
}
