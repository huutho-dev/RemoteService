package training.com.remoteservice.utils.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import training.com.remoteservice.app.AppController;


/**
 * Created by ThoNH on 24/03/2017.
 */

public abstract class BasePreferences {
    private SharedPreferences mSharePreferences;

    public BasePreferences(String preferName) {
        mSharePreferences = AppController.getInstance().getSharedPreferences(preferName, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditor() {
        return mSharePreferences.edit();
    }

    public SharedPreferences getSharePreferences() {
        return mSharePreferences;
    }

}
