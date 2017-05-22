package ir.jaryaan.matchmatch.model.manager;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ir.jaryaan.matchmatch.entities.Card;
import ir.jaryaan.matchmatch.entities.CardImage;
import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import ir.jaryaan.matchmatch.model.entities.CardFlipStatus;
import ir.jaryaan.matchmatch.model.repository.SettingRepositoryContract;
import ir.jaryaan.matchmatch.utils.ConvertUtil;
import ir.jaryaan.matchmatch.utils.CountDown;
import rx.Observable;

import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_MATCHED;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_NOTHING;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_NOT_MATCHED;
import static ir.jaryaan.matchmatch.entities.Card.CARD_STATUS_WAITING_FOR_MATCH;
import static ir.jaryaan.matchmatch.entities.Setting.DIFFICULTY_LEVEL_EASY;
import static ir.jaryaan.matchmatch.entities.Setting.DIFFICULTY_LEVEL_HARD;
import static ir.jaryaan.matchmatch.entities.Setting.DIFFICULTY_LEVEL_INSANE;
import static ir.jaryaan.matchmatch.entities.Setting.DIFFICULTY_LEVEL_NORMAL;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by E.Mehranvari on 5/13/2017.
 */

public class GameManager implements GameManagerContract {

    public static final String GAME_STATUS_FINISHED = "Completed";
    public static final String GAME_STATUS_OVER = "Game Over";

    private static Card firstFlippedCard;
    private SettingRepositoryContract settingRepository;
    private List<Card> cards;
    private boolean firstCardShouldBeCleared;
    private GameEventListener gameEventListener;
    private CountDown countDown;
    private ScoreboardLevel scoreboardLevel;

    public GameManager(@NonNull SettingRepositoryContract settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public void initialGame(@NonNull List<CardImage> cardImages, @NonNull GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;

        countDown = new CountDown(120, 1000);

        scoreboardLevel = new ScoreboardLevel();
        switch (settingRepository.getSetting().getDifficultyLevel()) {
            case DIFFICULTY_LEVEL_EASY:
                scoreboardLevel.setLevel("Easy");
                break;
            case DIFFICULTY_LEVEL_NORMAL:
                scoreboardLevel.setLevel("Normal");
                break;
            case DIFFICULTY_LEVEL_HARD:
                scoreboardLevel.setLevel("Hard");
                break;
            case DIFFICULTY_LEVEL_INSANE:
                scoreboardLevel.setLevel("Insane");
                break;
        }
        scoreboardLevel.setNickname(settingRepository.getSetting().getNickname());

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
                    scoreboardLevel.increaseScore();
                    gameEventListener.onScoreChanged(scoreboardLevel.getScore());
                    if (isGameFinished()) {
                        countDown.stop();
                        gameEventListener.onGameCompleted(scoreboardLevel);
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
        scoreboardLevel.setScore(0);
        gameEventListener.onScoreChanged(scoreboardLevel.getScore());

        countDown.start(new CountDown.CountDownTimerListener() {
            @Override
            public void onTick(long milliseconds) {
                scoreboardLevel.setTimeRemaining(milliseconds);

                gameEventListener.onGameInProgress(
                        ConvertUtil.convertMillisecondToMinutesAndSecond(milliseconds));
            }

            @Override
            public void onFinished() {
                gameOver();
            }
        });

    }

    @Override
    public void gameOver() {

        if (!isGameFinished()) {
            gameEventListener.onGameOver(scoreboardLevel);
        } else {
            gameEventListener.onGameCompleted(scoreboardLevel);
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
        countDown.stop();
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

        void onGameOver(@NonNull ScoreboardLevel scoreboardLevel);

        void onGameCompleted(@NonNull ScoreboardLevel scoreboardLevel);

        void onScoreChanged(long score);
    }
}
