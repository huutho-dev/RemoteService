package training.com.tplayer.ui.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import training.com.tplayer.base.mvp.BasePresenterImpl;
import training.com.tplayer.ui.audioeffect.SoundEffectActivity;
import training.com.tplayer.utils.preferences.SettingPreference;

/**
 * Created by hnc on 16/05/2017.
 */

public class SettingPresenterImpl extends BasePresenterImpl<SettingActivity,SettingInteratorImpl> implements SettingContract.Presenter {
    @Override
    public void onSubcireView(SettingActivity view) {
        this.mView = view;
    }

    @Override
    public void onSubcireInteractor(SettingInteratorImpl interactor) {
        this.mInteractor = interactor ;
    }

    @Override
    public void settingEqualizer(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, SoundEffectActivity.class));
    }

    @Override
    public void settingDownloadOpt(boolean setting) {
        SettingPreference.getInstance().setDownloadOnlyOverWifi(setting);
    }

    @Override
    public void settingShake(boolean setting) {
        SettingPreference.getInstance().setShake(setting);
    }

    @Override
    public void settingPauseWhenDisableHeadset(boolean setting) {
        SettingPreference.getInstance().setDisableHeadser(setting);
    }

    @Override
    public void settingContinueWhenEnableHeadset(boolean setting) {
        SettingPreference.getInstance().setEnableHeadset(setting);
    }

    @Override
    public void settingPauseOtherSoundPlay(boolean setting) {
        SettingPreference.getInstance().setOtherSoundComming(setting);
    }
}
