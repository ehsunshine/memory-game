package ir.jaryaan.matchmatch.ui.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.model.repository.SettingRepositoryContract;

/**
 * Created by ehsun on 5/18/2017.
 */

public class SettingPresenter implements SettingContract.Presenter {

    SettingContract.View view;
    private SettingRepositoryContract settingRepository;

    public SettingPresenter(@NonNull SettingRepositoryContract settingRepository) {
        this.settingRepository = settingRepository;

    }

    @Override
    public void onBindView(@NonNull SettingContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewInitialized() {
        view.showCurrentSettings(settingRepository.getSetting());
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
    public void onSettingSaved(@NonNull String cardType) {

        settingRepository.setCardType(cardType);

        view.finishView();
    }
}
