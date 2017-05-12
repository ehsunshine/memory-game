package ir.jaryaan.matchmatch.ui.launch;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by ehsun on 5/12/2017.
 */

public class LaunchPresenter implements LaunchContract.Presenter {

    private LaunchContract.View view;

    @Override
    public void onBindView(@NonNull LaunchContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewInitialized() {
        view.showHomeScreen();
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
}
