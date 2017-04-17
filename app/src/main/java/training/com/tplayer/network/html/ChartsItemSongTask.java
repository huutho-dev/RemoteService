package training.com.tplayer.network.html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.network.html.base.BaseAsyncTask;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.ui.entity.HotSongOnlEntity;
import training.com.tplayer.utils.ZingHtmlUtils;

/**
 * Created by hnc on 14/04/2017.
 */

public class ChartsItemSongTask extends BaseAsyncTask<HotSongOnlEntity> {
    public static final String TAG = "ChartsItemSongTask";

    private final String TABLE = "div.table-body > ul > li";

    public ChartsItemSongTask(String url, IOnLoadSuccess listener) {
        super(url, listener, TAG);
    }

    @Override
    protected List<HotSongOnlEntity> doInBackground(Void... params) {
        super.doInBackground(params);
        List<HotSongOnlEntity> entities = new ArrayList<>();

        Elements table = document.select(TABLE);
        for (int i = 0; i < table.size(); i++) {
            Element element = table.get(i);
            String data_id = element.attr("data-id");
            String data_code = element.attr("data-code");
            String src = element.select("div.e-item").select("img").attr("src");
            String title = element.select("div.e-item").select("a").attr("title");
            String link = element.select("div.e-item").select("a").attr("href");

            HotSongOnlEntity entity = new HotSongOnlEntity();
            entity.order = i + 1;
            entity.image = src;
            entity.data_id = data_id;
            entity.data_code = data_code;
            entity.link = link;
            entity.title = ZingHtmlUtils.splitsNameAndArtist(title)[0];
            entity.artist = ZingHtmlUtils.splitsNameAndArtist(title)[1];

            entities.add(entity);
        }

        return entities;
    }
}
