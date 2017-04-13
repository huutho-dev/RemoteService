package training.com.tplayer.ui.online.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.custom.GravitySnapHelper;
import training.com.tplayer.network.html.HotAlbumTask;
import training.com.tplayer.network.html.HotHightLightTask;
import training.com.tplayer.network.html.HotNewTask;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.ui.adapter.online.HotAlbumAdapter;
import training.com.tplayer.ui.adapter.online.HotHightLightAdapter;
import training.com.tplayer.ui.adapter.online.HotNewAdapter;
import training.com.tplayer.ui.entity.DummyEntity;
import training.com.tplayer.ui.entity.HotAlbumEntity;
import training.com.tplayer.ui.entity.HotNewEntity;
import training.com.tplayer.ui.entity.HotSongOnlEntity;

/**
 * Created by hnc on 13/04/2017.
 */

public class HotFragment extends BaseFragment {

    @BindView(R.id.fragment_online_hot_rv_album)
    RecyclerView mRvAlbum;

    @BindView(R.id.fragment_online_hot_rv_new)
    RecyclerView mRvNew;

    @BindView(R.id.fragment_online_hot_rv_hightlight)
    RecyclerView mRvHightLight;

    private HotAlbumAdapter mAlbumAdapter;
    private HotNewAdapter mNewAdapter;
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

        GravitySnapHelper helperAlbum = new GravitySnapHelper(Gravity.START);
        mAlbumAdapter = new HotAlbumAdapter(mContext, albumListener);
        mRvAlbum.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRvAlbum.setNestedScrollingEnabled(false);
        mRvAlbum.setAdapter(mAlbumAdapter);
        helperAlbum.attachToRecyclerView(mRvAlbum);

        GravitySnapHelper helperTopic = new GravitySnapHelper(Gravity.START);
        mNewAdapter = new HotNewAdapter(mContext, topicListener);
        mRvNew.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRvNew.setNestedScrollingEnabled(false);
        mRvNew.setAdapter(mNewAdapter);
        helperTopic.attachToRecyclerView(mRvNew);

        mHightLightAdapter = new HotHightLightAdapter(mContext, hightLightListener);
        mRvHightLight.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRvHightLight.setNestedScrollingEnabled(false);
        mRvHightLight.setAdapter(mHightLightAdapter);


        HotAlbumTask hotAlbumTask = new HotAlbumTask(new IOnLoadSuccess() {
            @Override
            public void onResponse(List entity) {
               mAlbumAdapter.setDatas((ArrayList<HotAlbumEntity>) entity);
            }
        });



        HotNewTask hotPlayListTask = new HotNewTask(new IOnLoadSuccess() {
            @Override
            public void onResponse(List entity) {
                mNewAdapter.setDatas((ArrayList<HotNewEntity>) entity);
            }
        });

        HotHightLightTask hotHightLightTask = new HotHightLightTask(new IOnLoadSuccess() {
            @Override
            public void onResponse(List entity) {
                mHightLightAdapter.setDatas((ArrayList<HotSongOnlEntity>) entity);
            }
        });
        hotHightLightTask.execute();
        hotAlbumTask.execute();
        hotPlayListTask.execute();


    }


    private HotAlbumAdapter.hotAlbumAdapterListener albumListener = new HotAlbumAdapter.hotAlbumAdapterListener() {
        @Override
        public void onRecyclerViewItemClick(View view, BaseEntity baseEntity, int position) {

        }
    };


    private HotNewAdapter.HotTopicAdapterListener topicListener = new HotNewAdapter.HotTopicAdapterListener() {
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
