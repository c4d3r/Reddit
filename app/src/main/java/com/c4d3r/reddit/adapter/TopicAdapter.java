package com.c4d3r.reddit.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.c4d3r.reddit.R;
import com.c4d3r.reddit.TopicFragment;
import com.squareup.picasso.Picasso;

/**
 * Created by Maxim on 18/01/2015.
 */
public class TopicAdapter extends CursorAdapter
{
    public static class ViewHolder {
        public final TextView titleView;
        public final TextView authorView;
        public final TextView scoreView;
        public final ImageView thumbnailView;

        public ViewHolder(View view) {
            titleView = (TextView)view.findViewById(R.id.txtTitle);
            authorView = (TextView)view.findViewById(R.id.txtAuthor);
            scoreView = (TextView)view.findViewById(R.id.txtScore);
            thumbnailView = (ImageView)view.findViewById(R.id.imgThumbnail);
        }
    }

    public TopicAdapter(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int layoutId = R.layout.item_topic;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();

        String title = cursor.getString(TopicFragment.COL_TOPIC_TITLE);
        viewHolder.titleView.setText(title);

        String author = cursor.getString(TopicFragment.COL_TOPIC_AUTHOR);
        viewHolder.authorView.setText(author);

        int score = cursor.getInt(TopicFragment.COL_TOPIC_SCORE);
        viewHolder.scoreView.setText(Integer.toString(score));

        String thumbnail = cursor.getString(TopicFragment.COL_TOPIC_THUMBNAIL);
        Picasso.with(context).load(thumbnail).into(viewHolder.thumbnailView);
    }
}
