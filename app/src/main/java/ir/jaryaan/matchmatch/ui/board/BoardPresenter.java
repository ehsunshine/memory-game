package ir.jaryaan.matchmatch.ui.board;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;

import ir.jaryaan.matchmatch.entities.Card;
import ir.jaryaan.matchmatch.entities.CardImage;
import ir.jaryaan.matchmatch.model.manager.GameManager;
import ir.jaryaan.matchmatch.model.manager.GameManagerContract;
import ir.jaryaan.matchmatch.model.repository.ImageRepositoryContract;
import ir.jaryaan.matchmatch.model.repository.SettingRepositoryContract;
import ir.jaryaan.matchmatch.utils.scheduler.SchedulerProvider;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_MATCHED;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_NOTHING;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_NOT_MATCHED;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_WAITING_FOR_MATCH;

/**
 * Created by ehsun on 5/12/2017.
 */

public class BoardPresenter implements BoardContract.Presenter {

    private SettingRepositoryContract settingRepository;
    private GameManagerContract gameManager;
    private BoardContract.View view;
    private ImageRepositoryContract imageRepository;
    private SchedulerProvider schedulerProvider;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public BoardPresenter(@NonNull ImageRepositoryContract imageRepository,
                          @NonNull GameManagerContract gameManager,
                          @NonNull SchedulerProvider schedulerProvider,
                          @NonNull SettingRepositoryContract settingRepository) {
        this.imageRepository = imageRepository;
        this.gameManager = gameManager;
        this.schedulerProvider = schedulerProvider;
        this.settingRepository = settingRepository;
    }

    @Override
    public void onBindView(@NonNull BoardContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewInitialized() {
        view.showNickname(settingRepository.getSetting().getNickname());
        view.showLoading();
        Subscription subscription = imageRepository.getCardImages(
                settingRepository.getSetting().getDifficultyLevel(),
                settingRepository.getSetting().getCardType(), 4)
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

        gameManager.initialGame(cardImages, (GameManager.GameEventListener) view);
        gameManager.start();
        view.generateBoard(gameManager.getCards());
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
        gameManager.stop();
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onCardClicked(Card card) {
        Subscription subscription = gameManager.flip(card)
                .subscribeOn(schedulerProvider.getComputationScheduler())
                .observeOn(schedulerProvider.getMainScheduler())
                .subscribe(cardFlipStatus -> {
                    switch (cardFlipStatus.getStatus()) {
                        case CARD_STATUS_NOTHING:
                            break;
                        case CARD_STATUS_WAITING_FOR_MATCH:
                            break;
                        case CARD_STATUS_MATCHED:
                            view.winCards(cardFlipStatus.getFirstCard(), cardFlipStatus.getSecondCard());
                            break;
                        case CARD_STATUS_NOT_MATCHED: {
                            view.flipCardsBack(cardFlipStatus.getFirstCard(), cardFlipStatus.getSecondCard());
                            break;
                        }
                    }
                }, throwable -> view.showErrorMessage(throwable));

        compositeSubscription.add(subscription);
    }
}
