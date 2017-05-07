package training.com.tplayer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ThoNH on 5/7/2017.
 */

public class NetworkUtils {
    public static boolean isDeviceOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED);

    }


    public static boolean isConnectedWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null
                && networkInfo.isConnected()
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isConnectedMobieNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null
                && networkInfo.isConnected()
                && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }
    
}
