package training.com.tplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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
    public List<AlbumEntity> getList(Cursor cursor) {
        List<AlbumEntity> entities = new ArrayList<>();

        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn.MID);
        int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ID);
        int albumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ALBUM);
        int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ARTIST);
        int artistIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ARTIST_ID);
        int numberOfSongIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._NUMBER_OF_SONG);
        int albumArtIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn._ALBUM_ART);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AlbumEntity entity = new AlbumEntity();
            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.id = Integer.parseInt(cursor.getString(idIndex));
            entity.album = cursor.getString(albumIndex);
            entity.artist = cursor.getString(artistIndex);
            entity.artistId = Integer.parseInt(cursor.getString(artistIdIndex));
            entity.numberOfSong = Integer.parseInt(cursor.getString(numberOfSongIndex));
            entity.albumArt = String.valueOf(Integer.parseInt(cursor.getString(albumArtIndex)));

            cursor.moveToNext();
        }

        if (!cursor.isClosed())
            cursor.close();
        return entities;
    }

    @Override
    public AlbumEntity getRow(Cursor cursor) {
        return getList(cursor).get(0);
    }

    @Override
    public long insertRow(AlbumEntity entity) {
        return getSqlDb().insert(DataBaseUtils.TABLE_ALBUM, null, convertEntity2ContentValue(entity));
    }

    @Override
    public int deleteRow(AlbumEntity entity) {
        return getSqlDb().delete(DataBaseUtils.TABLE_ALBUM,
                DataBaseUtils.DbStoreMediaColumn.MID,
                new String[]{String.valueOf(entity.mId)});
    }

    @Override
    public int updateRow(AlbumEntity entity) {
        return getSqlDb().update(DataBaseUtils.TABLE_ALBUM,
                convertEntity2ContentValue(entity),
                DataBaseUtils.DbStoreMediaColumn.MID,
                new String[]{String.valueOf(entity.mId)});
    }

    @Override
    public ContentValues convertEntity2ContentValue(AlbumEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DataBaseUtils.DbStoreAlbumColumn.MID,entity.mId);
        values.put(DataBaseUtils.DbStoreAlbumColumn._ID,entity.id);
        values.put(DataBaseUtils.DbStoreAlbumColumn._ALBUM,entity.album);
        values.put(DataBaseUtils.DbStoreAlbumColumn._ARTIST,entity.artist);
        values.put(DataBaseUtils.DbStoreAlbumColumn._ARTIST_ID,entity.artistId);
        values.put(DataBaseUtils.DbStoreAlbumColumn._NUMBER_OF_SONG,entity.numberOfSong);
        values.put(DataBaseUtils.DbStoreAlbumColumn._ALBUM_ART,entity.albumArt);
        return values;
    }
}
