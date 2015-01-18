package com.c4d3r.reddit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.c4d3r.reddit.adapter.TopicAdapter;
import com.c4d3r.reddit.persistence.TopicContract.TopicEntry;
import com.c4d3r.reddit.rest.RedditClient;
import com.c4d3r.reddit.rest.model.Topic;

import java.util.List;
import java.util.Vector;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Maxim on 18/01/2015.
 */
public class TopicFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    public static final String TAG = TopicFragment.class.getSimpleName();
    private boolean DEBUG = true;

    private TopicAdapter mTopicAdapter;

    private static final int TOPIC_LOADER = 0;
    private String mLocation;

    private Context mContext;

    private static final String[] TOPIC_COLUMNS = {
            TopicEntry.TABLE_NAME + "." + TopicEntry._ID,
            TopicEntry.COLUMN_TITLE,
            TopicEntry.COLUMN_DOMAIN,
            TopicEntry.COLUMN_AUTHOR,
            TopicEntry.COLUMN_SCORE,
            TopicEntry.COLUMN_TEXT,
            TopicEntry.COLUMN_THUMBNAIL
    };

    //bind een volgnummer aan de kolommen
    public static final int COL_TOPIC_ID = 0;
    public static final int COL_TOPIC_TITLE = 1;
    public static final int COL_TOPIC_DOMAIN = 2;
    public static final int COL_TOPIC_AUTHOR = 3;
    public static final int COL_TOPIC_SCORE = 4;
    public static final int COL_TOPIC_TEXT = 5;
    public static final int COL_TOPIC_THUMBNAIL = 6;

    public TopicFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.topicfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if(id == R.id.action_refresh) {
//            fetchContent();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mTopicAdapter = new TopicAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_topics);
        listView.setAdapter(mTopicAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: on item click
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(TOPIC_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                TopicEntry.CONTENT_URI,
                TOPIC_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mTopicAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mTopicAdapter.swapCursor(null);
    }

    private void fetchContent() {
        RedditClient.getInstance().getTopicService(AppConst.URL).getTopics(new Callback<List<Topic>>() {
            @Override
            public void success(final List<Topic> topics, Response response) {

                // In de databank steken
                Vector<ContentValues> cVVector = new Vector<ContentValues>(topics.size());

                for(int i = 0; i < topics.size(); i++)
                {
                    Topic topic = topics.get(i);
                    ContentValues topicValues = new ContentValues();
                    topicValues.put(TopicEntry.COLUMN_AUTHOR, topic.getAuthor());
                    topicValues.put(TopicEntry.COLUMN_DOMAIN, topic.getDomain());
                    topicValues.put(TopicEntry.COLUMN_SCORE, topic.getScore());
                    topicValues.put(TopicEntry.COLUMN_TEXT, topic.getHtmlText());
                    topicValues.put(TopicEntry.COLUMN_TITLE, topic.getTitle());
                    topicValues.put(TopicEntry.COLUMN_THUMBNAIL, topic.getThumbnailUrl());

                    cVVector.add(topicValues);
                }

                if(cVVector.size() > 0) {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);
                    int rowsInserted = mContext.getContentResolver().bulkInsert(TopicEntry.CONTENT_URI, cvArray);
                    Log.v(TAG, "Inserted " + rowsInserted + " rows of topic data");

                    if(DEBUG) {
                        Cursor topicCursor = mContext.getContentResolver().query(
                                TopicEntry.CONTENT_URI,
                                null,
                                null,
                                null,
                                null
                        );

                        if(topicCursor.moveToFirst()) {
                            ContentValues resultValues = new ContentValues();
                            DatabaseUtils.cursorRowToContentValues(topicCursor, resultValues);
                            Log.v(TAG, "Query succeeded! **********");
                            for (String key : resultValues.keySet()) {
                                Log.v(TAG, key + ": " + resultValues.getAsString(key));
                            }
                        } else {
                            Log.v(TAG, "Query failed! :( **********");
                        }
                    }
                }
                //ListView lvTopics = (ListView) findViewById(R.id.lvThreads);
                //lvTopics.setAdapter(new TopicAdapter(getLayoutInflater(), getApplicationContext(), topics));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "---------- ERROR ----------");
                Log.d(TAG, "Response: " + error.getResponse());
                Log.d(TAG, "Error: " + error.toString());
                Log.d(TAG, "---------------------------");
            }
        });
    }
}
