package training.com.tplayerservice;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.remote.communication.Song;

import java.util.ArrayList;
import java.util.List;

import training.com.tplayerservice.app.Config;

/**
 * Created by HuuTho on 4/9/2017.
 */

public class PlayerManager implements MediaPlayer.OnCompletionListener {
    private Context mContext;
    private TPlayer mTPlayer;
    private ArrayList<Song> mPlayLists = new ArrayList<>();

    private int mCurrentSong;
    private boolean mLoopList;

    public PlayerManager(Context context) {
        this.mContext = context;
        this.mTPlayer = new TPlayer(context);
        this.mTPlayer.setOnCompletionListenerPlayer(this);
        this.mTPlayer.setOnCompletionListener(this);
    }

    public void setListSong(List<Song> origins) {
        mPlayLists.clear();
        mPlayLists.addAll(origins);
        startSong(0);
    }

    public void startSong(int position) {
        Song song = mPlayLists.get(position);
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
            mTPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();

                    Song song = mPlayLists.get(mCurrentSong);
                    song.duration = getDuration();

                    Intent intent = new Intent();
                    intent.setAction(Config.ACTION_PLAYER_START_PLAY);
                    intent.putExtra(Config.ACTION_PLAYER_START_PLAY, song);
                    mContext.getApplicationContext().sendBroadcast(intent);
                }
            });
        } else {
            mTPlayer.setDataSourcePlayer(mContext, Uri.parse(data));
            mTPlayer.preparePlayer();
            mTPlayer.startPlayer();
        }
    }

    public boolean changePlayStatus() {
        if (mTPlayer.isPlayerPlaying()) {
            mTPlayer.pausePlayer();
            return false;
        }
        mTPlayer.startPlayer();
        return true;
    }

    public int getDuration() {
        return mTPlayer.getDurationPlayer();
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


    public void setVolume(float level) {
        mTPlayer.setVolumePlayer(level, level);
    }

    public void setVolume(float left, float right) {
        mTPlayer.setVolumePlayer(left, right);
    }

    public int getCurrentPosition() {
        return mTPlayer.getCurrentPosition();
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

    public void playSong(Song song) {
        boolean isSongInPlayList = mPlayLists.contains(song);
        if (isSongInPlayList) {
            mCurrentSong = mPlayLists.indexOf(song);
            mPlayLists.get(mCurrentSong).isPlaying = true;
        }
        startSong(song._data);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Intent intent = new Intent();
        intent.setAction(Config.ACTION_PLAYER_COMPLETE);
        mContext.getApplicationContext().sendBroadcast(intent);
    }
}
