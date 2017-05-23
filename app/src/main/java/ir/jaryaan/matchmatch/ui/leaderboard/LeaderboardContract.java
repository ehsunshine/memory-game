package ir.jaryaan.matchmatch.ui.leaderboard;


import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.ui.base.BasePresenterContract;
import ir.jaryaan.matchmatch.ui.base.BaseViewContract;

/**
 * Created by E.Mehranvari on 5/23/2017.
 */

public interface LeaderboardContract {
    interface View extends BaseViewContract {

        @NonNull
        String getScoreId();
    }

    interface Presenter extends BasePresenterContract<LeaderboardContract.View> {

    }
}
