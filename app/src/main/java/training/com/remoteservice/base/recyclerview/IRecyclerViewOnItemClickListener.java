package training.com.remoteservice.base.recyclerview;

import android.view.View;

import training.com.remoteservice.base.BaseEntity;

/**
 * Created by ThoNH on 05/04/2017.
 */

public interface IRecyclerViewOnItemClickListener<E extends BaseEntity> {
    void onRecyclerViewItemClick(View view, E e, int position);
}
