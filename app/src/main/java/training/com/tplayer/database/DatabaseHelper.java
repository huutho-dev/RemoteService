package training.com.tplayer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hnc on 12/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper mInstance;
    private SQLiteDatabase sqLiteDatabase;

    public synchronized static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DataBaseUtils.DATABASE_NAME, null, DataBaseUtils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseUtils.CREATE_TABLE_MEDIA);
        db.execSQL(DataBaseUtils.CREATE_TABLE_ALBUM);
        db.execSQL(DataBaseUtils.CREATE_TABLE_ARTIST);
        db.execSQL(DataBaseUtils.CREATE_TABLE_PLAYLIST);
        db.execSQL(DataBaseUtils.CREATE_TABLE_PLAYLIST_MEMBER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist " + DataBaseUtils.CREATE_TABLE_MEDIA);
        db.execSQL("drop table if exist " + DataBaseUtils.CREATE_TABLE_ALBUM);
        db.execSQL("drop table if exist " + DataBaseUtils.CREATE_TABLE_ARTIST);
        db.execSQL("drop table if exist " + DataBaseUtils.CREATE_TABLE_PLAYLIST);
        db.execSQL("drop table if exist " + DataBaseUtils.CREATE_TABLE_PLAYLIST_MEMBER);
        onCreate(db);
    }

    public void openDatabase() {
        sqLiteDatabase = this.getWritableDatabase();
    }

    public void closeDatabase() {
        if (sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public SQLiteDatabase getSqlDatabase() {
        return sqLiteDatabase;
    }

}
