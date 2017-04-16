package training.com.tplayer.ui.player;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.custom.TextViewRoboto;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerActivity extends BaseActivity<PlayerPresenterImpl> implements PlayerContracts.View {

    @BindView(R.id.act_player_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.act_player_total_song)
    TextViewRoboto mTxtTotalSong;

    @BindView(R.id.act_player_song_name)
    TextViewRoboto mTxtSongName;

    @BindView(R.id.act_player_txt_songs_artist)
    TextViewRoboto mTxtTotalSongArtist;

    @BindView(R.id.act_player_image_artist)
    CircleImageView mImvArtist;

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

    @BindView(R.id.act_player_control_imv_shuffle)
    ImageView mImvShuffle;

    @BindView(R.id.act_player_play_list)
    RecyclerView  mRvPlayList;

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
    public void onBindView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onActivityCreated() {

    }

    @Override
    protected void createPresenterImpl() {

    }
}
