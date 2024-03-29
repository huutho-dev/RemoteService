package training.com.tplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import com.remote.communication.AlbumEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThoNH on 4/27/2017.
 */

public class SourceTableAlbum extends DatabaseSource<AlbumEntity> {

    private static SourceTableAlbum mInstance;

    public synchronized static SourceTableAlbum getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SourceTableAlbum(context);
        }
        return mInstance;
    }

    private SourceTableAlbum(Context context) {
        super(context);
    }

    @Override
    public List<AlbumEntity> getList() {
        List<AlbumEntity> entities = new ArrayList<>();
        openDb();
        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_ALBUM, null, null, null, null, null, null);

        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn.MID);
        int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ID);
        int albumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ALBUM);
        int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ARTIST);
        int artistIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ARTIST_ID);
        int numberOfSongIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._NUMBER_OF_SONG);
        int albumArtIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ALBUM_ART);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DatabaseUtils.dumpCurrentRow(cursor);
            AlbumEntity entity = new AlbumEntity();
            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.id = Integer.parseInt(cursor.getString(idIndex));
            entity.album = cursor.getString(albumIndex);
            entity.artist = cursor.getString(artistIndex);
            entity.artistId = Integer.parseInt(cursor.getString(artistIdIndex));
            entity.numberOfSong = Integer.parseInt(cursor.getString(numberOfSongIndex));
            entity.albumArt = cursor.getString(albumArtIndex);

            entities.add(entity);
            cursor.moveToNext();
        }
        closeDb();
        if (!cursor.isClosed())
            cursor.close();
        return entities;
    }

    public List<AlbumEntity> getList(String selection, String [] args) {
        List<AlbumEntity> entities = new ArrayList<>();
        openDb();
        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_ALBUM, null, selection , args, null, null, null);

        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn.MID);
        int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ID);
        int albumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ALBUM);
        int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ARTIST);
        int artistIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ARTIST_ID);
        int numberOfSongIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._NUMBER_OF_SONG);
        int albumArtIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ALBUM_ART);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DatabaseUtils.dumpCurrentRow(cursor);
            AlbumEntity entity = new AlbumEntity();
            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.id = Integer.parseInt(cursor.getString(idIndex));
            entity.album = cursor.getString(albumIndex);
            entity.artist = cursor.getString(artistIndex);
            entity.artistId = Integer.parseInt(cursor.getString(artistIdIndex));
            entity.numberOfSong = Integer.parseInt(cursor.getString(numberOfSongIndex));
            entity.albumArt = cursor.getString(albumArtIndex);

            entities.add(entity);
            cursor.moveToNext();
        }
        closeDb();
        if (!cursor.isClosed())
            cursor.close();
        return entities;
    }

    @Override
    public AlbumEntity getRow(String selection, String[] selectionArgs) {
        AlbumEntity entity = new AlbumEntity();
        openDb();
        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_ALBUM, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn.MID);
            int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ID);
            int albumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ALBUM);
            int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ARTIST);
            int artistIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ARTIST_ID);
            int numberOfSongIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._NUMBER_OF_SONG);
            int albumArtIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ALBUM_ART);

            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.id = Integer.parseInt(cursor.getString(idIndex));
            entity.album = cursor.getString(albumIndex);
            entity.artist = cursor.getString(artistIndex);
            entity.artistId = Integer.parseInt(cursor.getString(artistIdIndex));
            entity.numberOfSong = Integer.parseInt(cursor.getString(numberOfSongIndex));
            entity.albumArt = String.valueOf(cursor.getString(albumArtIndex));

            if (!cursor.isClosed())
                cursor.close();
            closeDb();
        }


        return entity;
    }

    @Override
    public long insertRow(AlbumEntity entity) {
        openDb();
        long insert = getSqlDb().insert(DataBaseUtils.TABLE_ALBUM, null, convertEntity2ContentValue(entity));
        closeDb();
        return insert;
    }

    @Override
    public int deleteRow(AlbumEntity entity) {
        openDb();
        int delete = getSqlDb().delete(DataBaseUtils.TABLE_ALBUM,
                DataBaseUtils.DbStoreMediaColumn.MID + "=?",
                new String[]{String.valueOf(entity.mId)});
        closeDb();
        return delete;
    }

    @Override
    public int updateRow(AlbumEntity entity) {
        openDb();
        int update = getSqlDb().update(DataBaseUtils.TABLE_ALBUM,
                convertEntity2ContentValue(entity),
                DataBaseUtils.DbStoreMediaColumn._ID + "=?",
                new String[]{String.valueOf(entity.id)});
        closeDb();
        return update;
    }

    @Override
    public ContentValues convertEntity2ContentValue(AlbumEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DataBaseUtils.DbStoreAlbumColumn._ID, entity.id);
        values.put(DataBaseUtils.DbStoreAlbumColumn._ALBUM, entity.album);
        values.put(DataBaseUtils.DbStoreAlbumColumn._ARTIST, entity.artist);
        values.put(DataBaseUtils.DbStoreAlbumColumn._ARTIST_ID, entity.artistId);
        values.put(DataBaseUtils.DbStoreAlbumColumn._NUMBER_OF_SONG, entity.numberOfSong);
        values.put(DataBaseUtils.DbStoreAlbumColumn._ALBUM_ART, entity.albumArt);
        return values;
    }
}
