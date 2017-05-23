package training.com.tplayerservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

import com.remote.communication.IMyAidlInterface;
import com.remote.communication.MediaEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import training.com.tplayerservice.app.Constants;
import training.com.tplayerservice.utils.FileUtils;
import training.com.utils.LogUtils;


public class TPlayerService extends Service implements PlayerManager.IOnSongChangeListener {
    public static final String TAG = "[ThoNH]";

    private PlayerManager mPlayerManager;

    private Notification status;
    private RemoteViews views;
    private RemoteViews bigViews;
    private NotificationManager mNotificationManager;

    public TPlayerService() {
        LogUtils.printLog("Service_Contructor");
        mPlayerManager = new PlayerManager(this, this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.printLog("Service_onCreate");
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.printLog("Service_onBind");
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.printLog("Service_onStartCommand");

        if (intent != null) {
            String action = intent.getAction();

            if (action.equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
                showNotification();

            } else if (action.equals(Constants.ACTION.PREV_ACTION)) {

                mPlayerManager.previousSong();

            } else if (action.equals(Constants.ACTION.PLAY_ACTION)) {

                mPlayerManager.changePlayStatus();
                updateStatus();

            } else if (action.equals(Constants.ACTION.NEXT_ACTION)) {
                mPlayerManager.nextSong();

            } else if (action.equals(
                    Constants.ACTION.STOPFOREGROUND_ACTION)) {

                removeNotification();
            }

            if (views != null && bigViews != null && mPlayerManager.getPlaylist().size() != 0) {
                MediaEntity entity = mPlayerManager.getCurrentSong();
                updateView(entity);
            }

        }
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.printLog("Service_onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        LogUtils.printLog("Service_onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtils.printLog("Service_onDestroy");
        super.onDestroy();
    }


    private IMyAidlInterface.Stub iBinder = new IMyAidlInterface.Stub() {

        @Override
        public int getAudioSS() throws RemoteException {
            return mPlayerManager.getAudioSS();
        }

        @Override
        public void setBassBoost(int id) throws RemoteException {
            mPlayerManager.attachEffPresetReverb(id);
        }

        @Override
        public void setSong(MediaEntity song) throws RemoteException {
            LogUtils.printLog("Service_onDestroy");
            mPlayerManager.playSong(song);
        }

        @Override
        public void startSongPosition(int position) throws RemoteException {
            LogUtils.printLog("Service_startSongPosition = " + position);
            mPlayerManager.startSong(position);
        }

        @Override
        public void startSong(MediaEntity entity) throws RemoteException {
            mPlayerManager.startSong(entity);
        }

        @Override
        public void setPlayList(List<MediaEntity> playLists) throws RemoteException {
            LogUtils.printLog("Service_setPlayList : size = " + playLists.size());
            mPlayerManager.setListSong(playLists);
        }

        @Override
        public int getDuration() throws RemoteException {
            return mPlayerManager.getPlayerDuration();
        }

        @Override
        public boolean isPlayerPlaying() throws RemoteException {
            LogUtils.printLog("isPlayerPlaying ");
            return mPlayerManager.isPlayerPlaying();
        }

        @Override
        public boolean isPlayerStop() throws RemoteException {
            return mPlayerManager.isStop();
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            LogUtils.printLog("Service_getCurrentPosition = " + mPlayerManager.getCurrentPosition());
            return mPlayerManager.getCurrentPosition();
        }

        @Override
        public MediaEntity getCurrentSong() throws RemoteException {
            return mPlayerManager.getCurrentSong();
        }

        @Override
        public List<MediaEntity> getPlaylist() throws RemoteException {
            return mPlayerManager.getPlaylist();
        }

        @Override
        public void setPosition(int value) throws RemoteException {
            LogUtils.printLog("Service_setPosition = " + value);
            mPlayerManager.seek(value);
        }

        @Override
        public void forward() throws RemoteException {
            LogUtils.printLog("Service_forward");
            mPlayerManager.nextSong();
        }

        @Override
        public void backward() throws RemoteException {
            LogUtils.printLog("Service_backward");
            mPlayerManager.previousSong();
        }

        @Override
        public void setVolume(float value) throws RemoteException {
            LogUtils.printLog("Service_setVolume = " + value);
            mPlayerManager.setVolume(value);
        }

        @Override
        public void setShuffle(boolean isShuffle) throws RemoteException {
            LogUtils.printLog("Service_setShuffle " + isShuffle);
            mPlayerManager.setShuffle(isShuffle);
        }

        @Override
        public boolean playPause() throws RemoteException {
            LogUtils.printLog("Service_playPause");
            updateStatus();
            return mPlayerManager.changePlayStatus();
        }

        @Override
        public void repeat(int repeatType) throws RemoteException {
            mPlayerManager.setRepeat(repeatType);
        }

        @Override
        public void addNextPlaying(MediaEntity entity) throws RemoteException {
            mPlayerManager.addNextNowPlaying(entity);
        }

        @Override
        public void addEndPlaying(MediaEntity entity) throws RemoteException {
            mPlayerManager.addToEndPlaying(entity);
        }

        @Override
        public void addListNextPlaying(List<MediaEntity> entities) throws RemoteException {
            mPlayerManager.addNextNowPlaying(entities);
        }

        @Override
        public void addToEndListNowPlaying(List<MediaEntity> entities) throws RemoteException {
            mPlayerManager.addToEndPlaying(entities);
        }

        @Override
        public String download() throws RemoteException {
            MediaEntity song = mPlayerManager.getCurrentSong();
            FileUtils.downloadFile(song.data, FileUtils.PATH_DOWNLOAD + "/", song.title, ".mp3");
//            FileUtils.downloadFile(song.art,FileUtils.PATH_IMAGE,song.title,".jpg");
//            new DownloadLyric().execute(song);

            return FileUtils.PATH_DOWNLOAD + "/" + song.title + ".mp3";
        }

    };


    private void showNotification() {
// Using RemoteViews to bind custom layouts into Notification
        views = new RemoteViews(getPackageName(),
                R.layout.status_bar);
        bigViews = new RemoteViews(getPackageName(),
                R.layout.status_bar_expanded);

// showing default album image
        views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
        views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
        bigViews.setImageViewBitmap(R.id.status_bar_album_art,
                Constants.getDefaultAlbumArt(this));

        Intent notificationIntent = new Intent();

        // if Player isPlaying --> start to PlayerActivity
        // else start to MainActivity

        if (mPlayerManager.getPlaylist().size() != 0) {
            notificationIntent.setClassName("training.com.tplayer", "training.com.tplayer.ui.player.PlayerActivity");
            notificationIntent.setComponent(new ComponentName("training.com.tplayer", "training.com.tplayer.ui.player.PlayerActivity"));
            notificationIntent.setAction("android.intent.action.MAIN22");
        } else {
            notificationIntent.setClassName("training.com.tplayer", ".ui.main.MainActivity");
            notificationIntent.setComponent(new ComponentName("training.com.tplayer", ".ui.main.MainActivity"));
            notificationIntent.setAction("android.intent.action.MAIN");
        }

        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent previousIntent = new Intent(this, TPlayerService.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, TPlayerService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, TPlayerService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, TPlayerService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);

        views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

        views.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);

        views.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);

        views.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);

