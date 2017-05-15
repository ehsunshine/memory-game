package ir.jaryaan.matchmatch.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import ir.jaryaan.matchmatch.entities.Card;
import lombok.Builder;

/**
 * Created by E.Mehranvari on 5/14/2017.
 */
@Builder
public class CardAnimationUtil {

    private View view;
    private View faceImageView;
    private View backImageView;
    private Card card;

    private int targetX;
    private int targetY;
    private float targetScale;


    public void flipCard() {

        if (card.isFaceDown()) {
            flipRight();
        }
    }

    public void flipBackCard() {
        flipLeft();
    }

    public void moveToDeck(){

        view.animate()
                .rotationX(180)
                .setDuration(500)
                .setListener(null);
        view.animate()
                .alpha(0f)
                .setDuration(400);

    }

    private void flipRight() {

        view.animate()
                .rotationYBy(90)
                .setDuration(150)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (faceImageView.getVisibility() == View.GONE) {
                            faceImageView.setVisibility(View.VISIBLE);
                            flipRight();
                        }
                    }
                });

    }

    private void flipLeft() {
        view.animate()
                .rotationYBy(-90)
                .setDuration(150)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (faceImageView.getVisibility() == View.VISIBLE) {
                            faceImageView.setVisibility(View.GONE);
                            flipLeft();
                        }
                    }
                });
    }

}
