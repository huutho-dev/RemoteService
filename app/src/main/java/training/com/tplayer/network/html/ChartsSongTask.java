package training.com.tplayer.network.html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.network.html.base.BaseAsyncTask;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.ui.entity.BasicSongOnlEntity;
import training.com.tplayer.utils.ZingHtmlUtils;

/**
 * Created by hnc on 14/04/2017.
 */

public class ChartsSongTask extends BaseAsyncTask<BasicSongOnlEntity> {

    private final String TAG_LIST = "div.wrapper-page > div.wrap-body.page-bxh.container > div.wrap-fullwidth-content";
    private final String TAG_LIST_DETAIL = "div.box-chart-ov.bordered.non-bg-rank > ul > li";

    public ChartsSongTask(IOnLoadSuccess listener) {
        super("http://mp3.zing.vn/bang-xep-hang/index.html", listener);
    }

    @Override
    protected List<BasicSongOnlEntity> doInBackground(Void... params) {
        super.doInBackground(params);
        List<BasicSongOnlEntity> entities = new ArrayList<>();


        Elements root = document.select(TAG_LIST).select("div.row");
        Element row2 = root.get(1);


        // Get charts Vietnam
        Element elementsVn = row2.select("div.col-4").get(0);
        Elements vn = elementsVn.select(TAG_LIST_DETAIL);

        for (int i = 0; i < vn.size(); i++) {

            Elements element = vn.get(i).select("h3.song-name.ellipsis > a");

            String href = element.attr("href");
            String title = element.attr("title");

            String name = ZingHtmlUtils.splitsNameAndArtist2(title)[0];
            String artist = ZingHtmlUtils.splitsNameAndArtist2(title)[1];

            BasicSongOnlEntity basicSongOnlEntity = new BasicSongOnlEntity();
            basicSongOnlEntity.order = (i + 1);
            basicSongOnlEntity.title = name;
            basicSongOnlEntity.artist = artist;
            basicSongOnlEntity.link = href;

            entities.add(basicSongOnlEntity);

        }


        // Get charts national
        Element elementsNational = row2.select("div.col-4").get(1);
        Elements national = elementsNational.select(TAG_LIST_DETAIL);

        for (int i = 0; i < national.size(); i++) {

            Elements element = national.get(i).select("h3.song-name.ellipsis > a");

            String href = element.attr("href");
            String title = element.attr("title");

            String name = ZingHtmlUtils.splitsNameAndArtist(title)[0];
            String artist = ZingHtmlUtils.splitsNameAndArtist(title)[1];

            BasicSongOnlEntity basicSongOnlEntity = new BasicSongOnlEntity();
            basicSongOnlEntity.order = (i + 1);
            basicSongOnlEntity.title = name;
            basicSongOnlEntity.artist = artist;
            basicSongOnlEntity.link = href;

            entities.add(basicSongOnlEntity);

        }


//    list : 1,2,3,4,5,6,7,8,9,10 - 1,2,3,4,5,6,7,8,9,10

        return entities;
    }
}
