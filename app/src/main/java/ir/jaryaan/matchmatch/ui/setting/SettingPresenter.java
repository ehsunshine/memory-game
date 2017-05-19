package ir.jaryaan.matchmatch.ui.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by ehsun on 5/18/2017.
 */

public class SettingPresenter implements SettingContract.Presenter {

    SettingContract.View view;

    public SettingPresenter() {

    }

    @Override
    public void onBindView(@NonNull SettingContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewInitialized() {

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
    public void onSettingSaved() {

    }
}
