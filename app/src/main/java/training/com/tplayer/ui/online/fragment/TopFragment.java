package training.com.tplayer.ui.online.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;

/**
 * Created by hnc on 13/04/2017.
 */

public class TopFragment extends BaseFragment {

    public static TopFragment newInstance() {
        Bundle args = new Bundle();
        TopFragment fragment = new TopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_online_top;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {

    }
}
