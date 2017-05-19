package ir.jaryaan.matchmatch.model.repository;

import android.support.annotation.NonNull;

import ir.jaryaan.matchmatch.model.repository.SettingRepository.DifficultyLevel;

/**
 * Created by ehsun on 5/19/2017.
 */

public interface SettingRepositoryContract {

    String getCardType();

    void setCardType(@NonNull String typeName);

    @DifficultyLevel
    Integer getDifficultyLevel();

    void setDifficultyLevel(@NonNull @DifficultyLevel Integer difficultyLevel);

    String getNickname();

    void setNickname(@NonNull String nickname);
}
