package training.com.tplayerservice.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.media.MediaScannerConnection;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;

import com.remote.communication.MediaEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import training.com.utils.LogUtils;

/**
 * Created by ThoNH on 12/04/2017.
 */

public class FileUtils {
    public static final String PATH_DOWNLOAD = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath() + "/" + "TPlayer" + "/" + "Songs";

    public static final String PATH_LYRIC = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath() + "/" + "TPlayer" + "/" + "Lyric";

    public static final String PATH_IMAGE = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath() + "/" + "TPlayer" + "/" + "Image";

    public static final String PATH_SCREEN_SHOOT = Environment
            .getExternalStorageDirectory().toString() + "/TPlayer/ScreenShot";


    public interface IOnScanMediaComplete {
        void scanComplete(String path, Uri uri);
    }

    public static void setAudioRington(Context context, MediaEntity entity) {
        File k = new File(entity.data, entity.displayName); // path is a file playing

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, entity.title); //You will have to populate
        values.put(MediaStore.MediaColumns.SIZE, 215454);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.ARTIST, entity.artist); //You will have to populate this
        values.put(MediaStore.Audio.Media.DURATION, entity.duration);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        //Insert it into the database
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
        Uri newUri = context.getApplicationContext().getContentResolver().insert(uri, values);

        RingtoneManager.setActualDefaultRingtoneUri(
                context,
                RingtoneManager.TYPE_RINGTONE,
                newUri
        );
    }


    public static String downloadFile(final String link, final String dir, final String name, final String suffix) {

        new AsyncTask<Void, Void, String>() {
            File file ;

            @Override
            protected String doInBackground(Void... params) {

                InputStream input = null;
                OutputStream output = null;
                HttpURLConnection connection = null;
                try {

                    // create dir
                    File folder = new File(dir);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }

                    // create file
                     file = new File(dir + name + suffix);
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    URL url = new URL(link);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();


                    int fileLength = connection.getContentLength();

                    input = connection.getInputStream();
                    output = new FileOutputStream(file);


                    byte data[] = new byte[4096];
                    int count;
                    while ((count = input.read(data)) != -1) {
                        if (fileLength > 0)
                            output.write(data, 0, count);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (output != null)
                            output.close();
                        if (input != null)
                            input.close();
                    } catch (IOException ignored) {
                    }

                    if (connection != null)
                        connection.disconnect();
                }


                return file.getPath();
            }

        }.execute();

        return dir + name + suffix ;

    }


    public static void scanFileAfterDownloaded(final Context context, File file, final IOnScanMediaComplete scanMediaComplete) {
        MediaScannerConnection.scanFile(
                context.getApplicationContext(),
                new String[]{file.getAbsolutePath()},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        LogUtils.printLog("file " + path + " was scanned seccessfully: " + uri);
                        Cursor cursor = context.getApplicationContext().getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        DatabaseUtils.dumpCurrentRow(cursor);
                        scanMediaComplete.scanComplete(path, uri);
                    }
                });
    }

}
