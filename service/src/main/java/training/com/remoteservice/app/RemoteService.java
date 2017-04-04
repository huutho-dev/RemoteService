package training.com.remoteservice.app;

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
        // return START_STICKY to automatic restart service after service has been destroy
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private IMyAidlInterface.Stub iBinder = new IMyAidlInterface.Stub() {
        @Override
        public void setBook(Book book) throws RemoteException {
            Log.e("huutho", book.toString());
        }
    };
}
