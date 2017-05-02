package training.com.tplayer.network.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.remote.communication.MediaEntity;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import training.com.tplayer.network.retrofit.RetrofitApiRequest;
import training.com.tplayer.network.retrofit.RetrofitBuilderHelper;
import training.com.tplayer.ui.entity.DataCodeEntity;
import training.com.tplayer.ui.entity.SongOnlineEntity;
import training.com.tplayer.utils.LogUtils;
import training.com.tplayer.utils.SongConverterUtils;

/**
 * Created by ThoNH on 18/04/2017.
 */

public class LoadListDataCodeService extends IntentService {
    public static final String EXTRA_DATA_CODE = "extra.data.code";
    public static final String EXTRA_CALL_BACK = "extra.call.back";
    public static final String ACTION_CALL_BACK_SONG = "training.com.tplayer.action.call.back.song";

    private ArrayList<MediaEntity> songs = new ArrayList<>();

    private RetrofitApiRequest retrofitApiRequest;

    public LoadListDataCodeService() {
        super("LoadDataCodeFromZing");
        Retrofit retrofit = RetrofitBuilderHelper.getRetrofitBuilder();
        retrofitApiRequest = retrofit.create(RetrofitApiRequest.class);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtils.printLog("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LogUtils.printLog("onHandleIntent");
        if (intent != null) {
            ArrayList<DataCodeEntity> dataCodeEntities = intent.getParcelableArrayListExtra(EXTRA_DATA_CODE);
            for (DataCodeEntity dataCodeEntity : dataCodeEntities) {
                LogUtils.printLog(dataCodeEntity.dataCode + " -") ;

                if (dataCodeEntity.dataCode != null && !TextUtils.isEmpty(dataCodeEntity.dataCode)){
                    Call<SongOnlineEntity> call = retrofitApiRequest.getDataSource(dataCodeEntity.dataCode);
                    try {
                        Response<SongOnlineEntity> execute = call.execute();
                        MediaEntity song = SongConverterUtils.convert(execute.body());
                        songs.add(song);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    @Override
    public void onDestroy() {
        Intent callBackIntent = new Intent();
        callBackIntent.putExtra(EXTRA_CALL_BACK, songs);
        callBackIntent.setAction(ACTION_CALL_BACK_SONG);
        LocalBroadcastManager.getInstance(LoadListDataCodeService.this).sendBroadcast(callBackIntent);
        LogUtils.printLog("onDestroy");
        super.onDestroy();
    }
}
