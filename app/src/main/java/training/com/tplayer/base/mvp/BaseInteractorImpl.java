package training.com.tplayer.base.mvp;

import android.content.Context;

/**
 * Created by HuuTho on 4/9/2017.
 */

public class BaseInteractorImpl implements BaseInteractor {

    public Context mContext ;

    public BaseInteractorImpl(Context context) {
        this.mContext = context ;
    }
}
