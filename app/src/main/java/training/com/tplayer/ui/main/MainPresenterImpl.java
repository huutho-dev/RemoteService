package training.com.tplayer.ui.main;

import training.com.tplayer.base.mvp.BasePresenterImpl;

/**
 * Created by HuuTho on 4/9/2017.
 */

public class MainPresenterImpl extends BasePresenterImpl<MainActivity, MainInteractorImpl> {

    @Override
    public void onSubcireView(MainActivity view) {

    }

    @Override
    public void onSubcireInteractor(MainInteractorImpl interactor) {
        this.mInteractor = interactor;
    }
}

