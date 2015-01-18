package com.c4d3r.reddit.rest;

import com.c4d3r.reddit.rest.model.Topic;
import com.c4d3r.reddit.rest.service.TopicService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Maxim on 17/01/2015.
 */
public class RedditClient
{
    private TopicService _topicService;

    private static RedditClient _redditClient;

    public TopicService getTopicService(String url)
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<Topic>>() {
                }.getType(), new TopicDeserializer())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                //.setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(url)
                .setConverter(new GsonConverter(gson))
                .build();

        _topicService = restAdapter.create(TopicService.class);
        return _topicService;
    }

    public static synchronized RedditClient getInstance() {
        if(_redditClient != null)
            return _redditClient;

        _redditClient = new RedditClient();

        return _redditClient;
    }

}
