package com.pllug.course.tkachuk.basicandroidsocialapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.adapter.AlbumAdapter;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.ApiService;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.RetroClient;
import com.pllug.course.tkachuk.basicandroidsocialapp.model.Album;
import com.pllug.course.tkachuk.basicandroidsocialapp.reposisitory.AlbumRepository;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.InternetConnection;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumFragment extends Fragment implements View.OnClickListener{

    private View root;
    private Context mContext;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    AlbumRepository albumRepository;
    private String responseBody;

    FloatingActionButton downloadAll_fab;
    FloatingActionButton search_fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_albums, container, false);
        mContext = root.getContext();

        recyclerView = (RecyclerView) root.findViewById(R.id.albums_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        downloadAll_fab = (FloatingActionButton) root.findViewById(R.id.album_update_fab);
        search_fab = (FloatingActionButton) root.findViewById(R.id.album_search_fab);;

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

            case R.id.album_update_fab: {
                //Binding that List to Adapter
                adapter = new AlbumAdapter(getContext(), albumRepository.getList());
                recyclerView.setAdapter(adapter);
                break;
            }
            case R.id.album_search_fab: {
                final EditText titleEdit = new EditText(mContext);
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("Search album")
                        .setMessage("Enter a title of album")
                        .setView(titleEdit)
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String title = String.valueOf(titleEdit.getText());

                                if (albumRepository.getByName(title) == null) {
                                    Toast.makeText(mContext, "Not Found", Toast.LENGTH_SHORT).show();
                                } else {
                                    adapter = new AlbumAdapter(mContext,
                                            albumRepository.getByName(title));
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
        //Creating an object for our api interface
        ApiService api = RetroClient.getRetroClient();

        //Calling Json
        Call<JsonArray> jsonArrayCall = api.getAlbums();

        //Enqueue Callback will be call when get response...
        jsonArrayCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                try
                {
                    responseBody = response.body().toString();
                    Log.i("responseBodyParser",responseBody);

                    Type type = new TypeToken<ArrayList<Album>>(){}.getType();
                    ArrayList<Album> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                    albumRepository = new AlbumRepository(arrayList);

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