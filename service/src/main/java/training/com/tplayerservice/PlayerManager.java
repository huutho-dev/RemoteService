package training.com.tplayerservice;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.remote.communication.MediaEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import training.com.tplayerservice.app.Config;

/**
 * Created by HuuTho on 4/9/2017.
 */

public class PlayerManager implements MediaPlayer.OnCompletionListener {

    public static final int REPEAT_NO = 0;
    public static final int REPEAT_ONE = 1;
    public static final int REPEAT_ALL = 2;

    private Context mContext;
    private TPlayer mTPlayer;
    private ArrayList<MediaEntity> mPlayLists = new ArrayList<>();

    private int mCurrentSong;

    private boolean mLoopList;

    private boolean mIsShuffle;

    private boolean mIsStop = true;

    private int mCurrentRepeat;

    private Random random = new Random();

    public PlayerManager(Context context) {
        this.mContext = context;
        this.mTPlayer = new TPlayer(context);
        this.mTPlayer.setOnCompletionListenerPlayer(this);
        this.mTPlayer.setOnCompletionListener(this);

    }

    public void setListSong(List<MediaEntity> origins) {
        mPlayLists.clear();
        mPlayLists.addAll(origins);
    }

    public void startSong(int position) {
        mCurrentSong = position;
        MediaEntity song = mPlayLists.get(position);
        String data = song.data;
        startSong(data);

        song.isPlaying = true;

        mIsStop = false;
    }

    public void startSong(MediaEntity entity) {
        for (MediaEntity song : mPlayLists) {
            if (entity.mId == song.mId) {
                startSong(mPlayLists.indexOf(song));
                return;
            }
        }
    }


    private void startSong(String data) {
        mTPlayer.resetPlayer();
        final MediaEntity song = mPlayLists.get(mCurrentSong);
        if (data.startsWith("http://")) {
            mTPlayer.setDataSourcePlayer(data);
            mTPlayer.setPlayerStreamOverNetwork(mContext);
            mTPlayer.prepareAsyncPlayer();

            mTPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mp) {
                    mp.start();
                    new DownloadLyric().execute(song);
                }
            });
        } else {
            mTPlayer.setDataSourcePlayer(mContext, Uri.parse(data));
            mTPlayer.preparePlayer();
            mTPlayer.startPlayer();
        }

        Intent intent = new Intent();
        intent.setAction(Config.ACTION_PLAYER_START_PLAY);
        intent.putExtra(Config.ACTION_PLAYER_START_PLAY, song);
        intent.putExtra(Config.ACTION_PLAYER_BUFFER, mTPlayer.getDuration());
        mContext.getApplicationContext().sendBroadcast(intent);
    }

    public boolean changePlayStatus() {
        if (mTPlayer.isPlayerPlaying()) {
            mTPlayer.pausePlayer();
            return false;
        }
        mTPlayer.startPlayer();
        return true;
    }

    public int getPlayerDuration() {
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


    public void setVolume(float level) {
        mTPlayer.setVolumePlayer(level, level);
    }

    public void setVolume(float left, float right) {
        mTPlayer.setVolumePlayer(left, right);
    }

    public int getCurrentPosition() {
        return mTPlayer.getCurrentPosition();
    }

    public List<MediaEntity> getPlaylist() {
        return mPlayLists;
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

    public void playSong(MediaEntity song) {
        boolean isSongInPlayList = mPlayLists.contains(song);
        if (isSongInPlayList) {
            mCurrentSong = mPlayLists.indexOf(song);
            mPlayLists.get(mCurrentSong).isPlaying = true;
        }
        startSong(song.data);
    }


    public void addToEndPlaying(List<MediaEntity> entities) {
        mPlayLists.addAll(entities);
    }

    public void addNextNowPlaying(List<MediaEntity> entities) {
        mPlayLists.addAll(mCurrentSong +1, entities);
    }

    public void addNextNowPlaying(MediaEntity entity) {
        mPlayLists.add(mCurrentSong+1, entity);
    }

    public void addToEndPlaying(MediaEntity entity) {
        mPlayLists.add(entity);
    }

    public void setShuffle(boolean isShuffle) {
        this.mIsShuffle = isShuffle;
    }

    public void setRepeat(int repeatType) {
        mCurrentRepeat = repeatType;
    }

    public MediaEntity getCurrentSong() {
        return mPlayLists.get(mCurrentSong);
    }

    public boolean isStop() {
        return mIsStop;
    }

    public boolean isPlayerPlaying() {
        return mTPlayer.isPlayerPlaying();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        mPlayLists.get(mCurrentSong).isPlaying = false;
        mIsStop = true;

        proccessAfterComplete(mIsShuffle, mCurrentRepeat);

        Intent intent = new Intent();
        intent.setAction(Config.ACTION_PLAYER_COMPLETE);
        mContext.getApplicationContext().sendBroadcast(intent);
    }


    private void proccessAfterComplete(boolean isShuffle, int currentRepeat) {
        if (!isShuffle) {

            switch (currentRepeat) {
                case REPEAT_NO:
                    setLoop(false);
                    if (mCurrentSong != mPlayLists.size() - 1) {
                        mCurrentSong++;
                        startSong(mCurrentSong);
                    } else {
                        mTPlayer.resetPlayer();
                    }
                    break;

                case REPEAT_ONE:
                    startSong(mCurrentSong);
                    break;

                case REPEAT_ALL:
                    setLoop(false);
                    if (mCurrentSong == mPlayLists.size() - 1) {
                        mCurrentSong = 0;
                        startSong(mCurrentSong);
                    } else {
                        mCurrentSong++;
                        startSong(mCurrentSong);
                    }

                    break;
            }

        } else {

            switch (currentRepeat) {
                case REPEAT_NO:
                    setLoop(false);
                    mCurrentSong = random.nextInt(mPlayLists.size() - 1);
                    startSong(mCurrentSong);
                    break;

                case REPEAT_ONE:
                    startSong(mCurrentSong);
                    break;

                case REPEAT_ALL:
                    setLoop(false);
                    mCurrentSong = random.nextInt(mPlayLists.size() - 1);
                    startSong(mCurrentSong);
                    setLoop(true);
                    break;
            }


        }
    }


    private class DownloadLyric extends AsyncTask<MediaEntity, Void, Void> {

        @Override
        protected Void doInBackground(MediaEntity... params) {
            try {
                if (params[0] != null && !TextUtils.isEmpty(params[0].lyric)) {
                    URL url = new URL(params[0].lyric);
                    String text = new Scanner(url.openStream()).useDelimiter("\\A").next();

                    params[0].lyric = text;

                    Intent intent = new Intent();
                    intent.setAction(Config.ACTION_PLAYER_DOWNLOAD_LYRIC);
                    intent.putExtra(Config.ACTION_PLAYER_DOWNLOAD_LYRIC, text);
                    mContext.getApplicationContext().sendBroadcast(intent);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
