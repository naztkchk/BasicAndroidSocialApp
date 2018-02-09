package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.post;

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
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.adapter.CommentAdapter;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.ApiService;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.RetroClient;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Comment;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Post;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.reposisitory.CommentRepository;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostWithCommentsFragment extends Fragment implements ICommentView {

    private View root;
    private Context mContext;
    private Post post;

    private RecyclerView recyclerView;

    private TextView full_post_author_tv;
    private TextView full_post_id_tv;
    private TextView full_post_title_tv;
    private TextView full_post_body_tv;

    private CommentPresenter commentPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_post, container, false);
        mContext = root.getContext();

        initView();
        initPresenter();
        return root;
    }

    private void initView() {
        recyclerView =  root.findViewById(R.id.comments_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        full_post_author_tv = root.findViewById(R.id.full_post_author_tv);
        full_post_id_tv = root.findViewById(R.id.full_post_id_tv);
        full_post_title_tv = root.findViewById(R.id.full_post_title_tv);
        full_post_body_tv = root.findViewById(R.id.full_post_body_tv);
    }

    public void initPresenter() {commentPresenter = new CommentPresenter(this, mContext);}

    public void setInfoToPost() {
        full_post_author_tv.setText(String.valueOf(post.getUserId()));
        full_post_id_tv.setText(String.valueOf(post.getId()));
        full_post_title_tv.setText(post.getTitle());
        full_post_body_tv.setText(post.getBody());
    }

    public void setPost (Post post){
        this.post = post;
        commentPresenter.loadComments(post.getId());
    }


    @Override
    public void showProgress() {
        //none
    }

    @Override
    public void hideProgress() {
        //none
    }

    @Override
    public void hideRefreshing() {
        //none
    }

    @Override
    public void showNotFound() {
        //none
    }

    @Override
    public void showNotInternetConnection() {
      Toast.makeText(mContext, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
       recyclerView.setAdapter(adapter);
    }

    @Override
    public void setEnabledSearch(boolean b) {
        //none
    }
}

