package ir.jaryaan.matchmatch.ui.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.jaryaan.matchmatch.ApplicationComponent;
import ir.jaryaan.matchmatch.MatchMatchApplication;
import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.exception.ApiBusinessException;
import ir.jaryaan.matchmatch.exception.ApiNetworkException;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ehsun on 5/11/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseViewContract {
    protected ApplicationComponent applicationComponent;

    protected int mediumAnimationDuration;
    protected int shortAnimationDuration;


    @Nullable
    @BindView(R.id.loading_container)
    View loadingView;

    private ViewPropertyAnimator animator;

    protected abstract void injectDependencies();

    protected abstract void initViews();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.applicationComponent = MatchMatchApplication.getInstance().getApplicationComponent();
        injectDependencies();

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mediumAnimationDuration = getResources().getInteger(
                android.R.integer.config_mediumAnimTime);
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animator != null)
            animator.setListener(null);
        applicationComponent = null;
    }

    protected void updateScreenTitle(@Nullable String title) {
        if (title != null && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void showErrorMessage(@NonNull String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorMessage(int errorMessageResourceId) {
        Toast.makeText(this, errorMessageResourceId, Toast.LENGTH_LONG).show();
    }

    @Override
    public final void showErrorMessage(@NonNull Throwable throwable) {
        if (throwable instanceof ApiBusinessException) {
            showErrorMessage(throwable.getMessage());
        } else if (throwable instanceof ApiNetworkException) {
            showErrorMessage(R.string.network_error_message);
        } else {
            showErrorMessage(R.string.unknown_error_message);
        }
    }

    @Override
    public void showLoading() {
        if (loadingView != null) {
            loadingView.setAlpha(1f);
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (loadingView != null) {
            animator = loadingView.animate()
                    .alpha(0f)
                    .setDuration(mediumAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            loadingView.setVisibility(View.GONE);
                        }
                    });
        }
    }

}
