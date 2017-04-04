package huutho.com.remoteservice.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import training.com.remoteservice.IMyAidlInterface;
import training.com.remoteservice.ui.entity.Book;

public class RemoteService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("huutho","onStartCommand");
        return START_STICKY;
    }

    private IMyAidlInterface.Stub iBinder = new IMyAidlInterface.Stub() {
        @Override
        public void setBook(Book book) throws RemoteException {
            Log.e("huutho", book.toString());
        }
    };

}
