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
 * Created by ThoNH on 13/04/2017.
 */

public class TopFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.textViewRobotoVietnam)
    TextViewRoboto txtTitleVietnam;
    @BindView(R.id.textViewRobotoNational)
    TextViewRoboto txtTitleNational;

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

        ButterKnife.bind(this,getView());
        txtTitleNational.setText(R.string.fragment_title_top_music_national);
        txtTitleVietnam.setText(R.string.fragment_title_top_music_vietnam);

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
                //http://mp3.zing.vn/top100/Nhac-Tre/IWZ9Z088.html

                break;
            case R.id.vietnam_bolero:
                //http://mp3.zing.vn/top100/Nhac-Tru-Tinh/IWZ9Z08B.html

                break;
            case R.id.vietnam_dance:
                //http://mp3.zing.vn/top100/Nhac-Dance/IWZ9Z0CW.html

                break;
            case R.id.vietnam_rap:
                //http://mp3.zing.vn/top100/Rap-Viet/IWZ9Z089.html

                break;
            case R.id.vietnam_rock:
                //http://mp3.zing.vn/top100/Rock-Viet/IWZ9Z08A.html

                break;
            case R.id.vietnam_trinh:
                //http://mp3.zing.vn/top100/Nhac-Trinh/IWZ9Z08E.html

                break;

            case R.id.nation_japan:
                //http://mp3.zing.vn/top100/Nhat-Ban/IWZ9Z08Z.html

                break;
            case R.id.nation_rap:
                //http://mp3.zing.vn/top100/Rap-Hip-Hop/IWZ9Z09B.html

                break;
            case R.id.national_edm:
                //http://mp3.zing.vn/top100/Electronic-Dance/IWZ9Z09A.html

                break;
            case R.id.national_kpop:
                //http://mp3.zing.vn/top100/Han-Quoc/IWZ9Z08W.html

                break;
            case R.id.national_rock:
                //http://mp3.zing.vn/top100/Rock/IWZ9Z099.html

                break;
            case R.id.national_pop:
                //http://mp3.zing.vn/top100/Pop/IWZ9Z097.html

                break;
        }
    }
}
