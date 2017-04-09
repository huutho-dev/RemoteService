package training.com.tplayer.ui.offline;

import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;

/**
 * Created by HuuTho on 4/9/2017.
 */

public class OfflineActivity extends BaseActivity<OfflinePresenterImpl>
        implements OfflineContracts.View {
    @Override
    public int setLayoutId() {
        return R.layout.activity_offline;
    }

    @Override
    public void onBindView() {

    }

    @Override
    public void onActivityCreated() {

    }

    @Override
    protected void createPresenterImpl() {
        mPresenter = new OfflinePresenterImpl();
        mPresenter.onSubcireView(this);
        mPresenter.onSubcireInteractor(new OfflineInteractorImpl(this));
    }
}
