package training.com.tplayer.network.html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.network.html.base.BaseAsyncTask;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.ui.entity.HotNewEntity;
import training.com.tplayer.utils.ZingHtmlUtils;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class HotNewTask extends BaseAsyncTask<HotNewEntity> {
    public static final String TAG = "HotNewTask";
    private final String TAG_NEW_TRACK = "div#viet-new-song";
    private final String TAG_NEW_TRACK_ITEM = "div.list-item.tool-song-hover.style2 > ul > li";

    public HotNewTask(IOnLoadSuccess listener) {
        super("http://mp3.zing.vn/", listener, TAG);
    }

    @Override
    protected List<HotNewEntity> doInBackground(Void... params) {
        super.doInBackground(params);

        List<HotNewEntity> entities = new ArrayList<>();

        if (document != null) {
            Element hotSongs = document.select(TAG_NEW_TRACK).first();
            Elements items = hotSongs.select(TAG_NEW_TRACK_ITEM);

            for (Element element : items) {
                String data_id = element.attr("data-id");
                String data_code = element.attr("data-code");
                String href = element.select("a").attr("href");
                String title = element.select("a").attr("title");
                String src = element.select("img").attr("src");

                String link = ZingHtmlUtils.subStringAlbum(href);
                String name = ZingHtmlUtils.splitsNameAndArtist(title)[0];
                String artist = ZingHtmlUtils.splitsNameAndArtist(title)[1];

                HotNewEntity hotSongOnlEntity = new HotNewEntity();
                hotSongOnlEntity.artist = artist;
                hotSongOnlEntity.data_code = data_code;
                hotSongOnlEntity.data_id = data_id;
                hotSongOnlEntity.link = link;
                hotSongOnlEntity.title = name;
                hotSongOnlEntity.image = src;

                entities.add(hotSongOnlEntity);
            }
        }


        return entities;
    }
}
