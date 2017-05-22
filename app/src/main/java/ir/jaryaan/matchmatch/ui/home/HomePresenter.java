package ir.jaryaan.matchmatch.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.entities.Setting;
import ir.jaryaan.matchmatch.model.gateways.online.FirebaseOnlineGateway;
import ir.jaryaan.matchmatch.model.gateways.user.FirebaseUserGateway;
import ir.jaryaan.matchmatch.model.repository.SettingRepositoryContract;
import ir.jaryaan.matchmatch.utils.scheduler.SchedulerProvider;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ehsun on 5/17/2017.
 */

public class HomePresenter implements HomeContract.Presenter {

    HomeContract.View view;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    private SettingRepositoryContract settingRepository;
    private FirebaseOnlineGateway firebaseOnlineGateway;
    private FirebaseUserGateway firebaseUserGateway;
    private SchedulerProvider schedulerProvider;

    public HomePresenter(@NonNull SettingRepositoryContract settingRepository,
                         @NonNull FirebaseOnlineGateway firebaseOnlineGateway,
                         @NonNull FirebaseUserGateway firebaseUserGateway,
                         @NonNull SchedulerProvider schedulerProvider) {
        this.settingRepository = settingRepository;
        this.firebaseOnlineGateway = firebaseOnlineGateway;
        this.firebaseUserGateway = firebaseUserGateway;
        this.schedulerProvider = schedulerProvider;

    }

    @Override
    public void onBindView(@NonNull HomeContract.View view) {
        this.view = view;
        Subscription subscription = firebaseUserGateway.signInAnonymously()
                .flatMap(result -> firebaseOnlineGateway.setUserOnline(view.getInstanceId()))
                .subscribeOn(schedulerProvider.getComputationScheduler())
                .subscribe();
        compositeSubscription.add(subscription);

    }

    @Override
    public void onViewInitialized() {
        view.showCurrentSetting(settingRepository.getSetting());
    }

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewPaused() {

    }

    @Override
    public void onViewSaveInstanceState(@NonNull Bundle bundle) {

    }

    @Override
    public void onViewRestoreInstanceState(@NonNull Bundle bundle) {

    }

    @Override
    public void onViewDestroyed() {
        firebaseOnlineGateway.setUserOffline(view.getInstanceId())
                .subscribeOn(schedulerProvider.getComputationScheduler())
                .subscribe();

        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }

    }

    @Override
    public void onSettingClicked() {
        view.showSettingScreen();
    }

    @Override
    public void onGameStarted(@NonNull String nickname, @Setting.DifficultyLevel int difficultyLevel) {
        settingRepository.setNickname(nickname);
        settingRepository.setDifficultyLevel(difficultyLevel);
        view.showGameScreen(view.getInstanceId());
    }
}