        views.setImageViewResource(R.id.status_bar_play,
                R.drawable.apollo_holo_dark_pause);
        bigViews.setImageViewResource(R.id.status_bar_play,
                R.drawable.apollo_holo_dark_pause);

        views.setTextViewText(R.id.status_bar_track_name, "Loading...");
        bigViews.setTextViewText(R.id.status_bar_track_name, "Loading...");

        views.setTextViewText(R.id.status_bar_artist_name, "");
        bigViews.setTextViewText(R.id.status_bar_artist_name, "");


        status = new Notification.Builder(this).build();
        status.contentView = views;
        status.bigContentView = bigViews;
        status.flags = Notification.FLAG_ONGOING_EVENT;
        status.icon = R.drawable.ic_launcher;
        status.contentIntent = pendingIntent;
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);

    }

    private void updateStatus() {
        if (!mPlayerManager.isPlayerPlaying()) {
            views.setImageViewResource(R.id.status_bar_play,
                    R.drawable.apollo_holo_dark_play);
            bigViews.setImageViewResource(R.id.status_bar_play,
                    R.drawable.apollo_holo_dark_play);
        } else {
            views.setImageViewResource(R.id.status_bar_play,
                    R.drawable.apollo_holo_dark_pause);
            bigViews.setImageViewResource(R.id.status_bar_play,
                    R.drawable.apollo_holo_dark_pause);
        }
        mNotificationManager.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
    }

    private void updateView(MediaEntity entity) {
        views.setTextViewText(R.id.status_bar_track_name, entity.title);
        bigViews.setTextViewText(R.id.status_bar_track_name, entity.title);

        views.setTextViewText(R.id.status_bar_artist_name, entity.artist);
        bigViews.setTextViewText(R.id.status_bar_artist_name, entity.artist);

        bigViews.setImageViewBitmap(R.id.status_bar_album_art,
                Constants.getImageNoti(this, entity.art));
    }

    private void removeNotification() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE);
    }

    @Override
    public void onChange(MediaEntity mediaEntity) {

        updateView(mediaEntity);
        updateStatus();
        mNotificationManager.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
    }


    private class DownloadLyric extends AsyncTask<MediaEntity, Void, Void> {

        @Override
        protected Void doInBackground(MediaEntity... params) {
            String text;
            File file;
            try {
                if (params[0] != null && !TextUtils.isEmpty(params[0].lyric)) {

//                    "lyric":"http://static.mp3.zdn.vn/lyrics/2017/04/10/437fd8dab336d63566e90d61e2dde4ea_1075841896.lrc"

                    if (params[0].lyric.contains("http://")) {
                        URL url = new URL(params[0].lyric);
                        text = new Scanner(url.openStream()).useDelimiter("\\A").next();

                    } else {
                        text = params[0].lyric;
                    }

                    file = new File(FileUtils.PATH_LYRIC + "/" + params[0].title + ".lyric");

                    FileOutputStream fos;
                    byte[] data = new String(text.toString()).getBytes();

                    try {
                        fos = new FileOutputStream(file);
                        fos.write(data);
                        fos.flush();
                        fos.close();

                        params[0].lyric = text;

                    } catch (FileNotFoundException e) {

                    } catch (IOException e) {

                    }

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
