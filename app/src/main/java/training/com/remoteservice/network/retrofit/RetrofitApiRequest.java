package training.com.remoteservice.network.retrofit;

import retrofit2.http.GET;

/**
 * Created by hnc on 05/04/2017.
 */

public interface RetrofitApiRequest {
    @GET("")
    void getInfoSong();
}
