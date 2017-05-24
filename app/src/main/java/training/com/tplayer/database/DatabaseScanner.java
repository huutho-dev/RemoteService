package training.com.tplayer.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.MediaStore;

import com.remote.communication.AlbumEntity;
import com.remote.communication.ArtistEntity;
import com.remote.communication.MediaEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import training.com.tplayer.utils.FileUtils;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 4/27/2017.
 */

public class DatabaseScanner {
    // Đọc Provider
    // Nếu chưa có thì insert, có rồi thì update

    private Context mContext;

    public DatabaseScanner(Context context) {
        this.mContext = context;
    }

    public List<MediaEntity> scanMedia() {
        List<MediaEntity> entities = new ArrayList<>();

        String[] projection = new String[]{
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.DISPLAY_NAME,
                MediaStore.Audio.AudioColumns.SIZE,
                MediaStore.Audio.AudioColumns.MIME_TYPE,
                MediaStore.Audio.AudioColumns.DATE_ADDED,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.AudioColumns.ARTIST_ID,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.ALBUM_ID};

        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
                MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null);
        if (cursor != null) {

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

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                DatabaseUtils.dumpCurrentRow(cursor);
                MediaEntity entity = new MediaEntity();
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

                Cursor cursorArt = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null,
                        MediaStore.Audio.Albums._ID + "=?", new String[]{String.valueOf(entity.albumId)}, null);
                if (cursorArt != null && cursorArt.moveToFirst()) {
                    entity.art = cursorArt.getString(cursorArt.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART));
                    LogUtils.printLog("Art : " + entity.art);
                    cursorArt.close();
                }

                entities.add(entity);
                cursor.moveToNext();
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return entities;
    }

    public List<AlbumEntity> scanAlbum() {
        List<AlbumEntity> entities = new ArrayList<>();
        String[] projection = new String[]{
                DataBaseUtils.DbStoreAlbumColumn._ID,
                DataBaseUtils.DbStoreAlbumColumn._ALBUM,
                DataBaseUtils.DbStoreAlbumColumn._ARTIST,
                DataBaseUtils.DbStoreAlbumColumn._ARTIST_ID,
                DataBaseUtils.DbStoreAlbumColumn._NUMBER_OF_SONG,
                DataBaseUtils.DbStoreAlbumColumn._ALBUM_ART
        };

        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null, null, null);
        if (cursor != null) {

//            int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreAlbumColumn.MID);
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
                entity.mId = 0;
                entity.id = Integer.parseInt(cursor.getString(idIndex));
                entity.album = cursor.getString(albumIndex);
                entity.artist = cursor.getString(artistIndex);
                entity.artistId = Integer.parseInt(cursor.getString(artistIdIndex));
                entity.numberOfSong = Integer.parseInt(cursor.getString(numberOfSongIndex));
                entity.albumArt = cursor.getString(albumArtIndex);

                entities.add(entity);
                cursor.moveToNext();
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return entities;
    }

    public List<ArtistEntity> scanArtist() {
        List<ArtistEntity> entities = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        };

        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {

            int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._ID);
            int artistIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._ARTIST);
            int numberOfAlbumIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_ALBUMS);
            int numberOfTrackIndex = cursor.getColumnIndex(DataBaseUtils.DbStoreArtistColumn._NUMBER_OF_TRACK);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                DatabaseUtils.dumpCurrentRow(cursor);
                ArtistEntity entity = new ArtistEntity();
                entity.id = Integer.parseInt(cursor.getString(idIndex));
                entity.author = cursor.getString(artistIndex);
                entity.numberOfAlbum = Integer.parseInt(cursor.getString(numberOfAlbumIndex));
                entity.numberOfTrack = Integer.parseInt(cursor.getString(numberOfTrackIndex));

                entities.add(entity);
                cursor.moveToNext();
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return entities;
    }

    public void insertOrUpdateTableMedia(List<MediaEntity> mediaEntities) {

        for (MediaEntity entity : mediaEntities) {
            int countRowAffect = SourceTableMedia.getInstance(mContext).updateRow(entity);
            if (countRowAffect < 1) {
                SourceTableMedia.getInstance(mContext).insertRow(entity);
            }
        }
    }

    public void insertOrUpdateTableAlbum(List<AlbumEntity> albumEntities) {
        for (AlbumEntity entity : albumEntities) {
            int countRowAffect = SourceTableAlbum.getInstance(mContext).updateRow(entity);
            if (countRowAffect < 1) {
                SourceTableAlbum.getInstance(mContext).insertRow(entity);
            }
        }
    }

    public void insertOrUpdateTableArtist(List<ArtistEntity> artistEntities) {
        for (ArtistEntity entity : artistEntities) {
            int countRowAffect = SourceTableArtist.getInstance(mContext).updateRow(entity);
            if (countRowAffect < 1) {
                SourceTableArtist.getInstance(mContext).insertRow(entity);
            }
        }
    }

    public void insertAfterDownload(File file) {
        FileUtils.scanFileAfterDownloaded(mContext, file, new FileUtils.IOnScanMediaComplete() {
            @Override
            public void scanComplete(String path, Uri uri) {

                String[] projection = new String[]{
                        MediaStore.Audio.AudioColumns._ID,
                        MediaStore.Audio.AudioColumns.DATA,
                        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
                        MediaStore.Audio.AudioColumns.SIZE,
                        MediaStore.Audio.AudioColumns.MIME_TYPE,
                        MediaStore.Audio.AudioColumns.DATE_ADDED,
                        MediaStore.Audio.AudioColumns.TITLE,
                        MediaStore.Audio.AudioColumns.DURATION,
                        MediaStore.Audio.AudioColumns.ARTIST_ID,
                        MediaStore.Audio.AudioColumns.ARTIST,
                        MediaStore.Audio.AudioColumns.ALBUM,
                        MediaStore.Audio.AudioColumns.ALBUM_ID};

                Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);

                if (cursor != null) {

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

                    cursor.moveToFirst();

                    MediaEntity entity = new MediaEntity();
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

                    // Query --> get[Db of OS]Row of artist mabe changed after download
                    // Update --> [Update to my DB]

                    AlbumEntity rowAlbumOfOs = SourceTableAlbum
                            .getInstance(mContext)
                            .getRow(DataBaseUtils.DbStoreAlbumColumn._ID  + "=?",
                                    new String[]{String.valueOf(entity.albumId)});


                    ArtistEntity rowArtistOfOs = SourceTableArtist
                            .getInstance(mContext)
                            .getRow(DataBaseUtils.DbStoreArtistColumn._ID + "=?",
                                    new String[]{String.valueOf(entity.artistId)});


                    int updateArtist = SourceTableArtist.getInstance(mContext).updateRow(rowArtistOfOs);
                    int updateAlbum = SourceTableAlbum.getInstance(mContext).updateRow(rowAlbumOfOs);
                    long updateMedia = SourceTableMedia.getInstance(mContext).insertRow(entity);

                    if (!cursor.isClosed()) {
                        cursor.close();
                    }
                }
            }
        });
    }
}
