package training.com.tplayer.ui.online.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.remote.communication.MediaEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.network.html.GetDataCodeTask;
import training.com.tplayer.network.html.ListAlbumTask;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.network.service.LoadListDataCodeService;
import training.com.tplayer.ui.adapter.online.ListAlbumAdapter;
import training.com.tplayer.ui.entity.AlbumBasicEntity;
import training.com.tplayer.ui.player.PlayerActivity;

/**
 * Created by ThoNH on 4/17/2017.
 */

public class ListAlbumFragment extends BaseFragment
        implements IOnLoadSuccess, ListAlbumAdapter.ListAlbumAdapterListener {


    public static final String BUNDLE_ALBUM_TYPE = "bundle.album.type";

    @BindView(R.id.fragment_list_album_rv)
    RecyclerView mRvListAlbum;

    ListAlbumAdapter mAdapter;
    ArrayList<AlbumBasicEntity> mListAlbum;

    int pageIndex = 0;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private String urlType;

    public static ListAlbumFragment newInstance(String urlType) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_ALBUM_TYPE, urlType);
        ListAlbumFragment fragment = new ListAlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_list_album;
    }

    @Override
    public void getDataBundle(@Nullable Bundle savedInstanceState) {
        super.getDataBundle(savedInstanceState);
        urlType = getArguments().getString(BUNDLE_ALBUM_TYPE);
        loadNextPage();
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        mListAlbum = new ArrayList<>();
        final GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        mAdapter = new ListAlbumAdapter(mContext, this);
        mRvListAlbum.setLayoutManager(mLayoutManager);
        mRvListAlbum.setAdapter(mAdapter);
        mAdapter.setDatas(mListAlbum);

        mRvListAlbum.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mRvListAlbum.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    loadNextPage();
                    Log.i("Yaeye!", "end called");
                    loading = true;
                }
            }
        });

    }


    @Override
    public void onResponse(List entity, String TAG) {
        mAdapter.addAll((ArrayList<AlbumBasicEntity>) entity);
        mAdapter.notifyDataSetChanged();
    }


    private void loadNextPage() {
        pageIndex++;
        String urlPage = urlType + "?view=list&sort=total_play&filter=day&page=" + pageIndex;
        new ListAlbumTask(urlPage, this, "ListAlbumTask").execute();
    }

    @Override
    public void onRecyclerViewItemClick(View view, BaseEntity baseEntity, int position) {
        if (baseEntity instanceof AlbumBasicEntity) {
            AlbumBasicEntity albumBasicEntity = (AlbumBasicEntity) baseEntity;
            GetDataCodeTask getDataCodeTask = new GetDataCodeTask(albumBasicEntity.link, new IOnLoadSuccess() {
                @Override
                public void onResponse(List entities, String TAG) {
                    Intent intent = new Intent(mContext, PlayerActivity.class);
                    intent.putParcelableArrayListExtra(PlayerActivity.EXTRA_DATA_PLAYER, (ArrayList<? extends Parcelable>) entities);
                    if (isAdded() && getActivity() != null)
                    startActivity(intent);
                }
            }, "getDataCodeTask");

            getDataCodeTask.execute();
        }
    }


    private void startActivity(List<MediaEntity> songs){
        Intent intent = new Intent(mContext, PlayerActivity.class);
        intent.putParcelableArrayListExtra(PlayerActivity.EXTRA_DATA_PLAYER, (ArrayList<? extends Parcelable>) songs);
        startActivity(intent);
    }

    private void startLoadSongZingMp3(List entity){
        Intent intent = new Intent(mContext, LoadListDataCodeService.class);
        intent.putParcelableArrayListExtra(LoadListDataCodeService.EXTRA_DATA_CODE,
                (ArrayList<? extends Parcelable>) entity);
        mContext.startService(intent);
    }
    private void registerLoadSongComplete(){
        IntentFilter filterZing = new IntentFilter();
        filterZing.addAction(LoadListDataCodeService.ACTION_CALL_BACK_SONG);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<MediaEntity> songs =
                        intent.getParcelableArrayListExtra(LoadListDataCodeService.EXTRA_CALL_BACK);
                startActivity(songs);

            }
        }, filterZing);
    }

}
