package training.com.tplayer.ui.player;

import com.remote.communication.MediaEntity;

import java.io.File;
import java.util.List;

import training.com.tplayer.base.mvp.BaseInteractor;
import training.com.tplayer.base.mvp.BasePresenter;
import training.com.tplayer.base.mvp.BaseView;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerContracts {
    public interface View extends BaseView {

        void onRemotePlayNewSong(MediaEntity song);

        void onBufferPlaySong(int percent);

        void onRemotePlayCompleteASong();

        void onLoadLyricComplete(File lyric);
    }

    public interface Presenter extends BasePresenter {

        void setPlayLists(List<MediaEntity> playLists);

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

        void onRemotePlayNewSong(MediaEntity song);

        void onBufferPlayNewSong(int percent);

        void onRemoteCompleteASong();

        void onDownloadLyricComplete(File lyric);
    }
}
