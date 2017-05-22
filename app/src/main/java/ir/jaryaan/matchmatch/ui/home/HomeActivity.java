package ir.jaryaan.matchmatch.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ir.jaryaan.matchmatch.MatchMatchApplication;
import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.entities.Setting;
import ir.jaryaan.matchmatch.entities.Setting.DifficultyLevel;
import ir.jaryaan.matchmatch.ui.base.BaseActivity;
import ir.jaryaan.matchmatch.ui.main.MainActivity;
import ir.jaryaan.matchmatch.ui.setting.SettingActivity;

/**
 * Created by ehsun on 5/17/2017.
 */

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @Inject
    HomeContract.Presenter presenter;
    @BindView(R.id.settingImageButton)
    ImageView settingImageView;
    @BindView(R.id.nickname_text_view)
    EditText nicknameEditText;

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @NonNull
    @Override
    public String getInstanceId() {
        return MatchMatchApplication.getInstance().getInstanceId();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.MaterialTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }

    @OnClick(R.id.settingImageButton)
    void onSettingClick() {
        presenter.onSettingClicked();
    }

    @OnClick({R.id.easy_button, R.id.normal_button, R.id.hard_button, R.id.insane_button})
    void onStartGameClick(Button button) {
        @DifficultyLevel int difficultyLevel = DEFAULT_KEYS_DIALER;
        switch (button.getText().toString().toLowerCase()) {
            case "easy": {
                difficultyLevel = Setting.DIFFICULTY_LEVEL_EASY;
                break;
            }
            case "normal": {
                difficultyLevel = Setting.DIFFICULTY_LEVEL_NORMAL;
                break;
            }
            case "hard": {
                difficultyLevel = Setting.DIFFICULTY_LEVEL_HARD;
                break;
            }
            case "insane": {
                difficultyLevel = Setting.DIFFICULTY_LEVEL_INSANE;
                break;
            }
        }
        presenter.onGameStarted(nicknameEditText.getText().toString(), difficultyLevel);
    }

    @Override
    public void showGameScreen(@NonNull String scoreID) {
        startActivity(MainActivity.newIntent(this, scoreID));
    }

    @Override
    public void showSettingScreen() {
        startActivity(SettingActivity.newIntent(this));
    }

    @Override
    public void showScoreScreen() {

    }

    @Override
    public void showCurrentSetting(Setting setting) {
        nicknameEditText.setText(setting.getNickname());
    }

    @Override
    protected void injectDependencies() {
        applicationComponent.inject(this);
        presenter.onBindView(this);
    }

    @Override
    protected void initViews() {
        presenter.onViewInitialized();
    }
}
