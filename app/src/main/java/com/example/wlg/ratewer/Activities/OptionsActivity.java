package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.wlg.ratewer.Builder.RetroBuilder;
import com.example.wlg.ratewer.IO.LocalStorage;
import com.example.wlg.ratewer.Model.neu.User;
import com.example.wlg.ratewer.Network.UserAPI;
import com.example.wlg.ratewer.R;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        //TODO: set Text of button according to the login status (Button) findViewById(R.id.btnOptionLogin)

        //btnOptionLogin
        final Button bOptionLogin = (Button) findViewById(R.id.btnOptionLogin);
        if(LocalStorage.getUserId(this) == 0)
        {
            bOptionLogin.setClickable(false);
            bOptionLogin.setVisibility(View.INVISIBLE);
        }
        else
        {
            bOptionLogin.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {

                    LocalStorage.updateUserId(OptionsActivity.this, 0);
                    final Intent firstIntent = new Intent(v.getContext(), LogInActivity.class);
                    startActivity(firstIntent);
                }
            });
        }



        displayUser();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void displayUser()
    {
        int userId = LocalStorage.getUserId(this.getApplicationContext());
        Retrofit retrofit = RetroBuilder.getRetroObject();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<User> call = userAPI.GetUser(userId);
        call.enqueue(new Callback<User>()
        {
            @Override
            public void onResponse(Response<User> response,
                                   Retrofit retrofit)
            {
                RetroBuilder.printResponse(response);
                if(response.code() == 200)
                {
                    System.out.println("Bin in OptionResponse");
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                Log.d("Add Highscore Error ", t.getMessage());
            }
        });
    }

}
