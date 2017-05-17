package ir.jaryaan.matchmatch.model.gateways.user;

import android.support.annotation.NonNull;

import rx.Observable;

/**
 * Created by ehsun on 5/17/2017.
 */

public interface FirebaseUserGateway {
    @NonNull
    Observable<Boolean> signInAnonymously();
}
