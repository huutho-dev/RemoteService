package training.com.tplayer.ui.offline.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.remote.communication.AlbumEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.database.SourceTableAlbum;
import training.com.tplayer.ui.adapter.offline.AlbumAdapter;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class AlbumsFragment extends BaseFragment implements AlbumAdapter.AlbumAdapterListener {

    @BindView(R.id.fragment_offline_rv_album)
    RecyclerView mRvAlbum;

    private AlbumAdapter mAdapter;

    public static AlbumsFragment newInstance() {
        Bundle args = new Bundle();
        AlbumsFragment fragment = new AlbumsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_offline_albums;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());

        mAdapter = new AlbumAdapter(mContext,this);
        mRvAlbum.setLayoutManager(new LinearLayoutManager(mContext));
        mRvAlbum.setAdapter(mAdapter);

        new AsyncTask<Void, Void, List<AlbumEntity>>() {
            @Override
            protected List<AlbumEntity> doInBackground(Void... params) {
                return SourceTableAlbum.getInstance(mContext).getList();
            }

            @Override
            protected void onPostExecute(List<AlbumEntity> albumEntities) {
                super.onPostExecute(albumEntities);
                mAdapter.setDatas(albumEntities);
            }
        }.execute();

    }

    @Override
    public void onRecyclerViewItemClick(View view, AlbumEntity albumEntity, int position) {

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_album_play:
                LogUtils.printLog("action_add_to_first_now_playing");
                break;
            case R.id.action_album_play_shuffle:
                LogUtils.printLog("action_add_to_now_plays");
                break;
            case R.id.action_album_add_now_playing:
                LogUtils.printLog("action_add_playlist");
                break;
            case R.id.action_album_add_playlist:
                LogUtils.printLog("action_set_is_rington");
                break;

        }
        return super.onContextItemSelected(item);
    }

}
