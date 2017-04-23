package training.com.tplayer.ui.player;

import com.remote.communication.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.base.mvp.BaseInteractor;
import training.com.tplayer.base.mvp.BasePresenter;
import training.com.tplayer.base.mvp.BaseView;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerContracts {
    public interface View extends BaseView {

        void onRemotePlayNewSong(Song song);

        void onBufferPlaySong(int percent);

        void onRemotePlayCompleteASong();

        void onLoadDataZingComplete(ArrayList<Song> songs);

        void onDownloadLyricComplete(File lyric);
    }

    public interface Presenter extends BasePresenter {

        void setPlayLists(List<Song> playLists);

        boolean playPause();

        void forward();

        void backward();

        void repeat();

        void setVolume(float volume);

        void seekToPosition(int value);

        int getCurrentPosition();

        int getDuration();

        void startSongPosition(int position);

        void onUnregisterBroadcast();
    }

    public interface Interactor extends BaseInteractor {
        void onUnregisterBroadcast();
    }


    public interface IOnPlayerListener {

        void onRemotePlayNewSong(Song song);

        void onBufferPlayNewSong(int percent);

        void onRemoteCompleteASong();

        void onLoadZingComplete(ArrayList<Song> songs);

        void onDownloadLyricComplete(File lyric);
    }
}
