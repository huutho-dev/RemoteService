package training.com.tplayer.ui.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.remote.communication.MediaEntity;

import java.io.File;

import training.com.tplayer.app.Config;
import training.com.tplayer.base.mvp.BaseInteractorImpl;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerInteractorImpl extends BaseInteractorImpl implements PlayerContracts.Interactor {

    private BroadcastReceiver mRemoteServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {

                case Config.ACTION_PLAYER_START_PLAY:
                    MediaEntity song = intent.getParcelableExtra(Config.ACTION_PLAYER_START_PLAY);
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


    private PlayerContracts.IOnPlayerListener mOnPlayerListener;

    public PlayerInteractorImpl(Context context) {
        super(context);

        IntentFilter filterRemote = new IntentFilter();
        filterRemote.addAction(Config.ACTION_PLAYER_COMPLETE);
        filterRemote.addAction(Config.ACTION_PLAYER_START_PLAY);
        filterRemote.addAction(Config.ACTION_PLAYER_DOWNLOAD_LYRIC);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mRemoteServiceReceiver, filterRemote);

        mContext.registerReceiver(mRemoteServiceReceiver, filterRemote);
    }

    public void setListener(PlayerContracts.IOnPlayerListener onPlayerListener) {
        this.mOnPlayerListener = onPlayerListener;
    }

    @Override
    public void onUnregisterBroadcast() {
        mContext.unregisterReceiver(mRemoteServiceReceiver);
    }
}
