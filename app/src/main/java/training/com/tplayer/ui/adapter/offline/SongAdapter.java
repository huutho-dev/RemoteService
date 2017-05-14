package training.com.tplayer.ui.adapter.offline;

import android.content.Context;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.remote.communication.MediaEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.recyclerview.BaseRecyclerViewAdapter;
import training.com.tplayer.base.recyclerview.BaseViewHolder;
import training.com.tplayer.base.recyclerview.IRecyclerViewOnItemClickListener;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.database.SourceTableMedia;
import training.com.tplayer.utils.ImageUtils;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class SongAdapter extends BaseRecyclerViewAdapter<MediaEntity, SongAdapter.ViewHolder> {

    public int positionContext = -1;


    public interface SongAdapterListener extends IRecyclerViewOnItemClickListener<MediaEntity> {
    }

    public SongAdapter(Context context, SongAdapterListener listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offline_songs, parent, false));
    }

    @Override
    public void onBindViewHolderAdapter(ViewHolder holder, final int position) {
        final MediaEntity entity = getDataItem(position);
        LogUtils.printLog(entity.toString());
        if (entity.art != null && !entity.art.equals("")) {
            holder.mArt.setImageURI(Uri.parse(entity.art));
        } else {
            ImageUtils.loadRoundImage(mContext, R.drawable.dummy_image, holder.mArt);
        }


        if (entity.isFavorite) {
            holder.mFav.setImageResource(R.drawable.ic_favorite_24dp);
        } else {
            holder.mFav.setImageResource(R.drawable.ic_favorite_border_24dp);
        }

        holder.mTitle.setText(entity.title);
        holder.mArtist.setText(entity.artist);
        holder.itemView.setOnLongClickListener(null);
        holder.mCtxMenu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                positionContext = position;
                return false;
            }
        });

        holder.mFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entity.isFavorite = !entity.isFavorite;
              int update =  SourceTableMedia.getInstance(mContext).updateRow(entity);
                notifyItem(position);
                LogUtils.printLog(update+"");
            }
        });
    }


    public class ViewHolder extends BaseViewHolder implements View.OnCreateContextMenuListener {
        @BindView(R.id.item_fragment_song_fav)
        ImageView mFav;
        @BindView(R.id.item_fragment_song_art)
        ImageView mArt;
        @BindView(R.id.item_fragment_song_title)
        TextViewRoboto mTitle;
        @BindView(R.id.item_fragment_Song_artist)
        TextViewRoboto mArtist;
        @BindView(R.id.item_fragment_song_context_menu)
        ImageView mCtxMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mCtxMenu.setOnCreateContextMenuListener(this);
        }


        public int getCtxPosition() {
            return positionContext;
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.action_add_to_first_now_playing, Menu.NONE, R.string.context_menu_song_first_now_playings);
            menu.add(Menu.NONE, R.id.action_add_to_now_plays, Menu.NONE, R.string.context_menu_song_now_playings);
            menu.add(Menu.NONE, R.id.action_add_playlist, Menu.NONE, R.string.context_menu_song_to_playlist);
            menu.add(Menu.NONE, R.id.action_set_is_rington, Menu.NONE, R.string.context_menu_song_set_rington);
            menu.add(Menu.NONE, R.id.action_delete, Menu.NONE, R.string.context_menu_song_delete);
        }
    }
}
