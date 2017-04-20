package training.com.tplayerservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.remote.communication.IMyAidlInterface;
import com.remote.communication.Song;

import java.util.List;


public class TPlayerService extends Service {
    public static final String TAG = "[ThoNH]";

    private PlayerManager mPlayerManager;

    public TPlayerService() {
        Log.e(TAG, "Contructor");
        mPlayerManager = new PlayerManager(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onStartCommand");
        super.onDestroy();
    }


    private IMyAidlInterface.Stub iBinder = new IMyAidlInterface.Stub() {

        @Override
        public void setSong(Song song) throws RemoteException {
            mPlayerManager.playSong(song);
        }

        @Override
        public void setPlayList(List<Song> playLists) throws RemoteException {
            mPlayerManager.setListSong(playLists);
        }

        @Override
        public int getDuration() throws RemoteException {
            return mPlayerManager.getDuration();
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return mPlayerManager.getCurrentPosition();
        }

        @Override
        public void setPosition(int value) throws RemoteException {
            mPlayerManager.seek(value);
        }

        @Override
        public void forward() throws RemoteException {
            mPlayerManager.nextSong();
        }

        @Override
        public void backward() throws RemoteException {
            mPlayerManager.previousSong();
        }

        @Override
        public void setVolume(float value) throws RemoteException {
            mPlayerManager.setVolume(value);
        }

        @Override
        public boolean playPause() throws RemoteException {
           return mPlayerManager.changePlayStatus();
        }

        @Override
        public void repeat() throws RemoteException {

        }
    };
}
