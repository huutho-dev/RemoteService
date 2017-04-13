package training.com.tplayer.utils;

import training.com.tplayer.app.Config;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class ZingHtmlUtils {


    //href="/album/Du-Anh-Muon-Single-Yen-Trang/ZOZZWAOF.html#home_albumhot_01"

    public static String subStringAlbum(String href) {
        href = href.substring(0, href.indexOf("#"));
        if (!href.contains(Config.BASE_ZING)) {
            return Config.BASE_ZING + href;
        }
        return href;
    }


    //title="Album Dù Anh Muốn (Single) - Yến Trang"
    public static String[] splitsNameAndArtist(String title) {

        String name = title.substring(0, title.indexOf("-")).trim();
        String artist = title.substring(title.lastIndexOf("-") + 1).trim();

        String[] titles = new String[2];
        titles[0] = name;
        titles[1] = artist;

        return titles;
    }
}
