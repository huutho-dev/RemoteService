package training.com.tplayer.ui.player;

import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.remote.communication.MediaEntity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhengken.lyricview.LyricView;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.ui.adapter.PlayListInPlayerAdapter;
import training.com.tplayer.utils.DateTimeUtils;
import training.com.tplayer.utils.ImageUtils;
import training.com.tplayer.utils.LogUtils;
import training.com.tplayer.utils.preferences.PlayerPreference;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerActivity extends BaseActivity<PlayerPresenterImpl>
        implements PlayerContracts.View, PlayListInPlayerAdapter.PlayListInPlayerAdapterListener,
        View.OnClickListener, DiscreteSeekBar.OnProgressChangeListener {

    public static final String EXTRA_DATA_PLAYER = "extra.data.player";

    public static final int REPEAT_NO = 0;
    public static final int REPEAT_ONE = 1;
    public static final int REPEAT_ALL = 2;


    @BindView(R.id.act_player_scroll_view)
    NestedScrollView mScroll;

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

    @BindView(R.id.act_player_control_imv_shuffle)
    ImageView mImvShuffle;

    @BindView(R.id.act_player_control_imv_repeat)
    ImageView mImvRepeat;

    @BindView(R.id.lyricView)
    LyricView mLyricView;

    @BindView(R.id.act_player_play_list)
    RecyclerView mRvPlayList;

    @BindView(R.id.act_player_capture_screen)
    ImageView mImgCaptureScreen;

    @BindView(R.id.act_player_download)
    ImageView mImgDownload;

    @BindView(R.id.act_player_equalizer)
    ImageView mImgEqualizer;


    private PlayListInPlayerAdapter mAdapter;

    private List<MediaEntity> songs = new ArrayList<>();

    private final long TIME_TO_UPDATE_SEEKBAR = 1000;

    private int mCurrentRepeat;

    private boolean mIsShuffle;

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
        if (getIntent() != null)
            songs = getIntent().getParcelableArrayListExtra(EXTRA_DATA_PLAYER);
    }


    @Override
    public void onBindView() {
        ButterKnife.bind(this);

        mImvRepeat.setOnClickListener(this);
        mImvShuffle.setOnClickListener(this);
        mImvPlayPause.setOnClickListener(this);
        mImvForward.setOnClickListener(this);
        mImvBackward.setOnClickListener(this);

        mImgCaptureScreen.setOnClickListener(this);
        mImgDownload.setOnClickListener(this);
        mImgEqualizer.setOnClickListener(this);

        mSeekbar.setOnProgressChangeListener(this);
        mAdapter = new PlayListInPlayerAdapter(this, this);
        mAdapter.setDatas(songs);
        ImageUtils.loadRoundImage(this.getApplicationContext(), R.drawable.dummy_image, mImvArtistCover);

        mIsShuffle = PlayerPreference.getInstance().getPlayerShuffle();
        mCurrentRepeat = PlayerPreference.getInstance().getPlayerRepeat();

        setViewShuffle(mIsShuffle);
        setViewRepeat(mCurrentRepeat);

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

            // setPlaylist from source
            if (songs != null && songs.size() != 0) {
                mAdapter.setDatas(songs);
                mPresenter.setPlayLists(songs);
                mPresenter.startSongPosition(0);
            } else if (songs == null) {

                // go to PlayerActivity by click bottom panel

                try {
                    MediaEntity song = getPlayerService().getCurrentSong();

                    mTxtSongName.setText(song.title);
                    mTxtSongArtist.setText(song.artist);
                    mImvPlayPause.setSelected(true);
                    ImageUtils.loadRoundImage(this, song.art, mImvArtistCover);
                    ImageUtils.loadImageBasic(this, song.art, mImvSongCover);
                    onLoadLyricComplete(new File(song.lyric));
                    mSeekbar.setMax(mPresenter.getDuration());
                    mCurrentValueSeekbar = mPresenter.getCurrentPosition();
                    runSeekBarAndLyric(mCurrentValueSeekbar);
                    mAdapter.setDatas(mPresenter.getNowPlaylist());
                    mAdapter.setActivePlaying(song);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onUnregisterBroadcast();
    }


    @Override
    public void onRecyclerViewItemClick(View view, MediaEntity song, int position) {
        try {
            getPlayerService().startSong(song);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRecyclerViewImagePlayItemClick(View view, MediaEntity entity, int position) {
        mImvPlayPause.performClick();
        mImvPlayPause.setSelected(entity.isPlaying);
    }


    // layout control click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_player_control_imv_repeat:
                LogUtils.printLog("Client_repeat");

                if (mCurrentRepeat == REPEAT_NO) {
                    mCurrentRepeat = REPEAT_ONE;
                    mImvRepeat.setImageResource(R.drawable.ic_player_repeat_one);

                } else if (mCurrentRepeat == REPEAT_ONE) {
                    mCurrentRepeat = REPEAT_ALL;
                    mImvRepeat.setImageResource(R.drawable.ic_player_repeat_active);

                } else if (mCurrentRepeat == REPEAT_ALL) {
                    mCurrentRepeat = REPEAT_NO;
                    mImvRepeat.setImageResource(R.drawable.ic_player_repeat_anactive);
                }
                PlayerPreference.getInstance().setPlayerRepeat(mCurrentRepeat);
                mPresenter.repeat(mCurrentRepeat);

                break;
            case R.id.act_player_control_imv_shuffle:
                LogUtils.printLog("Client_volume");
                mIsShuffle = !mIsShuffle;
                if (mIsShuffle) {
                    mImvShuffle.setImageResource(R.drawable.ic_shuffle_active);
                } else {
                    mImvShuffle.setImageResource(R.drawable.ic_shuffle_anactive);
                }
                mPresenter.setShuffle(mIsShuffle);
                PlayerPreference.getInstance().setPlayerShuffle(mIsShuffle);
                break;
            case R.id.act_player_control_imv_play_pause:
                LogUtils.printLog("Client_playPause");
                boolean isPlaying = mPresenter.playPause();
                mImvPlayPause.setSelected(isPlaying);

                if (isPlaying){
                    mCurrentValueSeekbar = mPresenter.getCurrentPosition();
                    runSeekBarAndLyric(mCurrentValueSeekbar);
                }else {
                    pauseSeekbarAndLyric();
                }

                break;
            case R.id.act_player_control_imv_forward:
                LogUtils.printLog("Client_forward");
                mPresenter.forward();
                break;
            case R.id.act_player_control_imv_backward:
                LogUtils.printLog("Client_backward");
                mPresenter.backward();
                break;

            case R.id.act_player_capture_screen:
                mPresenter.onCaptureScreenClick(this);
                break;

            case R.id.act_player_equalizer:
                mPresenter.onEqualizerClick(this);
                break;
            case R.id.act_player_download:
                mPresenter.onDownloadClick(this);
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

        mAdapter.setActivePlaying(song);

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

        mLyricView.setLyricFile(lyric);

        mLyricView.setCurrentTimeMillis(mCurrentValueSeekbar);

        mLyricView.setOnPlayerClickListener(new LyricView.OnPlayerClickListener() {
            @Override
            public void onPlayerClicked(long progress, String content) {
                LogUtils.printLog("ihihi" + progress + " " + content);
            }
        });

    }

    @Override
    public void onResumePlayer(MediaEntity song, int currentPosition) {

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

    private void runSeekBarAndLyric(int currentPos) {
        mHandler.post(runUpdateSeekbar);
        mSeekbar.setProgress(currentPos);
        mLyricView.setCurrentTimeMillis(currentPos);
    }

    private void pauseSeekbarAndLyric(){
      mHandler.removeCallbacks(runUpdateSeekbar);
    }

    private void setViewShuffle(boolean isShuffle) {
        if (isShuffle) {
            mImvShuffle.setImageResource(R.drawable.ic_shuffle_active);
        } else {
            mImvShuffle.setImageResource(R.drawable.ic_shuffle_anactive);
        }
    }

    private void setViewRepeat(int repeat) {
        switch (repeat) {
            case REPEAT_NO:
                mImvRepeat.setImageResource(R.drawable.ic_player_repeat_anactive);
                break;
            case REPEAT_ONE:
                mImvRepeat.setImageResource(R.drawable.ic_player_repeat_one);
                break;
            case REPEAT_ALL:
                mImvRepeat.setImageResource(R.drawable.ic_player_repeat_active);
        }
    }

}
