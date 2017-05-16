package training.com.tplayer.ui.setting;

import android.content.Intent;
import android.os.RemoteException;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
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

public class SettingActivity extends BaseActivity<SettingPresenterImpl> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

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

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.act_settings_equalizer)
    TextViewRoboto mEqualizer;

    @BindView(R.id.act_settings_spinner_language)
    AppCompatSpinner mSpinnerLanguage;

    @BindView(R.id.act_settings_switch_download_opt)
    SwitchCompat mSwtDownload;

    @BindView(R.id.act_settings_switch_shake)
    SwitchCompat mSwtShake;

    @BindView(R.id.act_settings_switch_pause_when_disable_headset)
    SwitchCompat mSwtDisableHeadset;

    @BindView(R.id.act_settings_switch_continue_when_enable_headset)
    SwitchCompat mSwtEnableHeadset;

    @BindView(R.id.act_settings_switch_pause_when_other_sound_comming)
    SwitchCompat mSwtOtherSound;


    private MediaEntity mCurrentSong;

    @Override
    public int setLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPlayPause.setOnClickListener(this);
        mForward.setOnClickListener(this);
        mPanelPlayer.setOnClickListener(this);

        mEqualizer.setOnClickListener(this);
        mSwtDownload.setOnCheckedChangeListener(this);
        mSwtShake.setOnCheckedChangeListener(this);
        mSwtDisableHeadset.setOnCheckedChangeListener(this);
        mSwtEnableHeadset.setOnCheckedChangeListener(this);
        mSwtOtherSound.setOnCheckedChangeListener(this);
    }

    @Override
    public void onActivityCreated() {

    }

    @Override
    protected void createPresenterImpl() {
        mPresenter = new SettingPresenterImpl();
        mPresenter.onSubcireView(this);
        mPresenter.onSubcireInteractor(new SettingInteratorImpl(this));
    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {

                case R.id.act_settings_equalizer:
                    mPresenter.settingEqualizer(this);
                    break;

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
                    startActivity(new Intent(SettingActivity.this, PlayerActivity.class));
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.act_settings_switch_download_opt:
                mPresenter.settingDownloadOpt(isChecked);
                break;
            case R.id.act_settings_switch_shake:
                mPresenter.settingShake(isChecked);
                break;
            case R.id.act_settings_switch_pause_when_disable_headset:
                mPresenter.settingPauseWhenDisableHeadset(isChecked);
                break;
            case R.id.act_settings_switch_continue_when_enable_headset:
                mPresenter.settingContinueWhenEnableHeadset(isChecked);
                break;
            case R.id.act_settings_switch_pause_when_other_sound_comming:
                mPresenter.settingPauseOtherSoundPlay(isChecked);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
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
}
