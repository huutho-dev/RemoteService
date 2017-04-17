package training.com.tplayer.ui.online.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.network.html.ChartsItemSongTask;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.ui.adapter.online.ChartsItemAdapter;
import training.com.tplayer.ui.entity.DataCodeEntity;
import training.com.tplayer.ui.entity.HotSongOnlEntity;
import training.com.tplayer.ui.player.PlayerActivity;

/**
 * Created by ThoNH on 4/17/2017.
 */

public class ChartsItemFragment extends BaseFragment
        implements IOnLoadSuccess, ChartsItemAdapter.ChartsItemAdapterListener, View.OnClickListener {
    public static final String BUNDLE_TAG_CHARTS = "bundle.tag.charts";
    public static final String TAG_VN = "TAG_VN";
    public static final String TAG_NATIONAL = "TAG_NATIONAL";
    public static final String TAG_ELSE = "TAG_ELSE";

    private String tag;

    @BindView(R.id.fragment_charts_item_rv)
    RecyclerView mRvChartsItem;

    @BindView(R.id.loading)
    ProgressBar mLoading;

    @BindView(R.id.fragment_charts_item_layout_play_all)
    LinearLayout mLayoutPlayAll;

    private ChartsItemAdapter mAdapter;

    public static ChartsItemFragment newInstance(String tag) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_TAG_CHARTS, tag);
        ChartsItemFragment fragment = new ChartsItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_charts_item_pager;
    }

    @Override
    public void getDataBundle(@Nullable Bundle savedInstanceState) {
        super.getDataBundle(savedInstanceState);
        tag = getArguments().getString(BUNDLE_TAG_CHARTS);
        if (TAG_VN.equals(tag)) {
            new ChartsItemSongTask("http://mp3.zing.vn/bang-xep-hang/bai-hat-Viet-Nam/IWZ9Z08I.html", this).execute();
        } else if (TAG_NATIONAL.equals(tag)) {
            new ChartsItemSongTask("http://mp3.zing.vn/bang-xep-hang/bai-hat-Au-My/IWZ9Z0BW.html", this).execute();
        } else {
            // top fragment add this
            new ChartsItemSongTask(tag,this).execute();
        }
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());

        mLayoutPlayAll.setOnClickListener(this);

        mAdapter = new ChartsItemAdapter(mContext, this);
        mRvChartsItem.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRvChartsItem.setNestedScrollingEnabled(false);
        mRvChartsItem.setAdapter(mAdapter);
    }

    @Override
    public void onResponse(List entity, String TAG) {
        mAdapter.setDatas((ArrayList<HotSongOnlEntity>) entity);
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onRecyclerViewItemClick(View view, BaseEntity baseEntity, int position) {
        List<DataCodeEntity> entitiesHightLight = new ArrayList<>();
        DataCodeEntity dataCodeEntity = new DataCodeEntity(mAdapter.getDataItem(position).data_code);
        entitiesHightLight.add(dataCodeEntity);
        startActivityHasData(entitiesHightLight);
    }

    @Override
    public void onClick(View v) {
        List<DataCodeEntity> entitiesHightLight = new ArrayList<>();
        for (int i = 0; i < mAdapter.getSize(); i++) {
            DataCodeEntity dataCodeEntity = new DataCodeEntity(mAdapter.getDataItem(i).data_code);
            entitiesHightLight.add(dataCodeEntity);
        }
        startActivityHasData(entitiesHightLight);
    }

    private void startActivityHasData(List entity) {
        Intent intent = new Intent(mContext, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PlayerActivity.BUNDLE_DATA_ONLINE, (ArrayList<? extends Parcelable>) entity);
        intent.putExtra(PlayerActivity.EXTRA_DATA_PLAYER, bundle);
        startActivity(intent);
    }
}
