package training.com.tplayer.ui.share;

import android.content.Intent;
import android.os.RemoteException;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.remote.communication.MediaEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.ui.player.PlayerActivity;
import training.com.tplayer.utils.ImageUtils;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class ShareActivity extends BaseActivity<SharePresenterImpl> implements View.OnClickListener {

    @BindView(R.id.layout_bottom_panel_player)
    ConstraintLayout mPanelPlayer;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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

    @BindView(R.id.share_app)
    TextViewRoboto mShareApp;

    @BindView(R.id.share_social)
    TextViewRoboto mShareSocial;

    private MediaEntity mCurrentSong;


    @Override
    public int setLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);
        mShareApp.setOnClickListener(this);
        mShareSocial.setOnClickListener(this);
        mPlayPause.setOnClickListener(this);
        mForward.setOnClickListener(this);
        mPanelPlayer.setOnClickListener(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle(R.string.activity_share_title_toolbar);
    }

    @Override
    public void onActivityCreated() {

    }

    @Override
    protected void createPresenterImpl() {
        mPresenter = new SharePresenterImpl();
        mPresenter.onSubcireView(this);
        mPresenter.onSubcireInteractor(new ShareInteratorImpl(this));

//            try {
//                PackageInfo info = getPackageManager().getPackageInfo(
//                        getPackageName(), PackageManager.GET_SIGNATURES);
//                for (Signature signature : info.signatures) {
//                    MessageDigest md = MessageDigest.getInstance("SHA");
//                    md.update(signature.toByteArray());
//                    Log.i("KeyHash:",
//                            Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                }
//            } catch (PackageManager.NameNotFoundException e) {
//                Log.e("jk", "Exception(NameNotFoundException) : " + e);
//            } catch (NoSuchAlgorithmException e) {
//                Log.e("mkm", "Exception(NoSuchAlgorithmException) : " + e);
//            }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                        if (isSongPlaying) {
                            mPlayPause.setImageResource(R.drawable.ic_player_pause);
                        } else {
                            mPlayPause.setImageResource(R.drawable.ic_player_play);
                        }
                    }
                    break;
                case R.id.panel_bottom_player_forward:
                    if (getPlayerService() != null)
                        getPlayerService().forward();
                    break;
                case R.id.layout_bottom_panel_player:
                    startActivity(new Intent(this, PlayerActivity.class));
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    break;
                case R.id.share_app:
                    mPresenter.shareApp(this);
                    break;
                case R.id.share_social:
                    mPresenter.shareSocial(this);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
