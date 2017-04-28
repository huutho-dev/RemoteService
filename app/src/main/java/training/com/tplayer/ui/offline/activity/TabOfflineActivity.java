package training.com.tplayer.ui.offline.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.ui.adapter.offline.OfflinePagerAdapter;

/**
 * Created by hnc on 28/04/2017.
 */

public class TabOfflineActivity extends BaseActivity {

    public static final String KEY_TAB = "key.tab";
    public static final int TAB_SONG = 0;
    public static final int TAB_ALBUM = 1;
    public static final int TAB_ARTIST = 2;
    public static final int TAB_PLAYLIST = 3;
    public static final int TAB_FOLDER = 4;
    public static final int TAB_DOWNLOAD = 5;

    @BindView(R.id.act_offline_tablayout)
    SmartTabLayout mTabLayout;

    @BindView(R.id.act_offline_viewpager)
    ViewPager mViewPager;

    private OfflinePagerAdapter mAdapter;

    private int mCurrentTab = TAB_SONG;

    @Override
    public int setLayoutId() {
        return R.layout.activity_tab_offline;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);
        mAdapter = new OfflinePagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void getDataBundle(Bundle savedInstanceState) {
        super.getDataBundle(savedInstanceState);
        if (getIntent() != null) {
            mCurrentTab = getIntent().getIntExtra(KEY_TAB,TAB_SONG);
            mViewPager.setCurrentItem(mCurrentTab);
        }

    }


    @Override
    public void onActivityCreated() {

    }

    @Override
    protected void createPresenterImpl() {

    }
}
