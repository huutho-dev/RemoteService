package training.com.tplayer.ui.online.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.ui.adapter.online.HotAlbumAdapter;
import training.com.tplayer.ui.adapter.online.HotHightLightAdapter;
import training.com.tplayer.ui.adapter.online.HotTopicAdapter;
import training.com.tplayer.ui.entity.DummyEntity;

/**
 * Created by hnc on 13/04/2017.
 */

public class HotFragment extends BaseFragment {

    @BindView(R.id.fragment_online_hot_rv_album)
    RecyclerView mRvAlbum;

    @BindView(R.id.fragment_online_hot_rv_topic)
    RecyclerView mRvTopic;

    @BindView(R.id.fragment_online_hot_rv_hightlight)
    RecyclerView mRvHightLight;

    private HotAlbumAdapter mAlbumAdapter;
    private HotTopicAdapter mTopicAdapter;
    private HotHightLightAdapter mHightLightAdapter;

    private ArrayList<DummyEntity> dummyEntities;

    public static HotFragment newInstance() {
        Bundle args = new Bundle();
        HotFragment fragment = new HotFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_online_hot;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());

        dummyEntities = new ArrayList<>();
        dummyEntities.add(new DummyEntity("Title", R.drawable.dummy_image, "Desc"));
        dummyEntities.add(new DummyEntity("Title", R.drawable.dummy_image, "Desc"));
        dummyEntities.add(new DummyEntity("Title", R.drawable.dummy_image, "Desc"));
        dummyEntities.add(new DummyEntity("Title", R.drawable.dummy_image, "Desc"));
        dummyEntities.add(new DummyEntity("Title", R.drawable.dummy_image, "Desc"));
        dummyEntities.add(new DummyEntity("Title", R.drawable.dummy_image, "Desc"));
        dummyEntities.add(new DummyEntity("Title", R.drawable.dummy_image, "Desc"));
        dummyEntities.add(new DummyEntity("Title", R.drawable.dummy_image, "Desc"));
        dummyEntities.add(new DummyEntity("Title", R.drawable.dummy_image, "Desc"));
        dummyEntities.add(new DummyEntity("Title", R.drawable.dummy_image, "Desc"));

        mAlbumAdapter = new HotAlbumAdapter(mContext, albumListener);
        mAlbumAdapter.setDatas(dummyEntities);
        mRvAlbum.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRvAlbum.setNestedScrollingEnabled(false);
        mRvAlbum.setAdapter(mAlbumAdapter);


        mTopicAdapter = new HotTopicAdapter(mContext, topicListener);
        mTopicAdapter.setDatas(dummyEntities);
        mRvTopic.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRvTopic.setNestedScrollingEnabled(false);
        mRvTopic.setAdapter(mTopicAdapter);


        mHightLightAdapter = new HotHightLightAdapter(mContext, hightLightListener);
        mHightLightAdapter.setDatas(dummyEntities);
        mRvHightLight.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRvHightLight.setNestedScrollingEnabled(false);
        mRvHightLight.setAdapter(mHightLightAdapter);
    }


    private HotAlbumAdapter.hotAlbumAdapterListener albumListener = new HotAlbumAdapter.hotAlbumAdapterListener() {
        @Override
        public void onRecyclerViewItemClick(View view, BaseEntity baseEntity, int position) {

        }
    };


    private HotTopicAdapter.HotTopicAdapterListener topicListener = new HotTopicAdapter.HotTopicAdapterListener() {
        @Override
        public void onRecyclerViewItemClick(View view, BaseEntity baseEntity, int position) {

        }
    };


    private HotHightLightAdapter.HotHightLightAdapterListener hightLightListener = new HotHightLightAdapter.HotHightLightAdapterListener() {
        @Override
        public void onRecyclerViewItemClick(View view, BaseEntity baseEntity, int position) {

        }
    };

}
