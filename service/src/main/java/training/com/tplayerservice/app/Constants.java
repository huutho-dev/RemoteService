package training.com.tplayerservice.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import training.com.tplayerservice.R;

/**
 * Created by hnc on 18/05/2017.
 */

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "com.marothiatechs.customnotification.action.main";
        public static String INIT_ACTION = "com.marothiatechs.customnotification.action.init";
        public static String PREV_ACTION = "com.marothiatechs.customnotification.action.prev";
        public static String PLAY_ACTION = "com.marothiatechs.customnotification.action.play";
        public static String NEXT_ACTION = "com.marothiatechs.customnotification.action.next";
        public static String STARTFOREGROUND_ACTION = "com.marothiatechs.customnotification.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.marothiatechs.customnotification.action.stopforeground";

    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

    public static Bitmap getDefaultAlbumArt(Context context) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bm = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.default_album_art, options);
        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }

    public static Bitmap getImageNoti(Context context,String path) {
        if (!TextUtils.isEmpty(path)){
            Bitmap bm = null;
            try {

                bm = BitmapFactory.decodeFile(path);
            } catch (Error ee) {
            } catch (Exception e) {
            }
            return bm;
        }else {
            return getDefaultAlbumArt(context);
        }
    }
}
