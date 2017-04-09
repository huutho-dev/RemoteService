package training.com.tplayer.network.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hnc on 05/04/2017.
 */

public class RetrofitBuilderHelper {

    public static Retrofit getRetrofitBuilder(String baseUrl) {
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return builder;
    }

}
