package ir.jaryaan.matchmatch.model.manager;

import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import ir.jaryaan.matchmatch.model.gateways.scoreboard.FirebaseScoreboardGateway;
import rx.Observable;

/**
 * Created by E.Mehranvari on 5/22/2017.
 */

public class ScoreManager implements ScoreManagerContract {

    private FirebaseScoreboardGateway firebaseScoreboardGateway;

    public ScoreManager(@NonNull FirebaseScoreboardGateway firebaseScoreboardGateway) {
        this.firebaseScoreboardGateway = firebaseScoreboardGateway;
    }

    @Override
    public Observable<ScoreboardLevel> listenToNewScore(@NonNull String scoreId, String levelName) {
        return firebaseScoreboardGateway.subscribeScores(scoreId, levelName);
    }

    @NonNull
    @Override
    public Observable<ScoreboardLevel> sendScore(@NonNull String scoreId, @NonNull ScoreboardLevel scoreboardLevel) {
        return firebaseScoreboardGateway.addScore(scoreId, scoreboardLevel);
    }

}
