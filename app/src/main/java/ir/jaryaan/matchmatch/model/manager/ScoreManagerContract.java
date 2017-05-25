package ir.jaryaan.matchmatch.model.manager;

import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import rx.Observable;

/**
 * Created by E.Mehranvari on 5/22/2017.
 */

public interface ScoreManagerContract {
    Observable<ScoreboardLevel> listenToNewScore(@NonNull String scoreId, String levelName);

    @NonNull
    Observable<ScoreboardLevel> sendScore(@NonNull String scoreId, @NonNull ScoreboardLevel scoreboardLevel);
}
