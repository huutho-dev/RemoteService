package training.com.tplayer.ui.adapter.online;


import android.content.Context;
import android.view.LayoutInflater;
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
import training.com.tplayer.ui.entity.AlbumBasicEntity;
import training.com.tplayer.utils.ImageUtils;

/**
 * Created by hnc on 18/04/2017.
 */

public class ListAlbumAdapter  extends BaseRecyclerViewAdapter<AlbumBasicEntity, ListAlbumAdapter.ViewHolder> {

    public interface ListAlbumAdapterListener extends IRecyclerViewOnItemClickListener {

    }

    public ListAlbumAdapter(Context context, ListAlbumAdapterListener listener) {
        super(context, listener);
    }


    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_item_list_album_onl, parent, false));
    }

    @Override
    public void onBindViewHolderAdapter(ViewHolder holder, int position) {
        AlbumBasicEntity entity = getDataItem(position);
        ImageUtils.loadImageBasic(mContext, entity.image, holder.mImage);
        holder.mTitle.setText(entity.name);
        holder.mDesc.setText(entity.artist);
    }



    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.layout_item_recyclerview_image)
        ImageView mImage;
        @BindView(R.id.layout_item_recyclerview_title)
        TextViewRoboto mTitle;
        @BindView(R.id.layout_item_recyclerview_desc)
        TextViewRoboto mDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}