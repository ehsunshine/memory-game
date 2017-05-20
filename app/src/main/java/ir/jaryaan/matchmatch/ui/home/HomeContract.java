package ir.jaryaan.matchmatch.ui.home;

import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.entities.Setting;
import ir.jaryaan.matchmatch.ui.base.BasePresenterContract;
import ir.jaryaan.matchmatch.ui.base.BaseViewContract;

/**
 * Created by ehsun on 5/17/2017.
 */

public interface HomeContract {
    interface View extends BaseViewContract {
        void showGameScreen();

        void showSettingScreen();

        void showScoreScreen();

        void showCurrentSetting(Setting setting);
    }

    interface Presenter extends BasePresenterContract<View> {

        void onSettingClicked();

        void onGameStarted(@NonNull String nickname, @Setting.DifficultyLevel int difficultyLevel);
    }
}
