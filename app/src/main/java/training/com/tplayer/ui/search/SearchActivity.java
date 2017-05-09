package training.com.tplayer.ui.search;

import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;

/**
 * Created by ThoNH on 5/9/2017.
 */

public class SearchActivity extends BaseActivity<SearchPresenterImpl> {
    @Override
    public int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void onBindView() {

    }

    @Override
    public void onActivityCreated() {

    }

    @Override
    protected void createPresenterImpl() {
        mPresenter = new SearchPresenterImpl();
        mPresenter.onSubcireView(this);
        mPresenter.onSubcireInteractor(new SearchPresenterIntoractor(this));
    }
}
