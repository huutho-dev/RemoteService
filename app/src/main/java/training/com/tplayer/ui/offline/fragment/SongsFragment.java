package training.com.tplayer.ui.offline.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.remote.communication.IMyAidlInterface;
import com.remote.communication.MediaEntity;
import com.remote.communication.PlaylistEntity;
import com.remote.communication.PlaylistMemberEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.database.DataBaseUtils;
import training.com.tplayer.database.SourceTableMedia;
import training.com.tplayer.database.SourceTablePlaylist;
import training.com.tplayer.database.SourceTablePlaylistMember;
import training.com.tplayer.ui.adapter.offline.PlaylistAdapter;
import training.com.tplayer.ui.adapter.offline.SongAdapter;
import training.com.tplayer.ui.offline.activity.TabOfflineActivity;
import training.com.tplayer.ui.player.PlayerActivity;
import training.com.tplayer.utils.FileUtils;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class SongsFragment extends BaseFragment implements SongAdapter.SongAdapterListener, View.OnClickListener {

    public static final String BUNDLE_FROM_WHERE = "bundle.from.where";
    public static final String BUNDLE_FROM_ALBUM = "bundle.from.album";
    public static final String BUNDLE_FROM_ARTIST = "bundle.from.artist";
    public static final String BUNDLE_FROM_PLAYLIST = "bundle.from.playlist";
    public static final String BUNDLE_FROM_FOLDER = "bundle.from.folder";

    @BindView(R.id.fragment_offline_rv_songs)
    RecyclerView mRvSong;

    @BindView(R.id.fragment_songs_play_all)
    FloatingActionButton mPlayAll;

    private SongAdapter mAdapter;

    private IMyAidlInterface mPlayerService;

    public static SongsFragment newInstance() {
        Bundle args = new Bundle();
        SongsFragment fragment = new SongsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static SongsFragment newInstance(String fromWhere, List<MediaEntity> entityList) {
        Bundle args = new Bundle();

        args.putString(BUNDLE_FROM_WHERE, fromWhere);

        if (fromWhere.equals(BUNDLE_FROM_ALBUM)) {
            args.putParcelableArrayList(BUNDLE_FROM_ALBUM, (ArrayList<? extends Parcelable>) entityList);

        } else if (fromWhere.equals(BUNDLE_FROM_ARTIST)) {
            args.putParcelableArrayList(BUNDLE_FROM_ARTIST, (ArrayList<? extends Parcelable>) entityList);

        } else if (fromWhere.equals(BUNDLE_FROM_PLAYLIST)) {
            args.putParcelableArrayList(BUNDLE_FROM_PLAYLIST, (ArrayList<? extends Parcelable>) entityList);

        } else if (fromWhere.equals(BUNDLE_FROM_FOLDER)) {
            args.putParcelableArrayList(BUNDLE_FROM_FOLDER, (ArrayList<? extends Parcelable>) entityList);
        }

        SongsFragment fragment = new SongsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String fromWhere;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_offline_songs;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());

        mPlayerService = ((TabOfflineActivity) mActivity).getPlayerService();

        registerForContextMenu(mRvSong);
        mAdapter = new SongAdapter(mContext, this);
        mRvSong.setLayoutManager(new LinearLayoutManager(mContext));
        mRvSong.setNestedScrollingEnabled(false);
        mRvSong.setAdapter(mAdapter);

        mPlayAll.setOnClickListener(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            fromWhere = bundle.getString(BUNDLE_FROM_WHERE);
            List<MediaEntity> entityList;
            if (fromWhere != null)

                switch (fromWhere) {
                    case BUNDLE_FROM_ALBUM:
                        entityList = bundle.getParcelableArrayList(BUNDLE_FROM_ALBUM);
                        mAdapter.setDatas(entityList);
                        break;
                    case BUNDLE_FROM_ARTIST:
                        entityList = bundle.getParcelableArrayList(BUNDLE_FROM_ARTIST);
                        mAdapter.setDatas(entityList);
                        break;
                    case BUNDLE_FROM_PLAYLIST:
                        entityList = bundle.getParcelableArrayList(BUNDLE_FROM_PLAYLIST);
                        mAdapter.setDatas(entityList);
                        break;
                    default:
                        loadDataFromdDatabase();
                }
            else loadDataFromdDatabase();
        }
    }


    @Override
    public void onRecyclerViewItemClick(View view, MediaEntity mediaEntity, int position) {
        List<MediaEntity> song = new ArrayList<>();
        song.add(mediaEntity);
        startActivity(song);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((BaseActivity) getActivity()).bindTPlayerService();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_first_now_playing:
                LogUtils.printLog("action_add_to_first_now_playing");
                try {
                    ((TabOfflineActivity) getActivity()).getPlayerService().addNextPlaying(mAdapter.getDataItem(mAdapter.positionContext));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            // thêm vào cuối danh sách
            case R.id.action_add_to_now_plays:
                LogUtils.printLog("action_add_to_now_plays");
                try {
                    ((TabOfflineActivity) getActivity()).getPlayerService().addNextPlaying(mAdapter.getDataItem(mAdapter.positionContext));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.action_add_playlist:
                LogUtils.printLog("action_add_playlist");
                addPlaylistDialog(mAdapter.getDataItem(mAdapter.positionContext));
                break;
            case R.id.action_set_is_rington:
                LogUtils.printLog("action_set_is_rington");
                FileUtils.setAudioRington(mContext, mAdapter.getDataItem(mAdapter.positionContext));
                Toast.makeText(mContext, R.string.set_ring_tone, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_delete:
                LogUtils.printLog("action_delete");
                comfirmDeleteDialog(mContext, mAdapter.getDataItem(mAdapter.positionContext), mAdapter);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void loadDataFromdDatabase() {
        new AsyncTask<Void, Void, List<MediaEntity>>() {
            @Override
            protected List<MediaEntity> doInBackground(Void... params) {
                return SourceTableMedia.getInstance(mContext).getList();
            }

            @Override
            protected void onPostExecute(List<MediaEntity> mediaEntities) {
                super.onPostExecute(mediaEntities);
                LogUtils.printLog(mediaEntities.toString());
                mAdapter.setDatas(mediaEntities);
            }
        }.execute();
    }

    private void addPlaylistDialog(final MediaEntity entity) {
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
                long insertToPlaylist = insertToPlaylist(playlistEntity, entity);
                if (insertToPlaylist > 0) {
                    Toast.makeText(mContext, R.string.dialog_add_playlist_add_success, Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(mContext, R.string.dialog_add_playlist_add_fail, Toast.LENGTH_SHORT).show();
                }
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
                        long insertToPlaylist = insertToPlaylist(rowToInsertSong, entity);
                        if (insertToPlaylist != -1) {
                            Toast.makeText(mContext, R.string.dialog_add_playlist_add_success, Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(mContext, R.string.dialog_add_playlist_add_fail, Toast.LENGTH_SHORT).show();
                        }
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

    private void comfirmDeleteDialog(final Context context, final MediaEntity entity, final SongAdapter adapter) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(R.string.dialog_confirm_delete_title);
        alertDialog.setMessage(mContext.getResources().getString(R.string.dialog_confirm_delete));
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int del = 0;

                if (fromWhere != null) {
                    switch (fromWhere) {
                        case BUNDLE_FROM_ALBUM:

                            break;
                        case BUNDLE_FROM_ARTIST:

                            break;
                        case BUNDLE_FROM_PLAYLIST:
                            del = SourceTablePlaylistMember.getInstance(context).deleteRow(entity);
                            break;
                    }
                } else {
                    del = SourceTableMedia.getInstance(context).deleteRow(entity);
                }

                if (del > 0) {
                    if (adapter != null) {
                        adapter.removeData(entity);
                        adapter.notifyDataSetChanged();
                    }
                    Toast.makeText(context, R.string.dialog_confirm_delete_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.dialog_confirm_delete_fail, Toast.LENGTH_SHORT).show();
                }


            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

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

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.fragment_songs_play_all:
                    startActivity(mAdapter.getDatas());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startActivity(List<MediaEntity> playlist) {
        Intent intent = new Intent(mContext, PlayerActivity.class);
        intent.putParcelableArrayListExtra(PlayerActivity.EXTRA_DATA_PLAYER, (ArrayList<? extends Parcelable>) playlist);
        startActivity(intent);
    }

}
