package com.example.wlg.ratewer.Network;

import com.example.wlg.ratewer.Model.neu.Login;
import com.example.wlg.ratewer.Model.neu.LoginResult;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by RareHue on 02.02.2016.
 */

public interface LoginAPI {


    @POST("/api/db/user/namepassword")
    Call<LoginResult> GetLoginResult(@Body Login login);

}


