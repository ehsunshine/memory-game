package ir.jaryaan.matchmatch.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ir.jaryaan.matchmatch.utils.scheduler.DefaultSchedulerProvider;
import ir.jaryaan.matchmatch.utils.scheduler.SchedulerProvider;

/**
 * Created by ehsun on 5/12/2017.
 */
@Module
public class UtilsModule {
    private Context context;

    public UtilsModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    public SchedulerProvider provideSchedulerProvider() {
        return new DefaultSchedulerProvider();
    }

}
