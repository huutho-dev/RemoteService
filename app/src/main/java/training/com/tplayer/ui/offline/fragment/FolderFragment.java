package training.com.tplayer.ui.offline.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
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
import training.com.tplayer.database.SourceTableMedia;
import training.com.tplayer.database.SourceTablePlaylist;
import training.com.tplayer.database.SourceTablePlaylistMember;
import training.com.tplayer.ui.adapter.offline.FolderAdapter;
import training.com.tplayer.ui.adapter.offline.PlaylistAdapter;
import training.com.tplayer.ui.entity.offline.FolderEntity;
import training.com.tplayer.ui.offline.activity.TabOfflineActivity;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class FolderFragment extends BaseFragment implements FolderAdapter.FolderAdapterListener {

    @BindView(R.id.fragment_offline_rv_folder)
    RecyclerView mRvFolder;

    private FolderAdapter mAdapter;

    private IMyAidlInterface mPlayerService;

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

        mPlayerService = ((TabOfflineActivity) mActivity).getPlayerService();
        mAdapter = new FolderAdapter(mContext, this);
        mRvFolder.setLayoutManager(new LinearLayoutManager(mContext));
        mRvFolder.setAdapter(mAdapter);


        new AsyncTask<Void, Void, List<FolderEntity>>() {
            @Override
            protected List<FolderEntity> doInBackground(Void... params) {
                return SourceTableMedia.getInstance(mContext).getListFolder();
            }

            @Override
            protected void onPostExecute(List<FolderEntity> folderEntities) {
                super.onPostExecute(folderEntities);
                LogUtils.printLog(folderEntities.toString());
                mAdapter.setDatas(folderEntities);
            }
        }.execute();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        List<MediaEntity> entityList = SourceTableMedia.getInstance(mContext)
                .getFileInFolder(mAdapter.getDataItem(mAdapter.mContextMenuPosition).mPath);
        try {
            switch (item.getItemId()) {
                case R.id.action_folder_play:
                    mPlayerService.setPlayList(entityList);
                    break;
                case R.id.action_folder_add_now_playing:
                    mPlayerService.addToEndListNowPlaying(entityList);
                    break;
                case R.id.action_folder_add_playlist:
                    addPlaylistDialog(entityList);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onContextItemSelected(item);
    }


    @Override
    public void onRecyclerViewItemClick(View view, FolderEntity folderEntity, int position) {
        FragmentManager fm = getFragmentManager();
        LogUtils.printLog(folderEntity.mPath);
        FragmentTransaction ft = fm.beginTransaction();
        List<MediaEntity> entityList = SourceTableMedia.getInstance(mContext)
                .getFileInFolder(folderEntity.mPath);
        ft.add(R.id.root, SongsFragment.newInstance(SongsFragment.BUNDLE_FROM_FOLDER, entityList));
        ft.addToBackStack(null);
        ft.commit();
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
