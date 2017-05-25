package ir.jaryaan.matchmatch.ui.leaderboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import ir.jaryaan.matchmatch.ui.base.BaseFragment;
import ir.jaryaan.matchmatch.ui.leaderboard.adapter.LeaderboardAdapter;

/**
 * Created by E.Mehranvari on 5/23/2017.
 */

public class LeaderboardFragment extends BaseFragment implements
        LeaderboardContract.View {

    public static final String EXTRA_SCORE_ID = "SCORE_ID";
    public static final String EXTRA_POSITION = "POSITION";

    @Inject
    LeaderboardContract.Presenter presenter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private LeaderboardAdapter adapter;
    private String scoreID;
    private int leaderboardID;

    public static Fragment newInstance(int position, @NonNull String scoreID) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_SCORE_ID, scoreID);
        args.putInt(EXTRA_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.scoreID = getArguments().getString(EXTRA_SCORE_ID);
        this.leaderboardID = getArguments().getInt(EXTRA_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }

    @Override
    protected void injectDependencies() {
        applicationComponent.inject(this);
        presenter.onBindView(this);

    }

    @Override
    protected void initViews() {
        adapter = new LeaderboardAdapter(scoreID);
        updateScreenTitle(getString(R.string.leaderboard));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        presenter.onViewInitialized();
    }

    @NonNull
    @Override
    public int getLeaderboardId() {
        return leaderboardID;
    }

    @NonNull
    @Override
    public String getScoreId() {
        return scoreID;
    }

    @Override
    public void addScore(ScoreboardLevel score) {
        adapter.add(score);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onViewDestroyed();
    }
}
