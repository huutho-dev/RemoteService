package training.com.tplayer.ui.offline.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.remote.communication.AlbumEntity;
import com.remote.communication.IMyAidlInterface;
import com.remote.communication.MediaEntity;
import com.remote.communication.PlaylistEntity;
import com.remote.communication.PlaylistMemberEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.database.DataBaseUtils;
import training.com.tplayer.database.SourceTableAlbum;
import training.com.tplayer.database.SourceTableMedia;
import training.com.tplayer.database.SourceTablePlaylist;
import training.com.tplayer.database.SourceTablePlaylistMember;
import training.com.tplayer.ui.adapter.offline.AlbumAdapter;
import training.com.tplayer.ui.adapter.offline.PlaylistAdapter;
import training.com.tplayer.ui.offline.activity.TabOfflineActivity;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class AlbumsFragment extends BaseFragment implements AlbumAdapter.AlbumAdapterListener {

    @BindView(R.id.fragment_offline_rv_album)
    RecyclerView mRvAlbum;

    private AlbumAdapter mAdapter;

    private IMyAidlInterface mPlayerService;

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

        registerForContextMenu(mRvAlbum);
        mPlayerService = ((TabOfflineActivity) mActivity).getPlayerService();

        mAdapter = new AlbumAdapter(mContext, this);
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
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        List<MediaEntity> entityList = SourceTableMedia.getInstance(mContext)
                .getList(DataBaseUtils.DbStoreAlbumColumn._ALBUM+"=?", new String[]{albumEntity.album});

        LogUtils.printLog(entityList.toString());
        ft.add(R.id.root, SongsFragment.newInstance(SongsFragment.BUNDLE_FROM_ALBUM, entityList));
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                LogUtils.printLog("AlbumFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        if (getUserVisibleHint()){
            String albumName = mAdapter.getDataItem(mAdapter.getPosMenuContext()).album;
            List<MediaEntity> songsInAlbum = SourceTableMedia.getInstance(mContext).getList(DataBaseUtils.DbStoreAlbumColumn._ALBUM+"=?", new String[]{albumName});
            try {
                switch (item.getItemId()) {
                    case R.id.action_album_play:
                        ((TabOfflineActivity) getActivity()).getPlayerService().setPlayList(songsInAlbum);
                        break;

                    case R.id.action_album_add_now_playing:
                        ((TabOfflineActivity) getActivity()).getPlayerService().addListNextPlaying(songsInAlbum);
                        break;

                    case R.id.action_album_add_end_playing:
                        ((TabOfflineActivity) getActivity()).getPlayerService().addToEndListNowPlaying(songsInAlbum);
                        break;

                    case R.id.action_album_add_playlist:
                        addPlaylistDialog(songsInAlbum);
                        break;

                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }



        return super.onContextItemSelected(item);
    }


    private void addPlaylistDialog(final List<MediaEntity> entities) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(R.string.dialog_add_playlist);

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_playlist, null);
        ImageView addNew = (ImageView) view.findViewById(R.id.dialog_btn_add_new_playlist);
        final EditText mNewPlaylist = (EditText) view.findViewById(R.id.dialog_edt_add_new_playlist);
        RecyclerView rvPlaylist = (RecyclerView) view.findViewById(R.id.dialog_rv_add_playlist);

        rvPlaylist.setLayoutManager(new LinearLayoutManager(mContext));

        final PlaylistAdapter mAdapter = new PlaylistAdapter(mContext, new PlaylistAdapter.PlaylistAdapterListener() {
            @Override
            public void onRecyclerViewItemClick(View view, PlaylistEntity playlistEntity, int position) {
                int count = 0;
                for (int i = 0; i < entities.size(); i++) {
                    count++;
                    insertToPlaylist(playlistEntity, entities.get(i));
                }
                if (count == entities.size()) {
                    Toast.makeText(mContext, R.string.dialog_add_playlist_add_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.dialog_add_playlist_add_fail + (entities.size() - count), Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playlistName = mNewPlaylist.getText().toString();
                if (!TextUtils.isEmpty(playlistName) && !"".equals(playlistName)) {
                    long success = SourceTablePlaylist
                            .getInstance(mContext)
                            .insertRow(new PlaylistEntity(0, playlistName, (int) System.currentTimeMillis()));

                    if (success > 0) {
                        PlaylistEntity rowToInsertSong =
                                SourceTablePlaylist
                                        .getInstance(mContext)
                                        .getRow(DataBaseUtils.DbStorePlaylistColumn._NAME, new String[]{playlistName});
                        int count = 0;
                        for (int i = 0; i < entities.size(); i++) {
                            count++;
                            insertToPlaylist(rowToInsertSong, entities.get(i));
                        }
                        if (count == entities.size()) {
                            Toast.makeText(mContext, R.string.dialog_add_playlist_add_success, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, R.string.dialog_add_playlist_add_fail + (entities.size() - count), Toast.LENGTH_SHORT).show();
                        }
                        alertDialog.dismiss();
                    }
                } else {
                    Toast.makeText(mContext, R.string.dialog_add_playlist_name_not_null, Toast.LENGTH_SHORT).show();
                }

            }
        });

        rvPlaylist.setAdapter(mAdapter);
        new AsyncTask<Void, Void, List<PlaylistEntity>>() {
            @Override
            protected List<PlaylistEntity> doInBackground(Void... params) {
                return SourceTablePlaylist.getInstance(mContext).getList();
            }

            @Override
            protected void onPostExecute(List<PlaylistEntity> mediaEntities) {
                super.onPostExecute(mediaEntities);
                mAdapter.setDatas(mediaEntities);
            }
        }.execute();

        alertDialog.setView(view);
        alertDialog.show();
    }

    private long insertToPlaylist(PlaylistEntity playlistEntity, MediaEntity entity) {
        PlaylistMemberEntity memberEntity = new PlaylistMemberEntity();

        memberEntity.id = playlistEntity.mId;
        memberEntity.album = entity.album;
        memberEntity.size = 0;
        memberEntity.albumId = entity.albumId;
        memberEntity.artistId = entity.artistId;
        memberEntity.artist = entity.artist;
        memberEntity.audioId = entity.id;
        memberEntity.title = entity.title;
        memberEntity.data = entity.data;
        memberEntity.playListId = playlistEntity.mId;
        memberEntity.displayName = entity.displayName;
        memberEntity.MIMEType = entity.mimeType;
        memberEntity.dateAdded = entity.dateAdded;

        return SourceTablePlaylistMember
                .getInstance(mContext)
                .insertRow(memberEntity);
    }


}
