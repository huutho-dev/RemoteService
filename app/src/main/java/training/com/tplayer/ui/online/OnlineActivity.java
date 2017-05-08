package training.com.tplayer.ui.online;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.remote.communication.MediaEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.ui.adapter.online.OnlinePagerAdapter;
import training.com.tplayer.ui.player.PlayerActivity;
import training.com.tplayer.utils.ImageUtils;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class OnlineActivity extends BaseActivity<OnlinePresenterImpl> implements View.OnClickListener {

    private final int REQUEST_PERMISSTION_CODE = 1011;

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
    @BindView(R.id.act_onl_tablayout)
    SmartTabLayout mTabLayout;

    @BindView(R.id.act_onl_viewpager)
    ViewPager mViewPager;

    private OnlinePagerAdapter mAdapter;

    private MediaEntity mCurrentSong;


    @Override
    public int setLayoutId() {
        return R.layout.activity_online;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);

        mPlayPause.setOnClickListener(this);
        mForward.setOnClickListener(this);
        mPanelPlayer.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated() {
        mAdapter = new OnlinePagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setViewPager(mViewPager);

    }

    @Override
    protected void createPresenterImpl() {
        mPresenter = new OnlinePresenterImpl();
        mPresenter.onSubcireView(this);
        mPresenter.onSubcireInteractor(new OnlineInteractorImpl(this));

        requestPermisstion();
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
                    startActivity(new Intent(this, PlayerActivity.class));
                    overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
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



    public void requestPermisstion() {

        // Kiểm tra xem đã có permission chưa
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Chưa có
            // Show một lời giải thích
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // tạo dialog hay gì đấy show 1 lời giải thích
                // cho người dùng cần cái permisstion này để làm gì
                // giải thích xong thì requestPermission

            } else {

                // k giải thích thì thôi
                // requestPermission luôn

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSTION_CODE);

            }


        } else {
            // Có rồi này
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSTION_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // đã cấp quyền
                    // todo somthing

                }else {

                    // người dùng ấn cancel rồi, ngu người

                }
                break;
        }

    }
}
