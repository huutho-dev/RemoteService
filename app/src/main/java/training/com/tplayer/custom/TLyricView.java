package training.com.tplayer.custom;

import android.content.Context;
import android.util.AttributeSet;

import java.io.File;
import java.io.IOException;

import me.zhengken.lyricview.LyricView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import training.com.tplayer.network.retrofit.RetrofitApiRequest;
import training.com.tplayer.network.retrofit.RetrofitBuilderHelper;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by hnc on 18/04/2017.
 */

public class TLyricView extends LyricView {
    public TLyricView(Context context) {
        super(context);
    }

    public TLyricView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TLyricView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setLyricUrl(String url) {
        Retrofit retrofit = RetrofitBuilderHelper.getLrc();
        RetrofitApiRequest retrofitApiRequest = retrofit.create(RetrofitApiRequest.class);
        Call<ResponseBody> call = retrofitApiRequest.download(url.substring(32));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = new String(response.body().bytes());
                    LogUtils.printLog(str);

                    File file = new File(str);
                    file.createNewFile();
                    setLyricFile(file,"UTF-8");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

}
