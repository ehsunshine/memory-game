package ir.jaryaan.matchmatch.ui;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ir.jaryaan.matchmatch.model.gateways.online.FirebaseOnlineGateway;
import ir.jaryaan.matchmatch.model.gateways.user.FirebaseUserGateway;
import ir.jaryaan.matchmatch.model.manager.GameManagerContract;
import ir.jaryaan.matchmatch.model.manager.ScoreManagerContract;
import ir.jaryaan.matchmatch.model.repository.ImageRepositoryContract;
import ir.jaryaan.matchmatch.model.repository.SettingRepositoryContract;
import ir.jaryaan.matchmatch.ui.board.BoardContract;
import ir.jaryaan.matchmatch.ui.board.BoardPresenter;
import ir.jaryaan.matchmatch.ui.home.HomeContract;
import ir.jaryaan.matchmatch.ui.home.HomePresenter;
import ir.jaryaan.matchmatch.ui.launch.LaunchContract;
import ir.jaryaan.matchmatch.ui.launch.LaunchPresenter;
import ir.jaryaan.matchmatch.ui.main.MainContract;
import ir.jaryaan.matchmatch.ui.main.MainPresenter;
import ir.jaryaan.matchmatch.ui.setting.SettingContract;
import ir.jaryaan.matchmatch.ui.setting.SettingPresenter;
import ir.jaryaan.matchmatch.utils.scheduler.SchedulerProvider;

/**
 * Created by ehsun on 5/12/2017.
 */

@Module
public class PresentersModule {

    @Provides
    public LaunchContract.Presenter provideLaunchPresenter() {
        return new LaunchPresenter();
    }

    @Provides
    public MainContract.Presenter provideMainPresenter() {
        return new MainPresenter();
    }

    @Provides
    public BoardContract.Presenter provideBoardPresenter(@NonNull ImageRepositoryContract imageRepository,
                                                         @NonNull GameManagerContract gameManager,
                                                         @NonNull SchedulerProvider schedulerProvider,
                                                         @NonNull SettingRepositoryContract settingRepository,
                                                         @NonNull ScoreManagerContract scoreManager) {
        return new BoardPresenter(imageRepository, gameManager, schedulerProvider, settingRepository, scoreManager);
    }

    @Provides
    public HomeContract.Presenter provideHomePresenter(@NonNull SettingRepositoryContract settingRepository,
                                                       @NonNull FirebaseOnlineGateway firebaseOnlineGateway,
                                                       @NonNull FirebaseUserGateway firebaseUserGateway,
                                                       @NonNull SchedulerProvider schedulerProvider) {
        return new HomePresenter(settingRepository, firebaseOnlineGateway, firebaseUserGateway, schedulerProvider);
    }

    @Provides
    public SettingContract.Presenter provideSettingPresenter(@NonNull SettingRepositoryContract settingRepository) {
        return new SettingPresenter(settingRepository);
    }
}
