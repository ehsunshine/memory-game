package ir.jaryaan.matchmatch.model.gateways.scoreboard;

import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import rx.Observable;

/**
 * Created by ehsun on 5/16/2017.
 */

public interface FirebaseScoreboardGateway {
    @NonNull
    Observable<ScoreboardLevel> subscribeScores(@NonNull String scoreID, @NonNull String levelName);

    @NonNull
    Observable<ScoreboardLevel> addScore(@NonNull String scoreID,
                                         @NonNull String levelName,
                                         @NonNull ScoreboardLevel scoreboardLevel);
}

