package ir.jaryaan.matchmatch.model.repository;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;

import ir.jaryaan.matchmatch.utils.SharedPreferenceUtils;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by ehsun on 5/19/2017.
 */

public class SettingRepository implements SettingRepositoryContract {
    public static final int DIFFICULTY_LEVEL_EASY = 8;
    public static final int DIFFICULTY_LEVEL_NORMAL = 12;
    public static final int DIFFICULTY_LEVEL_HARD = 20;
    public static final int DIFFICULTY_LEVEL_INSANE = 24;
    private SharedPreferenceUtils sharedPreferenceUtils;

    public SettingRepository(@NonNull SharedPreferenceUtils sharedPreferenceUtils) {
        this.sharedPreferenceUtils = sharedPreferenceUtils;
    }

    @Override
    public String getCardType() {
        return null;
    }

    @Override
    public void setCardType(@NonNull String typeName) {

    }

    @Override
    @DifficultyLevel
    public Integer getDifficultyLevel() {
        return null;
    }

    @Override
    public void setDifficultyLevel(@NonNull @DifficultyLevel Integer difficultyLevel) {

    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public void setNickname(@NonNull String nickname) {

    }

    @Retention(SOURCE)
    @IntDef({DIFFICULTY_LEVEL_EASY, DIFFICULTY_LEVEL_NORMAL, DIFFICULTY_LEVEL_HARD, DIFFICULTY_LEVEL_INSANE})
    public @interface DifficultyLevel {
    }
}
