package training.com.tplayer.utils.preferences;

import android.os.Build;

/**
 * Created by ThoNH on 12/04/2017.
 */

public class AndroidApiUtils {
    public boolean isLaterLolipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
