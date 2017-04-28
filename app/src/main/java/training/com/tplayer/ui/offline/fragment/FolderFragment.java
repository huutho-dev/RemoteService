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
import training.com.tplayer.ui.adapter.offline.FolderAdapter;
import training.com.tplayer.ui.entity.offline.FolderEntity;

/**
 * Created by hnc on 28/04/2017.
 */

public class FolderFragment extends BaseFragment implements FolderAdapter.FolderAdapterListener {

    @BindView(R.id.fragment_offline_rv_folder)
    RecyclerView mRvFolder;

    private FolderAdapter mAdapter;

    public static FolderFragment newInstance() {
        Bundle args = new Bundle();
        FolderFragment fragment = new FolderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_offline_folder;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        mAdapter = new FolderAdapter(mContext, this);
        mRvFolder.setLayoutManager(new LinearLayoutManager(mContext));
        mRvFolder.setAdapter(mAdapter);
        loadDataTask.execute();
    }

    @Override
    public void onRecyclerViewItemClick(View view, MediaEntity mediaEntity, int position) {

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_folder_play:
                break;
            case R.id.action_folder_play_shuffle:
                break;
            case R.id.action_folder_add_now_playing:
                break;
            case R.id.action_folder_add_playlist:
                break;
            case R.id.action_folder_hide:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private AsyncTask<Void, Void, List<FolderEntity>> loadDataTask = new AsyncTask<Void, Void, List<FolderEntity>>() {
        @Override
        protected List<FolderEntity> doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(List<FolderEntity> mediaEntities) {
            super.onPostExecute(mediaEntities);
            mAdapter.setDatas(mediaEntities);
        }
    };
}
