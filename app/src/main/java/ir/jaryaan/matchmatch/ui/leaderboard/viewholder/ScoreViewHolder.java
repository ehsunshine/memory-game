package ir.jaryaan.matchmatch.ui.leaderboard.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import ir.jaryaan.matchmatch.utils.ConvertUtil;

/**
 * Created by E.Mehranvari on 5/23/2017.
 */

public class ScoreViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.body_container)
    CardView bodyContainer;
    @BindView(R.id.nickname_text_view)
    TextView nicknameTextView;
    @BindView(R.id.score_text_view)
    TextView scoreTextView;
    @BindView(R.id.remaining_time_text_view)
    TextView remainingTimeTextView;
    @BindView(R.id.submit_time_text_view)
    TextView submitTextView;
    private Context context;
    private ScoreboardLevel scoreboardLevel;

    public ScoreViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext().getApplicationContext();
        ButterKnife.bind(this, itemView);
    }

    public void onBindView(@NonNull ScoreboardLevel scoreboardLevel) {
        this.scoreboardLevel = scoreboardLevel;
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("EEEE MM/dd/yyyy HH:mm:ss");

        scoreTextView.setText(String.valueOf(scoreboardLevel.getScore()));
        nicknameTextView.setText(scoreboardLevel.getNickname());
        remainingTimeTextView.setText(ConvertUtil.convertMillisecondToMinutesAndSecond(scoreboardLevel.getTimeRemaining()));
        submitTextView.setText(dateTimeFormatter.print(new DateTime(Long.valueOf(scoreboardLevel.getSubmitTime()))));
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
