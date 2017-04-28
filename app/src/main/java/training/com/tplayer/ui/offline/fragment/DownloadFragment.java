package training.com.tplayer.ui.offline.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;

/**
 * Created by hnc on 28/04/2017.
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

    }
}
