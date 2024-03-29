package training.com.tplayer.ui.player;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.remote.communication.IMyAidlInterface;
import com.remote.communication.MediaEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import training.com.tplayer.base.mvp.BasePresenterImpl;
import training.com.tplayer.database.DataBaseUtils;
import training.com.tplayer.database.DatabaseScanner;
import training.com.tplayer.database.SourceTableMedia;
import training.com.tplayer.ui.audioeffect.SoundEffectActivity;
import training.com.tplayer.utils.FileUtils;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 4/16/2017.
 */

public class PlayerPresenterImpl extends BasePresenterImpl<PlayerActivity, PlayerInteractorImpl>
        implements PlayerContracts.Presenter, PlayerContracts.IOnPlayerListener {

    private IMyAidlInterface mService;

    @Override
    public void onSubcireView(PlayerActivity view) {
        this.mView = view;
    }

    @Override
    public void onSubcireInteractor(PlayerInteractorImpl interactor) {
        this.mInteractor = interactor;
        mInteractor.setListener(this);
    }

    @Override
    public void setPlayLists(List<MediaEntity> playLists) {
        try {
            mService.setPlayList(playLists);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean playPause() {
        try {
            return mService.playPause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void forward() {
        try {
            mService.forward();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void backward() {
        try {
            mService.backward();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void repeat(int repeatType) {
        try {
            mService.repeat(repeatType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setShuffle(boolean isShuffle) {
        try {
            mService.setShuffle(isShuffle);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVolume(float volume) {
        try {
            mService.setVolume(volume);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MediaEntity> getNowPlaylist() {
        try {
            return mService.getPlaylist();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void seekToPosition(int value) {
        try {
            mService.setPosition(value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCurrentPosition() {
        try {
            return mService.getCurrentPosition();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getDuration() {
        try {
            return mService.getDuration();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void startSongPosition(int position) {
        try {
            mService.startSongPosition(position);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRemotePlayNewSong(MediaEntity song) {
        mView.onRemotePlayNewSong(song);
    }

    @Override
    public void onBufferPlayNewSong(int percent) {
        LogUtils.printLog("percen = " + percent);
        mView.onBufferPlaySong(percent);
    }

    @Override
    public void onRemoteCompleteASong() {
        mView.onRemotePlayCompleteASong();
    }


    @Override
    public void onDownloadLyricComplete(File lyric) {
        mView.onLoadLyricComplete(lyric);
    }


    @Override
    public void onUnregisterBroadcast() {
        mInteractor.onUnregisterBroadcast();
    }

    @Override
    public void onDownloadClick(final Context context) {
        try {
            if (mService != null){
                String path = mService.download();
                File file = new File(path);
                FileUtils.scanFileAfterDownloaded(context, file, new FileUtils.IOnScanMediaComplete() {
                    @Override
                    public void scanComplete(String path, Uri uri) {
                        try {
                            new DatabaseScanner(context).insertAfterDownload(new File(path));
                            MediaEntity entity = SourceTableMedia.getInstance(context).getRow(DataBaseUtils.DbStoreMediaColumn._DATA + "=?", new String[]{path});
                            if (mService.getCurrentSong().lyric != null &&
                                    TextUtils.isEmpty(mService.getCurrentSong().lyric)) {

                                File pathLyric = new File(FileUtils.PATH_LYRIC + "/" + entity.title + ".lyric");
                                FileOutputStream fos;
                                byte[] data = new String(mService.getCurrentSong().lyric.toString()).getBytes();

                                try {
                                    fos = new FileOutputStream(pathLyric);
                                    fos.write(data);
                                    fos.flush();
                                    fos.close();

                                    entity.lyric = pathLyric.getPath();
                                    int update =  SourceTableMedia.getInstance(context).updateRow(entity);
                                    if (update!=0){
                                        Toast.makeText(context,"Download complete",Toast.LENGTH_SHORT).show();
                                    }

                                } catch (FileNotFoundException e) {

                                } catch (IOException e) {

                                }

                            }


                            LogUtils.printLog(mService.getCurrentSong().lyric);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEqualizerClick(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, SoundEffectActivity.class));
    }

    @Override
    public void onCaptureScreenClick(AppCompatActivity activity) {
        String mPathImage = FileUtils.captureScreen(activity);
        if (mPathImage.equals("null")) {
            Toast.makeText(activity, "Cannot capture image, pls try again !", Toast.LENGTH_SHORT).show();
            return;
        }
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(BitmapFactory.decodeFile(mPathImage))
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

        boolean canShow = shareDialog.canShow(content, ShareDialog.Mode.AUTOMATIC);
        if (!canShow) {
            Toast.makeText(activity, "Cannot find fb app on your phone, pls install fb application and try again !", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    public void setService(IMyAidlInterface playerService) {
        this.mService = playerService;
    }
}
