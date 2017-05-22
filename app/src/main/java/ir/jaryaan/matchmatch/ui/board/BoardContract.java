package ir.jaryaan.matchmatch.ui.board;

import android.support.annotation.NonNull;

import java.util.List;

import ir.jaryaan.matchmatch.entities.Card;
import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import ir.jaryaan.matchmatch.ui.base.BasePresenterContract;
import ir.jaryaan.matchmatch.ui.base.BaseViewContract;

/**
 * Created by ehsun on 5/12/2017.
 */

public interface BoardContract {
    interface View extends BaseViewContract {

        void generateBoard(List<Card> cards);

        void flipCardsBack(Card firstCard, Card secondCard);

        void winCards(Card firstCard, Card secondCard);

        void showNickname(String nickname);

        void showCards();

        void hideGameOverDialog();

        @NonNull
        String getScoreId();

        void showHomeScreen();
    }

    interface Presenter extends BasePresenterContract<BoardContract.View> {

        void onCardClicked(Card card);

        void onGameStarted();

        void onScoreSubmitted(@NonNull ScoreboardLevel scoreboardLevel);

        void onGameRetried();
    }
}
