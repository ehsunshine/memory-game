package ir.jaryaan.matchmatch.model.gateways.scoreboard;

import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import ir.jaryaan.matchmatch.model.gateways.FirebaseStructure;
import rx.Observable;

/**
 * Created by ehsun on 5/16/2017.
 */

public class DefaultFirebaseScoreboardGateway
        implements FirebaseScoreboardGateway {

    private FirebaseDatabase firebaseDatabase;

    public DefaultFirebaseScoreboardGateway(@NonNull FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override
    @NonNull
    public Observable<ScoreboardLevel> subscribeScores(@NonNull String scoreID, @NonNull String levelName) {
        DatabaseReference scoreRef = firebaseDatabase.getReference()
                .child(FirebaseStructure.SCOREBOARD)
                .child(levelName);

        final ChildEventListener[] childEventListener = new ChildEventListener[1];

        Observable<ScoreboardLevel> scoreboardLevelObservable = Observable.create(subscriber -> {
            childEventListener[0] = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ScoreboardLevel scoreboardLevel = ScoreboardLevel.fromMap((Map<String, Object>) postSnapshot.getValue());
                        subscriber.onNext(scoreboardLevel);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    subscriber.onError(databaseError.toException());
                }
            };
            scoreRef.orderByChild(FirebaseStructure.Scoreboards.Scores.SCORE)
                    .addChildEventListener(childEventListener[0]);
        });

        scoreboardLevelObservable = scoreboardLevelObservable.doOnUnsubscribe(() ->
                scoreRef.removeEventListener(childEventListener[0]));

        return scoreboardLevelObservable;
    }

    @NonNull
    @Override
    public Observable<ScoreboardLevel> addScore(@NonNull String scoreID,
                                                @NonNull ScoreboardLevel scoreboardLevel) {
        return Observable.create(subscriber -> {
            DatabaseReference messageRef = firebaseDatabase.getReference()
                    .child(FirebaseStructure.SCOREBOARD)
                    .child(scoreboardLevel.getLevel())
                    .child(scoreID);

            messageRef.setValue(scoreboardLevel.toMap(), (databaseError, databaseReference) -> {
                if (databaseError != null) {
                    subscriber.onError(databaseError.toException());
                } else {
                    subscriber.onNext(scoreboardLevel);
                    subscriber.onCompleted();
                }
            });
        });
    }

}
