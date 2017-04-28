package training.com.tplayer.ui.offline.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.remote.communication.AlbumEntity;
import com.remote.communication.MediaEntity;

import java.util.List;

import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.database.SourceTableAlbum;

/**
 * Created by hnc on 28/04/2017.
 */

public class AlbumsFragment extends BaseFragment {

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
        ButterKnife.bind(this,getView());


    }


    private AsyncTask<Void, Void, List<AlbumEntity>> loadDataTask = new AsyncTask<Void, Void, List<AlbumEntity>>() {
        @Override
        protected List<AlbumEntity> doInBackground(Void... params) {
            return SourceTableAlbum.getInstance(mContext).getList();
        }

        @Override
        protected void onPostExecute(List<AlbumEntity> mediaEntities) {
            super.onPostExecute(mediaEntities);
            mAdapter.setDatas(mediaEntities);
        }
    };
}
