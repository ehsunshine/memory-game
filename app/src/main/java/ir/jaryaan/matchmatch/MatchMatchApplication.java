package ir.jaryaan.matchmatch;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import ir.jaryaan.matchmatch.model.ManagerModule;
import ir.jaryaan.matchmatch.model.source.SourceModule;
import ir.jaryaan.matchmatch.network.NetworkModule;
import ir.jaryaan.matchmatch.ui.PresentersModule;
import ir.jaryaan.matchmatch.utils.UtilsModule;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by ehsun on 5/12/2017.
 */

public class MatchMatchApplication extends Application {

    public static final String LOG_TAG = "Memory Game";
    private static MatchMatchApplication instance;
    private ApplicationComponent applicationComponent;

    public static MatchMatchApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule())
                .presentersModule(new PresentersModule())
                .sourceModule(new SourceModule(this))
                .managerModule(new ManagerModule(this))
                .utilsModule(new UtilsModule(this))
                .build();

        JodaTimeAndroid.init(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_wizzta))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
