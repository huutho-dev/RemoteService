package training.com.tplayer.ui.offline;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.ui.offline.activity.TabOfflineActivity;

/**
 * Created by HuuTho on 4/9/2017.
 */

public class OfflineActivity extends BaseActivity<OfflinePresenterImpl>
        implements OfflineContracts.View, View.OnClickListener {


    @BindView(R.id.act_offline_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.act_offline_txt_number_songs)
    TextViewRoboto mNumberSongs;
    @BindView(R.id.act_offline_txt_number_albums)
    TextViewRoboto mNumberAlbums;
    @BindView(R.id.act_offline_txt_number_artists)
    TextViewRoboto mNumberArtists;
    @BindView(R.id.act_offline_txt_number_playlists)
    TextViewRoboto mNumberPlaylists;
    @BindView(R.id.act_offline_txt_number_folder)
    TextViewRoboto mNumberFolders;
    @BindView(R.id.act_offline_txt_number_download)
    TextViewRoboto mNumberDownloads;


    @Override
    public int setLayoutId() {
        return R.layout.activity_offline;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        findViewById(R.id.act_offline_item_songs).setOnClickListener(this);
        findViewById(R.id.act_offline_item_albums).setOnClickListener(this);
        findViewById(R.id.act_offline_item_artist).setOnClickListener(this);
        findViewById(R.id.act_offline_item_playlists).setOnClickListener(this);
        findViewById(R.id.act_offline_item_folder).setOnClickListener(this);
        findViewById(R.id.act_offline_item_download).setOnClickListener(this);

    }

    @Override
    public void onActivityCreated() {
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
        }
        return super.onOptionsItemSelected(item);
    }
}
