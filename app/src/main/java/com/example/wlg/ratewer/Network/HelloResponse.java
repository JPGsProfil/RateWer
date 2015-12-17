package com.example.wlg.ratewer.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Created by RareHue on 13.12.2015.
 */
public class HelloResponse {

    public static HelloResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        HelloResponse boxOfficeMovieResponse = gson.fromJson(response, HelloResponse.class);
        return boxOfficeMovieResponse;
    }
}
