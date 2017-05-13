package ir.jaryaan.matchmatch.model.manager;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.jaryaan.matchmatch.entities.Card;
import ir.jaryaan.matchmatch.entities.CardImage;

/**
 * Created by E.Mehranvari on 5/13/2017.
 */

public class GameManager implements GameManagerContract {
    private List<Card> cards;
    private Card firstFlippedCard;

    @Override
    public void initialGame(@NonNull List<CardImage> cardImages) {
        List<Card> cardList = new ArrayList<>();
        for (CardImage image : cardImages) {
            Card card = new Card(image.getId(), image);
            cardList.add(card);
        }
        this.cards = duplicateAndShuffleList(cardList);
    }

    private List<Card> duplicateAndShuffleList(List<Card> cards) {
        cards.addAll(cards);
        Collections.shuffle(cards);
        return cards;
    }


    @Override
    @Card.CardStatus
    public int flip(@NonNull Card card) {

        if (card.isFaceDown()) {
            card.flip();
            if (firstFlippedCard == null) {
                firstFlippedCard = card;
                return Card.CARD_STATUS_WAITING_FOR_MATCH;
            } else {
                return matchCards(firstFlippedCard, card) ?
                        Card.CARD_STATUS_MATCHED :
                        Card.CARD_STATUS_NOT_MATCHED;

            }
        } else {
            return Card.CARD_STATUS_NOTHING;
        }
    }

    @NonNull
    @Override
    public List<Card> getCards() {
        return cards;
    }

    private boolean matchCards(Card firstCard, Card secondCard) {
        if (firstCard.equals(secondCard)) {
            return true;
        } else {
            firstCard.flip();
            secondCard.flip();
            firstFlippedCard = null;
            return false;
        }
    }
}
