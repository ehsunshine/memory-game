package ir.jaryaan.matchmatch.ui.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.ui.base.BaseActivity;
import ir.jaryaan.matchmatch.ui.home.HomeActivity;

/**
 * Created by ehsun on 5/12/2017.
 */

public class LaunchActivity extends BaseActivity implements LaunchContract.View {

    @Inject
    LaunchContract.Presenter presenter;

    @Override
    protected void injectDependencies() {
        applicationComponent.inject(this);
        presenter.onBindView(this);

    }

    @Override
    protected void initViews() {
        presenter.onViewInitialized();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showHomeScreen() {
        startActivity(HomeActivity.newIntent(this));
        finish();

    }
}
