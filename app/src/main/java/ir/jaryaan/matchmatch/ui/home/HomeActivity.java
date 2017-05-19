package ir.jaryaan.matchmatch.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ir.jaryaan.matchmatch.R;
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

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeActivity.class);
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

    @Override
    public void showGameScreen() {
        startActivity(MainActivity.newIntent(this));
    }

    @Override
    public void showSettingScreen() {
        startActivity(SettingActivity.newIntent(this));
    }

    @Override
    public void showScoreScreen() {

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
