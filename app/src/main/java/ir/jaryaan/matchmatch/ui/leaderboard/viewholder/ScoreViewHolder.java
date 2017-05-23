package ir.jaryaan.matchmatch.ui.leaderboard.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.entities.ScoreboardLevel;

/**
 * Created by E.Mehranvari on 5/23/2017.
 */

public class ScoreViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ScoreboardLevel scoreboardLevel;

    public ScoreViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext().getApplicationContext();
    }

    public void onBindView(@NonNull ScoreboardLevel scoreboardLevel) {
        this.scoreboardLevel = scoreboardLevel;
    }

    public static class Builder {
        private ViewGroup parent;

        @NonNull
        public Builder parent(@NonNull ViewGroup parent) {
            this.parent = parent;
            return this;
        }

        @NonNull
        public ScoreViewHolder build() {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_score_item, parent, false);

            return new ScoreViewHolder(view);
        }
    }
}
