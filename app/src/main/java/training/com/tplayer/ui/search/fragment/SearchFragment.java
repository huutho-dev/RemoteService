package training.com.tplayer.ui.search.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.remote.communication.AlbumEntity;
import com.remote.communication.MediaEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.database.DataBaseUtils;
import training.com.tplayer.database.SourceTableAlbum;
import training.com.tplayer.database.SourceTableMedia;
import training.com.tplayer.network.retrofit.RetrofitApiRequest;
import training.com.tplayer.network.retrofit.RetrofitBuilderHelper;
import training.com.tplayer.ui.adapter.SearchAdapter;
import training.com.tplayer.ui.entity.ItemSearchEntity;
import training.com.tplayer.ui.entity.SearchOnlEntity;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by hnc on 15/05/2017.
 */

public class SearchFragment extends BaseFragment implements TextWatcher {

    public static final String KEY_SEARCH = "key.search";
    public static final String TYPE_SEARCH_ONLINE = "search.online";
    public static final String TYPE_SEARCH_OFFLINE = "search.offline";

    @BindView(R.id.fragment_search_rv_song)
    RecyclerView mRvSong;

    @BindView(R.id.fragment_search_rv_album)
    RecyclerView mRvAlbum;

    @BindView(R.id.fragment_search_edit)
    EditText mSearchPanel;

    private String mTypeSearch = TYPE_SEARCH_OFFLINE;

    private SearchAdapter mSearchSongAdapter;

    private SearchAdapter mSearchAlbumAdapter;

    private List<ItemSearchEntity> mDataSongs = new ArrayList<>();

    private List<ItemSearchEntity> mDataAlbums = new ArrayList<>();

    public static SearchFragment newInstance(String typeSearch) {
        Bundle args = new Bundle();
        args.putString(KEY_SEARCH, typeSearch);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void getDataBundle(@Nullable Bundle savedInstanceState) {
        super.getDataBundle(savedInstanceState);
        if (savedInstanceState == null && getArguments() != null) {
            mTypeSearch = getArguments().getString(KEY_SEARCH);
        }
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());

        mSearchPanel.addTextChangedListener(this);

        // Song Adapter
        mSearchSongAdapter = new SearchAdapter(mContext, mTypeSearch, new SearchAdapter.SearchAdapterListener() {
            @Override
            public void onRecyclerViewItemClick(View view, ItemSearchEntity itemSearch, int position) {

            }
        });
        mRvSong.setLayoutManager(new LinearLayoutManager(mContext));
        mRvSong.setNestedScrollingEnabled(false);
        mRvSong.setAdapter(mSearchSongAdapter);


        // Album Adapter
        mSearchAlbumAdapter = new SearchAdapter(mContext, mTypeSearch, new SearchAdapter.SearchAdapterListener() {
            @Override
            public void onRecyclerViewItemClick(View view, ItemSearchEntity itemSearch, int position) {

            }
        });
        mRvAlbum.setLayoutManager(new LinearLayoutManager(mContext));
        mRvSong.setNestedScrollingEnabled(false);
        mRvAlbum.setAdapter(mSearchAlbumAdapter);


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        LogUtils.printLog(start + " - " + before + " - " + s);
        if (mTypeSearch.equals(TYPE_SEARCH_OFFLINE)) {
            notifyDataSearchOffline(String.valueOf(s));
        } else if (mTypeSearch.equals(TYPE_SEARCH_ONLINE)){
            notifyDataSearchOnline(String.valueOf(s));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void notifyDataSearchOffline(String query) {
        mDataSongs.clear();
        mDataAlbums.clear();

        List<MediaEntity> entityList = SourceTableMedia.getInstance(mContext).getList(DataBaseUtils.DbStoreMediaColumn._TITLE + " LIKE '%" + query + "%' ", null);
        for (int i = 0; i < entityList.size(); i++) {
            ItemSearchEntity itemSearch = new ItemSearchEntity(entityList.get(i).title, entityList.get(i).art);
            mDataSongs.add(itemSearch);
        }
        mSearchSongAdapter.setDatas(mDataSongs);

        List<AlbumEntity> albumsList = SourceTableAlbum.getInstance(mContext).getList(DataBaseUtils.DbStoreAlbumColumn._ALBUM + " LIKE '%" + query + "%' ", null);
        for (int i = 0; i < albumsList.size(); i++) {
            ItemSearchEntity itemSearch = new ItemSearchEntity(albumsList.get(i).album, albumsList.get(i).albumArt);
            mDataAlbums.add(itemSearch);
        }
        mSearchAlbumAdapter.setDatas(mDataAlbums);

    }

    private void notifyDataSearchOnline(String query) {

        mDataSongs.clear();
        mDataAlbums.clear();

        Retrofit retrofit = RetrofitBuilderHelper.getRetrofitSearchBuilder();
        RetrofitApiRequest retrofitApiRequest = retrofit.create(RetrofitApiRequest.class);
        Call<SearchOnlEntity> call = retrofitApiRequest.getDataSearch(query);
        call.enqueue(new Callback<SearchOnlEntity>() {
            @Override
            public void onResponse(Call<SearchOnlEntity> call, Response<SearchOnlEntity> response) {
                LogUtils.printLog(response.body().toString());
                LogUtils.printLog(response.raw().request().url().toString());
                mSearchSongAdapter.setDatas(response.body().data.get(1).song);
                mSearchAlbumAdapter.setDatas(response.body().data.get(0).album);

            }

            @Override
            public void onFailure(Call<SearchOnlEntity> call, Throwable t) {

            }
        });
    }


}
