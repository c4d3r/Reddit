package com.c4d3r.reddit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.c4d3r.reddit.listener.ErrorListener;
import com.c4d3r.reddit.model.Topic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 17/01/2015.
 */
public class MainActivity extends Activity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchContent(AppConst.FUNNY_HOT);
    }

    public void fetchContent(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject)
            {
                List<Topic> topics = new ArrayList<Topic>();


            }
        }, new ErrorListener(TAG));

        AppController.getInstance().addToRequestQueue(request);
    }

}

