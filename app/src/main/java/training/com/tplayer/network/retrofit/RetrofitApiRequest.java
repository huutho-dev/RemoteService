package training.com.tplayer.network.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import training.com.tplayer.ui.entity.SearchOnlEntity;
import training.com.tplayer.ui.entity.SongOnlineEntity;

/**
 * Created by ThoNH on 05/04/2017.
 */

public interface RetrofitApiRequest {
    @GET("json/song/get-source/{dataCode}")
    Call<SongOnlineEntity> getDataSource(@Path("dataCode") String dataCode);

    @GET
    Call<ResponseBody> download(@Url String fileUrl);

    @GET("/complete/desktop?type=album,song&num=5")
    Call<SearchOnlEntity> getDataSearch(@Query("query") String query);
}
