package training.com.tplayer.ui.main;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.MediaStore;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.ui.MyCM;
import training.com.tplayer.ui.favorite.FavoriteActivity;
import training.com.tplayer.ui.offline.OfflineActivity;
import training.com.tplayer.ui.online.OnlineActivity;
import training.com.tplayer.ui.rate.RateActivity;
import training.com.tplayer.ui.setting.SettingActivity;
import training.com.tplayer.ui.share.ShareActivity;

import static training.com.tplayer.R.id.action_menu_offline;

public class MainActivity extends BaseActivity<MainPresenterImpl>
        implements MainContracts.View, CircleMenu.OnItemClickListener {

    private int targetActivity;

    private final int ACTIVITY_ONLINE = 1;
    private final int ACTIVITY_OFFLINE = 2;
    private final int ACTIVITY_FAVORITE = 6;
    private final int ACTIVITY_SETTING = 3;
    private final int ACTIVITY_SHARE = 4;
    private final int ACTIVITY_RATE = 5;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;


    @BindView(R.id.circleMenu)
    MyCM mCircleMenu;


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
    }


    @Override
    public void onActivityCreated() {


        ContentResolver contentResolver = getContentResolver();
        Uri uriPlaylistTracks = MediaStore.Audio.Playlists.Members.getContentUri("external", 1757);
        Cursor cursor = contentResolver.query(uriPlaylistTracks,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DatabaseUtils.dumpCurrentRow(cursor);
            cursor.moveToNext();

        }


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
            case R.id.action_menu_rate:
                targetActivity = ACTIVITY_RATE;
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
            case ACTIVITY_RATE:
                startActivity(new Intent(MainActivity.this, RateActivity.class));
                break;
        }
    }

}
