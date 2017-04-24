package training.com.tplayer.ui.adapter.online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
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
 * Created by ThoNH on 4/17/2017.
 */

public class ChartsItemAdapter extends BaseRecyclerViewAdapter<HotSongOnlEntity, ChartsItemAdapter.ViewHolder> {

    public interface ChartsItemAdapterListener extends IRecyclerViewOnItemClickListener {

    }

    public ChartsItemAdapter(Context context, ChartsItemAdapterListener listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_item_charts_item, parent, false));
    }

    @Override
    public void onBindViewHolderAdapter(ViewHolder holder, int position) {
        HotSongOnlEntity entity = getDataItem(position);
        ImageUtils.loadImageBasic(mContext, entity.image, holder.mImvSong);
        holder.mOrder.setText(entity.order + "");
        holder.mTxtTitle.setText(entity.title);
        holder.mTxtArtist.setText(entity.artist);

        setTranslateFromLeftAnim(holder.mTxtArtist);
        setTranslateFromLeftAnim(holder.mTxtTitle);
        setTranslateFromLeftAnim(holder.mOrder);
        setFadeAnim(holder.mImvSong);

    }

    private void setTranslateFromLeftAnim(View view){
        TranslateAnimation translateAnimation = new TranslateAnimation(400,view.getX(),view.getY(),view.getY());
        view.startAnimation(translateAnimation);
        translateAnimation.setDuration(300);
    }


    private void setFadeAnim(View view) {
        ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(300);
        view.startAnimation(animation);
    }


    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.txt_order)
        TextViewRoboto mOrder;

        @BindView(R.id.txt_title)
        TextViewRoboto mTxtTitle;

        @BindView(R.id.txt_artist)
        TextViewRoboto mTxtArtist;

        @BindView(R.id.imv_image_song)
        ImageView mImvSong;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
