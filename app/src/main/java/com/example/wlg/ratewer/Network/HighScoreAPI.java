package com.example.wlg.ratewer.Network;

import com.example.wlg.ratewer.Model.neu.Highscore;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Jean on 12.02.2016.
 */
public interface HighScoreAPI
{
    @GET("/api/db/score/user/{id}")
    Call<List<Highscore>> getHighScores(@Path("id") int id);

    @PUT("/api/db/score")
    Call<Highscore> updateHighScore(@Body Highscore highscore);
}
