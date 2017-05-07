package training.com.tplayer.utils.preferences;

/**
 * Created by ThoNH on 5/8/2017.
 */

public class PlayerPreference extends BasePreferences {

    private final String KEY_SHUFFLE = "key.shuffle";
    private final String KEY_REPEAT = "key.repeat";

    private static PlayerPreference mInstance;

    public static PlayerPreference getInstance() {

        if (mInstance == null) {
            mInstance = new PlayerPreference();
        }
        return mInstance;
    }

    private PlayerPreference() {
        super("PlayerPreference");
    }

    public void setPlayerShuffle(boolean isShuffle) {
        getEditor().putBoolean(KEY_SHUFFLE, isShuffle).commit();
    }

    public boolean getPlayerShuffle() {
        return getSharePreferences().getBoolean(KEY_SHUFFLE, false);
    }

    public void setPlayerRepeat(int repeatType) {
        /*0 : norepeat /*
        /*1 : repeat one */
        /*2 : repeat all */

        if (repeatType == 0 || repeatType == 1 || repeatType == 2) {
            getEditor().putInt(KEY_REPEAT, repeatType).commit();
        } else {
            try {
                throw new Exception("Invalid repeat type");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getPlayerRepeat() {
        return getSharePreferences().getInt(KEY_REPEAT, 0);
    }
}
