package training.com.tplayerservice.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import training.com.tplayerservice.TPlayerService;

/**
 * Created by HuuTho on 4/4/2017.
 */

public class StartServiceWhenCompleteBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, TPlayerService.class));
    }
}
