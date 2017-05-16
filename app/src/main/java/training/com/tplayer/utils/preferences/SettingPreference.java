package training.com.tplayer.utils.preferences;

/**
 * Created by hnc on 16/05/2017.
 */

public class SettingPreference extends BasePreferences {

    private final String LANGUAGE_VI = "vi";
    private final String LANGUAGE_JPN = "jpn";
    private final String LANGUAGE_ENG = "en";

    private final String KEY_LANGUAGE = "setting.language";
    private final String KEY_DOWNLOAD_OPT = "setting.download.opt";
    private final String KEY_SHAKE = "setting.shake";
    private final String KEY_DISABLE_HEADSET = "setting.disable.heaset";
    private final String KEY_ENABLE_HEADSET = "setting.enable.headset";
    private final String KEY_OTHER_SOUND = "setting.other.sound";


    private static SettingPreference mInstance;

    public static synchronized SettingPreference getInstance() {
        if (mInstance == null) {
            mInstance = new SettingPreference();
        }
        return mInstance;
    }


    private SettingPreference() {
        super("Setting");
    }


    public void setLanguage(String language) {
        getEditor().putString(KEY_LANGUAGE, language).commit();
    }

    public String getLanguage() {
        return getSharePreferences().getString(KEY_LANGUAGE, LANGUAGE_ENG);
    }


    public void setDownloadOnlyOverWifi(boolean isSet) {
        getEditor().putBoolean(KEY_DOWNLOAD_OPT, false).commit();
    }

    public boolean getDownloadOnlyOverWifi(){
        return getSharePreferences().getBoolean(KEY_DOWNLOAD_OPT,false);
    }

    public void setShake(boolean isSet) {
        getEditor().putBoolean(KEY_SHAKE, false).commit();
    }

    public boolean getShake(){
        return getSharePreferences().getBoolean(KEY_SHAKE,false);
    }

    public void setDisableHeadser(boolean isSet) {
        getEditor().putBoolean(KEY_DISABLE_HEADSET, false).commit();
    }

    public boolean getDisableHeadset(){
        return getSharePreferences().getBoolean(KEY_DISABLE_HEADSET,false);
    }

    public void setEnableHeadset(boolean isSet) {
        getEditor().putBoolean(KEY_ENABLE_HEADSET, false).commit();
    }

    public boolean getEnableHeadset(){
        return getSharePreferences().getBoolean(KEY_ENABLE_HEADSET,false);
    }

    public void setOtherSoundComming(boolean isSet) {
        getEditor().putBoolean(KEY_OTHER_SOUND, false).commit();
    }

    public boolean getOtherSoundComming(){
        return getSharePreferences().getBoolean(KEY_OTHER_SOUND,false);
    }
}
