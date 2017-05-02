package training.com.tplayer.ui.adapter.offline;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.remote.communication.ArtistEntity;

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

public class ArtistAdapter extends BaseRecyclerViewAdapter<ArtistEntity, ArtistAdapter.ViewHolder> {

    public interface ArtistAdapterListener extends IRecyclerViewOnItemClickListener<ArtistEntity> {
    }

    public ArtistAdapter(Context context, ArtistAdapterListener listener) {
        super(context, listener);
    }

    @Override
    public ArtistAdapter.ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offline_artist, parent, false));
    }

    @Override
    public void onBindViewHolderAdapter(ArtistAdapter.ViewHolder holder, int position) {
        ArtistEntity entity = getDataItem(position);
        holder.mArtist.setText(entity.author);
        holder.mNumberOfSong.setText(String.valueOf(entity.numberOfTrack).trim() + " songs");
        holder.mCtxMenu.setOnLongClickListener(null);
    }

    public class ViewHolder extends BaseViewHolder implements View.OnCreateContextMenuListener {
        @BindView(R.id.item_fragment_artist_name)
        TextViewRoboto mArtist ;
        @BindView(R.id.item_fragment_artist_number_of_song)
        TextViewRoboto mNumberOfSong ;
        @BindView(R.id.item_fragment_aritst_context_menu)
        ImageView mCtxMenu ;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mCtxMenu.setOnCreateContextMenuListener(this);
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
