package ir.jaryaan.matchmatch.ui.base;

import android.support.annotation.NonNull;

/**
 * Created by ehsun on 5/11/2017.
 */

public interface BaseViewContract {

    void showErrorMessage(@NonNull String errorMessage);

    void showErrorMessage(int errorMessageResourceId);

    void showErrorMessage(@NonNull Throwable throwable);

    void showLoading();

    void hideLoading();
}
