package training.com.tplayer.network.html;

import android.text.TextUtils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.network.html.base.BaseAsyncTask;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.ui.entity.DataCodeEntity;

/**
 * Created by ThoNH on 17/04/2017.
 */

public class GetDataCodeTask extends BaseAsyncTask<DataCodeEntity> {

    public GetDataCodeTask(String url, IOnLoadSuccess listener, String tag) {
        super(url, listener, tag);
    }

    @Override
    protected List<DataCodeEntity> doInBackground(Void... params) {
        super.doInBackground(params);

        List<DataCodeEntity> entities = new ArrayList<>();

        Elements list = document.select("div#playlistItems > ul > li");
        if (list.hasText())
        for (Element element : list) {
            String dataCode = element.attr("data-code");
            if ( dataCode!=null &&!TextUtils.isEmpty(dataCode))
            entities.add(new DataCodeEntity(dataCode));
        }


        String dataCode = document.select("a#tabService").attr("data-code");
        if ( dataCode!=null &&!TextUtils.isEmpty(dataCode))
        entities.add(new DataCodeEntity(dataCode));
        return entities;
    }
}
