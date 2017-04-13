package training.com.tplayer.network.html.base;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by hnc on 05/04/2017.
 */

public abstract class BaseAsyncTask<E extends BaseEntity> extends AsyncTask<Void, Void, List<E>> {

    private IOnLoadSuccess<E> listener;
    public Document document;
    private String url;

    public BaseAsyncTask(String url, IOnLoadSuccess listener) {
        this.listener = listener;
        this.url = url;
    }

    @Override
    protected List<E> doInBackground(Void... params) {
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(List<E> es) {
        if (es != null)
            listener.onResponse(es);
        onCancelled();
    }
}
