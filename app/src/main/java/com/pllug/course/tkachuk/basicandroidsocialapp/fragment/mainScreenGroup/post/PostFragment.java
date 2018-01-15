package com.pllug.course.tkachuk.basicandroidsocialapp.fragment.mainScreenGroup.post;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.adapter.CommentAdapter;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.ApiService;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.RetroClient;
import com.pllug.course.tkachuk.basicandroidsocialapp.model.Comment;
import com.pllug.course.tkachuk.basicandroidsocialapp.model.Post;
import com.pllug.course.tkachuk.basicandroidsocialapp.reposisitory.CommentRepository;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.InternetConnection;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {

    private View root;
    private Context mContext;
    private Post post;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private CommentRepository commentRepository;
    private String responseBody;

    private TextView full_post_author_tv;
    private TextView full_post_id_tv;
    private TextView full_post_title_tv;
    private TextView full_post_body_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i("onCreateView", "start");
        root = inflater.inflate(R.layout.fragment_post, container, false);
        mContext = root.getContext();

        recyclerView =  root.findViewById(R.id.comments_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        full_post_author_tv = root.findViewById(R.id.full_post_author_tv);
        full_post_id_tv = root.findViewById(R.id.full_post_id_tv);
        full_post_title_tv = root.findViewById(R.id.full_post_title_tv);
        full_post_body_tv = root.findViewById(R.id.full_post_body_tv);

        full_post_author_tv.setText(String.valueOf(post.getUserId()));
        full_post_id_tv.setText(String.valueOf(post.getId()));
        full_post_title_tv.setText(post.getTitle());
        full_post_body_tv.setText(post.getBody());

//        if(InternetConnection.checkConnection(mContext)){
//
//        }
//        else Toast.makeText(mContext, "No internet connection", Toast.LENGTH_SHORT).show();

//        if(commentRepository != null){
//            //setComments();
//            Toast.makeText(mContext, "Set comments", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(mContext, "commentRepository is null", Toast.LENGTH_SHORT).show();
//        }
        return root;
    }

    public void setPost (Post post){
        this.post = post;
        loadComments(post.getId());
    }

    public void setComments(){
        Log.i("setComments", "start");
        adapter = new CommentAdapter(getContext(), commentRepository.getList());
        recyclerView.setAdapter(adapter);
    }

    public void loadComments(int postId) {
        Log.e("loadComments", "start");

        //Creating an object for our api interface
        ApiService api = RetroClient.getRetroClient();

        Log.e("loadComments", "afterAPI");
        //Calling Json
        Call<JsonArray> jsonArrayCall = api.getCommentsById(String.valueOf(postId));

        Log.e("loadComments", "afterjsonArrayCall");

        //Enqueue Callback will be call when get response...
        jsonArrayCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                try
                {
                    responseBody = response.body().toString();
                    Log.i("responseComments", responseBody);

                    Type type = new TypeToken<ArrayList<Comment>>(){}.getType();
                    ArrayList<Comment> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                    Log.i("arrayListComments", arrayList.toString());
                    commentRepository = new CommentRepository(arrayList);

                    setComments();

                } catch (Exception e) {
                    Log.e("loadComments", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.i("loadComments", t.getMessage());
            }});
    }
}

