package training.com.tplayer.ui.setting;

import android.support.v7.app.AppCompatActivity;

import training.com.tplayer.base.mvp.BaseInteractor;
import training.com.tplayer.base.mvp.BasePresenter;
import training.com.tplayer.base.mvp.BaseView;

/**
 * Created by hnc on 16/05/2017.
 */

public class SettingContract {
    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter {
        void settingEqualizer(AppCompatActivity activity);

        void settingDownloadOpt(boolean setting);

        void settingShake(boolean setting);

        void settingPauseWhenDisableHeadset(boolean setting);

        void settingContinueWhenEnableHeadset(boolean setting);

        void settingPauseOtherSoundPlay(boolean setting);
    }

    public interface  Interator extends BaseInteractor {

    }
}
