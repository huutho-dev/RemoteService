package training.com.tplayer.ui.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.remote.communication.Song;

import java.io.File;
import java.util.ArrayList;

import training.com.tplayer.app.Config;
import training.com.tplayer.base.mvp.BaseInteractorImpl;
import training.com.tplayer.network.service.LoadListDataCodeService;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerInteractorImpl extends BaseInteractorImpl implements PlayerContracts.Interactor {

    private BroadcastReceiver mRemoteServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {

                case Config.ACTION_PLAYER_START_PLAY:
                    Song song = intent.getParcelableExtra(Config.ACTION_PLAYER_START_PLAY);
                    int i = intent.getIntExtra(Config.ACTION_PLAYER_BUFFER,-1);
                    mOnPlayerListener.onBufferPlayNewSong( i);
                    mOnPlayerListener.onRemotePlayNewSong(song);
                    break;

                case Config.ACTION_PLAYER_COMPLETE:
                    mOnPlayerListener.onRemoteCompleteASong();
                    break;

                case Config.ACTION_PLAYER_DOWNLOAD_LYRIC:
                    String textLyric = intent.getStringExtra(Config.ACTION_PLAYER_DOWNLOAD_LYRIC);
                    File lyric = new File(textLyric);
                    mOnPlayerListener.onDownloadLyricComplete(lyric);
            }
        }
    };

    private BroadcastReceiver mLoadDataFromZing = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Song> songs =
                    intent.getParcelableArrayListExtra(LoadListDataCodeService.EXTRA_CALL_BACK);
            mOnPlayerListener.onLoadZingComplete(songs);
        }
    };

    private PlayerContracts.IOnPlayerListener mOnPlayerListener;

    public PlayerInteractorImpl(Context context) {
        super(context);

        IntentFilter filterRemote = new IntentFilter();
        filterRemote.addAction(Config.ACTION_PLAYER_COMPLETE);
        filterRemote.addAction(Config.ACTION_PLAYER_START_PLAY);
        filterRemote.addAction(Config.ACTION_PLAYER_DOWNLOAD_LYRIC);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mRemoteServiceReceiver, filterRemote);

        IntentFilter filterZing = new IntentFilter();
        filterZing.addAction(LoadListDataCodeService.ACTION_CALL_BACK_SONG);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mLoadDataFromZing, filterZing);

        mContext.registerReceiver(mLoadDataFromZing, filterZing);
        mContext.registerReceiver(mRemoteServiceReceiver, filterRemote);
    }

    public void setListener(PlayerContracts.IOnPlayerListener onPlayerListener) {
        this.mOnPlayerListener = onPlayerListener;
    }

    @Override
    public void onUnregisterBroadcast() {
        mContext.unregisterReceiver(mLoadDataFromZing);
        mContext.unregisterReceiver(mRemoteServiceReceiver);
    }
}
