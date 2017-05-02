package training.com.tplayer.ui.adapter.offline;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.recyclerview.BaseRecyclerViewAdapter;
import training.com.tplayer.base.recyclerview.BaseViewHolder;
import training.com.tplayer.base.recyclerview.IRecyclerViewOnItemClickListener;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.ui.entity.offline.FolderEntity;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class FolderAdapter  extends BaseRecyclerViewAdapter<FolderEntity, FolderAdapter.ViewHolder> {

    public interface FolderAdapterListener extends IRecyclerViewOnItemClickListener<FolderEntity> {
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
        FolderEntity entity = getDataItem(position);
        LogUtils.printLog(entity.toString());
        holder.mNumberOfSong.setText(String.valueOf(entity.mNumberSong) + " songs");
        holder.mFolder.setText(entity.mFolderName);
        holder.mCtxMenu.setOnLongClickListener(null);
    }


    public class ViewHolder extends BaseViewHolder implements View.OnCreateContextMenuListener {

        @BindView(R.id.item_fragment_folder_name)
        TextViewRoboto mFolder ;
        @BindView(R.id.item_fragment_folder_number_song)
        TextViewRoboto mNumberOfSong ;
        @BindView(R.id.item_fragment_folder_context_menu)
        ImageView mCtxMenu ;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mCtxMenu.setOnCreateContextMenuListener(this);
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
