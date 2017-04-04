package training.com.remoteservice.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;

import training.com.remoteservice.IMyAidlInterface;
import training.com.remoteservice.R;
import training.com.remoteservice.app.RemoteService;
import training.com.remoteservice.ui.entity.Book;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface mRemoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IMyAidlInterface.Stub.asInterface(service);

            try {
                mRemoteService.setBook(new Book("Defug111", "Defuck222", "Defuck222"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteService = null;
        }
    };

}
