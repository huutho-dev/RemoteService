package training.com.tplayer.ui.main;

import android.content.Intent;
import android.os.RemoteException;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;
import com.remote.communication.MediaEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.ui.MyCM;
import training.com.tplayer.ui.favorite.FavoriteActivity;
import training.com.tplayer.ui.offline.OfflineActivity;
import training.com.tplayer.ui.online.OnlineActivity;
import training.com.tplayer.ui.player.PlayerActivity;
import training.com.tplayer.ui.setting.SettingActivity;
import training.com.tplayer.ui.share.ShareActivity;
import training.com.tplayer.utils.ImageUtils;

import static training.com.tplayer.R.id.action_menu_offline;

public class MainActivity extends BaseActivity<MainPresenterImpl>
        implements MainContracts.View, CircleMenu.OnItemClickListener, View.OnClickListener {

    private int targetActivity;

    private final int ACTIVITY_ONLINE = 1;
    private final int ACTIVITY_OFFLINE = 2;
    private final int ACTIVITY_FAVORITE = 6;
    private final int ACTIVITY_SETTING = 3;
    private final int ACTIVITY_SHARE = 4;
    private final int ACTIVITY_RATE = 5;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;


    private MediaEntity mCurrentSong;

    @BindView(R.id.circleMenu)
    MyCM mCircleMenu;

    @BindView(R.id.layout_bottom_panel_player)
    ConstraintLayout mPanelPlayer;

    @BindView(R.id.panel_bottom_player_image)
    ImageView mImage;

    @BindView(R.id.panel_bottom_player_title)
    TextViewRoboto mTitle;

    @BindView(R.id.panel_bottom_player_artist)
    TextViewRoboto mArtist;

    @BindView(R.id.panel_bottom_player_play_pause)
    ImageView mPlayPause;

    @BindView(R.id.panel_bottom_player_forward)
    ImageView mForward;


    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);
        mCircleMenu.setOnItemClickListener(this);
        mCircleMenu.addOnFinish(new MyCM.OnFinish() {
            @Override
            public void finish() {
                startNewActivity(targetActivity);
            }
        });

        mPlayPause.setOnClickListener(this);
        mForward.setOnClickListener(this);
        mPanelPlayer.setOnClickListener(this);
    }


    @Override
    public void onActivityCreated() {

    }


    @Override
    protected void createPresenterImpl() {
        mPresenter = new MainPresenterImpl();
        mPresenter.onSubcireView(this);
        mPresenter.onSubcireInteractor(new MainInteractorImpl(this));
    }


    @Override
    public void onItemClick(CircleMenuButton menuButton) {
        switch (menuButton.getId()) {
            case R.id.action_menu_online:
                targetActivity = ACTIVITY_ONLINE;
                break;
            case action_menu_offline:
                targetActivity = ACTIVITY_OFFLINE;
                break;
            case R.id.action_menu_favorite:
                targetActivity = ACTIVITY_FAVORITE;
                break;
            case R.id.action_menu_setting:
                targetActivity = ACTIVITY_SETTING;
                break;
            case R.id.action_menu_share:
                targetActivity = ACTIVITY_SHARE;
                break;
        }
    }

    private void startNewActivity(int targetActivity) {
        switch (targetActivity) {
            case ACTIVITY_ONLINE:
                startActivity(new Intent(MainActivity.this, OnlineActivity.class));
                break;
            case ACTIVITY_OFFLINE:
                startActivity(new Intent(MainActivity.this, OfflineActivity.class));
                break;
            case ACTIVITY_FAVORITE:
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                break;
            case ACTIVITY_SETTING:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case ACTIVITY_SHARE:
                startActivity(new Intent(MainActivity.this, ShareActivity.class));
                break;
        }
    }

    @Override
    public void serviceConnected() {
        super.serviceConnected();
        try {
            boolean isStop = getPlayerService().isPlayerStop();
            if (isStop) {
                mPanelPlayer.setVisibility(View.GONE);
            } else {
                mPanelPlayer.setVisibility(View.VISIBLE);
                mCurrentSong = getPlayerService().getCurrentSong();

                if (mCurrentSong != null) {
                    mTitle.setText(mCurrentSong.title);
                    mArtist.setText(mCurrentSong.artist);

                    boolean isSongPlaying = getPlayerService().isPlayerPlaying();

                    if (isSongPlaying)
                        mPlayPause.setImageResource(R.drawable.ic_player_pause);
                    else
                        mPlayPause.setImageResource(R.drawable.ic_player_play);

                    if (mCurrentSong.art != null && TextUtils.isEmpty(mCurrentSong.art))
                        ImageUtils.loadImagePlayList(this, mCurrentSong.art, mImage);
                    else
                        mImage.setImageResource(R.drawable.ic_offline_song);
                }
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.panel_bottom_player_play_pause:
                    if (getPlayerService() != null) {
                        boolean isSongPlaying = getPlayerService().playPause();

                        if (isSongPlaying)
                            mPlayPause.setImageResource(R.drawable.ic_player_pause);
                        else
                            mPlayPause.setImageResource(R.drawable.ic_player_play);
                    }


                    break;
                case R.id.panel_bottom_player_forward:
                    if (getPlayerService() != null)
                        getPlayerService().forward();
                    break;
                case R.id.layout_bottom_panel_player:
                    startActivity(new Intent(MainActivity.this, PlayerActivity.class));
                    overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
