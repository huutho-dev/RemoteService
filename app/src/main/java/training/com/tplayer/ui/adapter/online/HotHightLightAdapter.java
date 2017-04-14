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
import training.com.tplayer.ui.entity.HotSongOnlEntity;
import training.com.tplayer.utils.ImageUtils;

/**
 * Created by hnc on 13/04/2017.
 */

public class HotHightLightAdapter extends BaseRecyclerViewAdapter<HotSongOnlEntity, HotHightLightAdapter.ViewHolder> {

    public interface HotHightLightAdapterListener extends IRecyclerViewOnItemClickListener {

    }

    public HotHightLightAdapter(Context context, HotHightLightAdapterListener listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_recyclerview_simple_vertical, parent, false));
    }

    @Override
    public void onBindViewHolderAdapter(ViewHolder holder, int position) {
        HotSongOnlEntity entity = getDataItem(position);
        ImageUtils.loadImageBasic(mContext, entity.image, holder.mImage);
        holder.mTitle.setText(entity.title);
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
