package training.com.tplayer.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;

import com.google.gson.Gson;
import com.remote.communication.MediaEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import training.com.tplayer.base.BaseEntity;

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
            .getExternalStorageDirectory().toString()+ "/TPlayer/ScreenShot";



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

    public static String captureScreen(AppCompatActivity activity) {
        try {
            Date now = new Date();
            DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

            File parent  = new File(PATH_SCREEN_SHOOT);
            if (!parent.exists()) parent.mkdirs();

            String mPath = PATH_SCREEN_SHOOT + "/" + now.getTime() + ".jpg";

            // create bitmap screen capture
            View view = activity.getWindow().getDecorView().getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            // save image
            File file = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            outputStream.flush();
            outputStream.close();

            LogUtils.printLog("captureScreen : " + mPath);

            return mPath;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }

    public static void downloadFile(String link, String dir, String name, String suffix) {
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
            File file = new File(dir + name + suffix);
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


    public static class CacheFile<E extends BaseEntity> {


        public static boolean isFileExist(Context context, String name) {
            File myFile = new File(context.getApplicationContext().getCacheDir(), name + ".tmp");
            return myFile.exists();
        }


        public void writeCacheFile(Context context, String name, List<E> entities) {

            String data = new Gson().toJson(entities);
            LogUtils.printLog(data);

            try {
                File myFile = new File(context.getApplicationContext().getCacheDir(), name + ".tmp");
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);
                myOutWriter.close();
                fOut.close();
                LogUtils.printLog("Path file : " + myFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public <E extends BaseEntity> List<E> readCacheFile(Context context, String name, Class<E[]> clazz) {

            String s = "";
            String fileContent = "";
            try {
                File myFile = new File(context.getApplicationContext().getCacheDir(), name + ".tmp");
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));

                while ((s = myReader.readLine()) != null) {
                    fileContent += s + "\n";
                }

                LogUtils.printLog("File content :" + fileContent);

                myReader.close();

                E[] arr = new Gson().fromJson(fileContent, clazz);
                return Arrays.asList(arr);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
