package training.com.tplayer.ui.player;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
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
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.custom.TLyricView;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.network.service.LoadListDataCodeService;
import training.com.tplayer.ui.adapter.PlayListInPlayerAdapter;
import training.com.tplayer.ui.entity.DataCodeEntity;
import training.com.tplayer.utils.ImageUtils;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerActivity extends BaseActivity<PlayerPresenterImpl>
        implements PlayerContracts.View, PlayListInPlayerAdapter.PlayListInPlayerAdapterListener,
        LyricView.OnPlayerClickListener, View.OnClickListener, DiscreteSeekBar.OnProgressChangeListener {

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

    // Local variable

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
        if (bundle != null) {
            mListDataCode = bundle.getParcelableArrayList(BUNDLE_DATA_ONLINE);
            Intent intent = new Intent(this, LoadListDataCodeService.class);
            intent.putParcelableArrayListExtra(LoadListDataCodeService.EXTRA_DATA_CODE,
                    (ArrayList<? extends Parcelable>) mListDataCode);
            startService(intent);
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
        mAdapter.setDatas(songs);
        ImageUtils.loadRoundImage(this.getApplicationContext(), R.drawable.dummy_image, mImvArtist);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onUnregisterBroadcast();
    }


    @Override
    public void onRecyclerViewItemClick(View view, BaseEntity baseEntity, int position) {

    }


    boolean isPlay ;

    // layout control click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_player_control_imv_repeat:
                mPresenter.repeat();
                break;
            case R.id.act_player_control_imv_volume:

                break;
            case R.id.act_player_control_imv_play_pause:
                mPresenter.playPause();
                mImvPlayPause.setSelected(!isPlay);
                break;
            case R.id.act_player_control_imv_forward:
                mPresenter.forward();
                break;
            case R.id.act_player_control_imv_backward:
                mPresenter.backward();
                break;
        }
    }

    // seekbar change progress
    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        if (fromUser) {
            currentSeekbar = value;
            seekBar.setProgress(value);
            mPresenter.seekToPosition(value);
        }
    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

    }

    //Lyric callback when playbutton click
    @Override
    public void onPlayerClicked(long l, String s) {

    }


    @Override
    public void onRemotePlayNewSong(Song song) {

    }

    @Override
    public void onRemotePlayCompleteASong() {

    }

    @Override
    public void onLoadDataZingComplete(ArrayList<Song> songs) {

    }
}
