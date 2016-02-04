package com.example.wlg.ratewer.Network;

import com.example.wlg.ratewer.Model.neu.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by RareHue on 13.01.2016.
 */
public interface UserAPI {

    /*
    @GET("/api/db/user/1")
    Call<User> GetUser(); //TODO CHANGE TO USERNAME AFTER TESTING
    */

    @POST("/api/db/user")
    Call<User> CreateUser(@Body User user);
}
