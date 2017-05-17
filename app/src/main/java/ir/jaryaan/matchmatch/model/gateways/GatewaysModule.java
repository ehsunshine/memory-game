package ir.jaryaan.matchmatch.model.gateways;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.jaryaan.matchmatch.model.gateways.online.DefaultFirebaseOnlineGateway;
import ir.jaryaan.matchmatch.model.gateways.online.FirebaseOnlineGateway;
import ir.jaryaan.matchmatch.model.gateways.scoreboard.DefaultFirebaseScoreboardGateway;
import ir.jaryaan.matchmatch.model.gateways.scoreboard.FirebaseScoreboardGateway;
import ir.jaryaan.matchmatch.model.gateways.user.DefaultFirebaseUserGateway;
import ir.jaryaan.matchmatch.model.gateways.user.FirebaseUserGateway;

/**
 * Created by ehsun on 5/17/2017.
 */

@Module
public class GatewaysModule {
    @Singleton
    @Provides
    public FirebaseUserGateway provideFirebaseUserGateway(@NonNull FirebaseAuth firebaseAuth) {
        return new DefaultFirebaseUserGateway(firebaseAuth);
    }

    @Singleton
    @Provides
    public FirebaseOnlineGateway provideFirebaseOnlineGateway(@NonNull FirebaseDatabase firebaseDatabase) {
        return new DefaultFirebaseOnlineGateway(firebaseDatabase);
    }

    @Singleton
    @Provides
    public FirebaseScoreboardGateway provideFirebaseScoreboardGateway(@NonNull FirebaseDatabase firebaseDatabase) {
        return new DefaultFirebaseScoreboardGateway(firebaseDatabase);
    }

}
