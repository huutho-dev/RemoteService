package training.com.tplayer.ui.adapter.online;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.R;
import training.com.tplayer.ui.online.fragment.AlbumOnlineFragment;
import training.com.tplayer.ui.online.fragment.ChartsOnlineFragment;
import training.com.tplayer.ui.online.fragment.HotOnlineFragment;
import training.com.tplayer.ui.online.fragment.TopOnlineFragment;
import training.com.tplayer.ui.search.fragment.SearchFragment;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class OnlinePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public OnlinePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        fragments.add(TopOnlineFragment.newInstance());
        fragments.add(HotOnlineFragment.newInstance());
        fragments.add(ChartsOnlineFragment.newInstance());
        fragments.add(AlbumOnlineFragment.newInstance());
        fragments.add(SearchFragment.newInstance(SearchFragment.TYPE_SEARCH_ONLINE));

        titles.add(context.getResources().getString(R.string.tab_title_top));
        titles.add(context.getResources().getString(R.string.tab_title_hot));
        titles.add(context.getResources().getString(R.string.tab_title_charts));
        titles.add(context.getResources().getString(R.string.tab_title_album));
        titles.add(context.getResources().getString(R.string.tab_title_search));
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
