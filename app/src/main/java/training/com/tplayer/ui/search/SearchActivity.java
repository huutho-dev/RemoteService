package training.com.tplayer.ui.search;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.ui.search.fragment.SearchFragment;

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
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.root,SearchFragment.newInstance());
        ft.commit();
    }

    @Override
    protected void createPresenterImpl() {
        mPresenter = new SearchPresenterImpl();
        mPresenter.onSubcireView(this);
        mPresenter.onSubcireInteractor(new SearchPresenterIntoractor(this));
    }
}
