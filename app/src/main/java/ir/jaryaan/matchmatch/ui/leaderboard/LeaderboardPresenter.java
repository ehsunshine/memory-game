package ir.jaryaan.matchmatch.ui.leaderboard;

import android.os.Bundle;
import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.model.manager.ScoreManagerContract;
import ir.jaryaan.matchmatch.utils.scheduler.SchedulerProvider;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by E.Mehranvari on 5/23/2017.
 */

public class LeaderboardPresenter implements LeaderboardContract.Presenter {

    LeaderboardContract.View view;
    private ScoreManagerContract scoreManager;
    private SchedulerProvider schedulerProvider;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public LeaderboardPresenter(@NonNull ScoreManagerContract scoreManager, @NonNull SchedulerProvider schedulerProvider) {
        this.scoreManager = scoreManager;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void onBindView(@NonNull LeaderboardContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewInitialized() {
        String levelName = "";
        switch (view.getLeaderboardId()) {
            case 1:
                levelName = "easy";
                break;
            case 2:
                levelName = "normal";
                break;
            case 3:
                levelName = "hard";
                break;
            case 4:
                levelName = "insane";
                break;
        }
        listenToNewScore(levelName);
    }

    private void listenToNewScore(@NonNull String levelName) {
        Subscription subscription = scoreManager.listenToNewScore(view.getScoreId(), levelName)
                .subscribeOn(schedulerProvider.getComputationScheduler())
                .observeOn(schedulerProvider.getMainScheduler())
                .subscribe(score -> {
                    view.addScore(score);
                }, throwable -> {
                    view.showErrorMessage(throwable);
                });
        compositeSubscription.add(subscription);

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
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }
}
