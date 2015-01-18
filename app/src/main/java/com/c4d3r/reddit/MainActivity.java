package com.c4d3r.reddit;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.c4d3r.reddit.rest.model.Topic;

import com.c4d3r.reddit.persistence.TopicContract.TopicEntry;

/**
 * Created by Maxim on 17/01/2015.
 */
public class MainActivity extends ActionBarActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private boolean DEBUG = true;

    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_topics, new TopicFragment())
                    .commit();
        }

        mContext = getApplicationContext();

    }

    private long addTopic(Topic topic)
    {
        //kijk eerst of het bestaat
        Cursor cursor = mContext.getContentResolver().query(
                TopicEntry.CONTENT_URI,
                new String[]{TopicEntry._ID},
                null, null, null
        );

        if(cursor.moveToFirst()) {
            int topicIdIndex = cursor.getColumnIndex(TopicEntry._ID);
            return cursor.getLong(topicIdIndex);
        } else {
            ContentValues topicValues = new ContentValues();
            topicValues.put(TopicEntry.COLUMN_AUTHOR, topic.getAuthor());
            topicValues.put(TopicEntry.COLUMN_DOMAIN, topic.getDomain());
            topicValues.put(TopicEntry.COLUMN_SCORE, topic.getScore());
            topicValues.put(TopicEntry.COLUMN_TEXT, topic.getHtmlText());
            topicValues.put(TopicEntry.COLUMN_TITLE, topic.getTitle());
            topicValues.put(TopicEntry.COLUMN_THUMBNAIL, topic.getThumbnailUrl());

            Uri locationInsertUri = mContext.getContentResolver()
                    .insert(TopicEntry.CONTENT_URI, topicValues);

            return ContentUris.parseId(locationInsertUri);
        }
    }

}

