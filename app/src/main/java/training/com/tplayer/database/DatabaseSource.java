package training.com.tplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by hnc on 12/04/2017.
 */

public abstract class DatabaseSource<E extends BaseEntity> {
    private Context mContext;

    public DatabaseSource(Context context) {
        this.mContext = context;
    }


    public abstract List<E> getList();

    public abstract E getRow(String selection, String[] selectionArgs);

    public abstract long insertRow(E entity);

    public abstract int deleteRow(E entity);

    public abstract int updateRow(E entity);

    public abstract ContentValues convertEntity2ContentValue(E e);

    public void openDb() {
        DatabaseHelper.getInstance(mContext).openDatabase();
    }

    public void closeDb() {
        DatabaseHelper.getInstance(mContext).close();
    }

    public SQLiteDatabase getSqlDb() {
        return DatabaseHelper.getInstance(mContext).getSqlDatabase();
    }
}
