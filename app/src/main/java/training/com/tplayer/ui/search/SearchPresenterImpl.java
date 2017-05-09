package training.com.tplayer.ui.search;

import training.com.tplayer.base.mvp.BasePresenterImpl;

/**
 * Created by ThoNH on 5/9/2017.
 */

public class SearchPresenterImpl extends BasePresenterImpl<SearchActivity,SearchPresenterIntoractor> {
    @Override
    public void onSubcireView(SearchActivity view) {
        this.mView = view;
    }

    @Override
    public void onSubcireInteractor(SearchPresenterIntoractor interactor) {
        this.mInteractor = interactor;
    }
}
