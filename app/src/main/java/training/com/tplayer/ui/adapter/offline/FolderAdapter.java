package training.com.tplayer.ui.adapter.offline;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.remote.communication.MediaEntity;

import training.com.tplayer.R;
import training.com.tplayer.base.recyclerview.BaseRecyclerViewAdapter;
import training.com.tplayer.base.recyclerview.BaseViewHolder;
import training.com.tplayer.base.recyclerview.IRecyclerViewOnItemClickListener;
import training.com.tplayer.ui.entity.offline.FolderEntity;

/**
 * Created by hnc on 28/04/2017.
 */

public class FolderAdapter  extends BaseRecyclerViewAdapter<FolderEntity, FolderAdapter.ViewHolder> {

    public interface FolderAdapterListener extends IRecyclerViewOnItemClickListener<MediaEntity> {
    }

    public FolderAdapter(Context context, FolderAdapterListener listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offline_folder, parent, false));
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
            menu.add(Menu.NONE, R.id.action_folder_play, Menu.NONE, R.string.context_menu_folder_play);
            menu.add(Menu.NONE, R.id.action_folder_play_shuffle, Menu.NONE, R.string.context_menu_folder_shuffle);
            menu.add(Menu.NONE, R.id.action_folder_add_now_playing, Menu.NONE, R.string.context_menu_folder_add_now_playing);
            menu.add(Menu.NONE, R.id.action_folder_add_playlist, Menu.NONE, R.string.context_menu_folder_add_playlist);
            menu.add(Menu.NONE, R.id.action_folder_hide, Menu.NONE, R.string.context_menu_folder_hide);

        }
    }
}
