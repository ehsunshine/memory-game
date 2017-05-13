package ir.jaryaan.matchmatch.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.jaryaan.matchmatch.model.manager.GameManager;
import ir.jaryaan.matchmatch.model.manager.GameManagerContract;
import ir.jaryaan.matchmatch.model.repository.ImageRepository;
import ir.jaryaan.matchmatch.model.repository.ImageRepositoryContract;
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
    public ImageRepositoryContract provideImageRepository(@NonNull ApiService apiService,
                                                          @NonNull SharedPreferences sharedPreferences) {
        return new ImageRepository(apiService);
    }

    @Provides
    @Singleton
    public GameManagerContract provideGameManager() {
        return new GameManager();
    }
}
