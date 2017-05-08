package training.com.tplayer.ui.player;

import android.os.RemoteException;

import com.remote.communication.IMyAidlInterface;
import com.remote.communication.MediaEntity;

import java.io.File;
import java.util.List;

import training.com.tplayer.base.mvp.BasePresenterImpl;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerPresenterImpl extends BasePresenterImpl<PlayerActivity, PlayerInteractorImpl>
        implements PlayerContracts.Presenter, PlayerContracts.IOnPlayerListener {

    private IMyAidlInterface mService;

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
    public void setPlayLists(List<MediaEntity> playLists) {
        try {
            mService.setPlayList(playLists);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean playPause() {
        try {
            return mService.playPause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
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
    public void repeat(int repeatType) {
        try {
            mService.repeat(repeatType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setShuffle(boolean isShuffle) {
        try {
            mService.setShuffle(isShuffle);
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
    public List<MediaEntity> getNowPlaylist() {
        try {
           return mService.getPlaylist();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
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
    public int getCurrentPosition() {
        try {
            return mService.getCurrentPosition();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getDuration() {
        try {
            return mService.getDuration();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void startSongPosition(int position) {
        try {
            mService.startSongPosition(position);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRemotePlayNewSong(MediaEntity song) {
        mView.onRemotePlayNewSong(song);
    }

    @Override
    public void onBufferPlayNewSong(int percent) {
        LogUtils.printLog("percen = " + percent);
        mView.onBufferPlaySong(percent);
    }

    @Override
    public void onRemoteCompleteASong() {
        mView.onRemotePlayCompleteASong();
    }


    @Override
    public void onDownloadLyricComplete(File lyric) {
        mView.onLoadLyricComplete(lyric);
    }


    @Override
    public void onUnregisterBroadcast() {
        mInteractor.onUnregisterBroadcast();
    }


    public void setService(IMyAidlInterface playerService) {
        this.mService = playerService;
    }
}
