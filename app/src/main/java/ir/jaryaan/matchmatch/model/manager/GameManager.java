package ir.jaryaan.matchmatch.model.manager;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.jaryaan.matchmatch.entities.Card;
import ir.jaryaan.matchmatch.entities.CardImage;
import rx.Observable;

/**
 * Created by E.Mehranvari on 5/13/2017.
 */

public class GameManager implements GameManagerContract {
    private List<Card> cards;
    private static Card firstFlippedCard;

    @Override
    public void initialGame(@NonNull List<CardImage> cardImages) {
        List<Card> cardList = new ArrayList<>();
        for (CardImage image : cardImages) {
            cardList.add(new Card(image.getId(), image));
            cardList.add(new Card(image.getId(), image));
        }
        this.cards = shuffleList(cardList);
    }

    private List<Card> shuffleList(List<Card> cards) {
        Collections.shuffle(cards);
        return cards;
    }


    @Override

    public Observable<Integer> flip(@NonNull Card card) {

        if (card.isFaceDown()) {
            card.flip();
            if (firstFlippedCard == null) {
                firstFlippedCard = card;
                return Observable.just(Card.CARD_STATUS_WAITING_FOR_MATCH);
            } else {

                if (matchCards(firstFlippedCard, card)) {
                    return Observable.just(Card.CARD_STATUS_MATCHED);
                } else {
                    return Observable.just(Card.CARD_STATUS_NOT_MATCHED);
                }

            }
        } else {
            return Observable.just(Card.CARD_STATUS_NOTHING);
        }
    }

    @NonNull
    @Override
    public List<Card> getCards() {
        return cards;
    }

    private boolean matchCards(Card firstCard, Card secondCard) {
        firstFlippedCard = null;
        if (firstCard.equals(secondCard)) {
            return true;
        } else {
            firstCard.flip();
            secondCard.flip();
            return false;
        }
    }
}
