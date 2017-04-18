package training.com.tplayer.ui.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.WakefulBroadcastReceiver;
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
import training.com.tplayer.R;
import training.com.tplayer.app.Config;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.custom.TLyricView;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.network.service.LoadListDataCodeService;
import training.com.tplayer.ui.adapter.PlayListInPlayerAdapter;
import training.com.tplayer.ui.entity.DataCodeEntity;
import training.com.tplayer.utils.ImageUtils;
import training.com.tplayer.utils.LogUtils;

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
    TextViewRoboto mTxtSongArtist;

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
    TLyricView mLyricView;

    @BindView(R.id.act_player_play_list)
    RecyclerView mRvPlayList;

    private BroadcastReceiver broadcastReceiver;

    private PlayListInPlayerAdapter mAdapter;

    private List<DataCodeEntity> mListDataCode = new ArrayList<>();

    private ArrayList<Song> songs = new ArrayList<>();

    private int currentSeekbar;


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

        bindTPlayerService();

        Intent in = getIntent();
        Bundle bundle = in.getBundleExtra(EXTRA_DATA_PLAYER);
        if (bundle != null) mListDataCode = bundle.getParcelableArrayList(BUNDLE_DATA_ONLINE);
        LogUtils.printLog(mListDataCode.size() + "");
        for (DataCodeEntity dataCodeEntity : mListDataCode) {
            LogUtils.printLog(dataCodeEntity.dataCode + " - ");
        }
        Intent intent = new Intent(this, LoadListDataCodeService.class);
        intent.putParcelableArrayListExtra(LoadListDataCodeService.EXTRA_DATA_CODE,
                (ArrayList<? extends Parcelable>) mListDataCode);

        startService(intent);
        LocalBroadcastManager.getInstance(this).registerReceiver(new WakefulBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<Song> parcelableArrayListExtra = intent.getParcelableArrayListExtra(LoadListDataCodeService.EXTRA_CALL_BACK);
                mAdapter.setDatas(parcelableArrayListExtra);
                mTxtTotalSong.setText(parcelableArrayListExtra.size() + "");
                if (getPlayerService() != null) {
                    try {
                        getPlayerService().setPlayList(parcelableArrayListExtra);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new IntentFilter(LoadListDataCodeService.ACTION_CALL_BACK_SONG));
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
        mAdapter.setDatas(songs);
        ImageUtils.loadRoundImage(this.getApplicationContext(), R.drawable.dummy_image, mImvArtist);
    }

    @Override
    public void onActivityCreated() {
        mRvPlayList.setLayoutManager(new LinearLayoutManager(this));
        mRvPlayList.setNestedScrollingEnabled(false);
        mRvPlayList.setAdapter(mAdapter);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Config.ACTION_PLAYER_COMPLETE.equals(intent.getAction())) {
                    LogUtils.printLog("ACTION_PLAYER_COMPLETE");

                } else if (Config.ACTION_PLAYER_START_PLAY.equals(intent.getAction())) {
                    LogUtils.printLog("ACTION_PLAYER_START_PLAY");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int duration = getPlayerService().getDuration();
                                mSeekbar.setMax(duration);
                                mHandler.post(updateSeekbar);
                                LogUtils.printLog(duration + "");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 1000);

                    Song song = intent.getParcelableExtra(Config.ACTION_PLAYER_START_PLAY);
                    updateUi(song);
                }
            }

        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.ACTION_PLAYER_COMPLETE);
        filter.addAction(Config.ACTION_PLAYER_START_PLAY);

        registerReceiver(broadcastReceiver, filter);

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void updateUi(Song song) {
        mTxtSongName.setText(song._title);
        mTxtSongArtist.setText(song.artist_name);
        mSeekbar.setMax((int) song.duration);
        mAdapter.notifyItem(song);
        LogUtils.printLog(song.toString());
    }


    private Runnable updateSeekbar = new Runnable() {
        @Override
        public void run() {
            mSeekbar.setProgress(currentSeekbar);
            currentSeekbar += 1000 ;
            mHandler.postDelayed(this, 1000);
        }
    };
}
