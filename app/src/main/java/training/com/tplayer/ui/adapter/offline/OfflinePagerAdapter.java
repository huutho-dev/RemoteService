package training.com.tplayer.ui.adapter.offline;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.R;
import training.com.tplayer.ui.offline.fragment.AlbumsFragment;
import training.com.tplayer.ui.offline.fragment.ArtistsFragment;
import training.com.tplayer.ui.offline.fragment.DownloadFragment;
import training.com.tplayer.ui.offline.fragment.FolderFragment;
import training.com.tplayer.ui.offline.fragment.PlaylistsFragment;
import training.com.tplayer.ui.offline.fragment.SongsFragment;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class OfflinePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public OfflinePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        fragments.add(SongsFragment.newInstance());
        fragments.add(AlbumsFragment.newInstance());
        fragments.add(ArtistsFragment.newInstance());
        fragments.add(PlaylistsFragment.newInstance());
        fragments.add(FolderFragment.newInstance());
        fragments.add(DownloadFragment.newInstance());

        titles.add(context.getResources().getString(R.string.fragment_offline_title_songs));
        titles.add(context.getResources().getString(R.string.fragment_offline_title_albums));
        titles.add(context.getResources().getString(R.string.fragment_offline_title_artist));
        titles.add(context.getResources().getString(R.string.fragment_offline_title_playlists));
        titles.add(context.getResources().getString(R.string.fragment_offline_title_folder));
        titles.add(context.getResources().getString(R.string.fragment_offline_title_download));
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
