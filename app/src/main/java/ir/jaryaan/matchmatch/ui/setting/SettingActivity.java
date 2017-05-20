package ir.jaryaan.matchmatch.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.entities.Setting;
import ir.jaryaan.matchmatch.ui.base.BaseActivity;

/**
 * Created by ehsun on 5/18/2017.
 */

public class SettingActivity extends BaseActivity
        implements SettingContract.View {
    @Inject
    SettingContract.Presenter presenter;
    @BindView(R.id.card_type_radioGroup)
    RadioGroup cardTypeRadioGroup;

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setFinishOnTouchOutside(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }

    @Override
    public void showCurrentSettings(@NonNull Setting setting) {
        for (int i = 0; i < cardTypeRadioGroup.getChildCount(); i++) {
            RadioButton radioButton = ((RadioButton) cardTypeRadioGroup.getChildAt(i));
            if (radioButton.getText().equals(setting.getCardType())) {
                radioButton.setChecked(true);
            }
        }

    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    protected void injectDependencies() {
        applicationComponent.inject(this);
        presenter.onBindView(this);
    }

    @Override
    protected void initViews() {
        presenter.onViewInitialized();
    }

    @OnClick(R.id.save_settings_button)
    void onSaveClick() {
        RadioButton selectedRadioButton = ButterKnife.findById(this, cardTypeRadioGroup.getCheckedRadioButtonId());
        presenter.onSettingSaved(selectedRadioButton.getText().toString());
    }
}
