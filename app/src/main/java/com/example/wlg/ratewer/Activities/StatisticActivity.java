package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.wlg.ratewer.IO.LocalStorage;
import com.example.wlg.ratewer.Model.neu.Highscore;
import com.example.wlg.ratewer.Model.neu.User;
import com.example.wlg.ratewer.Network.HighScoreAPI;
import com.example.wlg.ratewer.Network.LoginAPI;
import com.example.wlg.ratewer.Network.UserAPI;
import com.example.wlg.ratewer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class StatisticActivity extends AppCompatActivity implements Callback<User> {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        TextView tv_Name = (TextView) findViewById(R.id.tv_statistic_Name);
        tv_Name.setText("du");

        TextView tv_Won = (TextView) findViewById(R.id.tv_statistic_Won);
        tv_Won.setText("0 mal");

        TextView tv_Lost = (TextView) findViewById(R.id.tv_statistic_Lost);
        tv_Lost.setText("0 mal");
        requestHighscore();



    }


    private void requestHighscore()
    {
        int userId = LocalStorage.getUserId(this.getApplicationContext());
        System.out.println("letzte User_ID: "+userId);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://isit-fhemc2.rhcloud.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserAPI userAPI = retrofit.create(UserAPI.class);

        Call<User> call = userAPI.GetUser(userId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<User> response, Retrofit retrofit)
    {
        System.out.println("Response body " + response.body());
        System.out.println("Response message " + response.message());
        System.out.println("Response code " + response.code());
        System.out.println("Response ERROR :" +response.errorBody());

        if (response.code() == 200 )
        {
            //System.out.println("gewonnen :" + response.body().get(0).getHighscores().get(0).getMatchesWon());
            TextView tv_Name = (TextView) findViewById(R.id.tv_statistic_Name);
            tv_Name.setText(response.body().getName());
            // should not happen, but maybe no highscore entry for this user
            if(response.body().getHighscores() != null)
            {
                TextView tv_Won = (TextView) findViewById(R.id.tv_statistic_Won);
                tv_Won.setText(response.body().getHighscores().get(0).getMatchesWon().toString());

                TextView tv_Lost = (TextView) findViewById(R.id.tv_statistic_Lost);
                tv_Lost.setText(response.body().getHighscores().get(0).getMatchesLost().toString());
            }

        }
        else
        {
            System.out.println("no highscore");
        }
    }

    @Override
    public void onFailure(Throwable t)
    {
        Log.d("onFailure Error ", t.getMessage());
        System.out.println("Retrieving User Failed");
    }


}

