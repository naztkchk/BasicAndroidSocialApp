package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.posts;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;

public class PostsFragment extends Fragment implements IPostView, View.OnClickListener{

    private View root;
    private Context mContext;

    private RecyclerView recyclerView;
    private FloatingActionButton search_fab;

    private PostPresenter postPresenter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_posts, container, false);
        mContext = root.getContext();

        initView();
        initListener();
        initPresenter();

        postPresenter.loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postPresenter.loadData();
            }
        });

        return root;
    }

    private void initView() {
        recyclerView =  root.findViewById(R.id.post_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        search_fab =  root.findViewById(R.id.post_search_fab);
        swipeRefreshLayout = root.findViewById(R.id.swiperefresh3);
    }

    private void initListener() {
        search_fab.setOnClickListener(this);
    }

    private void initPresenter() {
        postPresenter = new PostPresenter(this, mContext);
    }

    public void onClick (View view){
        switch (view.getId()) {
            case R.id.post_search_fab: {
                final EditText idEdit = new EditText(mContext);
                idEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("Search post")
                        .setMessage("Enter an id of post")
                        .setView(idEdit)
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String id  = idEdit.getText().toString();
                                postPresenter.searchAPostById(id);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                break;
            }
        }
    }

    private void showProgressLoaderWithBackground (boolean visibility, String text) {
        if (text == null)
            text = "";
        ((TextView) root.findViewById(R.id.progress_bar_text)).setText(text);

        if(visibility){
            root.findViewById(R.id.container_progress_bar).setVisibility(View.VISIBLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else
        {
            root.findViewById(R.id.container_progress_bar).setVisibility(View.GONE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        Log.i("ProgressloaderAlbum", visibility ? "start" : "finish");
    }

    @Override
    public void showProgress() {
        showProgressLoaderWithBackground(true, "load data");
    }

    @Override
    public void hideProgress() {
        showProgressLoaderWithBackground(false, "load data");
    }

    @Override
    public void hideRefreshing() {
        if(swipeRefreshLayout!=null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNotFound() {
        Toast.makeText(mContext, "Not Found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotInternetConnection() {
        Toast.makeText(mContext, "Not Internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setEnabledSearch(boolean b) {
        search_fab.setEnabled(b);
    }
}