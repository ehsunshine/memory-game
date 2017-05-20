package ir.jaryaan.matchmatch.model.repository;

import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.entities.Setting;
import ir.jaryaan.matchmatch.entities.Setting.DifficultyLevel;

/**
 * Created by ehsun on 5/19/2017.
 */

public interface SettingRepositoryContract {

    void setNickname(@NonNull String nickname);

    void setDifficultyLevel(@DifficultyLevel Integer difficultyLevel);

    void setCardType(@NonNull String cardType);

    Setting getSetting();
}
