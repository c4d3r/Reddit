package com.c4d3r.reddit.rest;

import android.util.Log;

import com.c4d3r.reddit.rest.model.Topic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Maxim on 17/01/2015.
 */
public class TopicDeserializer implements JsonDeserializer<List<Topic>>
{
    @Override
    public List<Topic> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonElement dataObject = json.getAsJsonObject().get("data");
        JsonArray childrenArray = dataObject.getAsJsonObject().get("children").getAsJsonArray();

        List<Topic> topics = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Calendar.class, new JsonDeserializer<Calendar>() {
            @Override
            public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                Calendar cal = GregorianCalendar.getInstance();
                cal.setTimeInMillis(json.getAsJsonPrimitive().getAsLong() * 1000);
                return cal;
            }
        });

        Gson gson = builder.create();

        Iterator<JsonElement> it = childrenArray.iterator();
        while(it.hasNext()) {
            JsonElement topicData = it.next().getAsJsonObject().get("data");
            Topic topic = gson.fromJson(topicData, Topic.class);
            topics.add(topic);
        }

        return topics;

    }
}
