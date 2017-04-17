package training.com.tplayer.network.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import training.com.tplayer.ui.entity.SongOnlineEntity;

/**
 * Created by hnc on 05/04/2017.
 */

public interface RetrofitApiRequest {
    @GET("/json/song/get-source/{dataCode}")
    Call<SongOnlineEntity> getDataSource(@Path("dataCode")  String dataCode);
}
