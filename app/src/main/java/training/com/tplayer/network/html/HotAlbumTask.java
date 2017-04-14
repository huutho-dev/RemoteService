package training.com.tplayer.network.html;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.network.html.base.BaseAsyncTask;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.ui.entity.HotAlbumEntity;
import training.com.tplayer.utils.LogUtils;
import training.com.tplayer.utils.ZingHtmlUtils;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class HotAlbumTask extends BaseAsyncTask<HotAlbumEntity> {

    private final String TAG_ALBUM_HOT = "div#albumHot";
    private final String TAG_ALBUM_HOT_ROW = "div.row";
    private final String TAG_ALBUM_HOT_ROW_ITEM = "div.album-item.des-inside.otr.col-3";

    public HotAlbumTask(IOnLoadSuccess listener) {
        super("http://mp3.zing.vn/", listener);
    }


    @Override
    protected List<HotAlbumEntity> doInBackground(Void... params) {
        super.doInBackground(params);

        List<HotAlbumEntity> entities = new ArrayList<>();

        Elements albumHot = document.select(TAG_ALBUM_HOT);
        Elements rows = albumHot.select(TAG_ALBUM_HOT_ROW);
        int rowsSize = rows.size();

        for (int i = 0; i < rowsSize; i++) {

            Elements rowItems = rows.get(i).select(TAG_ALBUM_HOT_ROW_ITEM);

            for (int j = 0; j < rowItems.size(); j++) {
                String href = rowItems.get(j).select("a").attr("href");
                String title = rowItems.get(j).select("a").attr("title");
                String src = rowItems.get(j).select("img").attr("src");

                String linkAlbum = ZingHtmlUtils.subStringAlbum(href);
                String name = ZingHtmlUtils.splitsNameAndArtist(title)[0];
                String artist = ZingHtmlUtils.splitsNameAndArtist(title)[1];

                HotAlbumEntity entity = new HotAlbumEntity();
                entity.artist = artist ;
                entity.name = name;
                entity.image = src;
                entity.link = linkAlbum;

                entities.add(entity);
            }
        }
        return entities;
    }
}
