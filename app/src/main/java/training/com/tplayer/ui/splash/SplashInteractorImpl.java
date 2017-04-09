package training.com.tplayer.ui.splash;

import android.content.Context;
import android.os.Handler;

import training.com.tplayer.base.mvp.BaseInteractorImpl;

/**
 * Created by HuuTho on 4/8/2017.
 */

public class SplashInteractorImpl extends BaseInteractorImpl implements SplashContracts.Interactor {

    public SplashInteractorImpl(Context context) {
        super(context);
    }

    @Override
    public void delayToStart(final IDelayLoadingComplete callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.delayComplete();
            }
        }, 1000);
    }


    interface IDelayLoadingComplete {
        void delayComplete();
    }
}
