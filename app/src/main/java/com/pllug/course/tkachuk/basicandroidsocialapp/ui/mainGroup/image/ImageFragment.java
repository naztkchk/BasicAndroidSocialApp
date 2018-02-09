package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.image;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;

public class ImageFragment extends Fragment implements IImageView, View.OnClickListener{

    private View root;
    private Context mContext;
    private RecyclerView recyclerView;
    private FloatingActionButton search_fab;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ImagePresenter imagePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_images, container, false);

        mContext = root.getContext();

        initView();
        initPresenter();
        initListener();

        imagePresenter.loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                imagePresenter.loadData();
                Log.i("image", "uuu");
            }
        });

        return root;
    }

    private void initView() {
        search_fab = root.findViewById(R.id.photo_search_fab);
        swipeRefreshLayout = root.findViewById(R.id.swiperefresh2);
        recyclerView = root.findViewById(R.id.photo_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        imagePresenter = new ImagePresenter(this, mContext);
    }

    private void initListener() {
        search_fab.setOnClickListener(this);
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

        Log.i("ProgressloaderImage", visibility ? "start" : "finish");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo_search_fab: {
                final EditText titleEdit = new EditText(mContext);
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("Search image")
                        .setMessage("Enter a title of image")
                        .setView(titleEdit)
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String title = String.valueOf(titleEdit.getText());
                                imagePresenter.searchImageByTitle(title);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                break;
            }
        }
    }

    @Override
    public void showProgress() {
        showProgressLoaderWithBackground(true, " loading data...");
    }

    @Override
    public void hideProgress() {
        showProgressLoaderWithBackground(false, " loading data...");
    }

    @Override
    public void hideRefreshing() {
        if (swipeRefreshLayout!=null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNotFound() {
        Toast.makeText(mContext, "This Image not found", Toast.LENGTH_SHORT).show();
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
        search_fab.setEnabled(b);
    }

}
