package ir.jaryaan.matchmatch.model.manager;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    private boolean firstCardShouldBeCleared;
    private GameEventListener gameEventListener;
    private CountDownTimer countDownTimer;
    private SharedPreferences sharedPreferences;
    private int currentScore = 0;

    public GameManager(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void initialGame(@NonNull List<CardImage> cardImages, @NonNull GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;

        List<Card> cardList = new ArrayList<>();
        for (CardImage image : cardImages) {
            Random rand = new Random();

            cardList.add(new Card(rand.nextInt(9999), image));
            cardList.add(new Card(rand.nextInt(9999), image));
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

        if (firstCardShouldBeCleared) {
            firstFlippedCard = null;
            firstCardShouldBeCleared = false;
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
                    gameEventListener.onScoreChanged(++currentScore);
                    if (isGameFinished()) {
                        countDownTimer.cancel();
                        gameEventListener.onGameCompleted();
                    }
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

    @Override
    public void start() {

        countDownTimer = new CountDownTimer(90000, 1000) {

            public void onTick(long millisUntilFinished) {
                Duration duration = new Duration(millisUntilFinished);
                Period period = duration.toPeriod();
                PeriodFormatter minutesAndSeconds = new PeriodFormatterBuilder()
                        .printZeroAlways()
                        .appendMinutes()
                        .appendSeparator(":")
                        .appendSeconds()
                        .toFormatter();
                gameEventListener.onGameInProgress(minutesAndSeconds.print(period));
            }

            public void onFinish() {
                gameOver();
            }
        }.start();
    }

    @Override
    public void gameOver() {

        if (!isGameFinished()) {
            gameEventListener.onGameOver();
        } else {
            gameEventListener.onGameCompleted();
        }
    }

    @Override
    public boolean isGameFinished() {
        int collectedCards = 0;
        for (Card card : cards) {
            if (card.isCollected()) {
                collectedCards++;
            }
        }
        return collectedCards == cards.size();
    }

    private boolean matchCards(Card firstCard, Card secondCard) {

        firstCardShouldBeCleared = true;

        if (firstCard.isMatchWith(secondCard)) {
            firstCard.collect();
            secondCard.collect();
            return true;
        } else {
            firstCard.flip();
            secondCard.flip();
            return false;
        }
    }

    public interface GameEventListener {
        void onGameInProgress(@NonNull String remainingTime);

        void onGameOver();

        void onGameCompleted();

        void onScoreChanged(int score);
    }
}
