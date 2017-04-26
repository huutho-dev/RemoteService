package training.com.tplayer.database;

import android.provider.MediaStore;

/**
 * Created by hnc on 12/04/2017.
 */

public class DataBaseUtils {

    /**
     * Name of database
     */
    public static final String DATABASE_NAME = "TPlayerDb.db";


    /**
     * Version of db
     */
    public static final int DATABASE_VERSION = 1;


    public abstract class DbStoreMediaColumn {
        public static final String MID = "mid";
        public static final String _ID = MediaStore.Audio.AudioColumns._ID;
        public static final String _DATA = MediaStore.Audio.AudioColumns.DATA;
        public static final String _DISPLAY_NAME = MediaStore.Audio.AudioColumns.DISPLAY_NAME;
        public static final String _SIZE = MediaStore.Audio.AudioColumns.SIZE;
        public static final String _MIME_TYPE = MediaStore.Audio.AudioColumns.MIME_TYPE;
        public static final String _DATE_ADDED = MediaStore.Audio.AudioColumns.DATE_ADDED;
        public static final String _TITLE = MediaStore.Audio.AudioColumns.TITLE;
        public static final String _DURATION = MediaStore.Audio.AudioColumns.DURATION;
        public static final String _ARTIST_ID = MediaStore.Audio.AudioColumns.ARTIST_ID;
        public static final String _ARTIST = MediaStore.Audio.AudioColumns.ARTIST;
        public static final String _ALBUM = MediaStore.Audio.AudioColumns.ALBUM;
        public static final String _ALBUM_ID = MediaStore.Audio.AudioColumns.ALBUM_ID;

    }

    public abstract class DbStoreArtistColumn {
        public static final String MID = "mid";
        public static final String _ID = MediaStore.Audio.Artists._ID;
        public static final String _ARTIST = MediaStore.Audio.Artists.ARTIST;
        public static final String _NUMBER_OF_ALBUMS = MediaStore.Audio.Artists.NUMBER_OF_ALBUMS;
        public static final String _NUMBER_OF_TRACK = MediaStore.Audio.Artists.NUMBER_OF_TRACKS;
    }

    public abstract class DbStoreAlbumColumn {
        public static final String MID = "mid";
        public static final String _ID = MediaStore.Audio.Albums._ID;
        public static final String _ALBUM = MediaStore.Audio.Albums.ALBUM;
        public static final String _ARTIST = MediaStore.Audio.Albums.ARTIST;
        public static final String _ARTIST_ID = MediaStore.Audio.AudioColumns.ARTIST_ID;
        public static final String _NUMBER_SONG = MediaStore.Audio.Albums.NUMBER_OF_SONGS;
        public static final String _ALBUM_ART = MediaStore.Audio.Albums.ALBUM_ART;
    }

    public abstract class DbStorePlaylistColumn {
        public static final String MID = "mid";
        public static final String _ID = MediaStore.Audio.Playlists._ID;
        public static final String _DATA = MediaStore.Audio.Playlists.DATA;
        public static final String _NAME = MediaStore.Audio.Playlists.NAME;
        public static final String _DATA_ADDED = MediaStore.Audio.Playlists._ID;

        public abstract class MemberColum {
            public static final String _ID = MediaStore.Audio.Playlists.Members._ID;
            public static final String _AUDIO_ID = MediaStore.Audio.Playlists.Members.AUDIO_ID;
            public static final String _PLAYLIST_ID = MediaStore.Audio.Playlists.Members.PLAYLIST_ID;
            public static final String _ARTIST_ID = MediaStore.Audio.Playlists.Members.ARTIST_ID;
            public static final String _ARTIST = MediaStore.Audio.Playlists.Members.ARTIST;
            public static final String _ALBUM_ID = MediaStore.Audio.Playlists.Members.ALBUM_ID;
            public static final String _ALBUM = MediaStore.Audio.Playlists.Members.ALBUM;
            public static final String _DATA = MediaStore.Audio.Playlists.Members.DATA;
            public static final String _DISPLAY_NAME = MediaStore.Audio.Playlists.Members.DISPLAY_NAME;
            public static final String _SIZE = MediaStore.Audio.Playlists.Members.SIZE;
            public static final String _MIME_TYPE = MediaStore.Audio.Playlists.Members.MIME_TYPE;
            public static final String _DATA_ADDED = MediaStore.Audio.Playlists.Members.DATE_ADDED;
            public static final String _TITLE = MediaStore.Audio.Playlists.Members.TITLE;
        }
    }


    public static final String TABLE_MEDIA = "Media";
    public static final String TABLE_ARTIST = "Artist";
    public static final String TABLE_ALBUM = "Album";
    public static final String TABLE_PLAYLIST = "Playlist";
    public static final String TABLE_PLAYLIST_MEMBER = "PlaylistMember";

    public static final String CREATE_TABLE_MEDIA =
            "CREATE TABLE " + TABLE_MEDIA + "("
                    + DbStoreMediaColumn._ID + " text "


                    + ")";


}
