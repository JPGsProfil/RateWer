package com.example.wlg.ratewer.Builder;

import android.util.Log;

import com.example.wlg.ratewer.Model.neu.Highscore;
import com.example.wlg.ratewer.Network.HighScoreAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Jean on 13.02.2016.
 */
public class RetroBuilder
{
    public static final Retrofit getRetroObject()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://isit-fhemc2.rhcloud.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    //private Retrofit retrofit;
    //private List<Highscore> highscores;
    /*
    public RetroBuilder()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://isit-fhemc2.rhcloud.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //highscores = new ArrayList<>();
    }*/

    /*
    public List<Highscore> getHighscores(int userId)
    {
        HighScoreAPI highScoreAPI = retrofit.create(HighScoreAPI.class);
        Call<List<Highscore>> call = highScoreAPI.getHighScores(userId);
        call.enqueue(new Callback<List<Highscore>>()
        {
            @Override
            public void onResponse(Response <List<Highscore>> response,
                                   Retrofit retrofit)
            {
                printResponse(response);
                List<Highscore> highscoresReturn = response.body();
                if (highscoresReturn != null)
                {
                    highscores = highscoresReturn;
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                Log.d("Add Highscore Error ", t.getMessage());
            }
        });

        return highscores;
    }*/


    public static void printResponse(Response response)
    {
        System.out.println("Response body " + response.body());
        System.out.println("Response message " + response.message());
        System.out.println("Response code " + response.code());

        if (response.code() == 200 )
        {
            System.out.println("200 erfolgreich");
        }
        else
        {
            System.out.println("Response ERROR :" + response.errorBody());
        }
    }


}
