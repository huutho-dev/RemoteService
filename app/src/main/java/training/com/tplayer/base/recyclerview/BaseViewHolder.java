package training.com.tplayer.base.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hnc on 05/04/2017.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public View mItemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mItemView = itemView;
    }
}
