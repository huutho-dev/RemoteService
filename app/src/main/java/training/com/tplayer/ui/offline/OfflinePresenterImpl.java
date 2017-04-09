package training.com.tplayer.ui.offline;

import training.com.tplayer.base.mvp.BasePresenterImpl;

/**
 * Created by HuuTho on 4/9/2017.
 */

public class OfflinePresenterImpl extends BasePresenterImpl<OfflineActivity,OfflineInteractorImpl> {
    @Override
    public void onSubcireView(OfflineActivity view) {
        this.mView = view;
    }

    @Override
    public void onSubcireInteractor(OfflineInteractorImpl interactor) {
        this.mInteractor = interactor;
    }
}
