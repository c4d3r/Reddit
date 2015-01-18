package com.c4d3r.reddit.persistence;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Maxim on 17/01/2015.
 */
public class TopicContract
{
    // Is een naam voor de gehele contentprovider, gelijkaardig aan de relatie domein<->website. We gebruiken vaak de pakage name --> altijd unique
    public static final String CONTENT_AUTHORITY = "com.c4d3r.reddit";

    // Gebruik CONTENT_AUTHORITY om de basis van alle URI's te maken
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TOPIC = "topic";

    public static final class TopicEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOPIC).build();
        //directory --> lijst van items
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TOPIC;
        //single item
        public static final String CONTENT_TOPIC_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TOPIC;

        public static final String TABLE_NAME = "topic";

        /* TOPICS */
        public static final String COLUMN_TITLE     = "title";
        public static final String COLUMN_DOMAIN    = "domain";
        public static final String COLUMN_AUTHOR    = "author";
        public static final String COLUMN_SCORE     = "score";
        public static final String COLUMN_TEXT      = "text";
        public static final String COLUMN_THUMBNAIL = "thumbnail";

        //minder plaatsen aware van de actual URI encoding
        public static Uri buildTopicUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id); //enkel indien men id als PK gebruikt
        }
        public static Uri buildRedditTopic(String topicSetting) {
            return CONTENT_URI.buildUpon().appendPath(topicSetting).build();
        }
    }
}
