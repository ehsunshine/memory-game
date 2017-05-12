package ir.jaryaan.matchmatch.model.repository;

import android.support.annotation.NonNull;

import java.util.List;

import ir.jaryaan.matchmatch.entities.CardImage;
import rx.Observable;

/**
 * Created by ehsun on 5/12/2017.
 */

public interface ImageRepositoryContract {
    @NonNull
    Observable<List<CardImage>> getCardImages(int totalNumber,
                                              String cardFace,
                                              int imageSize);

}
