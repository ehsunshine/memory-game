package ir.jaryaan.matchmatch.ui.board;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.jaryaan.matchmatch.entities.Card;
import ir.jaryaan.matchmatch.entities.CardImage;
import ir.jaryaan.matchmatch.model.repository.ImageRepositoryContract;
import ir.jaryaan.matchmatch.utils.scheduler.SchedulerProvider;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ehsun on 5/12/2017.
 */

public class BoardPresenter implements BoardContract.Presenter {

    private BoardContract.View view;
    private ImageRepositoryContract imageRepository;
    private SchedulerProvider schedulerProvider;
    private List<Card> cards = new ArrayList<>();
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public BoardPresenter(@NonNull ImageRepositoryContract imageRepository,
                          @NonNull SchedulerProvider schedulerProvider) {
        this.imageRepository = imageRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void onBindView(@NonNull BoardContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewInitialized() {
        view.showLoading();
        Subscription subscription = imageRepository.getCardImages(12, "Cat", 4)
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getMainScheduler())
                .subscribe(cardImages -> {
                    view.hideLoading();
                    initialGame(cardImages);
                }, throwable -> {
                    view.showErrorMessage(throwable);
                });


        compositeSubscription.add(subscription);
    }

    private void initialGame(List<CardImage> cardImages) {
        cardImages.addAll(cardImages);
        Collections.shuffle(cardImages);
        for (CardImage image : cardImages) {
            Card card = new Card(image.getId(), image);
            cards.add(card);
        }
        view.generateBoard(cards);
    }

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewPaused() {

    }

    @Override
    public void onViewSaveInstanceState(@NonNull Bundle bundle) {

    }

    @Override
    public void onViewRestoreInstanceState(@NonNull Bundle bundle) {

    }

    @Override
    public void onViewDestroyed() {
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

}
