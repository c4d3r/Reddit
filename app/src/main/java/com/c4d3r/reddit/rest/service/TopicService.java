package com.c4d3r.reddit.rest.service;

import com.c4d3r.reddit.rest.model.Topic;

import java.util.List;

import retrofit.Callback;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;

/**
 * Created by Maxim on 17/01/2015.
 */
public interface TopicService
{
    @GET("/r/funny/hot/.json")
    public void getTopics(Callback<List<Topic>> callback);
}
