package ir.jaryaan.matchmatch.model.gateways.online;

import android.support.annotation.NonNull;

import rx.Observable;

/**
 * Created by ehsun on 5/17/2017.
 */

public interface FirebaseOnlineGateway {
    @NonNull
    Observable<Boolean> setUserOnline(@NonNull String userId);

    @NonNull
    Observable<Boolean> setUserOffline(@NonNull String userId);
}
