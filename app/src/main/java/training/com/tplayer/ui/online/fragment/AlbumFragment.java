package training.com.tplayer.ui.online.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.custom.TextViewRoboto;

/**
 * Created by hnc on 13/04/2017.
 */

public class AlbumFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.textViewRobotoVietnam)
    TextViewRoboto txtTitleVietnam;
    @BindView(R.id.textViewRobotoNational)
    TextViewRoboto txtTitleNational;

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

        ButterKnife.bind(this,getView());

        txtTitleNational.setText(R.string.fragment_title_album_national);
        txtTitleVietnam.setText(R.string.fragment_title_album_vietnam);

        view.findViewById(R.id.vietnam_bolero).setOnClickListener(this);
        view.findViewById(R.id.vietnam_dance).setOnClickListener(this);
        view.findViewById(R.id.vietnam_rap).setOnClickListener(this);
        view.findViewById(R.id.vietnam_rock).setOnClickListener(this);
        view.findViewById(R.id.vietnam_trinh).setOnClickListener(this);
        view.findViewById(R.id.vietnam_young).setOnClickListener(this);

        view.findViewById(R.id.nation_japan).setOnClickListener(this);
        view.findViewById(R.id.nation_rap).setOnClickListener(this);
        view.findViewById(R.id.national_edm).setOnClickListener(this);
        view.findViewById(R.id.national_kpop).setOnClickListener(this);
        view.findViewById(R.id.national_rock).setOnClickListener(this);
        view.findViewById(R.id.national_pop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vietnam_young:
                break;
            case R.id.vietnam_bolero:
                break;
            case R.id.vietnam_dance:
                break;
            case R.id.vietnam_rap:

                break;
            case R.id.vietnam_rock:

                break;
            case R.id.vietnam_trinh:
                break;

            case R.id.nation_japan:
                break;
            case R.id.nation_rap:
                break;
            case R.id.national_edm:
                break;
            case R.id.national_kpop:
                break;
            case R.id.national_rock:
                break;
            case R.id.national_pop:
                break;
        }
    }
}
