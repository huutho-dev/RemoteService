package training.com.tplayer.ui.player;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.remote.communication.MediaEntity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhengken.lyricview.LyricView;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.ui.adapter.PlayListInPlayerAdapter;
import training.com.tplayer.utils.DateTimeUtils;
import training.com.tplayer.utils.ImageUtils;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerActivity extends BaseActivity<PlayerPresenterImpl>
        implements PlayerContracts.View, PlayListInPlayerAdapter.PlayListInPlayerAdapterListener,
        View.OnClickListener, DiscreteSeekBar.OnProgressChangeListener {

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
    ImageView mImvArtistCover;

    @BindView(R.id.act_player_cover_image)
    ImageView mImvSongCover;

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

    private ArrayList<MediaEntity> songs = new ArrayList<>();

    private final long TIME_TO_UPDATE_SEEKBAR = 1000;
    private int mCurrentValueSeekbar;


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

        if (getIntent() != null) {
            Bundle bundle = getIntent().getBundleExtra(EXTRA_DATA_PLAYER);
            if (bundle != null) {
                songs = bundle.getParcelableArrayList(BUNDLE_DATA_ONLINE);
                LogUtils.printLog("Client_onLoadDataZingComplete : size = " + songs.size());
            }
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
        mAdapter = new PlayListInPlayerAdapter(this, this);
        mAdapter.setDatas(songs);
        ImageUtils.loadRoundImage(this.getApplicationContext(), R.drawable.dummy_image, mImvArtistCover);

    }

    @Override
    public void onActivityCreated() {
        mRvPlayList.setLayoutManager(new LinearLayoutManager(this));
        mRvPlayList.setNestedScrollingEnabled(false);
        mRvPlayList.setAdapter(mAdapter);
    }

    @Override
    protected void createPresenterImpl() {
        mPresenter = new PlayerPresenterImpl();
        mPresenter.onSubcireInteractor(new PlayerInteractorImpl(this));
        mPresenter.onSubcireView(this);
        LogUtils.printLog("createPresenterImpl");

    }

    @Override
    public void serviceConnected() {
        super.serviceConnected();
        if (getPlayerService() != null) {
            mPresenter.setService(getPlayerService());
            LogUtils.printLog("getPlayerService != nu");
            if (songs != null && mAdapter != null) {
                mAdapter.setDatas(songs);
                mPresenter.setPlayLists(songs);
                mPresenter.startSongPosition(0);
                LogUtils.printLog("start");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onUnregisterBroadcast();
    }


    @Override
    public void onRecyclerViewItemClick(View view, BaseEntity baseEntity, int position) {

    }


    // layout control click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_player_control_imv_repeat:
                LogUtils.printLog("Client_repeat");
                mPresenter.repeat();
                break;
            case R.id.act_player_control_imv_volume:
                LogUtils.printLog("Client_volume");
                break;
            case R.id.act_player_control_imv_play_pause:
                LogUtils.printLog("Client_playPause");
                boolean isPlaying = mPresenter.playPause();
                mImvPlayPause.setSelected(isPlaying);
                changeSeekbarState(isPlaying);
                break;
            case R.id.act_player_control_imv_forward:
                LogUtils.printLog("Client_forward");
                mPresenter.forward();
                break;
            case R.id.act_player_control_imv_backward:
                LogUtils.printLog("Client_backward");
                mPresenter.backward();
                break;
        }
    }

    // seekbar change progress
    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        if (fromUser) {
            LogUtils.printLog("Client_seekbar_onProgressChanged = " + value);
            resetSeekbar();
            mCurrentValueSeekbar = value;
            mPresenter.seekToPosition(value);
            seekBar.setProgress(value);
            seekBar.setIndicatorFormatter(DateTimeUtils.formatMinuteSecond(value));
            mHandler.post(runUpdateSeekbar);
        }
    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

    }


    @Override
    public void onRemotePlayNewSong(MediaEntity song) {
        LogUtils.printLog("Client_onRemotePlayNewSong :" + song.toString());
        mTxtSongName.setText(song.title);
        mTxtSongArtist.setText(song.artist);
        mImvPlayPause.setSelected(true);
        ImageUtils.loadRoundImage(this, song.art, mImvArtistCover);
        ImageUtils.loadImageBasic(this, song.art, mImvSongCover);

        song.isPlaying= true;
        mAdapter.notifyItem(song);

        resetSeekbar();
        mHandler.postDelayed(runUpdateSeekbar, 100);
    }

    int duration = 0;

    @Override
    public void onBufferPlaySong(int percent) {
        duration = percent;
        mSeekbar.setMax(percent);
        LogUtils.printLog("onBufferPlaySong : percent = " + percent);
    }

    @Override
    public void onRemotePlayCompleteASong() {
        LogUtils.printLog("Client_onRemotePlayCompleteASong");
        mImvPlayPause.setSelected(false);
        resetSeekbar();
    }

    @Override
    public void onLoadLyricComplete(File lyric) {
        LogUtils.printLog("Client_onDownloadLyricComplete : file = " + lyric.toString());

        File file = new File(Environment.getExternalStorageDirectory(), "temp.lrc");
        LogUtils.printLog(file.getAbsolutePath());
        FileOutputStream fos;
        byte[] data = new String(lyric.toString()).getBytes();
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
            fos.close();

            File getFile = new File(Environment.getExternalStorageDirectory() + "/temp.lrc");
            mLyricView.setLyricFile(getFile);

            mLyricView.setCurrentTimeMillis(mCurrentValueSeekbar);

            mLyricView.setOnPlayerClickListener(new LyricView.OnPlayerClickListener() {
                @Override
                public void onPlayerClicked(long progress, String content) {
                    LogUtils.printLog("ihihi" + progress + " " + content);
                }
            });


        } catch (FileNotFoundException e) {
            // handle exception
        } catch (IOException e) {
            // handle exception
        }
    }


    private Runnable runUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            mCurrentValueSeekbar += TIME_TO_UPDATE_SEEKBAR;
            mSeekbar.setProgress(mCurrentValueSeekbar);
            mHandler.postDelayed(runUpdateSeekbar, TIME_TO_UPDATE_SEEKBAR);
            mLyricView.setCurrentTimeMillis(mCurrentValueSeekbar);
        }
    };

    private void resetSeekbar() {
        mCurrentValueSeekbar = 0;
        mSeekbar.setProgress(mCurrentValueSeekbar);
        mHandler.removeCallbacks(runUpdateSeekbar);
    }

    private void changeSeekbarState(boolean isPlaying) {
        if (isPlaying) {
            mHandler.post(runUpdateSeekbar);
        }
        mHandler.removeCallbacks(runUpdateSeekbar);
    }

}
