package ir.jaryaan.matchmatch.ui.main;

import ir.jaryaan.matchmatch.ui.base.BasePresenterContract;
import ir.jaryaan.matchmatch.ui.base.BaseViewContract;

/**
 * Created by ehsun on 5/11/2017.
 */

public interface MainContract {
    interface View extends BaseViewContract {

        void showBoardScreen();

    }

    interface Presenter extends BasePresenterContract<View> {

    }

}
