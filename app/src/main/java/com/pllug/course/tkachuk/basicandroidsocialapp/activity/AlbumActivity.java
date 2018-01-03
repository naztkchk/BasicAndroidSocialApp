package com.pllug.course.tkachuk.basicandroidsocialapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    AlbumRepository albumRepository;
    private String responseBody;

    FloatingActionButton downloadAll_fab;
    FloatingActionButton search_fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        recyclerView = (RecyclerView) findViewById(R.id.albums_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        downloadAll_fab = (FloatingActionButton) findViewById(R.id.album_update_fab);
        search_fab = (FloatingActionButton) findViewById(R.id.album_search_fab);;

        if(InternetConnection.checkConnection(getApplicationContext())) {
            downloadAll_fab.setOnClickListener(this);
            search_fab.setOnClickListener(this);
            loadData();
        }
        else Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    public void onClick (View view){

        switch (view.getId()) {
            case R.id.album_update_fab: {
                //Binding that List to Adapter
                adapter = new AlbumAdapter(getApplicationContext(), albumRepository.getList());
                recyclerView.setAdapter(adapter);
            }
            case R.id.album_search_fab: {
                final EditText titleEdit = new EditText(AlbumActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(AlbumActivity.this)
                        .setTitle("Search album")
                        .setMessage("Enter a title of album")
                        .setView(titleEdit)
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String title = String.valueOf(titleEdit.getText());

                                if (albumRepository.getByName(title) == null) {
                                    Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_SHORT).show();
                                } else {
                                    adapter = new AlbumAdapter(getApplicationContext(),
                                            albumRepository.getByName(title));
                                    recyclerView.setAdapter(adapter);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
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

