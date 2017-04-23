package training.com.tplayer.utils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by ThoNH on 4/22/2017.
 */

public class DateTimeUtils {

    public static String formatMinuteSecond(int millis){
       return String.format(Locale.getDefault(),"%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
