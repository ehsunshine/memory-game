package ir.jaryaan.matchmatch.ui.leaderboard.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import ir.jaryaan.matchmatch.ui.leaderboard.viewholder.ScoreViewHolder;

/**
 * Created by E.Mehranvari on 5/23/2017.
 */

public class LeaderboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int ITEM_TYPE_SCORE = 1;

    private String currentScoreId;
    private List<ScoreboardLevel> scoreboardLevels = new ArrayList<>();


    public LeaderboardAdapter(@NonNull String currentScoreId) {

        this.currentScoreId = currentScoreId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScoreViewHolder.Builder()
                .parent(parent)
                .build();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ScoreboardLevel scoreboardLevel = scoreboardLevels.get(position);
        ((ScoreViewHolder) holder).onBindView(scoreboardLevel);
    }

    @Override
    public int getItemCount() {
        return scoreboardLevels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_SCORE;
    }

    public void addAll(@NonNull List<ScoreboardLevel> scoreboardLevels) {
        this.scoreboardLevels.addAll(scoreboardLevels);
        notifyDataSetChanged();
    }

    public void add(@NonNull ScoreboardLevel scoreboardLevel) {
        this.scoreboardLevels.add(scoreboardLevel);
        notifyDataSetChanged();
    }

    public void set(@NonNull List<ScoreboardLevel> scoreboardLevels) {
        this.scoreboardLevels.clear();
        this.scoreboardLevels.addAll(scoreboardLevels);
        notifyDataSetChanged();
    }

    public void update(@NonNull ScoreboardLevel scoreboardLevel) {
        int index = scoreboardLevels.indexOf(scoreboardLevel);
        scoreboardLevels.set(index, scoreboardLevel);
        notifyItemChanged(index);
    }
}
