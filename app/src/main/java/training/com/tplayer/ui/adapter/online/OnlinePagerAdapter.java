package training.com.tplayer.ui.adapter.online;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.R;
import training.com.tplayer.ui.online.fragment.AlbumFragment;
import training.com.tplayer.ui.online.fragment.ChartsFragment;
import training.com.tplayer.ui.online.fragment.HotFragment;
import training.com.tplayer.ui.online.fragment.TopFragment;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class OnlinePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public OnlinePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        fragments.add(TopFragment.newInstance());
        fragments.add(HotFragment.newInstance());
        fragments.add(ChartsFragment.newInstance());
        fragments.add(AlbumFragment.newInstance());

        titles.add(context.getResources().getString(R.string.tab_title_top));
        titles.add(context.getResources().getString(R.string.tab_title_hot));
        titles.add(context.getResources().getString(R.string.tab_title_charts));
        titles.add(context.getResources().getString(R.string.tab_title_album));
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
