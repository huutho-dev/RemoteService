package training.com.tplayer.ui.player;

import com.remote.communication.Song;

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

        void onRemotePlayCompleteASong();

        void onLoadDataZingComplete(ArrayList<Song> songs);


    }

    public interface Presenter extends BasePresenter {

        void setPlayLists(List<Song> playLists);

        void playPause();

        void forward();

        void backward();

        void repeat();

        void setVolume(float volume);

        void seekToPosition(int value);

        void onUnregisterBroadcast();
    }

    public interface Interactor extends BaseInteractor {
        void onUnregisterBroadcast();
    }


    public interface IOnPlayerListener {

        void onRemotePlayNewSong(Song song);

        void onRemoteCompleteASong();

        void onLoadZingComplete(ArrayList<Song> songs);
    }
}
