package ir.jaryaan.matchmatch.network;

import java.util.List;

import ir.jaryaan.matchmatch.entities.CardImage;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ehsun on 5/12/2017.
 */

public interface ApiService {

    @GET("/v1/photos/search")
    Observable<List<CardImage>> getKittens(@Query("rpp") int totalNumber,
                                           @Query("term") String cardFace,
                                           @Query("image_size") int imageSize);

}
