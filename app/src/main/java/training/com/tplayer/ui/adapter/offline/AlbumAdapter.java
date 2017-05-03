package training.com.tplayer.ui.adapter.offline;

import android.content.Context;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.remote.communication.AlbumEntity;

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

public class AlbumAdapter extends BaseRecyclerViewAdapter<AlbumEntity, AlbumAdapter.ViewHolder> {

    public int positionMenuContext = -1 ;

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
        AlbumEntity entity = getDataItem(position);
        if (entity.albumArt != null && !entity.albumArt.equals(""))
           holder.mArt.setImageURI(Uri.parse(entity.albumArt));

        holder.mTitle.setText(entity.album);
        holder.mArtist.setText(entity.artist);
        holder.mNumberOfSong.setText(String.valueOf(entity.numberOfSong));
        holder.mArt.setOnLongClickListener(null);
        holder.mCtxMenu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                positionMenuContext = position;
                return false;
            }
        });
    }


    public class ViewHolder extends BaseViewHolder implements View.OnCreateContextMenuListener {
        @BindView(R.id.item_fragment_album_art)
        ImageView mArt;
        @BindView(R.id.item_fragment_album_title)
        TextViewRoboto mTitle;
        @BindView(R.id.item_fragment_album_artist)
        TextViewRoboto mArtist;
        @BindView(R.id.item_fragment_album_number_song)
        TextViewRoboto mNumberOfSong;
        @BindView(R.id.item_fragment_album_context_menu)
        ImageView mCtxMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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

    public int getPosMenuContext(){
        return positionMenuContext;
    }
}
