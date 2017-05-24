package training.com.tplayer.ui.offline.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;

import com.remote.communication.MediaEntity;

import java.util.List;

import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.database.SourceTableMedia;
import training.com.tplayer.utils.FileUtils;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class DownloadFragment extends BaseFragment {

    public static DownloadFragment newInstance() {

        Bundle args = new Bundle();
        DownloadFragment fragment = new DownloadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_offline_download;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        List<MediaEntity> entityList = SourceTableMedia.getInstance(mContext)
                .getFileInFolder(FileUtils.PATH_DOWNLOAD);
        ft.replace(R.id.root, SongsFragment.newInstance(SongsFragment.BUNDLE_FROM_DOWNLOAD, entityList));
        ft.addToBackStack(null);
        ft.commit();

    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_first_now_playing:
                break;
            case R.id.action_add_to_now_plays:
                break;
            case R.id.action_add_playlist:
                break;
            case R.id.action_set_is_rington:
                break;
            case R.id.action_delete_in_download:
                break;
            case R.id.action_delete:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
