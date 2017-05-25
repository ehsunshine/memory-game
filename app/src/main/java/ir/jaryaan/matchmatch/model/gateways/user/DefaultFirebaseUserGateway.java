package ir.jaryaan.matchmatch.model.gateways.user;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import rx.Observable;

/**
 * Created by ehsun on 5/17/2017.
 */

public class DefaultFirebaseUserGateway implements FirebaseUserGateway {
    private FirebaseAuth firebaseAuth;

    public DefaultFirebaseUserGateway(@NonNull FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @NonNull
    @Override
    public Observable<Boolean> signInAnonymously() {
        return Observable.create(subscriber -> {
            firebaseAuth.signInAnonymously()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.e("test", "UID: " + task.getResult().getUser().getUid());
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        } else if (task.getException() != null) {
                            subscriber.onError(task.getException());
                        }
                    });
        });
    }

}
