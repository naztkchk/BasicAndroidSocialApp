package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.album;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.adapter.AlbumAdapter;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.ApiService;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.RetroClient;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Album;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.reposisitory.AlbumRepository;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.InternetConnection;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumPresenter  implements IAlbumPresenter{

    private IAlbumView albumView;
    private Context context;

    private RecyclerView.Adapter adapter;
    private AlbumRepository albumRepository;
    private String responseBody;

    public AlbumPresenter(IAlbumView albumView, Context context){
        this.albumView = albumView;
        this.context = context;
    }

    @Override
    public void loadData() {
        albumView.setEnabledSearch(false);

        if (InternetConnection.checkConnection(context)) {
            albumView.showProgress();

            //Creating an object for our api interface
            ApiService api = RetroClient.getRetroClient();

            //Calling Json
            Call<JsonArray> jsonArrayCall = api.getAlbums();

            //Enqueue Callback will be call when get response...
            jsonArrayCall.enqueue(new Callback<JsonArray>() {

                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    try {
                        responseBody = response.body().toString();
                        Log.i("responseBodyParser", responseBody);
                        Type type = new TypeToken<ArrayList<Album>>() {
                        }.getType();
                        ArrayList<Album> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                        albumRepository = new AlbumRepository(arrayList);

                        setList();
                        albumView.setEnabledSearch(true);
                        albumView.hideProgress();
                        albumView.hideRefreshing();

                    } catch (Exception e) {
                        Log.e("onResponse", "There is an error");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    Log.i("onFailure", t.getMessage());
                    albumView.hideProgress();
                    albumView.hideProgress();
                }
            });
        }else
            albumView.showNotInternetConnection();
            albumView.setEnabledSearch(false);
            albumView.hideProgress();
            albumView.hideRefreshing();
    }

    @Override
    public void setList() {
        //Binding that List to Adapter
        adapter = new AlbumAdapter(context, albumRepository.getList());
        albumView.setAdapter(adapter);
    }

    @Override
    public void searchAlbumByTitle(final String title) {
        if (albumRepository.getByName(title) == null) {
            albumView.showNotFound();
        } else {
            adapter = new AlbumAdapter(context,
                    albumRepository.getByName(title));
            albumView.setAdapter(adapter);
        }
    }
}
