package training.com.tplayer.ui.adapter.online;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.R;
import training.com.tplayer.ui.online.fragment.ChartsItemFragment;

/**
 * Created by ThoNH on 4/17/2017.
 */

public class ChartsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public ChartsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);

        fragments.add(ChartsItemFragment.newInstance(ChartsItemFragment.TAG_VN));
        fragments.add(ChartsItemFragment.newInstance(ChartsItemFragment.TAG_NATIONAL));

        titles.add(context.getResources().getString(R.string.fragment_title_charts_vietnam));
        titles.add(context.getResources().getString(R.string.fragment_title_charts_usuk));

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
