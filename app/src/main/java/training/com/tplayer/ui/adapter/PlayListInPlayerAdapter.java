package training.com.tplayer.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import training.com.tplayer.utils.ImageUtils;

/**
 * Created by ThoNH on 17/04/2017.
 */

public class PlayListInPlayerAdapter extends BaseRecyclerViewAdapter<MediaEntity, PlayListInPlayerAdapter.ViewHolder> {

    public void setActivePlaying(MediaEntity song) {
        for (MediaEntity temp : mDatas) {
            temp.isPlaying = false;
            if (temp.mId == song.mId) {
                temp.isPlaying = true;
            }
        }
        notifyDataSetChanged();
    }

    private PlayListInPlayerAdapterListener mListener;

    public interface PlayListInPlayerAdapterListener extends IRecyclerViewOnItemClickListener<MediaEntity> {
        void onRecyclerViewImagePlayItemClick(View view, MediaEntity entity, int position);
    }

    public PlayListInPlayerAdapter(Context context, PlayListInPlayerAdapterListener listener) {
        super(context, listener);
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_item_play_list, parent, false));
    }

    @Override
    public void onBindViewHolderAdapter(ViewHolder holder, final int position) {
        final MediaEntity song = getDataItem(position);
        holder.mTxtSongName.setText(song.title);
        holder.mTxtSongArtist.setText(song.artist);

        if (song.art != null && TextUtils.isEmpty(song.art)) {
            ImageUtils.loadImagePlayList(mContext, song.art, holder.mImvArtist);
        } else {
            holder.mImvArtist.setImageResource(R.drawable.ic_offline_song);
        }


    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.imv_artist)
        ImageView mImvArtist;

        @BindView(R.id.txt_song_name)
        TextViewRoboto mTxtSongName;

        @BindView(R.id.txt_song_artist)
        TextViewRoboto mTxtSongArtist;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
