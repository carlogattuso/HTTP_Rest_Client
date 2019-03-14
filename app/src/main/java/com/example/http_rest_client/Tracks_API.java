package com.example.http_rest_client;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<Track> saveTrack(@Body Track track);

    @PUT("tracks")
    Call<Void> updateTrack(@Body Track track);

    @DELETE("tracks/{id}")
    Call<Void> deleteTrack(@Path("id") String id);

}
