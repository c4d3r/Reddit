package com.c4d3r.reddit.listener;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Maxim on 17/01/2015.
 */
public class ErrorListener implements Response.ErrorListener
{
    public static final String TAG = ErrorListener.class.getSimpleName();

    private String _name;

    public ErrorListener(String name)
    {
        this._name = name;
    }
    public ErrorListener()
    {
        this(TAG);
    }

    public void onErrorResponse(VolleyError volleyError) {
        Log.d(TAG, "Could not fetch response");
    }
}
