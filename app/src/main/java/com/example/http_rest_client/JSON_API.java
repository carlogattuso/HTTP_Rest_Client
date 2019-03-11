package com.example.http_rest_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JSON_API {

    @GET("posts")
    Call<List<Post>> getPosts();

    @POST("posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("id") int id,
                        @Field("title") String title);

    @PUT("posts/{id}")
    @FormUrlEncoded
    Call<Post> updatePost(@Path("id") int id_path,
                          @Field("id") int id,
                          @Field("title") String title);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

}
