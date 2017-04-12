package training.com.tplayer.database;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by hnc on 12/04/2017.
 */

public abstract class DatabaseSource<E extends BaseEntity> {
    private DatabaseHelper mDatabaseHelper;

    public DatabaseSource(DatabaseHelper databaseHelper) {
        this.mDatabaseHelper = databaseHelper;
    }

    public abstract void getAll();

    public abstract void getRow(String rawQuery);

    public abstract long insertRow(E e);

    public abstract int deleteRow(E e);

    public abstract int updateRow(E e);
}
