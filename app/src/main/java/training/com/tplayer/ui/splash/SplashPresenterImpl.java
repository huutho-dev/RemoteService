package training.com.tplayer.ui.splash;

import training.com.tplayer.base.mvp.BasePresenterImpl;

/**
 * Created by HuuTho on 4/8/2017.
 */

public class SplashPresenterImpl extends BasePresenterImpl<SplashActivity,SplashInteractorImpl>
        implements SplashContracts.Presenter {


    @Override
    public void onSubcireView(SplashActivity view) {
        this.mView = view;
    }

    @Override
    public void onSubcireInteractor(SplashInteractorImpl interactor) {
        this.mInteractor = interactor;
    }

    @Override
    public void loading() {
        mInteractor.delayToStart(new SplashInteractorImpl.IDelayLoadingComplete() {
            @Override
            public void delayComplete() {
                mView.delayComplete();
            }
        });
    }


}
