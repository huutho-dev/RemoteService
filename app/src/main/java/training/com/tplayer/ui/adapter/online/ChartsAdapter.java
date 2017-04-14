package training.com.tplayer.ui.adapter.online;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.base.recyclerview.BaseRecyclerViewAdapter;
import training.com.tplayer.base.recyclerview.BaseViewHolder;
import training.com.tplayer.base.recyclerview.IRecyclerViewOnItemClickListener;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.ui.entity.BasicSongOnlEntity;

/**
 * Created by hnc on 14/04/2017.
 */

public class ChartsAdapter extends BaseRecyclerViewAdapter<BasicSongOnlEntity, ChartsAdapter.ViewHolder> {

    public interface ChartsVietnamAdapterListener extends IRecyclerViewOnItemClickListener<BasicSongOnlEntity> {

    }

    public ChartsAdapter(Context context, ChartsVietnamAdapterListener listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_recyclerview_charts, parent, false));
    }

    @Override
    public void onBindViewHolderAdapter(ViewHolder holder, int position) {
        BasicSongOnlEntity entity = getDataItem(position);
        holder.mTxtOrder.setText(entity.order+"");
        holder.mTxtTitle.setText(entity.title);
        holder.mTxtArtist.setText(entity.artist);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_order)
        TextViewRoboto mTxtOrder;

        @BindView(R.id.txt_title)
        TextViewRoboto mTxtTitle;

        @BindView(R.id.txt_artist)
        TextViewRoboto mTxtArtist;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setDatas(List<BaseEntity> mDatas, int startPositionSplit, int endPositionSplit) {
        for (int i = 0; i < mDatas.size(); i++) {
            if (i >= startPositionSplit && i < endPositionSplit) {
                addData((BasicSongOnlEntity) mDatas.get(i));
            }
        }
    }
}
