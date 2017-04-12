package training.com.tplayer.database;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hnc on 12/04/2017.
 */

public class DataBaseUtils {

    /**
     * Name of database
     */
    public static final String DATABASE_NAME ="TPlayerDb.db";


    /**
     * Version of db
     */
    public static final int DATABASE_VERSION = 1 ;


    /**
     * Name of table Song
     */
    public static final String TABLE_SONG = "TPlayerSong";


    /**
     * All column in table Song
     */
    public class SongColumn {

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DATA = "_data";
        public static final String COLUMN_DISPLAY_NAME = "_data";
        public static final String COLUMN_TITLE = "_data";
        public static final String COLUMN_DURATION = "_data";
        public static final String COLUMN_LYRIC = "_data";
        public static final String COLUMN_ART = "_data";
        public static final String COLUMN_GENRE_NAME = "_data";
        public static final String COLUMN_FAVORITE = "_data";
        public static final String COLUMN_ARTIST_NAME = "_data";
        public static final String COLUMN_ALBUM_NAME = "_data";
        public static final String COLUMN_ALBUM_ART = "_data";
    }


    /**
     * all colum in each playlist
     */
    public class PlayListColumn {
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DATA = "_id_song";
    }


    /**
     * String to create table song
     */
    public static final String CREATE_TABLE_SONG =
            "create table " + TABLE_SONG + "("
                    + SongColumn.COLUMN_ID + " integer not null,"
                    + SongColumn.COLUMN_DATA + " text not null,"
                    + SongColumn.COLUMN_DISPLAY_NAME + " text not null,"
                    + SongColumn.COLUMN_TITLE + " text not null,"
                    + SongColumn.COLUMN_DURATION + " int,"
                    + SongColumn.COLUMN_LYRIC + " text,"
                    + SongColumn.COLUMN_ART + " text,"
                    + SongColumn.COLUMN_GENRE_NAME + " text,"
                    + SongColumn.COLUMN_FAVORITE + " boolean,"
                    + SongColumn.COLUMN_ARTIST_NAME + " text,"
                    + SongColumn.COLUMN_ALBUM_NAME + " text,"
                    + SongColumn.COLUMN_ALBUM_ART + " text,"
                    + ")";


    /**
     * @param playListName name of playlits user want to create
     * @return String to create table playlist with name is name table
     */
    public static String createPlayList(String playListName) {
        return "create table " + playListName + "("
                + PlayListColumn.COLUMN_ID + " integer not null autoincrement,"
                + PlayListColumn.COLUMN_DATA + " text"
                + ")";
    }

    /**
     * @param db DB to query table
     * @return name of all table has in db
     */
    public static List<String> queryAllTable(SQLiteDatabase db) {
        List<String> tables = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                DatabaseUtils.dumpCurrentRow(cursor);
                tables.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        return tables;
    }

}
