package ir.jaryaan.matchmatch.model.manager;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.jaryaan.matchmatch.entities.Card;
import ir.jaryaan.matchmatch.entities.CardImage;
import ir.jaryaan.matchmatch.model.entities.CardFlipStatus;
import rx.Observable;

import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_MATCHED;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_NOTHING;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_NOT_MATCHED;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_WAITING_FOR_MATCH;

/**
 * Created by E.Mehranvari on 5/13/2017.
 */

public class GameManager implements GameManagerContract {
    private static Card firstFlippedCard;
    private List<Card> cards;
    private boolean firstCardShouldBeCleard;

    @Override
    public void initialGame(@NonNull List<CardImage> cardImages) {
        List<Card> cardList = new ArrayList<>();
        for (CardImage image : cardImages) {
            cardList.add(new Card(image));
            cardList.add(new Card(image));
        }
        this.cards = shuffleList(cardList);
    }

    private List<Card> shuffleList(List<Card> cards) {
        Collections.shuffle(cards);
        return cards;
    }


    @Override
    @NonNull
    public Observable<CardFlipStatus> flip(@NonNull Card card) {

        if(firstCardShouldBeCleard)
        {
            firstFlippedCard = null;
            firstCardShouldBeCleard = false;
        }

        if (card.isFaceDown()) {
            card.flip();
            if (firstFlippedCard == null) {
                firstFlippedCard = card;
                return Observable.just(CardFlipStatus
                        .builder()
                        .firstCard(firstFlippedCard)
                        .secondCard(null)
                        .status(CARD_STATUS_WAITING_FOR_MATCH)
                        .build());
            } else {

                if (matchCards(firstFlippedCard, card)) {
                    return Observable.just(CardFlipStatus
                            .builder()
                            .firstCard(firstFlippedCard)
                            .secondCard(card)
                            .status(CARD_STATUS_MATCHED)
                            .build());
                } else {
                    return Observable.just(CardFlipStatus
                            .builder()
                            .firstCard(firstFlippedCard)
                            .secondCard(card)
                            .status(CARD_STATUS_NOT_MATCHED)
                            .build());
                }

            }
        } else {
            return Observable.just(CardFlipStatus
                    .builder()
                    .firstCard(null)
                    .secondCard(null)
                    .status(CARD_STATUS_NOTHING)
                    .build());
        }
    }

    @NonNull
    @Override
    public List<Card> getCards() {
        return cards;
    }

    private boolean matchCards(Card firstCard, Card secondCard) {

        firstCardShouldBeCleard = true;

        if (firstCard.equals(secondCard)) {
            return true;
        } else {
            firstCard.flip();
            secondCard.flip();
            return false;
        }
    }
}
