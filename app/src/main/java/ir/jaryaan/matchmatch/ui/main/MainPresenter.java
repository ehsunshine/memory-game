package ir.jaryaan.matchmatch.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by ehsun on 5/12/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;

    @Override
    public void onBindView(@NonNull MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewInitialized() {
        view.showBoardScreen();
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
