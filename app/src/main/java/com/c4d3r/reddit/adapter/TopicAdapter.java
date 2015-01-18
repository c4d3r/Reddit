package com.c4d3r.reddit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.c4d3r.reddit.R;
import com.c4d3r.reddit.rest.model.Topic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Maxim on 17/01/2015.
 */
public class TopicAdapter extends BaseAdapter
{
    private LayoutInflater _layoutInflater;
    private List<Topic> _topics;
    private Context _context;

    public TopicAdapter(LayoutInflater layoutInflater, Context context, List<Topic> topics)
    {
        _layoutInflater = layoutInflater;
        _context = context;
        _topics = topics;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        if(view == null)
            view = _layoutInflater.inflate(R.layout.item_topic, null);

        TextView title      = (TextView)view.findViewById(R.id.txtTitle);
        TextView author     = (TextView)view.findViewById(R.id.txtAuthor);
        ImageView imgThumb  = (ImageView)view.findViewById(R.id.imgThumbnail);
        TextView score      = (TextView)view.findViewById(R.id.txtScore);

        Topic topic = _topics.get(position);

        title.setText(topic.getTitle());
        author.setText(topic.getAuthor());
        score.setText(Integer.toString(topic.getScore()));

        Picasso.with(_context).load(topic.getThumbnailUrl()).into(imgThumb);

        return view;
    }

    public int getCount() {
        return _topics.size();
    }

    @Override
    public Object getItem(int position) {
        return _topics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
