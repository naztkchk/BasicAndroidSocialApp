package com.pllug.course.tkachuk.basicandroidsocialapp.fragment.mainScreenGroup.post;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.adapter.PostAdapter;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.ApiService;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.RetroClient;
import com.pllug.course.tkachuk.basicandroidsocialapp.model.Post;
import com.pllug.course.tkachuk.basicandroidsocialapp.reposisitory.PostRepository;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.InternetConnection;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class PostsFragment extends Fragment implements View.OnClickListener{

    private View root;
    private Context mContext;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    PostRepository postRepository;
    private String responseBody;

    FloatingActionButton downloadAll_fab;
    FloatingActionButton search_fab;

    Button see_comments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_posts, container, false);
        mContext = root.getContext();

        recyclerView = (RecyclerView) root.findViewById(R.id.post_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        downloadAll_fab = (FloatingActionButton) root.findViewById(R.id.post_update_fab);
        search_fab = (FloatingActionButton) root.findViewById(R.id.post_search_fab);
        see_comments = root.findViewById(R.id.row_post_see_comments_btn);

        if(InternetConnection.checkConnection(mContext)) {
            downloadAll_fab.setOnClickListener(this);
            search_fab.setOnClickListener(this);
            loadData();
        }
        else Toast.makeText(mContext, "No internet connection", Toast.LENGTH_SHORT).show();

        return root;
    }

    public void onClick (View view){

        switch (view.getId()) {

            case R.id.post_update_fab: {
                //Binding that List to Adapter
                PostAdapter postAdapter = new PostAdapter(getContext(), postRepository.getList());
                adapter = postAdapter;
                recyclerView.setAdapter(adapter);
                break;
            }

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
                                if (id.matches("") || postRepository.getById(parseInt(id)) == null) {
                                    Toast.makeText(mContext, "Not Found", Toast.LENGTH_SHORT).show();
                                } else {
                                    adapter = new PostAdapter(mContext,
                                            postRepository.getById(parseInt(id)));
                                    recyclerView.setAdapter(adapter);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                break;
            }
        }
    }

    private void loadData(){

        Log.i("loadData", "postFragment");
        //Creating an object for our api interface
        ApiService api = RetroClient.getRetroClient();

        //Calling Json
        Call<JsonArray> jsonArrayCall = api.getPosts();

        //Enqueue Callback will be call when get response...

        Log.i("loadData", "before");

        jsonArrayCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                try
                {
                    Log.i("loadData", "onResponse");

                    responseBody = response.body().toString();
                    Log.i("responseBodyParser",responseBody);

                    Type type = new TypeToken<ArrayList<Post>>(){}.getType();
                    ArrayList<Post> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                    Log.i("arrayList", arrayList.toString());
                    postRepository = new PostRepository(arrayList);

                } catch (Exception e) {
                    Log.e("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.i("onFailure", t.getMessage());
            }});
    }

}