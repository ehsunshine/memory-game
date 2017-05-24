package ir.jaryaan.matchmatch.ui.scoreboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.ui.base.BaseActivity;
import ir.jaryaan.matchmatch.ui.scoreboard.adapter.FragmentListPagerAdapter;

/**
 * Created by ehsun on 5/22/2017.
 */

public class ScoreboardActivity extends BaseActivity implements
        ScoreboardContract.View {
    public static final String EXTRA_SCORE_ID = "SCORE_ID";

    @Inject ScoreboardContract.Presenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root_container)
    View rootContainer;
    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private FragmentListPagerAdapter fragmentListPagerAdapter;
    private String scoreID;

    public static Intent newIntent(Context context, @NonNull String scoreID) {
        Intent intent = new Intent(context, ScoreboardActivity.class);
        intent.putExtra(EXTRA_SCORE_ID, scoreID);
        return intent;
    }

    @NonNull
    @Override
    public String getScoreId() {
        return scoreID;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scoreID = getIntent().getStringExtra(EXTRA_SCORE_ID);
        setContentView(R.layout.activity_leaderboard);
    }

    @Override
    protected void injectDependencies() {
        applicationComponent.inject(this);
        presenter.onBindView(this);
    }

    @Override
    protected void initViews() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentListPagerAdapter = new FragmentListPagerAdapter(getSupportFragmentManager(), scoreID);
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(fragmentListPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        presenter.onViewInitialized();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }
}
