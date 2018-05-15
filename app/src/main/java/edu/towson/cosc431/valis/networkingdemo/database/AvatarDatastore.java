package edu.towson.cosc431.valis.networkingdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.net.MalformedURLException;

import edu.towson.cosc431.valis.networkingdemo.models.Message;

/**
 * Created by Montrell on 5/15/2018.
 */

public class AvatarDatastore {

    private AvatarDB helper;

    public AvatarDatastore(Context ctx) {
        helper = new AvatarDB(ctx);
    }

    public void addMessage(Message message) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues cv = intoContentValues(message);
        db.insert(DatabaseContract.TABLE_NAME, null, cv);
    }

    // Creates a ContentValue object from the properties of the Message object
    private ContentValues intoContentValues(Message message) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract._ID, message.getId());
        cv.put(DatabaseContract.NAME_COLUMN, message.getName());
        cv.put(DatabaseContract.MESSAGE_COLUMN, message.getMessage());
        return cv;
    }
}
