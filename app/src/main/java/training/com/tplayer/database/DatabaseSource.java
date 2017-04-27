package training.com.tplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by hnc on 12/04/2017.
 */

public abstract class DatabaseSource<E extends BaseEntity> {
    public DatabaseHelper mDatabaseHelper;

    public DatabaseSource(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }


    public abstract List<E> getList(Cursor cursor);

    public abstract E getRow(Cursor cursor);

    public abstract long insertRow(E entity);

    public abstract int deleteRow(E entity);

    public abstract int updateRow(E entity);

    public abstract ContentValues convertEntity2ContentValue(E e);

    public void openDb() {
        mDatabaseHelper.openDatabase();
    }

    public void closeDb() {
        mDatabaseHelper.closeDatabase();
    }

    public SQLiteDatabase getSqlDb() {
        return mDatabaseHelper.getSqlDatabase();
    }
}
