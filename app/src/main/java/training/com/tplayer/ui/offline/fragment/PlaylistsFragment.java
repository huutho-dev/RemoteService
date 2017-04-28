package training.com.tplayer.ui.offline.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.remote.communication.MediaEntity;
import com.remote.communication.PlaylistEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.ui.adapter.offline.PlaylistAdapter;

/**
 * Created by hnc on 28/04/2017.
 */

public class PlaylistsFragment extends BaseFragment implements PlaylistAdapter.PlaylistAdapterListener {

    @BindView(R.id.fragment_offline_rv_playlist)
    RecyclerView mRvPlaylist;

    private PlaylistAdapter mAdapter;

    public static PlaylistsFragment newInstance() {

        Bundle args = new Bundle();

        PlaylistsFragment fragment = new PlaylistsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_offline_playlist;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        registerForContextMenu(mRvPlaylist);
        mAdapter = new PlaylistAdapter(mContext, this);
        mRvPlaylist.setLayoutManager(new LinearLayoutManager(mContext));
        mRvPlaylist.setAdapter(mAdapter);
        loadDataTask.execute();
    }

    @Override
    public void onRecyclerViewItemClick(View view, MediaEntity mediaEntity, int position) {

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_playlist_play:
                break;
            case R.id.action_playlist_add_now_playing:
                break;
            case R.id.action_playlist_rename:
                break;
            case R.id.action_playlist_delete:
                break;

        }
        return super.onContextItemSelected(item);
    }

    private AsyncTask<Void, Void, List<PlaylistEntity>> loadDataTask = new AsyncTask<Void, Void, List<PlaylistEntity>>() {
        @Override
        protected List<PlaylistEntity> doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(List<PlaylistEntity> mediaEntities) {
            super.onPostExecute(mediaEntities);
            mAdapter.setDatas(mediaEntities);
        }
    };
}
