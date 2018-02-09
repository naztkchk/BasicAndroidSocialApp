package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.image;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.ApiService;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.RetroClient;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Image;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.reposisitory.ImageRepository;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.adapter.ImageAdapter;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.InternetConnection;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImagePresenter  implements IImagePresenter{

    private IImageView imageView;
    private Context context;

    private RecyclerView.Adapter adapter;
    private ImageRepository imageRepository;
    private String responseBody;

    public ImagePresenter (IImageView imageView, Context context){
        this.imageView = imageView;
        this.context = context;
    }

    public void loadData(){

        imageView.setEnabledSearch(false);

        if (InternetConnection.checkConnection(context)) {
            imageView.showProgress();

        //Creating an object for our api interface
        ApiService api = RetroClient.getRetroClient();

        //Calling Json
        Call<JsonArray> jsonArrayCall = api.getPhotos();

        //Enqueue Callback will be call when get response...
        jsonArrayCall.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                try
                {
                    responseBody = response.body().toString();
                    Log.i("responseBodyParser",responseBody);
                    Type type = new TypeToken<ArrayList<Image>>(){}.getType();
                    ArrayList<Image> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                    imageRepository = new ImageRepository(arrayList);

                    setList();
                    imageView.setEnabledSearch(true);
                    imageView.hideProgress();
                    imageView.hideRefreshing();

                } catch (Exception e) {
                    Log.e("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.i("onFailure", t.getMessage());
                imageView.hideProgress();
            }
        });
        }else
            imageView.showNotInternetConnection();
            imageView.setEnabledSearch(false);
            imageView.hideRefreshing();
    }

    @Override
    public void setList() {
        adapter = new ImageAdapter(context, imageRepository.getList());
        imageView.setAdapter(adapter);
    }

    @Override
    public void searchImageByTitle(final String title) {
        if (imageRepository.getByName(title) == null) {
            imageView.showNotFound();
        } else {
            adapter = new ImageAdapter(context,
                    imageRepository.getByName(title));
            imageView.setAdapter(adapter);
        }
    }
}
