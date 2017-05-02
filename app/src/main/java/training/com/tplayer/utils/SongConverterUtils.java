package training.com.tplayer.utils;

import com.remote.communication.MediaEntity;

import training.com.tplayer.ui.entity.SongOnlineEntity;

/**
 * Created by ThoNH on 17/04/2017.
 */

public class SongConverterUtils {
    public static MediaEntity convert(SongOnlineEntity onlineEntity) {
        SongOnlineEntity.DataBean bean = onlineEntity.getData().get(0);
        MediaEntity song = new MediaEntity();
        song.id = 0;
        song.data = bean.source_list.get(0);
        song.displayName = bean.name + "mp3";
        song.title = bean.name;
        song.lyric = bean.lyric;
        song.art = bean.cover;
        song.isFavorite = false;
        song.artist = bean.artist;
        song.art = bean.cover;
        song.album = "";
        song.isPlaying = false;
        song.dateAdded = (int) System.currentTimeMillis();

        return song;
    }
}
