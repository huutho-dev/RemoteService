package training.com.tplayerservice;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;

import com.remote.communication.Song;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by HuuTho on 4/9/2017.
 */

public class PlayerManager {
    private Context mContext;
    private TPlayer mTPlayer;
    private ArrayList<Song> mSongPrimaryList = new ArrayList<>();
    private ArrayList<Song> mOriginList = new ArrayList<>();

    private int mCurrentSong;
    private boolean mLoopList;

    public PlayerManager(Context context) {
        this.mContext = context;
        this.mTPlayer = new TPlayer(context);
    }

    public void setListSong(ArrayList<Song> origins) {
        this.mOriginList.addAll(origins);
        this.mSongPrimaryList.addAll(origins);
    }

    public void startSong(int position) {
        Song song = mSongPrimaryList.get(position);
        String data = song._data;
        startSong(data);
    }

    private void startSong(String data) {
        mTPlayer.resetPlayer();
        mTPlayer.setAudioStreamTypePlayer(AudioManager.STREAM_MUSIC);
        if (data.startsWith("http://")) {
            mTPlayer.setDataSourcePlayer(data);
            mTPlayer.setPlayerStreamOverNetwork(mContext);
            mTPlayer.prepareAsyncPlayer();
        } else {
            mTPlayer.setDataSourcePlayer(mContext, Uri.parse(data));
            mTPlayer.preparePlayer();
        }
        mTPlayer.startPlayer();
    }

    public void changePlayStatus() {
        if (mTPlayer.isPlayerPlaying()) {
            mTPlayer.pausePlayer();
            return;
        }
        mTPlayer.startPlayer();
    }

    public void nextSong() {
        changeCurrentSong(1);
    }

    public void previousSong() {
        changeCurrentSong(-1);
    }

    public void seek(int milis) {
        mTPlayer.seekToPlayer(milis);
    }

    public void setLoop(boolean isLoop) {
        mTPlayer.setLoopingPlayer(isLoop);
    }

    public void setLoopList(boolean isLoop) {
        mLoopList = isLoop;
    }

    public void setShuffle(boolean isShuffle) {
        if (isShuffle) {
            Collections.shuffle(mSongPrimaryList);
        } else {
            mSongPrimaryList.clear();
            mSongPrimaryList.addAll(mOriginList);
        }
    }

    public void setVolume(float level) {
        mTPlayer.setVolumePlayer(level, level);
    }

    public void setVolume(float left, float right) {
        mTPlayer.setVolumePlayer(left, right);
    }


    /**
     * i = 1 : next
     * i = -1 : previous
     *
     * @param i kiểm tra đây là next hay previous
     */
    public void changeCurrentSong(int i) {
        mCurrentSong += i;
        startSong(mCurrentSong);
    }
}
