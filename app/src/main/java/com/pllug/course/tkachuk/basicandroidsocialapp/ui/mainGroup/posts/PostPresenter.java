package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.posts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.ApiService;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.RetroClient;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Post;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.reposisitory.PostRepository;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.adapter.PostAdapter;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.InternetConnection;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class PostPresenter implements IPostPresenter {

    private IPostView postView;
    private Context context;

    private RecyclerView.Adapter adapter;
    private PostRepository postRepository;
    private String responseBody;

    public PostPresenter (IPostView iPostView, Context context){
        this.postView = iPostView;
        this.context = context;
    }

    @Override
    public void loadData() {

        postView.setEnabledSearch(false);

        if (InternetConnection.checkConnection(context)) {
            postView.showProgress();

            //Creating an object for our api interface
            ApiService api = RetroClient.getRetroClient();

            //Calling Json
            Call<JsonArray> jsonArrayCall = api.getPosts();

            //Enqueue Callback will be call when get response...

            jsonArrayCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    try {
                        responseBody = response.body().toString();

                        Type type = new TypeToken<ArrayList<Post>>() {
                        }.getType();
                        ArrayList<Post> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                        postRepository = new PostRepository(arrayList);

                        setList();
                        postView.hideRefreshing();
                        postView.setEnabledSearch(true);
                        postView.hideProgress();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    Log.i("onFailure", t.getMessage());
                    postView.hideRefreshing();
                    postView.hideProgress();

                }
            });
        }
        else{
            postView.showNotInternetConnection();
            postView.hideRefreshing();
        }

    }


    @Override
    public void setList() {
        adapter = new PostAdapter(context, postRepository.getList());
        postView.setAdapter(adapter);
    }

    @Override
    public void searchAPostById(final String id) {
        if (id.matches("") || postRepository.getById(parseInt(id)) == null) {
                postView.showNotFound();
        } else {
            adapter = new PostAdapter(context,
                    postRepository.getById(parseInt(id)));
            postView.setAdapter(adapter);
        }
    }
}
