package training.com.tplayerservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.remote.communication.IMyAidlInterface;
import com.remote.communication.MediaEntity;

import java.util.List;

import training.com.utils.LogUtils;


public class TPlayerService extends Service {
    public static final String TAG = "[ThoNH]";

    private PlayerManager mPlayerManager;

    public TPlayerService() {
        LogUtils.printLog("Service_Contructor");
        mPlayerManager = new PlayerManager(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.printLog("Service_onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.printLog("Service_onBind");
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.printLog("Service_onStartCommand");
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.printLog("Service_onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        LogUtils.printLog("Service_onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtils.printLog("Service_onDestroy");
        super.onDestroy();
    }


    private IMyAidlInterface.Stub iBinder = new IMyAidlInterface.Stub() {

        @Override
        public void setSong(MediaEntity song) throws RemoteException {
            LogUtils.printLog("Service_onDestroy");
            mPlayerManager.playSong(song);
        }

        @Override
        public void startSongPosition(int position) throws RemoteException {
            LogUtils.printLog("Service_startSongPosition = " + position);
            mPlayerManager.startSong(position);
        }

        @Override
        public void setPlayList(List<MediaEntity> playLists) throws RemoteException {
            LogUtils.printLog("Service_setPlayList : size = " + playLists.size());
            mPlayerManager.setListSong(playLists);
        }

        @Override
        public int getDuration() throws RemoteException {
            LogUtils.printLog("Service_getDuration = " + getDuration());
            return mPlayerManager.getDuration();
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            LogUtils.printLog("Service_getCurrentPosition = " + mPlayerManager.getCurrentPosition());
            return mPlayerManager.getCurrentPosition();
        }

        @Override
        public void setPosition(int value) throws RemoteException {
            LogUtils.printLog("Service_setPosition = " + value);
            mPlayerManager.seek(value);
        }

        @Override
        public void forward() throws RemoteException {
            LogUtils.printLog("Service_forward");
            mPlayerManager.nextSong();
        }

        @Override
        public void backward() throws RemoteException {
            LogUtils.printLog("Service_backward");
            mPlayerManager.previousSong();
        }

        @Override
        public void setVolume(float value) throws RemoteException {
            LogUtils.printLog("Service_setVolume = " +value);
            mPlayerManager.setVolume(value);
        }

        @Override
        public boolean playPause() throws RemoteException {
            LogUtils.printLog("Service_playPause");
           return mPlayerManager.changePlayStatus();
        }

        @Override
        public void repeat() throws RemoteException {
            LogUtils.printLog("Service_repeat");
        }

        @Override
        public void addNextNowPlaying(MediaEntity entity) throws RemoteException {
            mPlayerManager.addNextNowPlaying(entity);
        }

        @Override
        public void addNowPlaying(MediaEntity entity) throws RemoteException {
            mPlayerManager.addNowPlaying(entity);
        }

        @Override
        public void addListNowPlaying(List<MediaEntity> entities) throws RemoteException {
            mPlayerManager.addNowPlaying(entities);
        }
    };
}
