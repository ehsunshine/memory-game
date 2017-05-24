package ir.jaryaan.matchmatch.ui.leaderboard.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import ir.jaryaan.matchmatch.ui.leaderboard.viewholder.LoadMoreViewHolder;
import ir.jaryaan.matchmatch.ui.leaderboard.viewholder.ScoreViewHolder;

/**
 * Created by E.Mehranvari on 5/23/2017.
 */

public class LeaderboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int ITEM_TYPE_SCORE = 1;
    private final static int ITEM_TYPE_LOAD_MORE = 2;

    private String currentScoreId;
    private List<ScoreboardLevel> scoreboardLevels = new ArrayList<>();
    private boolean shouldDisplayLoadMore;
    private ScoreEventListener scoreEventListener;

    public LeaderboardAdapter(@NonNull String currentScoreId) {

        this.currentScoreId = currentScoreId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_SCORE:
                return new ScoreViewHolder.Builder()
                        .parent(parent)
                        .build();

            case ITEM_TYPE_LOAD_MORE:
                return new LoadMoreViewHolder.Builder()
                        .parent(parent)
                        .loadMoreListener(() -> scoreEventListener.onLoadMoreClick())
                        .build();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ScoreViewHolder) {
            ScoreboardLevel scoreboardLevel = scoreboardLevels.get(position);

            if (position + 1 == scoreboardLevels.size()) {
                ((ScoreViewHolder) holder).onBindView(scoreboardLevel);
            } else {
                ((ScoreViewHolder) holder).onBindView(scoreboardLevel);
            }
        } else if (holder instanceof LoadMoreViewHolder) {
            ((LoadMoreViewHolder) holder).onBindView();
        }
    }

    @Override
    public int getItemCount() {
        return scoreboardLevels.size() + (shouldDisplayLoadMore && scoreboardLevels.size() > 0 ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return position == scoreboardLevels.size()
                ? ITEM_TYPE_LOAD_MORE
                : ITEM_TYPE_SCORE;
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

    public void setShouldDisplayLoadMore(boolean shouldDisplayLoadMore) {
        this.shouldDisplayLoadMore = shouldDisplayLoadMore;
        notifyDataSetChanged();
    }

    public void update(@NonNull ScoreboardLevel scoreboardLevel) {
        int index = scoreboardLevels.indexOf(scoreboardLevel);
        scoreboardLevels.set(index, scoreboardLevel);
        notifyItemChanged(index);
    }

    public interface ScoreEventListener {
        void onLoadMoreClick();
    }
}
