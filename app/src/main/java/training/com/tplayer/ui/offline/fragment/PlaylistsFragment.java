package training.com.tplayer.ui.offline.fragment;

import android.content.DialogInterface;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import training.com.tplayer.database.SourceTablePlaylist;
import training.com.tplayer.database.SourceTablePlaylistMember;
import training.com.tplayer.ui.adapter.offline.PlaylistAdapter;
import training.com.tplayer.ui.offline.activity.TabOfflineActivity;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class PlaylistsFragment extends BaseFragment implements PlaylistAdapter.PlaylistAdapterListener {

    @BindView(R.id.fragment_offline_rv_playlist)
    RecyclerView mRvPlaylist;

    private PlaylistAdapter mAdapter;

    private IMyAidlInterface mPlayerService;

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
        mPlayerService = ((TabOfflineActivity) mActivity).getPlayerService();

        registerForContextMenu(mRvPlaylist);
        mAdapter = new PlaylistAdapter(mContext, this);
        mRvPlaylist.setLayoutManager(new LinearLayoutManager(mContext));
        mRvPlaylist.setAdapter(mAdapter);


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
    }

    @Override
    public void onRecyclerViewItemClick(View view, PlaylistEntity entity, int position) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        List<PlaylistMemberEntity> playlistMemberEntities = SourceTablePlaylistMember.getInstance(mContext)
                .getList(DataBaseUtils.DbStorePlaylistColumn.MemberColum._ID, new String[]{String.valueOf(entity.mId)});

        List<MediaEntity> entities = SourceTablePlaylistMember.getInstance(mContext).convertPlaylistMemberToMedia(playlistMemberEntities);
        ft.replace(R.id.root, SongsFragment.newInstance(SongsFragment.BUNDLE_FROM_PLAYLIST, entities));
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_playlist_play:
                try {
                    List<PlaylistMemberEntity> songsOfPlaylist =
                            SourceTablePlaylistMember.getInstance(mContext).getList();

                    List<MediaEntity> songsToPlay =
                            SourceTablePlaylistMember
                                    .getInstance(mContext)
                                    .convertPlaylistMemberToMedia(songsOfPlaylist);

                    mPlayerService.setPlayList(songsToPlay);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.action_playlist_rename:
                renamePlaylistDialog(mAdapter.getDataItem(mAdapter.positionContext));
                break;
            case R.id.action_playlist_delete:
                deletePlaylistDialog(mAdapter.getDataItem(mAdapter.positionContext));
                break;

        }
        return super.onContextItemSelected(item);
    }

    private void renamePlaylistDialog(final PlaylistEntity entity) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(R.string.dialog_playlist_rename);
        final EditText editText = new EditText(mContext);
        editText.setHint("Enter new name of playlist");
        alertDialog.setView(editText);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    entity.name = editText.getText().toString();
                    int update = SourceTablePlaylist.getInstance(mContext).updateRow(entity);
                    if (update > 0) {
                        Toast.makeText(mContext, R.string.dialog_playlist_rename_success, Toast.LENGTH_SHORT).show();
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(mContext, R.string.dialog_playlist_rename_fail, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, R.string.dialog_playlist_rename_empty, Toast.LENGTH_SHORT).show();
                }

            }
        });

        alertDialog.show();
    }

    private void deletePlaylistDialog(final PlaylistEntity entity) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(R.string.dialog_playlist_delete);
        alertDialog.setMessage(mContext.getResources().getString(R.string.dialog_playlist_delete_msg));


        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int delete = SourceTablePlaylist.getInstance(mContext).deleteRow(entity);
                if (delete > 0) {
                    Toast.makeText(mContext, R.string.dialog_playlist_delete_success, Toast.LENGTH_SHORT).show();
                    mAdapter.removeData(entity);
                    mAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(mContext, R.string.dialog_playlist_delete_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.show();
    }


}
