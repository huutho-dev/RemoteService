package training.com.tplayer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hnc on 12/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context) {
        super(context, DataBaseUtils.DATABASE_NAME, null, DataBaseUtils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(DataBaseUtils.CREATE_TABLE_SONG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exist " + DataBaseUtils.CREATE_TABLE_SONG);
        onCreate(db);
    }

    public void openDatabase() {
        sqLiteDatabase = this.getWritableDatabase();
    }

    public void closeDatabase() {
        sqLiteDatabase.close();
    }

}
