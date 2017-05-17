package ir.jaryaan.matchmatch.model.gateways.online;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ir.jaryaan.matchmatch.model.gateways.FirebaseStructure;
import rx.Observable;

/**
 * Created by ehsun on 5/17/2017.
 */

public class DefaultFirebaseOnlineGateway implements FirebaseOnlineGateway {

    private FirebaseDatabase firebaseDatabase;

    public DefaultFirebaseOnlineGateway(@NonNull FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override
    @NonNull
    public Observable<Boolean> setUserOnline(@NonNull String userId) {
        return Observable.create(subscriber -> {
            DatabaseReference onlineReference = firebaseDatabase.getReference()
                    .child(FirebaseStructure.ONLINE)
                    .child(userId);
            DatabaseReference connectionReference = firebaseDatabase.getReference()
                    .child(".info")
                    .child("connected");

            connectionReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if ((Boolean) dataSnapshot.getValue()) {
                        onlineReference.onDisconnect().removeValue();
                        onlineReference.setValue(true);

                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    subscriber.onError(databaseError.toException());
                }
            });
        });
    }

    @NonNull
    @Override
    public Observable<Boolean> setUserOffline(@NonNull String userId) {
        return Observable.fromCallable(() -> {
            DatabaseReference onlineReference = firebaseDatabase.getReference()
                    .child(FirebaseStructure.ONLINE)
                    .child(userId);
            onlineReference.removeValue();
            return true;
        });
    }
}
