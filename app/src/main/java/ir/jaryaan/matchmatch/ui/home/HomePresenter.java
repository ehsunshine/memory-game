package ir.jaryaan.matchmatch.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.entities.Setting;
import ir.jaryaan.matchmatch.model.repository.SettingRepositoryContract;

/**
 * Created by ehsun on 5/17/2017.
 */

public class HomePresenter implements HomeContract.Presenter {

    HomeContract.View view;
    private SettingRepositoryContract settingRepository;

    public HomePresenter(@NonNull SettingRepositoryContract settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public void onBindView(@NonNull HomeContract.View view) {
        this.view = view;
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

    }

    @Override
    public void onSettingClicked() {
        view.showSettingScreen();
    }

    @Override
    public void onGameStarted(@NonNull String nickname, @Setting.DifficultyLevel int difficultyLevel) {
        settingRepository.setNickname(nickname);
        settingRepository.setDifficultyLevel(difficultyLevel);
        view.showGameScreen();
    }
}
