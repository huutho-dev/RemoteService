package training.com.tplayer.ui.online.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.ui.adapter.online.ChartsPagerAdapter;

/**
 * Created by ThoNH on 13/04/2017.
 */

public class ChartsOnlineFragment extends BaseFragment {

    @BindView(R.id.fragment_charts_viewpager)
    ViewPager mViewpager;

    @BindView(R.id.fragment_charts_tab)
    SmartTabLayout mTabLayout;


    public static ChartsOnlineFragment newInstance() {
        Bundle args = new Bundle();
        ChartsOnlineFragment fragment = new ChartsOnlineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_online_charts;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable final Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());

        ChartsPagerAdapter mAdapter = new ChartsPagerAdapter(mContext, getChildFragmentManager());
        mViewpager.setAdapter(mAdapter);
        mTabLayout.setViewPager(mViewpager);
    }

}
