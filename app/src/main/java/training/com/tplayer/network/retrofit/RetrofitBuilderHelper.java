package training.com.tplayer.network.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hnc on 05/04/2017.
 */

public class RetrofitBuilderHelper {

    public static Retrofit getRetrofitBuilder() {

        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit builder = new Retrofit.Builder()
                .baseUrl("http://mp3.zing.vn")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
        return builder;
    }

    public static Retrofit getLrc(){
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("http://static.mp3.zdn.vn/lyrics/") // REMEMBER TO END with /
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        return retrofit;
    }

}
