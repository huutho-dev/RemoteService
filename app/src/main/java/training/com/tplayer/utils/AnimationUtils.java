package training.com.tplayer.utils;

import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * Created by ThoNH on 4/25/2017.
 */

public class AnimationUtils {
    private static void  setTranslateFromLeftAnim(View view, long duration){
        TranslateAnimation translateAnimation = new TranslateAnimation(400,view.getX(),view.getY(),view.getY());
        view.startAnimation(translateAnimation);
        translateAnimation.setDuration(duration);
    }
}
