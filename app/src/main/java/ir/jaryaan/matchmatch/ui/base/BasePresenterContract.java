package ir.jaryaan.matchmatch.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by ehsun on 5/12/2017.
 */

public interface BasePresenterContract<T extends BaseViewContract> {
    void onBindView(@NonNull T view);

    void onViewInitialized();

    void onViewResumed();

    void onViewPaused();

    void onViewSaveInstanceState(@NonNull Bundle bundle);

    void onViewRestoreInstanceState(@NonNull Bundle bundle);

    void onViewDestroyed();

}
