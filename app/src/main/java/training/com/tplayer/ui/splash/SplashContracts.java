package training.com.tplayer.ui.splash;

import training.com.tplayer.base.mvp.BaseInteractor;
import training.com.tplayer.base.mvp.BasePresenter;
import training.com.tplayer.base.mvp.BaseView;

/**
 * Created by HuuTho on 4/8/2017.
 */

public class SplashContracts {
    public interface View  extends BaseView{
        void delayComplete();
    }

    public interface Presenter extends BasePresenter {
        void loading();
    }

    public interface Interactor extends BaseInteractor {
        void delayToStart(SplashInteractorImpl.IDelayLoadingComplete callback);
    }
}
