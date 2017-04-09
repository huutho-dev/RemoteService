package training.com.tplayer.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class AppController extends Application {

    private static AppController mInstance;

    /**
     * @return application context
     */
    public static synchronized AppController getInstance() {
        if (mInstance == null) {
            mInstance = new AppController();
        }
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}
