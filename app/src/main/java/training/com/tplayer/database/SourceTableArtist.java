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
    public List<ArtistEntity> getList(Cursor cursor) {
        List<ArtistEntity> entities = new ArrayList<>();

        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn.MID );
        int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._ID);
        int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._ARTIST);
        int numberOfAlbumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_ALBUMS);
        int numberOfTrackIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_TRACK);

        while (!cursor.isAfterLast()){

            ArtistEntity entity = new ArtistEntity();
            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.id = Integer.parseInt(cursor.getString(idIndex));
            entity.author = cursor.getString(artistIndex);
            entity.numberOfAlbum = Integer.parseInt(cursor.getString(numberOfAlbumIndex));
            entity.numberOfTrack = Integer.parseInt(cursor.getString(numberOfTrackIndex));

            cursor.moveToNext();
        }

        if (!cursor.isClosed())
            cursor.close();
        return entities;
    }

    @Override
    public ArtistEntity getRow(Cursor cursor) {
        return getList(cursor).get(0);
    }

    @Override
    public long insertRow(ArtistEntity entity) {
        return getSqlDb().insert(DataBaseUtils.TABLE_ARTIST, null, convertEntity2ContentValue(entity));
    }

    @Override
    public int deleteRow(ArtistEntity entity) {
        return getSqlDb().delete(DataBaseUtils.TABLE_ARTIST,
                DataBaseUtils.DbStoreMediaColumn.MID,
                new String[]{String.valueOf(entity.mId)});
    }

    @Override
    public int updateRow(ArtistEntity entity) {
        return getSqlDb().update(DataBaseUtils.TABLE_ARTIST,
                convertEntity2ContentValue(entity),
                DataBaseUtils.DbStoreMediaColumn.MID,
                new String[]{String.valueOf(entity.mId)});
    }

    @Override
    public ContentValues convertEntity2ContentValue(ArtistEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DataBaseUtils.DbStoreArtistColumn.MID ,entity.mId);
        values.put(DataBaseUtils.DbStoreArtistColumn._ID, entity.id);
        values.put(DataBaseUtils.DbStoreArtistColumn._ARTIST, entity.author);
        values.put(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_ALBUMS, entity.numberOfAlbum);
        values.put(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_TRACK, entity.numberOfTrack);
        return values;
    }
}
