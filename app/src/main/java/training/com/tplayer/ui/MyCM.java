package training.com.tplayer.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.imangazaliev.circlemenu.CircleMenu;

/**
 * Created by ThoNH on 14/04/2017.
 */

public class MyCM extends CircleMenu {
    private OnFinish onFinish;

    public MyCM(Context context) {
        super(context);
    }

    public MyCM(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCM(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onExitAnimationFinished() {
        super.onExitAnimationFinished();
        if (onFinish != null) {
            onFinish.finish();
        }
    }

    public void addOnFinish(OnFinish onFinish) {
        this.onFinish = onFinish;
    }

    public interface OnFinish {
        void finish();
    }
}
