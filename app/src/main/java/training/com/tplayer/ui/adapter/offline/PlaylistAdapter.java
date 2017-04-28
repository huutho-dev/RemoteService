package training.com.tplayer.ui.adapter.offline;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.remote.communication.MediaEntity;
import com.remote.communication.PlaylistEntity;

import training.com.tplayer.R;
import training.com.tplayer.base.recyclerview.BaseRecyclerViewAdapter;
import training.com.tplayer.base.recyclerview.BaseViewHolder;
import training.com.tplayer.base.recyclerview.IRecyclerViewOnItemClickListener;

/**
 * Created by hnc on 28/04/2017.
 */

public class PlaylistAdapter  extends BaseRecyclerViewAdapter<PlaylistEntity, PlaylistAdapter.ViewHolder> {

    public interface PlaylistAdapterListener extends IRecyclerViewOnItemClickListener<MediaEntity> {
    }

    public PlaylistAdapter(Context context, PlaylistAdapterListener listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offline_playlist, parent, false));
    }

    @Override
    public void onBindViewHolderAdapter(ViewHolder holder, final int position) {

    }


    public class ViewHolder extends BaseViewHolder implements View.OnCreateContextMenuListener {
        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.action_playlist_play, Menu.NONE, R.string.context_menu_playlist_play);
            menu.add(Menu.NONE, R.id.action_playlist_add_now_playing, Menu.NONE, R.string.context_menu_playlist_add_now_playing);
            menu.add(Menu.NONE, R.id.action_playlist_rename, Menu.NONE, R.string.context_menu_playlist_rename);
            menu.add(Menu.NONE, R.id.action_playlist_delete, Menu.NONE, R.string.context_menu_playlist_delete);
        }
    }
}
