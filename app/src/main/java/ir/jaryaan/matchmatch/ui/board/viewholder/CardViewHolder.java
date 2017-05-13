package ir.jaryaan.matchmatch.ui.board.viewholder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.entities.Card;

/**
 * Created by ehsun on 5/12/2017.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {

    private final int spanNumber;
    @BindView(R.id.face_image_view)
    ImageView faceImageView;
    @BindView(R.id.body_container)
    CardView bodyContainer;

    private Context context;
    private Card card;
    private ViewGroup parent;
    private int cardNumber;
    private CardListener cardListener;


    private CardViewHolder(@NonNull View itemView, @NonNull ViewGroup parent, int spanNumber, int cardNumber, @NonNull CardListener cardListener) {
        super(itemView);
        this.context = itemView.getContext().getApplicationContext();
        this.cardListener = cardListener;
        this.parent = parent;
        this.cardNumber = cardNumber;
        this.spanNumber = spanNumber;
        ButterKnife.bind(this, itemView);
        calculateItemSize();

    }

    private void calculateItemSize() {
        int numberOfRows = cardNumber / spanNumber;
        itemView.setLayoutParams(new RelativeLayout.LayoutParams(parent.getWidth() / spanNumber, parent.getHeight() / numberOfRows));
    }


    public void onBindView(@NonNull Card card) {
        this.card = card;

        Picasso.with(context)
                .load(card.getCardImage().getImageUrl())
                .into(faceImageView);

    }


    @OnClick(R.id.body_container)
    void onMessageBodyClick() {
        flipRight90degree();
        if (cardListener != null) {
            cardListener.onCardClick(card);
        }
    }

    private void flipRight90degree() {
        bodyContainer
                .animate()
                .rotationY(90)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (faceImageView.getVisibility() == View.GONE) {
                            faceImageView.setVisibility(View.VISIBLE);
                            flipRight180degree();
                        } else {
                            flipLeft90degree();
                        }
                    }
                });
    }

    private void flipLeft90degree() {
        bodyContainer
                .animate()
                .rotationY(-90)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (faceImageView.getVisibility() == View.VISIBLE) {
                            faceImageView.setVisibility(View.GONE);
                            flipLeft180degree();
                        } else {
                            flipRight90degree();
                        }
                    }
                });
    }

    private void flipLeft180degree() {
        bodyContainer
                .animate()
                .rotationY(-180)
                .setDuration(300)
                .setListener(null);
    }

    private void flipRight180degree() {
        bodyContainer
                .animate()
                .rotationY(180)
                .setDuration(300)
                .setListener(null);
    }

    public interface CardListener {
        void onCardClick(@NonNull Card card);
    }

    public static class Builder {
        private ViewGroup parent;
        private CardListener cardListener;
        private int cardNumber;
        private int spanCount;

        @NonNull
        public Builder parent(@NonNull ViewGroup parent) {
            this.parent = parent;
            return this;
        }

        @NonNull
        public Builder cardListener(@NonNull CardListener cardListener) {
            this.cardListener = cardListener;
            return this;
        }

        @NonNull
        public Builder cardNumber(int cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        @NonNull
        public CardViewHolder build() {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_item_card_item, parent, false);
            return new CardViewHolder(view, parent, cardNumber, spanCount, cardListener);
        }

        public Builder spanNumber(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }
    }

}
