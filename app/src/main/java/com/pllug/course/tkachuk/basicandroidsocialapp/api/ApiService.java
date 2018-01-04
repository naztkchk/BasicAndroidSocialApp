package com.pllug.course.tkachuk.basicandroidsocialapp.api;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/albums")
    Call<JsonArray> getAlbums();

    @GET("/photos")
    Call<JsonArray> getPhotos();

    @GET("/users")
    Call<JsonArray> getProfiles();
}
