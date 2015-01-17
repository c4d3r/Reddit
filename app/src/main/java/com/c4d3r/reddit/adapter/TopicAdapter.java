package com.c4d3r.reddit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.c4d3r.reddit.AppController;
import com.c4d3r.reddit.R;
import com.c4d3r.reddit.model.Topic;

import java.util.List;

/**
 * Created by Maxim on 17/01/2015.
 */
public class TopicAdapter extends BaseAdapter
{
    private LayoutInflater _layoutInflater;
    private List<Topic> _topics;
    private ImageLoader _imageLoader = AppController.getInstance().getImageLoader();

    public TopicAdapter(LayoutInflater layoutInflater, List<Topic> topics)
    {
        _layoutInflater = layoutInflater;
        _topics = topics;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        if(view == null)
            view = _layoutInflater.inflate(R.layout.item_topic, null);

        TextView title  = (TextView)view.findViewById(R.id.txtTitle);
        TextView author = (TextView)view.findViewById(R.id.txtAuthor);
        TextView score  = (TextView)view.findViewById(R.id.txtScore);

        NetworkImageView thumbnail = (NetworkImageView)view.findViewById(R.id.imgThumbnail);
        thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Topic topic = _topics.get(position);
        title.setText(topic.getTitle());
        author.setText(topic.getAuthor());
        score.setText(topic.getScore());

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
