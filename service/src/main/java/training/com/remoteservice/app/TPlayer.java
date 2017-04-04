package training.com.remoteservice.app;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by HuuTho on 4/4/2017.
 * <p>
 * Reference : https://developer.android.com/reference/android/media/MediaPlayer.html
 */

public class TPlayer extends MediaPlayer {
    public TPlayer(Context context) {
        setScreenOnWhilePlaying(true);
        setWakeMode(context,);
    }


    /**
     *  After reset(), the object is like being just created.
     */
    public void resetPlayer(){
        super.reset();
    }


    /**
     * After release(), the object is no longer available.
     */
    public void releasePlayer(){
        super.release();
    }


}
