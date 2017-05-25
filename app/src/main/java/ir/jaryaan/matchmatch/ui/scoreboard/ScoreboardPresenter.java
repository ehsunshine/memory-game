package ir.jaryaan.matchmatch.ui.scoreboard;

import android.os.Bundle;
import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.model.manager.ScoreManagerContract;

/**
 * Created by ehsun on 5/22/2017.
 */

public class ScoreboardPresenter implements ScoreboardContract.Presenter {

    private ScoreboardContract.View view;
    private ScoreManagerContract scoreManager;

    public ScoreboardPresenter(ScoreManagerContract scoreManager) {
        this.scoreManager = scoreManager;
    }

    @Override
    public void onBindView(@NonNull ScoreboardContract.View view) {
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
