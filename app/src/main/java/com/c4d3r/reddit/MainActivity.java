package com.c4d3r.reddit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Maxim on 17/01/2015.
 */
public class MainActivity extends ActionBarActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_topics, new TopicFragment())
                    .commit();
        }

    }
}

