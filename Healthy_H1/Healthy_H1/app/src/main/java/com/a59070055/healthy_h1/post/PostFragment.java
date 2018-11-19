package com.a59070055.healthy_h1.post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.a59070055.healthy_h1.MenuFragment;
import com.a59070055.healthy_h1.R;
import com.a59070055.healthy_h1.comment.CommentFragment;
import com.squareup.okhttp.MediaType;

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

public class PostFragment extends Fragment {

    private static final String TAG = "POST";

    private final String url = "https://jsonplaceholder.typicode.com/posts";

    private ArrayList<Post> postArrayList = new ArrayList<>();
    private ListView postList;
    private PostAdapter postAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initBackBtn();

        postList = getView().findViewById(R.id.frg_post_list);
        postAdapter = new PostAdapter(getActivity(), R.layout.fragment_post_item, postArrayList);

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
                                        Log.d(TAG, jsonObject.getString("title"));
                                        Post post = new Post(jsonObject.getInt("id"), jsonObject.getString("title"), jsonObject.getString("body"));
                                        postArrayList.add(post);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                postAdapter.notifyDataSetChanged();
                                postList.setAdapter(postAdapter);
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

        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = (Post) parent.getAdapter().getItem(position);
                Fragment fragment = new CommentFragment();
                Bundle bundle = new Bundle();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                bundle.putInt("postid", post.getId());
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.main_view, fragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void initBackBtn() {
        Button back = getView().findViewById(R.id.frg_post_backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).commit();
            }
        });
    }
}