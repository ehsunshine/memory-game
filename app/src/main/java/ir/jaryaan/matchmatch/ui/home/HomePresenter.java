package ir.jaryaan.matchmatch.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by ehsun on 5/17/2017.
 */

public class HomePresenter implements HomeContract.Presenter {

    HomeContract.View view;

    @Override
    public void onBindView(@NonNull HomeContract.View view) {
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
}
