package edu.towson.cosc431.valis.networkingdemo.database;

import android.provider.BaseColumns;

/**
 * Created by Montrell on 5/15/2018.
 */

public class DatabaseContract implements BaseColumns {
    public static final String TABLE_NAME = "Database";
    public static final String _ID = "id";
    public static final String NAME_COLUMN = "name";
    public static final String MESSAGE_COLUMN = "message";
}
