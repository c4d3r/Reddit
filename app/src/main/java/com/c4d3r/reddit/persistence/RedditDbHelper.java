package com.c4d3r.reddit.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.c4d3r.reddit.persistence.TopicContract.TopicEntry;

/**
 * Created by Maxim on 17/01/2015.
 */
public class RedditDbHelper extends SQLiteOpenHelper
{
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "reddit.db";

    private static final String TAG = RedditDbHelper.class.getSimpleName();

    private static final String CREATE_TABLE =
        "CREATE TABLE " + TopicEntry.TABLE_NAME + " (" +
            TopicEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TopicEntry.COLUMN_TITLE + " TEXT NOT NULL," +
            TopicEntry.COLUMN_DOMAIN + " TEXT NOT NULL," +
            TopicEntry.COLUMN_AUTHOR + " TEXT," +
            TopicEntry.COLUMN_TEXT + " TEXT," +
            TopicEntry.COLUMN_THUMBNAIL + " TEXT," +
            TopicEntry.COLUMN_SCORE + " INT" +
        ");";

    public RedditDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Maak de databank aan adhv de query die we gedefinieert hebben
     * @param db
     */
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
        Log.i(TAG, "Create table: " + TopicEntry.TABLE_NAME);
    }

    /**
     * Indien de versiecode niet meer overeenkomt is er een nieuwe databank update, dan updaten we alles
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(TAG, String.format("Upgrading from %d to %d", oldVersion, newVersion));
        db.execSQL("DROP IF EXISTS " + TopicEntry.TABLE_NAME);
        onCreate(db);
    }
}
