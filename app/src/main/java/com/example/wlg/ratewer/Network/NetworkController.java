package com.example.wlg.ratewer.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.net.NetworkInterface;

import retrofit.Retrofit;

/**
 * Created by RareHue on 13.12.2015.
 */
public class NetworkController {


    private static final String API_ENDPOINT = "http://rest-service.guides.spring.io";
    private static HelloInterface NETWORK_INTERFACE;

    private static final OkHttpClient client = new OkHttpClient();

    public static final HelloInterface get() {

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(API_ENDPOINT)
                .build();

        NETWORK_INTERFACE = retrofit.create(HelloInterface.class);

        return  NETWORK_INTERFACE;

    }


    /*
    Aus den Folien geht leider nicht.....

    private static  final String API_ENDPOINT ="http://rest-service.guides.spring.io/greeting";
    private static NetworkInterface NETWORK_INTERFACE;
    public static final NetworkInterface get()
    {
        if (NETWORK_INTERFACE == null)
        {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(API_ENDPOINT);
                    .build();
            NETWORK_INTERFACE = adapter.create(NetworkInterface.class);
        }
        return NETWORK_INTERFACE;
    }*/
}
