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
import training.com.tplayer.ui.adapter.offline.DownloadAdapter;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class DownloadFragment extends BaseFragment implements DownloadAdapter.DownloadAdapterListener {

    @BindView(R.id.fragment_offline_rv_download)
    RecyclerView mRvDownload;

    private DownloadAdapter mAdapter;

    public static DownloadFragment newInstance() {

        Bundle args = new Bundle();
        DownloadFragment fragment = new DownloadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_offline_download;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        registerForContextMenu(mRvDownload);
        mAdapter = new DownloadAdapter(mContext, this);
        mRvDownload.setLayoutManager(new LinearLayoutManager(mContext));
        mRvDownload.setAdapter(mAdapter);


        new AsyncTask<Void, Void, List<MediaEntity>>() {
            @Override
            protected List<MediaEntity> doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(List<MediaEntity> mediaEntities) {
                super.onPostExecute(mediaEntities);
//                mAdapter.setDatas(mediaEntities);
            }
        }.execute();
    }


    @Override
    public void onRecyclerViewItemClick(View view, MediaEntity mediaEntity, int position) {

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_first_now_playing:
                break;
            case R.id.action_add_to_now_plays:
                break;
            case R.id.action_add_playlist:
                break;
            case R.id.action_set_is_rington:
                break;
            case R.id.action_delete_in_download:
                break;
            case R.id.action_delete:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
