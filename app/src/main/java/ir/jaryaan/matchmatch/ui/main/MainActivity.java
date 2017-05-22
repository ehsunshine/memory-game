package ir.jaryaan.matchmatch.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.ui.base.BaseActivity;
import ir.jaryaan.matchmatch.ui.board.BoardFragment;

/**
 * Created by ehsun on 5/12/2017.
 */

public class MainActivity extends BaseActivity implements
        MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_SCORE_ID = "SCORE_ID";

    @Inject MainContract.Presenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.timerTextView)
    TextView timerTextView;
    @BindView(R.id.scoreTextView)
    TextView scoreTextView;
    private String scoreID;

    public static Intent newIntent(Context context, @NonNull String scoreID) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_SCORE_ID, scoreID);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.MaterialTheme);
        super.onCreate(savedInstanceState);
        scoreID = getIntent().getStringExtra(EXTRA_SCORE_ID);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void injectDependencies() {
        applicationComponent.inject(this);
        presenter.onBindView(this);

    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        presenter.onViewInitialized();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }

    @Override
    public void showBoardScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, BoardFragment.newInstance(scoreID))
                .commit();

    }

    public void setTimerValue(String timerValue) {
        timerTextView.setText(timerValue);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void setScoreValue(String score) {
        scoreTextView.setText(score);
    }
}
