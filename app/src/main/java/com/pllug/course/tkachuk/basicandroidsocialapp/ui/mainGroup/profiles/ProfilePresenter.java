package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.profiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.ApiService;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.RetroClient;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Profile;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.reposisitory.ProfileRepository;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.adapter.ProfileAdapter;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.InternetConnection;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class ProfilePresenter  implements IProfilePresenter{

    private IProfileView profileView;
    private Context mContext;
    private String responseBody;
    private ProfileRepository profileRepository;
    private RecyclerView.Adapter adapter;

    public ProfilePresenter(IProfileView iProfileView, Context mContext) {
        this.profileView = iProfileView;
        this.mContext = mContext;
    }

    @Override
    public void loadData() {

        profileView.setEnabledSearch(false);

        if (InternetConnection.checkConnection(mContext)) {
            profileView.showProgress();

            //Creating an object for our api interface
        ApiService api = RetroClient.getRetroClient();

        //Calling Json
        Call<JsonArray> jsonArrayCall = api.getProfiles();

        //Enqueue Callback will be call when get response...
        jsonArrayCall.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                try
                {
                    responseBody = response.body().toString();
                    Type type = new TypeToken<ArrayList<Profile>>(){}.getType();
                    ArrayList<Profile> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                    profileRepository = new ProfileRepository(arrayList);

                    setList();
                    profileView.setEnabledSearch(true);
                    profileView.hideProgress();
                    profileView.hideRefreshing();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.i("onFailure", t.getMessage());
                profileView.hideProgress();
            }});
    }else
            profileView.showNotInternetConnection();
            profileView.setEnabledSearch(false);
            profileView.hideRefreshing();
    }

    @Override
    public void setList() {
        adapter = new ProfileAdapter(mContext, profileRepository.getList());
        profileView.setAdapter(adapter);
    }

    @Override
    public void searchProfileById(String id) {
        if (id.matches("") || profileRepository.getById(parseInt(id)) == null) {
            profileView.showNotFound();
        } else {
            profileView.showProfile(profileRepository.getById(parseInt(id)));
        }
    }
}
