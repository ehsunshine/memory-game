package ir.jaryaan.matchmatch.model.repository;


import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import ir.jaryaan.matchmatch.entities.Setting;
import ir.jaryaan.matchmatch.entities.Setting.DifficultyLevel;
import ir.jaryaan.matchmatch.utils.SharedPreferenceUtils;

import static ir.jaryaan.matchmatch.entities.Setting.DIFFICULTY_LEVEL_DEFAULT;
import static ir.jaryaan.matchmatch.entities.Setting.DIFFICULTY_LEVEL_NORMAL;


/**
 * Created by ehsun on 5/19/2017.
 */

public class SettingRepository implements SettingRepositoryContract {

    private static final String SHARED_PREF_KEY_SETTING = "KEY_SETTING";
    private static final String CAT_CARD_TYPE = "Cat";
    private static final String NICKNAME_ANONYMOUS = "Anonymous";
    private Gson gson;

    private Setting setting;


    private SharedPreferences sharedPreferences;

    public SettingRepository(@NonNull SharedPreferences sharedPreferences,
                             @NonNull Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
        getSetting();
    }

    private void persistSetting() {
        String jsonSetting = gson.toJson(this.setting);
        SharedPreferenceUtils.setString(sharedPreferences, SHARED_PREF_KEY_SETTING, jsonSetting);
    }

    @Override
    public void setNickname(@NonNull String nickname) {
        if (!TextUtils.isEmpty(nickname)) {
            this.setting.setNickname(nickname);
        }
        persistSetting();
    }

    @Override
    public void setDifficultyLevel(@DifficultyLevel Integer difficultyLevel) {
        this.setting.setDifficultyLevel(difficultyLevel);
        persistSetting();
    }

    @Override
    public void setCardType(@NonNull String cardType) {
        this.setting.setCardType(cardType);
        persistSetting();
    }

    @Override
    public Setting getSetting() {
        if (this.setting == null) {
            String jsonSetting = SharedPreferenceUtils.getString(sharedPreferences, SHARED_PREF_KEY_SETTING, "");
            if (!TextUtils.isEmpty(jsonSetting)) {
                this.setting = gson.fromJson(jsonSetting, Setting.class);
            } else {
                this.setting = Setting.builder()
                        .nickname(NICKNAME_ANONYMOUS)
                        .difficultyLevel(DIFFICULTY_LEVEL_NORMAL)
                        .cardType(CAT_CARD_TYPE)
                        .build();
            }
        } else {
            if (TextUtils.isEmpty(setting.getCardType())) {
                setting.setCardType(CAT_CARD_TYPE);
            }
            if (setting.getDifficultyLevel() == DIFFICULTY_LEVEL_DEFAULT) {
                setting.setDifficultyLevel(DIFFICULTY_LEVEL_NORMAL);
            }
            if (TextUtils.isEmpty(setting.getNickname())) {
                setting.setNickname(NICKNAME_ANONYMOUS);
            }
        }
        return setting;
    }


}
