package training.com.tplayer.ui.online.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.network.html.base.ListAlbumTask;
import training.com.tplayer.ui.adapter.online.HotAlbumAdapter;
import training.com.tplayer.ui.entity.AlbumBasicEntity;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 4/17/2017.
 */

public class ListAlbumFragment extends BaseFragment
        implements HotAlbumAdapter.HotAlbumAdapterListener, IOnLoadSuccess {


    public static final String BUNDLE_ALBUM_TYPE = "bundle.album.type";

    @BindView(R.id.fragment_list_album_rv)
    RecyclerView mRvListAlbum;

    HotAlbumAdapter mAdapter;
    ArrayList<AlbumBasicEntity> mListAlbum;

    int pageIndex = 1;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private String urlType ;

    public static ListAlbumFragment newInstance(String urlType) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_ALBUM_TYPE,urlType);
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
        final GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 3);
        mAdapter = new HotAlbumAdapter(mContext, this);
        mRvListAlbum.setLayoutManager(mLayoutManager);
        mRvListAlbum.setAdapter(mAdapter);
        mAdapter.setDatas(mListAlbum);
        LogUtils.printLog("onViewCreatedFragment");
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
                if (!loading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + visibleThreshold)) {
                    loadNextPage();
                    Log.i("Yaeye!", "end called");
                    loading = true;
                }
            }
        });

    }

    @Override
    public void onRecyclerViewItemClick(View view, BaseEntity baseEntity, int position) {

    }


    @Override
    public void onResponse(List entity, String TAG) {
        mListAlbum.addAll(entity);
        mAdapter.notifyDataSetChanged();
    }


    private void loadNextPage() {
        pageIndex++;
        String urlPage = urlType + "?view=list&sort=total_play&filter=day&page=" + pageIndex;
        new ListAlbumTask(urlPage, this, "ListAlbumTask");
    }
}
