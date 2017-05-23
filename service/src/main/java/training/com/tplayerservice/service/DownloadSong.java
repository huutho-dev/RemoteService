package training.com.tplayerservice.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by hnc on 23/05/2017.
 */

public class DownloadSong extends IntentService {

    public DownloadSong() {
        super("DownloadSong");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }
}
