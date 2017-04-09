package training.com.tplayerservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by HuuTho on 4/4/2017.
 */

public class StartServiceWhenCompleteBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, TPlayerService.class));
    }
}
