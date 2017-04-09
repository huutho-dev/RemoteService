package training.com.tplayer.custom.tabBuilder;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ThoNH on 24/03/2017.
 */

public class TabBuilder {
    private AppCompatActivity mConText;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    private List<Fragment> mFragments;
    private List<String> mTitles;
    private List<Drawable> mIcons;

    private boolean isOnlyIcon;

    public TabBuilder(AppCompatActivity activity, TabLayout tabLayout, ViewPager viewPager) {
        this.mConText = activity;
        this.mTabLayout = tabLayout;
        this.mViewPager = viewPager;

        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        mIcons = new ArrayList<>();
    }

    public TabBuilder(Fragment fragment, TabLayout tabLayout, ViewPager viewPager) {
        this.mConText = (AppCompatActivity) fragment.getActivity();
        this.mTabLayout = tabLayout;
        this.mViewPager = viewPager;

        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        mIcons = new ArrayList<>();
    }

    public TabBuilder setTabTitle(String... titles) {
        mTitles.clear();
        Collections.addAll(mTitles, titles);
        return this;
    }

    public TabBuilder setTabIcon(boolean onlyIcon, Integer... icons) {
        mIcons.clear();

        for (Integer icon : icons) {
            mIcons.add(mConText.getResources().getDrawable(icon));
        }

        this.isOnlyIcon = onlyIcon;
        return this;
    }

    public TabBuilder setTabIcon(boolean onlyIcon, Drawable... icons) {
        mIcons.clear();
        Collections.addAll(mIcons, icons);
        this.isOnlyIcon = onlyIcon;
        return this;
    }

    public TabBuilder setPagerFragment(Fragment... fragments) {
        mFragments.clear();
        Collections.addAll(mFragments, fragments);
        return this;
    }

    public TabBuilder build() {

        if (mTabLayout != null && mViewPager != null) {
            mAdapter = new PagerAdapter(mConText.getSupportFragmentManager(), mTitles, mFragments, isOnlyIcon);
            mTabLayout.setupWithViewPager(mViewPager);
            mViewPager.setAdapter(mAdapter);


            if (!mIcons.isEmpty())
                for (int i = 0; i < mFragments.size(); i++) {
                    String title = mTitles.get(i);
                    Drawable icon = mIcons.get(i);

                    TextView viewTab = new TextView(mConText);
                    viewTab.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    viewTab.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
                    viewTab.setCompoundDrawablePadding(8);
                    viewTab.setText(title);
                    viewTab.setTextColor(Color.WHITE);
                    viewTab.setAllCaps(false);

                    TabLayout.Tab tab = mTabLayout.getTabAt(i);
                    if (!isOnlyIcon) {
                        tab.setCustomView(viewTab);
                    } else {
                        tab.setIcon(icon);
                    }
                }
        }
        return this;
    }

    public TabBuilder setOnPagerListener(ViewPager.OnPageChangeListener pagerListener) {
        mViewPager.addOnPageChangeListener(pagerListener);
        return this;
    }

    public TabLayout getTabLayout(){
        return mTabLayout;
    }
    public ViewPager getViewPager(){
        return mViewPager;
    }

}
