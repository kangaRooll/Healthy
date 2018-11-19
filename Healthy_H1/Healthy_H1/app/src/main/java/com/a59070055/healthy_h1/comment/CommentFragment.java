package com.a59070055.healthy_h1.comment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.a59070055.healthy_h1.R;
import com.a59070055.healthy_h1.post.PostFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentFragment extends Fragment {

    private static final String TAG = "COMMENT";

    private String url;

    private ArrayList<Comment> commentArrayList = new ArrayList<>();
    private ListView commentList;
    private CommentAdapter commentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initBackBtn();

        Bundle bundle;
        bundle = getArguments();
        final int postid = bundle.getInt("postid");

        url = "https://jsonplaceholder.typicode.com/posts/" + postid + "/comments";

        commentList = getView().findViewById(R.id.frg_comment_list);
        commentAdapter = new CommentAdapter(getActivity(), R.layout.fragment_comment_item, commentArrayList);

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getActivity(), "error - " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    try {
                        final JSONArray jsonArray = new JSONArray(response.body().string());
                        Log.d(TAG, "JSON ARRAY SIZE : " + jsonArray.length());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Comment comment = new Comment(postid, jsonObject.getInt("id"), jsonObject.getString("body"), jsonObject.getString("name"), jsonObject.getString("email"));
                                        commentArrayList.add(comment);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                commentAdapter.notifyDataSetChanged();
                                commentList.setAdapter(commentAdapter);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private void initBackBtn() {
        Button back = getView().findViewById(R.id.frg_comment_backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new PostFragment()).commit();
            }
        });
    }
}