package edu.towson.cosc431.valis.networkingdemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Montrell on 5/15/2018.
 */


public class AvatarDB extends SQLiteOpenHelper {

    private static final String DBNAME = "avatar.db";
    private static final int VERSION = 1;

    private static final String CREATE_TABLE =
            "create table " + DatabaseContract.TABLE_NAME + " ( " +
                    DatabaseContract._ID + " text primary key, " +
                    DatabaseContract.NAME_COLUMN + " text, " +
                    DatabaseContract.MESSAGE_COLUMN + " text, " ;

    public AvatarDB(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + DatabaseContract.TABLE_NAME);
        db.execSQL(CREATE_TABLE);
    }
}
