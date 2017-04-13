package training.com.tplayer.ui.online;

import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.ui.adapter.online.OnlinePagerAdapter;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class OnlineActivity extends BaseActivity<OnlinePresenterImpl> {

    @BindView(R.id.act_onl_tablayout)
    SmartTabLayout mTabLayout;

    @BindView(R.id.act_onl_viewpager)
    ViewPager mViewPager;

    private OnlinePagerAdapter mAdapter;


    @Override
    public int setLayoutId() {
        return R.layout.activity_online;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onActivityCreated() {
        mAdapter = new OnlinePagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setViewPager(mViewPager);

    }

    @Override
    protected void createPresenterImpl() {

    }
}
