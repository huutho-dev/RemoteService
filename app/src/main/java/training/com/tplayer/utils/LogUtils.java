package training.com.tplayer.utils;

import android.util.Log;

/**
 * Created by ThoNH on 24/03/2017.
 */

public class LogUtils {
    private static final boolean DEBUG = true;
    private static final String TAG = "[ThoNH]";


    public static void printLog(String msg) {
        if (DEBUG)
            Log.println(Log.ERROR, TAG, msg);
    }

    public static void printLogDetail(String msg) {
        StackTraceElement element = new Exception().getStackTrace()[1];
        String className = element.getClassName();
        String methodName = element.getMethodName();
        int line = element.getLineNumber();

        String tag = className + "-" + methodName + line + "-" + TAG;
        Log.println(Log.ERROR, tag, msg);
    }

    public static void printLogElement(Object... msgs) {
        String message;
        if (msgs != null) {
            if (msgs.length == 1) {
                message = msgs[0].toString();
            } else {
                StringBuilder sb = new StringBuilder();
                for (Object m : msgs) {
                    sb.append(m.toString());
                    sb.append("\n");
                }
                message = sb.toString();
            }
            if (DEBUG)
                Log.println(Log.ERROR, TAG, message);
        }
    }
}
