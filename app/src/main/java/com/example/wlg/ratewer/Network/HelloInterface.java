package com.example.wlg.ratewer.Network;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by RareHue on 13.12.2015.
 */
public interface HelloInterface {


    @GET("/greeting")
    void getHello( Callback<Hello> callback );


    @GET("/greeting")
    Hello getHello();
}
