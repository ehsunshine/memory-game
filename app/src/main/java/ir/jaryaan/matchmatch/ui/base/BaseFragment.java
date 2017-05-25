package ir.jaryaan.matchmatch.ui.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.jaryaan.matchmatch.ApplicationComponent;
import ir.jaryaan.matchmatch.MatchMatchApplication;
import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.exception.ApiBusinessException;
import ir.jaryaan.matchmatch.exception.ApiNetworkException;

/**
 * Created by ehsun on 5/12/2017.
 */

public abstract class BaseFragment extends Fragment implements BaseViewContract {

    protected ApplicationComponent applicationComponent;
    protected int mediumAnimationDuration;
    protected int shortAnimationDuration;

    @Nullable
    @BindView(R.id.loading_container)
    View loadingView;

    private Unbinder unbinder;
    private ViewPropertyAnimator animator;

    protected abstract void injectDependencies();

    protected abstract void initViews();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.applicationComponent = MatchMatchApplication.getInstance().getApplicationComponent();
        this.unbinder = ButterKnife.bind(this, view);
        mediumAnimationDuration = getResources().getInteger(
                android.R.integer.config_mediumAnimTime);
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        injectDependencies();
        initViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (animator != null)
            animator.setListener(null);
        this.unbinder.unbind();
        this.unbinder = null;
        this.applicationComponent = null;
    }

    @Override
    public void showErrorMessage(@NonNull String errorMessage) {
        if (getView() == null) {
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        } else {
            Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showErrorMessage(int errorMessageResourceId) {
        if (getView() == null) {
            Toast.makeText(getActivity(), errorMessageResourceId, Toast.LENGTH_LONG).show();
        } else {
            Snackbar.make(getView(), errorMessageResourceId, Snackbar.LENGTH_LONG).show();
        }
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

    protected void updateScreenTitle(@Nullable String title) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (title != null && appCompatActivity != null && appCompatActivity.getSupportActionBar() != null)
            appCompatActivity.getSupportActionBar().setTitle(title);
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
