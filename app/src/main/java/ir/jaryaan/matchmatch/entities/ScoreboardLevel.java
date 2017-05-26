package ir.jaryaan.matchmatch.entities;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

import ir.jaryaan.matchmatch.model.gateways.FirebaseStructure;
import ir.jaryaan.matchmatch.utils.ConvertUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ehsun on 5/17/2017.
 */
@Getter
@Setter
public class ScoreboardLevel {

    private String level;
    private String nickname;
    private int score;
    private long timeRemaining;
    private long submitTime;
    private long weight;
    private String scoreID;

    public static ScoreboardLevel fromMap(Map<String, Object> map, String scoreID) {
        ScoreboardLevel scoreboardLevel = new ScoreboardLevel();
        scoreboardLevel.level = (String) map.get(FirebaseStructure.Scoreboards.LEVEL);
        scoreboardLevel.nickname = ConvertUtil.castString(map.get(FirebaseStructure.Scoreboards.Scores.NICKNAME), "");
        scoreboardLevel.submitTime = ConvertUtil.castLong(map.get(FirebaseStructure.Scoreboards.Scores.SUBMIT_DATETIME), 0);
        scoreboardLevel.timeRemaining = ConvertUtil.castLong(map.get(FirebaseStructure.Scoreboards.Scores.REMAINING_TIME), 0);
        scoreboardLevel.score = ConvertUtil.castInt(map.get(FirebaseStructure.Scoreboards.Scores.SCORE), 0);
        scoreboardLevel.weight = ConvertUtil.castLong(map.get(FirebaseStructure.Scoreboards.Scores.WEIGHT), 0);
        scoreboardLevel.scoreID = scoreID;
        return scoreboardLevel;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(FirebaseStructure.Scoreboards.LEVEL, level);
        map.put(FirebaseStructure.Scoreboards.Scores.NICKNAME, nickname);
        map.put(FirebaseStructure.Scoreboards.Scores.SUBMIT_DATETIME, ServerValue.TIMESTAMP);
        map.put(FirebaseStructure.Scoreboards.Scores.SCORE, score);
        map.put(FirebaseStructure.Scoreboards.Scores.REMAINING_TIME, timeRemaining);
        map.put(FirebaseStructure.Scoreboards.Scores.WEIGHT, weight);
        return map;
    }

    public void increaseScore() {
        score++;
    }

    public void generateWeight() {
        weight = -((timeRemaining == 0 ? 1 : timeRemaining) * score);
    }
}
