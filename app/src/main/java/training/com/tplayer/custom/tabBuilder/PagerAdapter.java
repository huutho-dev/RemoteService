package training.com.tplayer.custom.tabBuilder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThoNH on 24/03/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private boolean isOnlyIcon;

    public PagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments, boolean onlyIcon) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
        this.isOnlyIcon = onlyIcon;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        if (mFragments == null)
            return 0;
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (!isOnlyIcon) {
            return mTitles.get(position);
        }
        return null;
    }
}
