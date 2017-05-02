package training.com.tplayer.ui.offline.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.remote.communication.ArtistEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.database.SourceTableArtist;
import training.com.tplayer.ui.adapter.offline.ArtistAdapter;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class ArtistsFragment extends BaseFragment implements ArtistAdapter.ArtistAdapterListener {

    @BindView(R.id.fragment_offline_rv_artist)
    RecyclerView mRvArtist;

    private ArtistAdapter mAdapter ;

    public static ArtistsFragment newInstance() {
        Bundle args = new Bundle();
        ArtistsFragment fragment = new ArtistsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_offline_artist;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        registerForContextMenu(mRvArtist);
        mAdapter = new ArtistAdapter(mContext, this);
        mRvArtist.setLayoutManager(new LinearLayoutManager(mContext));
        mRvArtist.setAdapter(mAdapter);


        new AsyncTask<Void, Void, List<ArtistEntity>>() {
            @Override
            protected List<ArtistEntity> doInBackground(Void... params) {
                return SourceTableArtist.getInstance(mContext).getList();
            }

            @Override
            protected void onPostExecute(List<ArtistEntity> artistEntities) {
                super.onPostExecute(artistEntities);
                mAdapter.setDatas(artistEntities);
            }
        }.execute();
    }


    @Override
    public void onRecyclerViewItemClick(View view, ArtistEntity artistEntity, int position) {

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
