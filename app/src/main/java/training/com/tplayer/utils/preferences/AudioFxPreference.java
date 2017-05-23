package training.com.tplayer.utils.preferences;

/**
 * Created by hnc on 17/05/2017.
 */

public class AudioFxPreference extends BasePreferences {

    private final String KEY_PRESET_REVERB = "key.preset.reverb";
    private final String KEY_BASS_BOOST = "key.bass.boost";
    private final String KEY_VIRTUALIZER = "key.virtualizer";
    private final String KEY_EQUALIZER = "key.equalizer";

    private static AudioFxPreference mInstance;

    public static synchronized AudioFxPreference getInstance() {
        if (mInstance == null) {
            mInstance = new AudioFxPreference();
        }
        return mInstance;
    }

    private AudioFxPreference() {
        super("AudioFxPreference");
    }

    public void setEnablePresetReverb(boolean isEnable) {
        getEditor().putBoolean(KEY_PRESET_REVERB, isEnable).commit();
    }

    public boolean isEnablePresetReverb() {
        return getSharePreferences().getBoolean(KEY_PRESET_REVERB, false);
    }

    public void setEnableBassBoost(boolean isEnable) {
        getEditor().putBoolean(KEY_BASS_BOOST, isEnable).commit();
    }

    public boolean isEnableBassBoost() {
        return getSharePreferences().getBoolean(KEY_BASS_BOOST, false);
    }

    public void setEnableVirtualizer(boolean isEnable) {
        getEditor().putBoolean(KEY_VIRTUALIZER, isEnable).commit();
    }

    public boolean isEnableVirtualizer() {
        return getSharePreferences().getBoolean(KEY_VIRTUALIZER, false);
    }

    public void setEnableEqualizer(boolean isEnable) {
        getEditor().putBoolean(KEY_EQUALIZER, isEnable).commit();
    }

    public boolean isEnableEqualizer() {
        return getSharePreferences().getBoolean(KEY_EQUALIZER, false);
    }
}
