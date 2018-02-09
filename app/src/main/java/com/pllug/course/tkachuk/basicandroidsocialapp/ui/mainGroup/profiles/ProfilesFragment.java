package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.profiles;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Profile;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.profile.ProfileFragment;

public class ProfilesFragment extends Fragment implements IProfileView, View.OnClickListener {

    private View root;
    private Context mContext;
    private RecyclerView recyclerView;

    private FloatingActionButton search_fab;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ProfilePresenter profilePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profiles, container, false);
        mContext = root.getContext();

        initView();
        initListeners();
        initPresenter();

        profilePresenter.loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                profilePresenter.loadData();
            }
        });
        return root;
    }

    private void initView() {
        recyclerView =root.findViewById(R.id.profile_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        search_fab = root.findViewById(R.id.profile_search_fab);
        swipeRefreshLayout = root.findViewById(R.id.swiperefresh4);
    }

    private void initListeners() {
        search_fab.setOnClickListener(this);
    }

    private void initPresenter() {
        profilePresenter = new ProfilePresenter(this, mContext);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_search_fab: {
                final EditText idEdit = new EditText(mContext);
                idEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("Search profile")
                        .setMessage("Enter an id of profile")
                        .setView(idEdit)
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String id = idEdit.getText().toString();
                                profilePresenter.searchProfileById(id);
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

        Log.i("ProgressloaderImage", visibility ? "start" : "finish");
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
        if(swipeRefreshLayout !=null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showNotFound() {
        Toast.makeText(mContext, "Profile not Found", Toast.LENGTH_SHORT).show();
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

    @Override
    public void showProfile(Profile profile) {
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_main_container, profileFragment)
                .addToBackStack(null)
                .commit();
        profileFragment.setProfile(profile);
    }
}
