package training.com.tplayer.base.recyclerview;

import android.view.View;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by ThoNH on 05/04/2017.
 */

public interface IRecyclerViewOnItemClickListener<E extends BaseEntity> {
    void onRecyclerViewItemClick(View view, E e, int position);
}
