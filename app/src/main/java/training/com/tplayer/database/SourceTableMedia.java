package training.com.tplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.provider.MediaStore;

import com.remote.communication.MediaEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.ui.entity.offline.FolderEntity;
import training.com.tplayer.utils.LogUtils;


/**
 * Created by ThoNH on 4/27/2017.
 */

public class SourceTableMedia extends DatabaseSource<MediaEntity> {

    private static SourceTableMedia mInstance;

    public synchronized static SourceTableMedia getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SourceTableMedia(context);
        }
        return mInstance;
    }

    private SourceTableMedia(Context context) {
        super(context);

    }

    @Override
    public List<MediaEntity> getList() {
        List<MediaEntity> entities = new ArrayList<>();
        openDb();
        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_MEDIA, null, null, null, null, null, null);
        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn.MID);
        int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ID);
        int dataIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATA);
        int displayNameIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DISPLAY_NAME);
        int sizeIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._SIZE);
        int mimeTypeIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._MIME_TYPE);
        int dateAddedIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATE_ADDED);
        int titleIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._TITLE);
        int durationIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DURATION);
        int artistIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ARTIST_ID);
        int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ARTIST);
        int albumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ALBUM);
        int albumIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ALBUM_ID);
        int isFavoriteIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._IS_FAVORITE);
        int lyricIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATA_LYRIC);
        int artIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ART_MEDIA);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            MediaEntity entity = new MediaEntity();
            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.id = Integer.parseInt(cursor.getString(idIndex));
            entity.data = cursor.getString(dataIndex);
            entity.displayName = cursor.getString(displayNameIndex);
            entity.size = Integer.parseInt(cursor.getString(sizeIndex));
            entity.mimeType = cursor.getString(mimeTypeIndex);
            entity.dateAdded = Integer.parseInt(cursor.getString(dateAddedIndex));
            entity.title = cursor.getString(titleIndex);
            entity.duration = Integer.parseInt(cursor.getString(durationIndex));
            entity.artistId = Integer.parseInt(cursor.getString(artistIdIndex));
            entity.artist = cursor.getString(artistIndex);
            entity.album = cursor.getString(albumIndex);
            entity.albumId = Integer.parseInt(cursor.getString(albumIdIndex));
            entity.isFavorite = Boolean.parseBoolean(cursor.getString(isFavoriteIndex));
            entity.lyric = cursor.getString(lyricIndex);
            entity.art = cursor.getString(artIndex);
            entities.add(entity);

            cursor.moveToNext();
        }
        closeDb();
        if (!cursor.isClosed())
            cursor.close();
        return entities;
    }

    @Override
    public MediaEntity getRow(String selection, String[] selectionArgs) {
        MediaEntity mediaEntity = new MediaEntity();

        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_MEDIA, null, selection, selectionArgs, null, null, null);
        openDb();
        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn.MID);
        int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ID);
        int dataIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATA);
        int displayNameIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DISPLAY_NAME);
        int sizeIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._SIZE);
        int mimeTypeIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._MIME_TYPE);
        int dateAddedIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATE_ADDED);
        int titleIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._TITLE);
        int durationIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DURATION);
        int artistIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ARTIST_ID);
        int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ARTIST);
        int albumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ALBUM);
        int albumIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ALBUM_ID);
        int isFavoriteIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._IS_FAVORITE);
        int lyricIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATA_LYRIC);
        int artIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ART_MEDIA);

        cursor.moveToFirst();
        MediaEntity entity = new MediaEntity();
        entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
        entity.id = Integer.parseInt(cursor.getString(idIndex));
        entity.data = cursor.getString(dataIndex);
        entity.displayName = cursor.getString(displayNameIndex);
        entity.size = Integer.parseInt(cursor.getString(sizeIndex));
        entity.mimeType = cursor.getString(mimeTypeIndex);
        entity.dateAdded = Integer.parseInt(cursor.getString(dateAddedIndex));
        entity.title = cursor.getString(titleIndex);
        entity.duration = Integer.parseInt(cursor.getString(durationIndex));
        entity.artistId = Integer.parseInt(cursor.getString(artistIdIndex));
        entity.artist = cursor.getString(artistIndex);
        entity.album = cursor.getString(albumIndex);
        entity.albumId = Integer.parseInt(cursor.getString(albumIdIndex));
        entity.isFavorite = Boolean.parseBoolean(cursor.getString(isFavoriteIndex));
        entity.lyric = cursor.getString(lyricIndex);
        entity.art = cursor.getString(artIndex);


        DatabaseUtils.dumpCurrentRow(cursor);

        closeDb();
        if (!cursor.isClosed())
            cursor.close();
        return mediaEntity;
    }

    @Override
    public long insertRow(MediaEntity entity) {
        openDb();
        long insert = getSqlDb().insert(DataBaseUtils.TABLE_MEDIA, null, convertEntity2ContentValue(entity));
        closeDb();
        return insert;
    }

    @Override
    public int deleteRow(MediaEntity entity) {
        openDb();
        int del = getSqlDb().delete(DataBaseUtils.TABLE_MEDIA,
                DataBaseUtils.DbStoreMediaColumn.MID + "=?",
                new String[]{String.valueOf(entity.mId)});
        closeDb();
        return del;
    }

    @Override
    public int updateRow(MediaEntity entity) {
        openDb();
        int update = getSqlDb().update(DataBaseUtils.TABLE_MEDIA,
                convertEntity2ContentValue(entity),
                DataBaseUtils.DbStoreMediaColumn._ID + "=?",
                new String[]{String.valueOf(entity.id)});
        LogUtils.printLog(entity.toString() +"\n" + update);
        closeDb();
        return update;
    }

    public boolean isHasData() {
        openDb();
        Cursor cursor = getSqlDb().rawQuery("SELECT count(*) FROM " + DataBaseUtils.TABLE_MEDIA, null);
        cursor.moveToFirst();
        int numberRow = cursor.getInt(0);
        cursor.close();
        return (numberRow > 0);
    }

    public List<FolderEntity> getListFolder() {

        String folderName = "";
        String newFolderName = "";

        List<FolderEntity> entities = new ArrayList<>();
        openDb();
        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_MEDIA,
                new String[]{DataBaseUtils.DbStoreMediaColumn._DATA,
                        DataBaseUtils.DbStoreMediaColumn._DISPLAY_NAME}
                , null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String data = cursor.getString(cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATA));
            String displayName = cursor.getString(cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DISPLAY_NAME));
            newFolderName = data.replace("/" + displayName, "");

            if (!newFolderName.equals(folderName) && !newFolderName.contains("Android")) {
                folderName = newFolderName;
                File file = new File(folderName);
                entities.add(new FolderEntity(folderName, folderName, file.listFiles().length));
            }
            cursor.moveToNext();
        }
        closeDb();
        cursor.close();
        return entities;
    }

    @Override
    public ContentValues convertEntity2ContentValue(MediaEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DataBaseUtils.DbStoreMediaColumn._ID, entity.id);
        values.put(DataBaseUtils.DbStoreMediaColumn._DATA, entity.data);
        values.put(DataBaseUtils.DbStoreMediaColumn._DISPLAY_NAME, entity.displayName);
        values.put(DataBaseUtils.DbStoreMediaColumn._SIZE, entity.size);
        values.put(DataBaseUtils.DbStoreMediaColumn._MIME_TYPE, entity.mimeType);
        values.put(DataBaseUtils.DbStoreMediaColumn._DATE_ADDED, entity.dateAdded);
        values.put(DataBaseUtils.DbStoreMediaColumn._TITLE, entity.title);
        values.put(DataBaseUtils.DbStoreMediaColumn._DURATION, entity.duration);
        values.put(DataBaseUtils.DbStoreMediaColumn._ARTIST_ID, entity.artistId);
        values.put(DataBaseUtils.DbStoreMediaColumn._ARTIST, entity.artist);
        values.put(DataBaseUtils.DbStoreMediaColumn._ALBUM, entity.album);
        values.put(DataBaseUtils.DbStoreMediaColumn._ALBUM_ID, entity.albumId);
        values.put(DataBaseUtils.DbStoreMediaColumn._IS_FAVORITE, entity.isFavorite);
        values.put(DataBaseUtils.DbStoreMediaColumn._DATA_LYRIC, entity.lyric);
        values.put(DataBaseUtils.DbStoreMediaColumn._ART_MEDIA, entity.art);
        return values;
    }


    public List<MediaEntity> getList(String selection, String[] selectionArgs) {
        List<MediaEntity> entities = new ArrayList<>();
        openDb();
        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_MEDIA, null, selection + "=?", selectionArgs, null, null, null);
        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn.MID);
        int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ID);
        int dataIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATA);
        int displayNameIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DISPLAY_NAME);
        int sizeIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._SIZE);
        int mimeTypeIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._MIME_TYPE);
        int dateAddedIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATE_ADDED);
        int titleIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._TITLE);
        int durationIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DURATION);
        int artistIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ARTIST_ID);
        int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ARTIST);
        int albumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ALBUM);
        int albumIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ALBUM_ID);
        int isFavoriteIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._IS_FAVORITE);
        int lyricIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATA_LYRIC);
        int artIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ART_MEDIA);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            MediaEntity entity = new MediaEntity();
            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.id = Integer.parseInt(cursor.getString(idIndex));
            entity.data = cursor.getString(dataIndex);
            entity.displayName = cursor.getString(displayNameIndex);
            entity.size = Integer.parseInt(cursor.getString(sizeIndex));
            entity.mimeType = cursor.getString(mimeTypeIndex);
            entity.dateAdded = Integer.parseInt(cursor.getString(dateAddedIndex));
            entity.title = cursor.getString(titleIndex);
            entity.duration = Integer.parseInt(cursor.getString(durationIndex));
            entity.artistId = Integer.parseInt(cursor.getString(artistIdIndex));
            entity.artist = cursor.getString(artistIndex);
            entity.album = cursor.getString(albumIndex);
            entity.albumId = Integer.parseInt(cursor.getString(albumIdIndex));
            entity.isFavorite = Boolean.parseBoolean(cursor.getString(isFavoriteIndex));
            entity.lyric = cursor.getString(lyricIndex);
            entity.art = cursor.getString(artIndex);
            entities.add(entity);

            cursor.moveToNext();
        }
        closeDb();
        if (!cursor.isClosed())
            cursor.close();
        return entities;
    }


    public List<MediaEntity> getFileInFolder(String path) {
        List<MediaEntity> entities = new ArrayList<>();
        openDb();
        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_MEDIA, null, MediaStore.Audio.Media.DATA + " LIKE '" + path + "/%'", null, null, null, null);

        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn.MID);
        int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ID);
        int dataIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATA);
        int displayNameIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DISPLAY_NAME);
        int sizeIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._SIZE);
        int mimeTypeIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._MIME_TYPE);
        int dateAddedIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATE_ADDED);
        int titleIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._TITLE);
        int durationIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DURATION);
        int artistIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ARTIST_ID);
        int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ARTIST);
        int albumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ALBUM);
        int albumIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ALBUM_ID);
        int isFavoriteIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._IS_FAVORITE);
        int lyricIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._DATA_LYRIC);
        int artIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreMediaColumn._ART_MEDIA);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            MediaEntity entity = new MediaEntity();
            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.id = Integer.parseInt(cursor.getString(idIndex));
            entity.data = cursor.getString(dataIndex);
            entity.displayName = cursor.getString(displayNameIndex);
            entity.size = Integer.parseInt(cursor.getString(sizeIndex));
            entity.mimeType = cursor.getString(mimeTypeIndex);
            entity.dateAdded = Integer.parseInt(cursor.getString(dateAddedIndex));
            entity.title = cursor.getString(titleIndex);
            entity.duration = Integer.parseInt(cursor.getString(durationIndex));
            entity.artistId = Integer.parseInt(cursor.getString(artistIdIndex));
            entity.artist = cursor.getString(artistIndex);
            entity.album = cursor.getString(albumIndex);
            entity.albumId = Integer.parseInt(cursor.getString(albumIdIndex));
            entity.isFavorite = Boolean.parseBoolean(cursor.getString(isFavoriteIndex));
            entity.lyric = cursor.getString(lyricIndex);
            entity.art = cursor.getString(artIndex);
            entities.add(entity);

            cursor.moveToNext();
        }
        closeDb();
        if (!cursor.isClosed())
            cursor.close();
        return entities;
    }

}
