package ir.jaryaan.matchmatch.ui.leaderboard.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.jaryaan.matchmatch.R;

/**
 * Created by E.Mehranvari on 5/23/2017.
 */

public class LoadMoreViewHolder extends RecyclerView.ViewHolder {

    private LoadMoreListener loadMoreListener;

    private LoadMoreViewHolder(@NonNull View itemView, @NonNull LoadMoreListener loadMoreListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.loadMoreListener = loadMoreListener;
    }

    public void onBindView() {
    }

    @OnClick(R.id.root_container)
    void onLoadMoreClick() {
        if (loadMoreListener != null) {
            loadMoreListener.onLoadMoreClick();
        }
    }

    public interface LoadMoreListener {
        void onLoadMoreClick();
    }

    public static class Builder {
        private ViewGroup parent;
        private LoadMoreListener loadMoreListener;

        @NonNull
        public Builder parent(@NonNull ViewGroup parent) {
            this.parent = parent;
            return this;
        }

        @NonNull
        public Builder loadMoreListener(@NonNull LoadMoreListener loadMoreListener) {
            this.loadMoreListener = loadMoreListener;
            return this;
        }

        @NonNull
        public LoadMoreViewHolder build() {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_score_load_more, parent, false);

            return new LoadMoreViewHolder(view, loadMoreListener);
        }
    }
}