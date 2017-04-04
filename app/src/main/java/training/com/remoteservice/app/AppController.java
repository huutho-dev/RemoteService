package training.com.remoteservice.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class AppController extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
