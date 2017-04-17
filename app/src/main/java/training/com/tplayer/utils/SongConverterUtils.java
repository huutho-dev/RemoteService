package training.com.tplayer.utils;

import com.remote.communication.Song;

import training.com.tplayer.ui.entity.SongOnlineEntity;

/**
 * Created by hnc on 17/04/2017.
 */

public class SongConverterUtils {
    public static Song convert(SongOnlineEntity onlineEntity) {
        SongOnlineEntity.DataBean bean = onlineEntity.getData().get(0);
        Song song = new Song();
        song._id = 0;
        song._data = bean.source_list.get(0);
        song._display_name = bean.name + "mp3";
        song._title = bean.name;
        song._lyric = bean.lyric;
        song._art = bean.cover;
        song._genre_name = "";
        song._is_favorite = false;
        song.artist_name = bean.artist;
        song.artist_art = bean.cover;
        song.album_name = "";
        song.album_art = "";
        song.isPlaying = false;

        LogUtils.printLog(song.toString());
        return song;
    }
}
