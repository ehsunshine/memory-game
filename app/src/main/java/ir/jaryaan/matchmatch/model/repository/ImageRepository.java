package ir.jaryaan.matchmatch.model.repository;

import android.support.annotation.NonNull;

import java.util.List;

import ir.jaryaan.matchmatch.entities.CardImage;
import ir.jaryaan.matchmatch.network.ApiService;
import rx.Observable;

/**
 * Created by ehsun on 5/12/2017.
 */

public class ImageRepository implements ImageRepositoryContract {

    private ApiService apiService;

    public ImageRepository(@NonNull ApiService apiService) {
        this.apiService = apiService;
    }


    @NonNull
    @Override
    public Observable<List<CardImage>> getCardImages(int totalNumber, String cardFace, int imageSize) {
        return apiService.getKittens(totalNumber, cardFace, imageSize);
    }
}
