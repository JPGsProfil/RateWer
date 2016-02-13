package com.example.wlg.ratewer.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.wlg.ratewer.Builder.RetroBuilder;
import com.example.wlg.ratewer.IO.LocalStorage;
import com.example.wlg.ratewer.Model.neu.Highscore;
import com.example.wlg.ratewer.Network.HighScoreAPI;
import com.example.wlg.ratewer.R;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class EndGameActivity extends ActionBarActivity implements Callback<List<Highscore>>
{
    private boolean hasPlayer1Won = false;
    /**
     * display whether player one had one or not
     * @param savedInstanceState get data from prev. activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String msg = extras.getString("msg");
            if (msg != null)
            {
                // Display end game msg
                TextView tv_title = (TextView) findViewById(R.id.tv_eg_msg);
                tv_title.setText(msg);
            }

            int numberOfWinner = extras.getInt("numberofwinner", 0);
            if(numberOfWinner != 0 )
            {
                if (numberOfWinner == 1)
                {
                    hasPlayer1Won = true;
                }
                updateHighscore();
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_end_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void updateHighscore()
    {
        int userId = LocalStorage.getUserId(this.getApplicationContext());
        System.out.println("letzte User_ID: "+userId);

        Retrofit retrofit = RetroBuilder.getRetroObject();
        HighScoreAPI highScoreAPI = retrofit.create(HighScoreAPI.class);
        Call<List<Highscore>> call = highScoreAPI.getHighScores(userId);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Response<List<Highscore>> response, Retrofit retrofit)
    {
        System.out.println("Response body " + response.body());
        System.out.println("Response message " + response.message());
        System.out.println("Response code " + response.code());
        System.out.println("Response ERROR :" + response.errorBody());

        if (response.code() == 200 )
        {
            HighScoreAPI highScoreAPI = retrofit.create(HighScoreAPI.class);
            System.out.println("Bin in erste Response = 200");

            List<Highscore> highscores = response.body();
            for(int index = 0; index < highscores.size(); index ++)
            {
                // increase number of won games:
                if (hasPlayer1Won == true)
                {
                    System.out.println("Bin in hasPlayer1Won");
                    System.out.println("gewonnen ausgelesen: " + highscores.get(index).getMatchesWon());
                    int matchesWonNew = highscores.get(index).getMatchesWon() + 1;
                    System.out.println("gewonnen bald: " + matchesWonNew);
                    highscores.get(index).setMatchesWon(matchesWonNew);
                }
                else
                {
                    // player lost, increase by one
                    highscores.get(index).setMatchesLost(highscores.get(index).getMatchesLost() + 1);
                }
                Call<Highscore> call = highScoreAPI.updateHighScore(highscores.get(index));
                call.enqueue(new Callback<Highscore>()
                {
                    @Override
                    public void onResponse(Response<Highscore> response,
                                           Retrofit retrofit)
                    {
                        System.out.println("Response body " + response.body());
                        System.out.println("Response message " + response.message());
                        System.out.println("Response code " + response.code());
                        System.out.println("Response ERROR :" + response.errorBody());
                        System.out.println("highscore erfolgreich geaendert");
                    }

                    @Override
                    public void onFailure(Throwable t)
                    {
                        Log.d("Add Highscore Error ", t.getMessage());
                    }
                });
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
