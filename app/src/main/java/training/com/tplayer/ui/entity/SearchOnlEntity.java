package training.com.tplayer.ui.entity;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by hnc on 15/05/2017.
 */


public class SearchOnlEntity extends BaseEntity {

    public List<DataBean> data;


    public static class DataBean {
        public List<ItemSearchEntity> album = new ArrayList<>();

        public List<ItemSearchEntity> song = new ArrayList<>();

    }
}
