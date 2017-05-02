package training.com.tplayer.ui.entity;

import java.util.List;

/**
 * Created by ThoNH on 17/04/2017.
 */

public class SongOnlineEntity {

    /**
     * msg : 0
     * data : [{"id":"ZW79ZBE8","name":"Nơi Này Có Anh","author":"Sơn Tùng M-TP","link":"/bai-hat/Noi-Nay-Co-Anh-Son-Tung-M-TP/ZW79ZBE8.html","cover":"http://zmp3-photo-td.zadn.vn/cover_artist/b/d/bd7115f888b12ce8c2889020dd7cdf2c_1487040734.jpg","qualities":["128","320"],"source_list":["http://zmp3-mp3-s1-tr.zadn.vn/d41230d1df9536cb6f84/1181817601640857205?key=qEZLTw-1ElttTsYk8OzLJg&expires=1492414145",""],"source_base":"http://","lyric":"http://static.mp3.zdn.vn/lyrics/2017/04/10/437fd8dab336d63566e90d61e2dde4ea_1075841896.lrc","mv_link":"/video-clip/Noi-Nay-Co-Anh-Son-Tung-M-TP/ZW79ZBE8.html"}]
     */

    public int msg;
    public List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : ZW79ZBE8
         * name : Nơi Này Có Anh
         * author : Sơn Tùng M-TP
         * link : /bai-hat/Noi-Nay-Co-Anh-Son-Tung-M-TP/ZW79ZBE8.html
         * cover : http://zmp3-photo-td.zadn.vn/cover_artist/b/d/bd7115f888b12ce8c2889020dd7cdf2c_1487040734.jpg
         * qualities : ["128","320"]
         * source_list : ["http://zmp3-mp3-s1-tr.zadn.vn/d41230d1df9536cb6f84/1181817601640857205?key=qEZLTw-1ElttTsYk8OzLJg&expires=1492414145",""]
         * source_base : http://
         * lyric : http://static.mp3.zdn.vn/lyrics/2017/04/10/437fd8dab336d63566e90d61e2dde4ea_1075841896.lrc
         * mv_link : /video-clip/Noi-Nay-Co-Anh-Son-Tung-M-TP/ZW79ZBE8.html
         */

        public String id;
        public String name;
        public String artist;
        public String link;
        public String cover;
        public String source_base;
        public String lyric;
        public List<String> source_list;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", author='" + artist + '\'' +
                    ", link='" + link + '\'' +
                    ", cover='" + cover + '\'' +
                    ", source_base='" + source_base + '\'' +
                    ", lyric='" + lyric + '\'' +
                    ", source_list=" + source_list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SongOnlineEntity{" +
                "msg=" + msg +
                ", data=" + data +
                '}';
    }
}
