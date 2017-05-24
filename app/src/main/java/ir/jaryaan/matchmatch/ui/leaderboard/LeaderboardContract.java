package ir.jaryaan.matchmatch.ui.leaderboard;


import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import ir.jaryaan.matchmatch.ui.base.BasePresenterContract;
import ir.jaryaan.matchmatch.ui.base.BaseViewContract;

/**
 * Created by E.Mehranvari on 5/23/2017.
 */

public interface LeaderboardContract {
    interface View extends BaseViewContract {

        @NonNull
        int getLeaderboardId();

        @NonNull
        String getScoreId();

        void addScore(ScoreboardLevel score);
    }

    interface Presenter extends BasePresenterContract<LeaderboardContract.View> {

    }
}
