package com.example.wlg.ratewer.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.wlg.ratewer.Model.neu.JSONTESTCLASS;
import com.example.wlg.ratewer.Model.neu.User;
import com.example.wlg.ratewer.Network.UserAPI;
import com.example.wlg.ratewer.R;


import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class NetworkTest2Activity extends AppCompatActivity implements Callback<User> {


    private TextView tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_test);

        tw = (TextView) findViewById(R.id.textView15);


        final Button bNetwork = (Button) findViewById(R.id.bGetJSON);
        bNetwork.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClick2();
            }
        });

    }

    public void onClick2() {

        final TextView tw = (TextView) findViewById(R.id.textView15);
        tw.setText("LADE");


        setProgressBarIndeterminateVisibility(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://isit-fhemc2.rhcloud.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserAPI userAPI = retrofit.create(UserAPI.class);

        Call<User> call = userAPI.GetUser();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<User> response, Retrofit retrofit) {

        System.out.println(response.body());
        System.out.println(response.message());
        //tw.setText(response.body().toString());
    }

    @Override
    public void onFailure(Throwable t) {
        System.out.println("RESPONSE_FAILED");
    }


}
