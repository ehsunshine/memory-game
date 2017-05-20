package ir.jaryaan.matchmatch.ui.board;

import java.util.List;

import ir.jaryaan.matchmatch.entities.Card;
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
    }

    interface Presenter extends BasePresenterContract<BoardContract.View> {

        void onCardClicked(Card card);
    }
}
