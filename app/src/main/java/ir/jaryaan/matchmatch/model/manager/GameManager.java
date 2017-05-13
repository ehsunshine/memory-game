package ir.jaryaan.matchmatch.model.manager;

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

    public void initialGame(List<Card> cards)
    {
        this.cards = duplicateAndShuffleList(cards);
    }

    private List<Card> duplicateAndShuffleList(List<Card> cards) {
        cards.addAll(cards);
        Collections.shuffle(cards);
        return cards;
    }
}
