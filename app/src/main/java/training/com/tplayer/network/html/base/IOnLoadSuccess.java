package training.com.tplayer.network.html.base;

import java.util.List;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by ThoNH on 4/13/2017.
 */

public interface IOnLoadSuccess<E extends BaseEntity> {
    void onResponse(List<E> entity);
}
