package training.com.tplayer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.remote.communication.Song;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.recyclerview.BaseRecyclerViewAdapter;
import training.com.tplayer.base.recyclerview.BaseViewHolder;
import training.com.tplayer.base.recyclerview.IRecyclerViewOnItemClickListener;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.utils.ImageUtils;

/**
 * Created by hnc on 17/04/2017.
 */

public class PlayListInPlayerAdapter extends BaseRecyclerViewAdapter<Song, PlayListInPlayerAdapter.ViewHolder> {

    public interface PlayListInPlayerAdapterListener extends IRecyclerViewOnItemClickListener {

    }

    public PlayListInPlayerAdapter(Context context, PlayListInPlayerAdapterListener listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_item_play_list, parent, false));
    }

    @Override
    public void onBindViewHolderAdapter(ViewHolder holder, int position) {
        Song song = getDataItem(position);
        holder.mTxtSongName.setText(song._title);
        holder.mTxtSongArtist.setText(song.artist_name);
        holder.mImvStatePlay.setBackground(mContext.getResources().getDrawable(R.drawable.background_play_state));
        ImageUtils.loadImagePlayList(mContext,song._art, holder.mImvArtist);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.imv_artist)
        ImageView mImvArtist;

        @BindView(R.id.txt_song_name)
        TextViewRoboto mTxtSongName;

        @BindView(R.id.txt_song_artist)
        TextViewRoboto mTxtSongArtist;

        @BindView(R.id.imv_state_play)
        ImageView mImvStatePlay;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
