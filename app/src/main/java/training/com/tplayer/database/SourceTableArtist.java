package training.com.tplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.remote.communication.ArtistEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThoNH on 4/27/2017.
 */

public class SourceTableArtist extends DatabaseSource<ArtistEntity> {
    private static SourceTableArtist mInstance;

    public synchronized static SourceTableArtist getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SourceTableArtist(context);
        }
        return mInstance;
    }

    private SourceTableArtist(Context context) {
        super(context);
    }

    @Override
    public List<ArtistEntity> getList() {
        List<ArtistEntity> entities = new ArrayList<>();
        openDb();
        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_ARTIST, null, null, null, null, null, null);

        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn.MID);
        int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._ID);
        int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._ARTIST);
        int numberOfAlbumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_ALBUMS);
        int numberOfTrackIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_TRACK);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            ArtistEntity entity = new ArtistEntity();
            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.id = Integer.parseInt(cursor.getString(idIndex));
            entity.author = cursor.getString(artistIndex);
            entity.numberOfAlbum = Integer.parseInt(cursor.getString(numberOfAlbumIndex));
            entity.numberOfTrack = Integer.parseInt(cursor.getString(numberOfTrackIndex));

            entities.add(entity);
            cursor.moveToNext();
        }
        closeDb();
        if (!cursor.isClosed())
            cursor.close();
        return entities;
    }

    @Override
    public ArtistEntity getRow(String selection, String[] selectionArgs) {
        ArtistEntity entity = new ArtistEntity();
        openDb();
        Cursor cursor = getSqlDb().query(DataBaseUtils.TABLE_ARTIST, null, selection, selectionArgs, null, null, null);
        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn.MID);
        int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._ID);
        int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._ARTIST);
        int numberOfAlbumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_ALBUMS);
        int numberOfTrackIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_TRACK);

        cursor.moveToFirst();

        entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
        entity.id = Integer.parseInt(cursor.getString(idIndex));
        entity.author = cursor.getString(artistIndex);
        entity.numberOfAlbum = Integer.parseInt(cursor.getString(numberOfAlbumIndex));
        entity.numberOfTrack = Integer.parseInt(cursor.getString(numberOfTrackIndex));

        if (!cursor.isClosed())
            cursor.close();
        closeDb();
        return entity;
    }

    @Override
    public long insertRow(ArtistEntity entity) {
        openDb();
        long insert = getSqlDb().insert(DataBaseUtils.TABLE_ARTIST, null, convertEntity2ContentValue(entity));
        closeDb();
        return insert;
    }

    @Override
    public int deleteRow(ArtistEntity entity) {
        openDb();
        int delete = getSqlDb().delete(DataBaseUtils.TABLE_ARTIST,
                DataBaseUtils.DbStoreMediaColumn.MID + "=?",
                new String[]{String.valueOf(entity.mId)});
        closeDb();
        return delete;
    }

    @Override
    public int updateRow(ArtistEntity entity) {
        openDb();
        int update = getSqlDb().update(DataBaseUtils.TABLE_ARTIST,
                convertEntity2ContentValue(entity),
                DataBaseUtils.DbStoreMediaColumn._ID + "=?",
                new String[]{String.valueOf(entity.id)});
        closeDb();
        return update;
    }

    @Override
    public ContentValues convertEntity2ContentValue(ArtistEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DataBaseUtils.DbStoreArtistColumn._ID, entity.id);
        values.put(DataBaseUtils.DbStoreArtistColumn._ARTIST, entity.author);
        values.put(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_ALBUMS, entity.numberOfAlbum);
        values.put(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_TRACK, entity.numberOfTrack);
        return values;
    }
}
