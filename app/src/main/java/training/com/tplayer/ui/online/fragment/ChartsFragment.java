package training.com.tplayer.ui.online.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.network.html.ChartsSongTask;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.ui.adapter.online.ChartsAdapter;
import training.com.tplayer.ui.entity.BasicSongOnlEntity;
import training.com.tplayer.ui.player.PlayerActivity;

/**
 * Created by hnc on 13/04/2017.
 */

public class ChartsFragment extends BaseFragment implements IOnLoadSuccess {

    @BindView(R.id.fragment_onl_charts_vietnam)
    RecyclerView mRvChartsVn;

    @BindView(R.id.fragment_onl_charts_national)
    RecyclerView mRvChartsNational;

    @BindView(R.id.textViewRobotoVietnam)
    TextViewRoboto mTxtTitleVietnam;

    @BindView(R.id.textViewRobotoNational)
    TextViewRoboto mTxtTitleNational;


    private ChartsAdapter mChartsVnAdapter;
    private ChartsAdapter mChartsNationalAdapter;

    public static ChartsFragment newInstance() {
        Bundle args = new Bundle();
        ChartsFragment fragment = new ChartsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_online_charts;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable final Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        new ChartsSongTask(this).execute();

        mTxtTitleVietnam.setText(R.string.fragment_title_charts_vietnam);
        mTxtTitleNational.setText(R.string.fragment_title_charts_usuk);
        mTxtTitleVietnam.setVisibility(View.GONE);
        mTxtTitleNational.setVisibility(View.GONE);


        mChartsVnAdapter = new ChartsAdapter(mContext, chartsVietnamListener);
        mRvChartsVn.setLayoutManager(new LinearLayoutManager(mContext));
        mRvChartsVn.setAdapter(mChartsVnAdapter);
        mRvChartsVn.setNestedScrollingEnabled(false);

        mChartsNationalAdapter = new ChartsAdapter(mContext, chartsNationalListener);
        mRvChartsNational.setLayoutManager(new LinearLayoutManager(mContext));
        mRvChartsNational.setAdapter(mChartsNationalAdapter);
        mRvChartsNational.setNestedScrollingEnabled(false);

    }


    ChartsAdapter.ChartsVietnamAdapterListener chartsVietnamListener = new ChartsAdapter.ChartsVietnamAdapterListener() {
        @Override
        public void onRecyclerViewItemClick(View view, BasicSongOnlEntity basicSongOnlEntity, int position) {
            mContext.startActivity(new Intent(mContext, PlayerActivity.class));
        }
    };

    ChartsAdapter.ChartsVietnamAdapterListener chartsNationalListener = new ChartsAdapter.ChartsVietnamAdapterListener() {
        @Override
        public void onRecyclerViewItemClick(View view, BasicSongOnlEntity basicSongOnlEntity, int position) {

        }
    };


    @Override
    public void onResponse(List entity, String TAG) {
        // size = 20;
        // viet nam : 0 - 9
        // national : 10-19

        int size = entity.size();
        if (size != 0) {
            mChartsVnAdapter.setDatas(entity, 0, size / 2);
            mChartsNationalAdapter.setDatas(entity, size / 2, size);

            mTxtTitleVietnam.setVisibility(View.VISIBLE);
            mTxtTitleNational.setVisibility(View.VISIBLE);
        }
    }
}
