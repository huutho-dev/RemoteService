package training.com.tplayer.ui.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.remote.communication.Song;

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
                    mOnPlayerListener.onRemotePlayNewSong(song);
                    break;
                case Config.ACTION_PLAYER_COMPLETE:
                    mOnPlayerListener.onRemoteCompleteASong();
                    break;
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
//        LocalBroadcastManager.getInstance(mContext).registerReceiver(mRemoteServiceReceiver, filterRemote);

        IntentFilter filterZing = new IntentFilter();
        filterZing.addAction(LoadListDataCodeService.ACTION_CALL_BACK_SONG);
//        LocalBroadcastManager.getInstance(mContext).registerReceiver(mLoadDataFromZing, filterZing);

        mContext.registerReceiver(mLoadDataFromZing, filterZing);
        mContext.registerReceiver(mRemoteServiceReceiver, filterRemote);
    }

    public void setListener(PlayerContracts.IOnPlayerListener onPlayerListener){
        this.mOnPlayerListener = onPlayerListener;
    }

    @Override
    public void onUnregisterBroadcast() {
        mContext.unregisterReceiver(mLoadDataFromZing);
        mContext.unregisterReceiver(mRemoteServiceReceiver);
    }
}
