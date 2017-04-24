package training.com.tplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.media.MediaScannerConnection;
import android.net.Uri;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by hnc on 12/04/2017.
 */

public class FileUtils {



    public void downloadFile(String link, String dir, String name, String suffix) {
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


    public void scanFileAfterDownloaded(final Context applicationContext, File file) {
        MediaScannerConnection.scanFile(
                applicationContext,
                new String[]{file.getAbsolutePath()},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        LogUtils.printLog("file " + path + " was scanned seccessfully: " + uri);
                        Cursor cursor = applicationContext.getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        DatabaseUtils.dumpCurrentRow(cursor);
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

        public <E extends BaseEntity>List<E> readCacheFile(Context context, String name, Class<E[]> clazz) {

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

                LogUtils.printLog("File content :"+fileContent);

                myReader.close();

                E[] arr = new Gson().fromJson(fileContent, clazz);
                return Arrays.asList(arr);

//                TypeToken<ArrayList<E>> token = new TypeToken<ArrayList<E>>(){};
//                return new Gson().fromJson(fileContent, token.getType());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
