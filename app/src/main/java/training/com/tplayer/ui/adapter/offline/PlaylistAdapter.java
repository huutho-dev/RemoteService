package training.com.tplayer.ui.adapter.offline;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.remote.communication.PlaylistEntity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.recyclerview.BaseRecyclerViewAdapter;
import training.com.tplayer.base.recyclerview.BaseViewHolder;
import training.com.tplayer.base.recyclerview.IRecyclerViewOnItemClickListener;
import training.com.tplayer.custom.TextViewRoboto;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class PlaylistAdapter  extends BaseRecyclerViewAdapter<PlaylistEntity, PlaylistAdapter.ViewHolder> {

    public int positionContext = -1 ;


    public interface PlaylistAdapterListener extends IRecyclerViewOnItemClickListener<PlaylistEntity> {
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
        PlaylistEntity entity = getDataItem(position);
        holder.mPlaylistName.setText(entity.name);
        holder.mDataAdded.setText(convertTime(entity.dateAdded));
    }


    public class ViewHolder extends BaseViewHolder implements View.OnCreateContextMenuListener {
        @BindView(R.id.item_fragment_playlist_name)
        TextViewRoboto mPlaylistName ;
        @BindView(R.id.item_fragment_playlist_date_added)
        TextViewRoboto mDataAdded ;
        @BindView(R.id.item_fragment_playlist_context_menu)
        ImageView mCtxMenu ;
        
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mCtxMenu.setOnCreateContextMenuListener(this);
            mCtxMenu.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    positionContext = getAdapterPosition();
                    return false;
                }
            });
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.action_playlist_play, Menu.NONE, R.string.context_menu_playlist_play);
            menu.add(Menu.NONE, R.id.action_playlist_rename, Menu.NONE, R.string.context_menu_playlist_rename);
            menu.add(Menu.NONE, R.id.action_playlist_delete, Menu.NONE, R.string.context_menu_playlist_delete);
        }
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

    public int getContextMenuPosition(){
        return positionContext;
    }
}
