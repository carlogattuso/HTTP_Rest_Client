package com.example.http_rest_client;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Tracks_API {

    @GET("tracks")
    Call<List<Track>> getTracks();

    @POST("tracks")
    @FormUrlEncoded
    Call<Track> savePost(@Field("id") String id,
                        @Field("title") String title,
                        @Field("singer") String singer);

    @PUT("tracks/{id}")
    @FormUrlEncoded
    Call<Track> udpdatePost(@Field("id") String id,
                        @Field("title") String title,
                        @Field("singer") String singer);

    @DELETE("tracks/{id}")
    Call<Void> deletePost(@Path("id") String id);

}
