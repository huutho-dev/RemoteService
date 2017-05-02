package training.com.tplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.remote.communication.PlaylistEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThoNH on 4/27/2017.
 */

public class SourceTablePlaylist extends DatabaseSource<PlaylistEntity> {

    private static SourceTablePlaylist mInstance;

    public synchronized static SourceTablePlaylist getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SourceTablePlaylist(context);
        }
        return mInstance;
    }

    private SourceTablePlaylist(Context context) {
        super(context);
    }

    @Override
    public List<PlaylistEntity> getList() {
        List<PlaylistEntity> entities = new ArrayList<>();
        openDb();
        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_PLAYLIST, null, null, null, null, null, null);

        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MID);
        int nameIndex = cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn._NAME);
        int dateAddedIndex = cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.DATE_ADDED);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            PlaylistEntity entity = new PlaylistEntity();
            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.name = cursor.getString(nameIndex);
            entity.dateAdded = Integer.parseInt(cursor.getString(dateAddedIndex));

            entities.add(entity);

            cursor.moveToNext();
        }
        closeDb();
        if (!cursor.isClosed())
            cursor.close();
        return entities;
    }

    @Override
    public PlaylistEntity getRow(String selection, String[] selectionArgs) {
        PlaylistEntity entity = new PlaylistEntity();
        openDb();
        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_PLAYLIST, null, selection + "=?", selectionArgs, null, null, null);

        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MID);
        int nameIndex = cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn._NAME);
        int dateAddedIndex = cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.DATE_ADDED);

        cursor.moveToFirst();

        entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
        entity.name = cursor.getString(nameIndex);
        entity.dateAdded = Integer.parseInt(cursor.getString(dateAddedIndex));

        closeDb();
        if (!cursor.isClosed())
            cursor.close();
        return entity;
    }

    @Override
    public long insertRow(PlaylistEntity entity) {
        openDb();
        long insert = getSqlDb().insert(DataBaseUtils.TABLE_PLAYLIST, null, convertEntity2ContentValue(entity));
        closeDb();
        return insert;
    }

    @Override
    public int deleteRow(PlaylistEntity entity) {
        openDb();
        int del = getSqlDb().delete(DataBaseUtils.TABLE_PLAYLIST,
                DataBaseUtils.DbStorePlaylistColumn.MID + "=?",
                new String[]{String.valueOf(entity.mId)});
        closeDb();
        return del;
    }

    @Override
    public int updateRow(PlaylistEntity entity) {
        openDb();
        int update =  getSqlDb().update(DataBaseUtils.TABLE_PLAYLIST,
                convertEntity2ContentValue(entity),
                DataBaseUtils.DbStorePlaylistColumn.MID + "=?",
                new String[]{String.valueOf(entity.mId)});

        closeDb();
        return update;
    }

    @Override
    public ContentValues convertEntity2ContentValue(PlaylistEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DataBaseUtils.DbStorePlaylistColumn._NAME, entity.name);
        values.put(DataBaseUtils.DbStorePlaylistColumn.DATE_ADDED, entity.dateAdded);
        return values;
    }
}
