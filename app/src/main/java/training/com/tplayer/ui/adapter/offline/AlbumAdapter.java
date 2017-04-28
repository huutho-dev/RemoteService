package training.com.tplayer.ui.adapter.offline;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.remote.communication.AlbumEntity;

import training.com.tplayer.R;
import training.com.tplayer.base.recyclerview.BaseRecyclerViewAdapter;
import training.com.tplayer.base.recyclerview.BaseViewHolder;
import training.com.tplayer.base.recyclerview.IRecyclerViewOnItemClickListener;

/**
 * Created by hnc on 28/04/2017.
 */

public class AlbumAdapter extends BaseRecyclerViewAdapter<AlbumEntity, AlbumAdapter.ViewHolder> {

    public interface AlbumAdapterListener extends IRecyclerViewOnItemClickListener<AlbumEntity> {
    }

    public AlbumAdapter(Context context, AlbumAdapterListener listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offline_album, parent, false));
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
            menu.add(Menu.NONE, R.id.action_album_play, Menu.NONE, R.string.context_menu_album_add_now_playing);
            menu.add(Menu.NONE, R.id.action_album_play_shuffle, Menu.NONE, R.string.context_menu_album_shuffle);
            menu.add(Menu.NONE, R.id.action_album_add_now_playing, Menu.NONE, R.string.context_menu_album_add_now_playing);
            menu.add(Menu.NONE, R.id.action_album_add_playlist, Menu.NONE, R.string.context_menu_album_add_playlist);
        }
    }
}
