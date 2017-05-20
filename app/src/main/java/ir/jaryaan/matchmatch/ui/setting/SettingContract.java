package ir.jaryaan.matchmatch.ui.setting;

import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.entities.Setting;
import ir.jaryaan.matchmatch.ui.base.BasePresenterContract;
import ir.jaryaan.matchmatch.ui.base.BaseViewContract;

/**
 * Created by ehsun on 5/18/2017.
 */

public interface SettingContract {
    interface View extends BaseViewContract {
        void showCurrentSettings(@NonNull Setting setting);

        void finishView();
    }

    interface Presenter extends BasePresenterContract<View> {
        void onSettingSaved(@NonNull String cardType);
    }
}
