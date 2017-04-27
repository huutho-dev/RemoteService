package training.com.tplayer.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.remote.communication.PlaylistMemberEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThoNH on 4/27/2017.
 */

public class SourceTablePlaylistMember extends DatabaseSource<PlaylistMemberEntity> {
    private static SourceTablePlaylistMember mInstance;

    public synchronized static SourceTablePlaylistMember getInstance(DatabaseHelper databaseHelper) {
        if (mInstance == null) {
            mInstance = new SourceTablePlaylistMember(databaseHelper);
        }
        return mInstance;
    }

    private SourceTablePlaylistMember(DatabaseHelper databaseHelper) {
        super(databaseHelper);
    }

    @Override
    public List<PlaylistMemberEntity> getList(Cursor cursor) {
        List<PlaylistMemberEntity> entities = new ArrayList<>();

        int mIdIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum.MID );
        int idIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._ID );
        int albumIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._ALBUM );
        int albumIdIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._ALBUM_ID );
        int artistIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._ARTIST );
        int artistIdIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._ARTIST_ID );
        int audioIdIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._AUDIO_ID );
        int dataIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._DATA );
        int titleIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._TITLE );
        int sizeIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._SIZE );
        int playListIdIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._PLAYLIST_ID );
        int displayNameIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._DISPLAY_NAME );
        int MIMETypeIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._MIME_TYPE );
        int dateAddedIndex= cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MemberColum._DATA_ADDED );



        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            PlaylistMemberEntity entity = new PlaylistMemberEntity();
            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.id = Integer.parseInt(cursor.getString(idIndex));
            entity.album = cursor.getString(albumIndex);
            entity.albumId = Integer.parseInt(cursor.getString(albumIdIndex));
            entity.artist = cursor.getString(artistIndex);
            entity.artistId = Integer.parseInt(cursor.getString(artistIdIndex));
            entity.audioId = Integer.parseInt(cursor.getString(audioIdIndex));
            entity.data = cursor.getString(dataIndex);
            entity.title = cursor.getString(titleIndex);
            entity.size = Integer.parseInt(cursor.getString(sizeIndex));
            entity.playListId = Integer.parseInt(cursor.getString(playListIdIndex));
            entity.displayName = cursor.getString(displayNameIndex);
            entity.MIMEType = cursor.getString(MIMETypeIndex);
            entity.dateAdded = Integer.parseInt(cursor.getString(dateAddedIndex));

            entities.add(entity);
            cursor.moveToNext();
        }
        if (!cursor.isClosed())
            cursor.close();
        return entities;
    }

    @Override
    public PlaylistMemberEntity getRow(Cursor cursor) {
        return getList(cursor).get(0);
    }

    @Override
    public long insertRow(PlaylistMemberEntity entity) {
        return getSqlDb().insert(DataBaseUtils.TABLE_PLAYLIST_MEMBER, null, convertEntity2ContentValue(entity));
    }

    @Override
    public int deleteRow(PlaylistMemberEntity entity) {
        return getSqlDb().delete(DataBaseUtils.TABLE_PLAYLIST_MEMBER,
                DataBaseUtils.DbStoreMediaColumn.MID,
                new String[]{String.valueOf(entity.mId)});
    }

    @Override
    public int updateRow(PlaylistMemberEntity entity) {
        return getSqlDb().update(DataBaseUtils.TABLE_PLAYLIST_MEMBER,
                convertEntity2ContentValue(entity),
                DataBaseUtils.DbStoreMediaColumn.MID,
                new String[]{String.valueOf(entity.mId)});
    }

    @Override
    public ContentValues convertEntity2ContentValue(PlaylistMemberEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum.MID , entity.mId);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._ID , entity.id);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._ALBUM , entity.album);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._ALBUM_ID , entity.albumId);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._ARTIST , entity.artist);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._ARTIST_ID , entity.artistId);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._AUDIO_ID , entity.audioId);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._DATA , entity.data);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._TITLE , entity.title);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._SIZE , entity.size);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._PLAYLIST_ID , entity.playListId);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._DISPLAY_NAME , entity.displayName);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._MIME_TYPE , entity.MIMEType);
        values.put(DataBaseUtils.DbStorePlaylistColumn.MemberColum._DATA_ADDED , entity.dateAdded);

        return values;
    }
}
