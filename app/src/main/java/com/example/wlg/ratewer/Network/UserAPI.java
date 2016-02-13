package com.example.wlg.ratewer.Network;

import com.example.wlg.ratewer.Model.neu.User;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by RareHue on 13.01.2016.
 */
public interface UserAPI
{

    /*
    @GET("/api/db/user/1")
    Call<User> GetUser(); //TODO CHANGE TO USERNAME AFTER TESTING
    */

    @POST("/api/db/user")
    Call<User> CreateUser(@Body User user);

    @PUT("/api/db/user")
    Call<User> UpdateUser(@Body User user);

    @GET("/api/db/user/{id}")
    Call<User> GetUser(@Path("id") int id);
}
