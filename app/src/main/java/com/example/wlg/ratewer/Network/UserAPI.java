package com.example.wlg.ratewer.Network;

import com.example.wlg.ratewer.Model.neu.JSONTESTCLASS;
import com.example.wlg.ratewer.Model.neu.User;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by RareHue on 13.01.2016.
 */
public interface UserAPI {

    @GET("/ip")
    Call<JSONTESTCLASS> GetUser(); //TODO CHANGE TO USER AFTER TESTING
}
