package training.com.tplayer.ui.online.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;

/**
 * Created by hnc on 13/04/2017.
 */

public class AlbumFragment extends BaseFragment {

    public static AlbumFragment newInstance() {
        Bundle args = new Bundle();
        AlbumFragment fragment = new AlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_online_album;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {

    }
}
