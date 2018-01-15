package com.pllug.course.tkachuk.basicandroidsocialapp.api;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/albums")
    Call<JsonArray> getAlbums();

    @GET("/photos")
    Call<JsonArray> getPhotos();

    @GET("/users")
    Call<JsonArray> getProfiles();

    @GET("/posts")
    Call<JsonArray> getPosts();

    @GET("/comments")
    Call<JsonArray> getComments();

    @GET("/comments")
    Call<JsonArray> getCommentsById(@Query("postId") int id);
}
