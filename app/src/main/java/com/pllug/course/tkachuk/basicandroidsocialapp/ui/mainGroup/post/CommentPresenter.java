package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.post;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.ApiService;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.RetroClient;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Comment;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.reposisitory.CommentRepository;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.adapter.CommentAdapter;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.InternetConnection;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentPresenter implements ICommentPresenter {

    private ICommentView commentView;
    private Context context;

    private RecyclerView.Adapter adapter;
    private CommentRepository commentRepository;
    private String responseBody;

    public CommentPresenter(ICommentView iCommentView, Context context){
        this.commentView = iCommentView;
        this.context = context;
    }

    @Override
    public void setList() {
        adapter = new CommentAdapter(context, commentRepository.getList());
        commentView.setAdapter(adapter);
    }

    public void loadComments(int postId)
    {
        commentView.setEnabledSearch(false);

        if (InternetConnection.checkConnection(context)) {
            commentView.showProgress();

            //Creating an object for our api interface
            ApiService api = RetroClient.getRetroClient();

            //Calling Json
            Call<JsonArray> jsonArrayCall = api.getCommentsById(String.valueOf(postId));

            //Enqueue Callback will be call when get response...
            jsonArrayCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    try
                    {
                        responseBody = response.body().toString();

                        Type type = new TypeToken<ArrayList<Comment>>(){}.getType();
                        ArrayList<Comment> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                        commentRepository = new CommentRepository(arrayList);

                        setList();
                        commentView.setInfoToPost();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    Log.i("loadComments", t.getMessage());
                }});
        }
    }
}
