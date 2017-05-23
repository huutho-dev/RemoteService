package training.com.tplayer.ui.player;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

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

        void onResumePlayer(MediaEntity song, int currentPosition);

    }

    public interface Presenter extends BasePresenter {

        void setPlayLists(List<MediaEntity> playLists);

        boolean playPause();

        void forward();

        void backward();

        void repeat(int repeatType);

        void setShuffle(boolean isShuffle);

        void setVolume(float volume);

        List<MediaEntity> getNowPlaylist();

        void seekToPosition(int value);

        int getCurrentPosition();

        int getDuration();

        void startSongPosition(int position);

        void onUnregisterBroadcast();

        void onDownloadClick(Context context);

        void onEqualizerClick(AppCompatActivity activity);

        void onCaptureScreenClick(AppCompatActivity activity);
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
