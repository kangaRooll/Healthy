package com.a59070055.healthy_h1.comment;

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

public class CommentAdapter extends ArrayAdapter<Comment> {

    List<Comment> commentList = new ArrayList<Comment>();
    Context context;

    public CommentAdapter (Context context, int resource, List<Comment> objects) {
        super(context, resource, objects);
        this.commentList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View commentItem = LayoutInflater.from(context).inflate(R.layout.fragment_comment_item, parent, false);

        TextView id = commentItem.findViewById(R.id.comment_item_id);
        TextView body = commentItem.findViewById(R.id.comment_item_body);
        TextView name = commentItem.findViewById(R.id.comment_item_name);
        TextView email = commentItem.findViewById(R.id.comment_item_email);

        Comment row = commentList.get(position);
        id.setText(row.getPostid() + " : " + row.getId());
        body.setText(row.getBody());
        name.setText(row.getName());
        email.setText(row.getEmail());

        return commentItem;
    }
}