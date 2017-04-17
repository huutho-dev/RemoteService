package training.com.tplayer.ui.player;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.remote.communication.Song;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhengken.lyricview.LyricView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.network.retrofit.RetrofitApiRequest;
import training.com.tplayer.network.retrofit.RetrofitBuilderHelper;
import training.com.tplayer.ui.adapter.PlayListInPlayerAdapter;
import training.com.tplayer.ui.entity.DataCodeEntity;
import training.com.tplayer.ui.entity.SongOnlineEntity;
import training.com.tplayer.utils.ImageUtils;
import training.com.tplayer.utils.LogUtils;
import training.com.tplayer.utils.SongConverterUtils;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerActivity extends BaseActivity<PlayerPresenterImpl> implements PlayerContracts.View, PlayListInPlayerAdapter.PlayListInPlayerAdapterListener, LyricView.OnPlayerClickListener, View.OnClickListener, DiscreteSeekBar.OnProgressChangeListener {

    public static final String EXTRA_DATA_PLAYER = "extra.data.player";
    public static final String BUNDLE_DATA_ONLINE = "extra.data.online";
    public static final String BUNDLE_DATA_OFFLINE = "extra.data.offline";


    @BindView(R.id.act_player_total_song)
    TextViewRoboto mTxtTotalSong;

    @BindView(R.id.act_player_song_name)
    TextViewRoboto mTxtSongName;

    @BindView(R.id.act_player_txt_songs_artist)
    TextViewRoboto mTxtTotalSongArtist;

    @BindView(R.id.act_player_image_artist)
    ImageView mImvArtist;

    @BindView(R.id.act_player_control_imv_backward)
    ImageView mImvBackward;

    @BindView(R.id.act_player_control_imv_forward)
    ImageView mImvForward;

    @BindView(R.id.act_player_control_imv_play_pause)
    ImageView mImvPlayPause;

    @BindView(R.id.act_player_control_seekbar)
    DiscreteSeekBar mSeekbar;

    @BindView(R.id.act_player_control_imv_volume)
    ImageView mImvVolume;

    @BindView(R.id.act_player_control_imv_repeat)
    ImageView mImvShuffle;

    @BindView(R.id.lyricView)
    LyricView mLyricView;

    @BindView(R.id.act_player_play_list)
    RecyclerView mRvPlayList;


    private PlayListInPlayerAdapter mAdapter;

    private List<DataCodeEntity> mListDataCode = new ArrayList<>();

    private ArrayList<Song> songs = new ArrayList<>();


    @Override
    public int setLayoutId() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        return R.layout.activity_player;
    }

    @Override
    public void getDataBundle(Bundle savedInstanceState) {
        super.getDataBundle(savedInstanceState);
        Intent in = getIntent();
        Bundle bundle = in.getBundleExtra(EXTRA_DATA_PLAYER);
        if (bundle != null) mListDataCode = bundle.getParcelableArrayList(BUNDLE_DATA_ONLINE);
        mTxtTotalSong.setText(mListDataCode.size() + " Songs");
        Retrofit retrofit = RetrofitBuilderHelper.getRetrofitBuilder();
        RetrofitApiRequest retrofitApiRequest = retrofit.create(RetrofitApiRequest.class);
        for (DataCodeEntity code : mListDataCode) {
            LogUtils.printLog(code.dataCode);

            Call<SongOnlineEntity> call = retrofitApiRequest.getDataSource(code.dataCode);
            call.enqueue(new Callback<SongOnlineEntity>() {
                @Override
                public void onResponse(Call<SongOnlineEntity> call, Response<SongOnlineEntity> response) {
                    Song song = SongConverterUtils.convert(response.body());
                    songs.add(song);
                   mAdapter.setDatas(songs);
                }

                @Override
                public void onFailure(Call<SongOnlineEntity> call, Throwable t) {
                    LogUtils.printLog(t.getMessage());
                }
            });
        }

    }


    @Override
    public void onBindView() {
        ButterKnife.bind(this);

        mImvShuffle.setOnClickListener(this);
        mImvVolume.setOnClickListener(this);
        mImvPlayPause.setOnClickListener(this);
        mImvForward.setOnClickListener(this);
        mImvBackward.setOnClickListener(this);
        mSeekbar.setOnProgressChangeListener(this);
        mLyricView.setOnPlayerClickListener(this);
        mAdapter = new PlayListInPlayerAdapter(this, this);

        ImageUtils.loadRoundImage(this.getApplicationContext(),R.drawable.dummy_image,mImvArtist);
    }

    @Override
    public void onActivityCreated() {
        mRvPlayList.setLayoutManager(new LinearLayoutManager(this));
        mRvPlayList.setNestedScrollingEnabled(false);
        mRvPlayList.setAdapter(mAdapter);

    }

    @Override
    protected void createPresenterImpl() {
        PlayerPresenterImpl playerPresenter = new PlayerPresenterImpl();
        playerPresenter.onSubcireInteractor(new PlayerInteractorImpl(this));
        playerPresenter.onSubcireView(this);
    }

    @Override
    public void onRecyclerViewItemClick(View view, BaseEntity baseEntity, int position) {

    }

    //Lyric callback when playbutton click
    @Override
    public void onPlayerClicked(long l, String s) {

    }

    // layout control click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_player_control_imv_repeat:
                break;
            case R.id.act_player_control_imv_volume:
                break;
            case R.id.act_player_control_imv_play_pause:
                break;
            case R.id.act_player_control_imv_forward:
                break;
            case R.id.act_player_control_imv_backward:
                break;
        }
    }

    // seekbar change progress
    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

    }


}
