package com.a59070055.healthy_h1.post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.a59070055.healthy_h1.R;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {

    List<Post> postList = new ArrayList<Post>();
    Context context;

    public PostAdapter (Context context, int resource, List<Post> objects) {
        super(context, resource, objects);
        this.postList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View postItem = LayoutInflater.from(context).inflate(R.layout.fragment_post_item, parent, false);

        TextView id = postItem.findViewById(R.id.post_item_id);
        TextView body = postItem.findViewById(R.id.post_item_body);

        Post row = postList.get(position);
        id.setText(row.getId() + " : " + row.getTitle());
        body.setText(row.getBody());

        return postItem;
    }
}