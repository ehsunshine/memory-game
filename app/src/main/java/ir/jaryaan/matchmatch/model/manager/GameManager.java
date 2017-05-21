package ir.jaryaan.matchmatch.model.manager;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ir.jaryaan.matchmatch.entities.Card;
import ir.jaryaan.matchmatch.entities.CardImage;
import ir.jaryaan.matchmatch.model.entities.CardFlipStatus;
import ir.jaryaan.matchmatch.utils.ConvertUtil;
import rx.Observable;

import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_MATCHED;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_NOTHING;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_NOT_MATCHED;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_WAITING_FOR_MATCH;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by E.Mehranvari on 5/13/2017.
 */

public class GameManager implements GameManagerContract {

    public static final String GAME_STATUS_FINISHED = "Completed";
    public static final String GAME_STATUS_OVER = "Game Over";

    private static Card firstFlippedCard;
    private List<Card> cards;
    private boolean firstCardShouldBeCleared;
    private GameEventListener gameEventListener;
    private CountDownTimer countDownTimer;
    private int currentScore = 0;
    private long currentTime;

    public GameManager() {
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
                        gameEventListener.onGameCompleted(currentScore, currentTime);
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
        currentScore = 0;

        countDownTimer = new CountDownTimer(15 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                currentTime = millisUntilFinished;

                gameEventListener.onGameInProgress(
                        ConvertUtil.convertMillisecondToMinutesAndSecond(millisUntilFinished));
            }

            public void onFinish() {
                gameOver();
            }
        }.start();
    }

    @Override
    public void gameOver() {

        if (!isGameFinished()) {
            gameEventListener.onGameOver(currentScore, currentTime);
        } else {
            gameEventListener.onGameCompleted(currentScore, currentTime);
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

    @Override
    public void stop() {
        countDownTimer.cancel();
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

    @Retention(SOURCE)
    @StringDef({GAME_STATUS_FINISHED, GAME_STATUS_OVER})
    public @interface GameStatus {
    }

    public interface GameEventListener {
        void onGameInProgress(@NonNull String remainingTime);

        void onGameOver(int score, long remainedTime);

        void onGameCompleted(int score, long remainedTime);

        void onScoreChanged(int score);
    }
}
