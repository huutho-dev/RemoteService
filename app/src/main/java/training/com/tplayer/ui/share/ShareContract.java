package training.com.tplayer.ui.share;

import android.support.v7.app.AppCompatActivity;

import training.com.tplayer.base.mvp.BaseInteractor;
import training.com.tplayer.base.mvp.BasePresenter;
import training.com.tplayer.base.mvp.BaseView;

/**
 * Created by ThoNH on 5/14/2017.
 */

public class ShareContract {
    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter{
        void shareApp (AppCompatActivity appCompatActivity);

        void shareSocial (AppCompatActivity appCompatActivity);
    }

    public interface Interator extends BaseInteractor {

    }
}
