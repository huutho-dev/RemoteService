package training.com.tplayer.network.html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.app.Config;
import training.com.tplayer.network.html.base.BaseAsyncTask;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.ui.entity.AlbumBasicEntity;
import training.com.tplayer.utils.LogUtils;
import training.com.tplayer.utils.ZingHtmlUtils;

/**
 * Created by ThoNH on 4/17/2017.
 */

public class ListAlbumTask extends BaseAsyncTask<AlbumBasicEntity> {

    public final String PANEL = "div.tab-pane > ul.item-list.album-list > li.item";


    // url: http://mp3.zing.vn/the-loai-album/Nhac-Tre/IWZ9Z088.html?view=list&sort=total_play&filter=day


    public ListAlbumTask(String url, IOnLoadSuccess listener, String tag) {
        super(url, listener, tag);
    }

    @Override
    protected List<AlbumBasicEntity> doInBackground(Void... params) {
        super.doInBackground(params);
        List<AlbumBasicEntity> entities = new ArrayList<>();

        if (document != null) {
            Elements items = document.select(PANEL);
            for (Element item : items) {
                String href = item.select("a").attr("href");
                String title = item.select("a").select("img").attr("alt");
                String src = item.select("a").select("img").attr("src");

                if (!href.contains(Config.BASE_ZING)) {
                    href = Config.BASE_ZING + href;
                }

                AlbumBasicEntity entity = new AlbumBasicEntity();
                entity.link = href;
                entity.image = src;
                entity.artist = ZingHtmlUtils.splitsNameAndArtist(title)[1];
                entity.name = ZingHtmlUtils.splitsNameAndArtist(title)[0];
                LogUtils.printLog(entity.toString());
                entities.add(entity);
            }
        }

        return entities;
    }
}
