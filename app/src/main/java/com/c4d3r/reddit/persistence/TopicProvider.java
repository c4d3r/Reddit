package com.c4d3r.reddit.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Maxim on 17/01/2015.
 */
public class TopicProvider extends ContentProvider
{
    private static final String TAG = TopicProvider.class.getSimpleName();

    private static final int TOPICS = 100;
    private static final int TOPIC_ID = 101;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TopicContract.CONTENT_AUTHORITY;

        // voor elk type URI dat we willen toevoegen creeeren we een code
        matcher.addURI(authority, TopicContract.PATH_TOPIC, TOPICS);
        matcher.addURI(authority, TopicContract.PATH_TOPIC + "/#", TOPIC_ID);

        return matcher;
    }

    private static final UriMatcher sUriMatcher = buildUriMatcher();


    //public static Uri CONTENT_URI = Uri.parse("content://com.c4d3r.reddit/topics");
    private RedditDbHelper mDbHelper;
    private SQLiteDatabase _db;

    @Override
    public boolean onCreate() {
        mDbHelper = new RedditDbHelper(getContext());
        return true; //contentprovider succesvol aangemaakt
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch(sUriMatcher.match(uri))
        {
            // "topic/*"
            case TOPIC_ID:
            {
                retCursor = mDbHelper.getReadableDatabase().query(
                        TopicContract.TopicEntry.TABLE_NAME,
                        projection,
                        TopicContract.TopicEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            // "topic"
            case TOPICS:
            {
                retCursor = mDbHelper.getReadableDatabase().query(
                        TopicContract.TopicEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /**
     * geeft het MIME type terug dat geassocieerd is met de data bij een URI
     * @param uri
     * @return
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TOPIC_ID:
                return TopicContract.TopicEntry.CONTENT_TOPIC_TYPE;
            case TOPICS:
                return TopicContract.TopicEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case TOPICS: {
                long _id = db.insert(TopicContract.TopicEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = TopicContract.TopicEntry.buildTopicUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted = 0;

        switch (match) {
            case TOPICS: {
                rowsDeleted = db.delete(TopicContract.TopicEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        //Because a null deletes all rows
        if(null == selection || 0 != rowsDeleted) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated = 0;

        switch (match) {
            case TOPICS: {
                rowsUpdated = db.update(TopicContract.TopicEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(0 != rowsUpdated) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values)
    {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TOPICS:
                db.beginTransaction();
                int returnCount = 0;
                try
                {
                    for(ContentValues value : values) {
                        long _id = db.insert(TopicContract.TopicEntry.TABLE_NAME, null, value);
                        if(-1 != _id) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
