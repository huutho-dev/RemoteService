package training.com.tplayer.ui.offline;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.remote.communication.MediaEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.database.DatabaseScanner;
import training.com.tplayer.database.SourceTableMedia;
import training.com.tplayer.ui.offline.activity.TabOfflineActivity;
import training.com.tplayer.ui.player.PlayerActivity;
import training.com.tplayer.ui.search.SearchActivity;
import training.com.tplayer.utils.ImageUtils;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by HuuTho on 4/9/2017.
 */

public class OfflineActivity extends BaseActivity<OfflinePresenterImpl>
        implements OfflineContracts.View, View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 1111;

    @BindView(R.id.act_offline_main_layout)
    NestedScrollView mMainLayout;

    @BindView(R.id.act_offline_scan_layout)
    RelativeLayout mScanLayout;

    @BindView(R.id.act_offline_button_scan)
    ImageView mButtonScan;

    @BindView(R.id.act_offline_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.act_offline_txt_number_folder)
    TextViewRoboto mNumberFolders;

    @BindView(R.id.act_offline_txt_number_download)
    TextViewRoboto mNumberDownloads;

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


    private MediaEntity mCurrentSong;

    @Override
    public int setLayoutId() {
        return R.layout.activity_offline;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.title_activity_offline);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.act_offline_item_songs).setOnClickListener(this);
        findViewById(R.id.act_offline_item_albums).setOnClickListener(this);
        findViewById(R.id.act_offline_item_artist).setOnClickListener(this);
        findViewById(R.id.act_offline_item_playlists).setOnClickListener(this);
        findViewById(R.id.act_offline_item_folder).setOnClickListener(this);
        findViewById(R.id.act_offline_item_download).setOnClickListener(this);
        findViewById(R.id.act_offline_button_scan).setOnClickListener(this);

        mPlayPause.setOnClickListener(this);
        mForward.setOnClickListener(this);
        mPanelPlayer.setOnClickListener(this);

        boolean isDbHasData = SourceTableMedia.getInstance(this).isHasData();
        LogUtils.printLog("Has data : " + isDbHasData);
        if (isDbHasData) {
            mMainLayout.setVisibility(View.VISIBLE);
            mScanLayout.setVisibility(View.GONE);
        } else {
            mMainLayout.setVisibility(View.GONE);
            mScanLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onActivityCreated() {
        checkSystemWritePermission();
    }

    @Override
    protected void createPresenterImpl() {
        mPresenter = new OfflinePresenterImpl();
        mPresenter.onSubcireView(this);
        mPresenter.onSubcireInteractor(new OfflineInteractorImpl(this));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(OfflineActivity.this, TabOfflineActivity.class);

        try {

            switch (v.getId()) {
                case R.id.act_offline_item_songs:
                    intent.putExtra(TabOfflineActivity.KEY_TAB, TabOfflineActivity.TAB_SONG);
                    startActivity(intent);
                    break;
                case R.id.act_offline_item_albums:
                    intent.putExtra(TabOfflineActivity.KEY_TAB, TabOfflineActivity.TAB_ALBUM);
                    startActivity(intent);
                    break;
                case R.id.act_offline_item_artist:
                    intent.putExtra(TabOfflineActivity.KEY_TAB, TabOfflineActivity.TAB_ARTIST);
                    startActivity(intent);
                    break;
                case R.id.act_offline_item_playlists:
                    intent.putExtra(TabOfflineActivity.KEY_TAB, TabOfflineActivity.TAB_PLAYLIST);
                    startActivity(intent);
                    break;
                case R.id.act_offline_item_folder:
                    intent.putExtra(TabOfflineActivity.KEY_TAB, TabOfflineActivity.TAB_FOLDER);
                    startActivity(intent);
                    break;
                case R.id.act_offline_item_download:
                    intent.putExtra(TabOfflineActivity.KEY_TAB, TabOfflineActivity.TAB_DOWNLOAD);
                    startActivity(intent);
                    break;

                case R.id.act_offline_button_scan:
                    RotateAnimation anim = new RotateAnimation(0, 720,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    anim.setDuration(1000);
                    anim.setRepeatMode(Animation.RESTART);
                    anim.setRepeatCount(Animation.INFINITE);
                    anim.setInterpolator(new LinearInterpolator());
                    mButtonScan.startAnimation(anim);

                    requestPermission();
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
                    startActivity(new Intent(OfflineActivity.this, PlayerActivity.class));
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_offline_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_rescan:
                mScanLayout.setVisibility(View.VISIBLE);
                mMainLayout.setVisibility(View.INVISIBLE);

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        DatabaseScanner scanner = new DatabaseScanner(OfflineActivity.this);
                        scanner.insertOrUpdateTableMedia(scanner.scanMedia());
                        scanner.insertOrUpdateTableAlbum(scanner.scanAlbum());
                        scanner.insertOrUpdateTableArtist(scanner.scanArtist());
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        boolean isDbHasData = SourceTableMedia.getInstance(OfflineActivity.this).isHasData();
                        if (isDbHasData) {
                            mMainLayout.setVisibility(View.VISIBLE);
                            mScanLayout.setVisibility(View.GONE);
                        } else {
                            mMainLayout.setVisibility(View.GONE);
                            mScanLayout.setVisibility(View.VISIBLE);
                        }
                    }
                };

                LogUtils.printLog("action_rescan");
                break;
            case R.id.action_volume:
                break;
            case R.id.action_timer:
                break;
            case R.id.action_equalizer:
                break;
            case R.id.action_setting:
                break;
            case R.id.action_about:
                break;

            case R.id.app_bar_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;

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


    @Override
    protected void onStop() {
        super.onStop();
        unbindTplayerService();
    }

    private AsyncTask<Void, Void, Void> scanner = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... params) {
            DatabaseScanner scanner = new DatabaseScanner(OfflineActivity.this);
            scanner.insertOrUpdateTableMedia(scanner.scanMedia());
            scanner.insertOrUpdateTableAlbum(scanner.scanAlbum());
            scanner.insertOrUpdateTableArtist(scanner.scanArtist());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            boolean isDbHasData = SourceTableMedia.getInstance(OfflineActivity.this).isHasData();
            if (isDbHasData) {
                mMainLayout.setVisibility(View.VISIBLE);
                mScanLayout.setVisibility(View.GONE);
            } else {
                mMainLayout.setVisibility(View.GONE);
                mScanLayout.setVisibility(View.VISIBLE);
            }
        }
    };

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);

        } else {
            scanner.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        ) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    scanner.execute();

                } else {
                    Toast.makeText(OfflineActivity.this, "Permission not found", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private boolean checkSystemWritePermission() {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);
            LogUtils.printLog("Can Write Settings: " + retVal);
            if (retVal) {
            } else {

                final AlertDialog alertDialog = new AlertDialog.Builder(OfflineActivity.this).create();
                alertDialog.setTitle(R.string.dialog_confirm_go_to_setting);
                alertDialog.setMessage(getResources().getString(R.string.dialog_confirm_go_to_setting_request));
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + OfflineActivity.this.getPackageName()));
                        startActivity(intent);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        }
        return retVal;
    }
}
