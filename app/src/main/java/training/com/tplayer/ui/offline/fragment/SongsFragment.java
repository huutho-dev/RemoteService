package training.com.tplayer.ui.offline.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.remote.communication.MediaEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.database.SourceTableMedia;
import training.com.tplayer.ui.adapter.offline.SongAdapter;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by hnc on 28/04/2017.
 */

public class SongsFragment extends BaseFragment implements SongAdapter.SongAdapterListener {
    @BindView(R.id.fragment_offline_rv_songs)
    RecyclerView mRvSong;

    private SongAdapter mAdapter;

    public static SongsFragment newInstance() {
        Bundle args = new Bundle();
        SongsFragment fragment = new SongsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_offline_songs;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        registerForContextMenu(mRvSong);
        mAdapter = new SongAdapter(mContext, this);
        mRvSong.setLayoutManager(new LinearLayoutManager(mContext));
        mRvSong.setAdapter(mAdapter);
        loadDataTask.execute();
    }


    @Override
    public void onRecyclerViewItemClick(View view, MediaEntity mediaEntity, int position) {

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_first_now_playing:
                LogUtils.printLog("action_add_to_first_now_playing");
                break;
            case R.id.action_add_to_now_plays:
                LogUtils.printLog("action_add_to_now_plays");
                break;
            case R.id.action_add_playlist:
                LogUtils.printLog("action_add_playlist");
                break;
            case R.id.action_set_is_rington:
                LogUtils.printLog("action_set_is_rington");
                break;
            case R.id.action_delete:
                LogUtils.printLog("action_delete");
                break;
        }
        return super.onContextItemSelected(item);
    }


    private AsyncTask<Void, Void, List<MediaEntity>> loadDataTask = new AsyncTask<Void, Void, List<MediaEntity>>() {
        @Override
        protected List<MediaEntity> doInBackground(Void... params) {
            return SourceTableMedia.getInstance(mContext).getList();
        }

        @Override
        protected void onPostExecute(List<MediaEntity> mediaEntities) {
            super.onPostExecute(mediaEntities);
            mAdapter.setDatas(mediaEntities);
        }
    };


}
