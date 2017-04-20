package training.com.tplayer.ui.player;

import android.os.RemoteException;

import com.remote.communication.IMyAidlInterface;
import com.remote.communication.Song;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.base.mvp.BasePresenterImpl;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerPresenterImpl extends BasePresenterImpl<PlayerActivity, PlayerInteractorImpl>
        implements PlayerContracts.Presenter, PlayerContracts.IOnPlayerListener {

    private IMyAidlInterface mService ;

    @Override
    public void onSubcireView(PlayerActivity view) {
        this.mView = view;
    }

    @Override
    public void onSubcireInteractor(PlayerInteractorImpl interactor) {
        this.mInteractor = interactor;
        mInteractor.setListener(this);
    }

    @Override
    public void setPlayLists(List<Song>playLists) {
        try {
            mService.setPlayList(playLists);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playPause() {
        try {
            mService.playPause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void forward() {
        try {
            mService.forward();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void backward() {
        try {
            mService.backward();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void repeat() {
        try {
            mService.repeat();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVolume(float volume) {
        try {
            mService.setVolume(volume);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seekToPosition(int value) {
        try {
            mService.setPosition(value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onRemotePlayNewSong(Song song) {
        mView.onRemotePlayNewSong(song);
    }

    @Override
    public void onRemoteCompleteASong() {
        mView.onRemotePlayCompleteASong();
    }

    @Override
    public void onLoadZingComplete(ArrayList<Song> songs) {
        mView.onLoadDataZingComplete(songs);
    }


    @Override
    public void onUnregisterBroadcast() {
        mInteractor.onUnregisterBroadcast();
    }

    public void setService(IMyAidlInterface playerService) {
        this.mService = playerService;
    }
}
