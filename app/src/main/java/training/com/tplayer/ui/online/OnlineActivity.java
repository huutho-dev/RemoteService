package training.com.tplayer.ui.online;

import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class OnlineActivity extends BaseActivity<OnlinePresenterImpl> {

    @BindView(R.id.act_onl_tablayout)
    SmartTabLayout mTabLayout ;

    @BindView(R.id.act_onl_viewpager)
    ViewPager mViewPager;


    @Override
    public int setLayoutId() {
        return R.layout.activity_online;
    }

    @Override
    public void onBindView() {

    }

    @Override
    public void onActivityCreated() {

    }

    @Override
    protected void createPresenterImpl() {

    }
}
