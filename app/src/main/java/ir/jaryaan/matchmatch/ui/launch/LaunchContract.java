package ir.jaryaan.matchmatch.ui.launch;

import ir.jaryaan.matchmatch.ui.base.BasePresenterContract;
import ir.jaryaan.matchmatch.ui.base.BaseViewContract;

/**
 * Created by ehsun on 5/12/2017.
 */

public interface LaunchContract {

    interface View extends BaseViewContract {
        void showHomeScreen();
    }

    interface Presenter extends BasePresenterContract<View> {

    }
}
