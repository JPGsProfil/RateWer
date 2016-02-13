package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wlg.ratewer.Builder.RetroBuilder;
import com.example.wlg.ratewer.Core.HashPassword;
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

    User displayedUser;

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

        displayedUser = null;
        final Button bChangeData = (Button) findViewById(R.id.tv_options_bt_changeUser);
        bChangeData.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(displayedUser != null)
                {
                    updateUser();
                }
                else
                {
                    // keine online-anbindung ...
                    System.out.println("displayedUser = null");
                }
            }
        });


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
                    if(response.body() != null)
                    {
                        displayedUser = response.body();
                        TextView tv_Name = (TextView) findViewById(R.id.tv_options_Name);
                        tv_Name.setText(response.body().getName());

                        TextView tv_Email = (TextView) findViewById(R.id.tv_options_Email);
                        tv_Email.setText(response.body().getEmail());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                Log.d("Add Highscore Error ", t.getMessage());
            }
        });
    }


    private void updateUser()
    {
        // read values from textviews
        TextView tv_Name = (TextView) findViewById(R.id.tv_options_Name);
        String name = tv_Name.getText().toString();
        TextView tv_Email = (TextView) findViewById(R.id.tv_options_Email);
        String email = tv_Email.getText().toString();
        TextView tv_Password = (TextView) findViewById(R.id.tv_options_Password);
        String password = tv_Password.getText().toString();

        if(name.length() > 1 && email.length() > 1)
        {
            // update user object
            displayedUser.setName(name);
            displayedUser.setEmail(email);

            if(password.length()>1 )
            {
                String passwordHashed = HashPassword.hashString(password);
                displayedUser.setPassword(passwordHashed);
            }
            System.out.println("name "+displayedUser.getName()+" password:"+displayedUser.getPassword()
                    +" email "+displayedUser.getEmail());

            displayedUser.setHighscores(null);

            // call server
            Retrofit retrofit = RetroBuilder.getRetroObject();
            UserAPI userAPI = retrofit.create(UserAPI.class);
            Call<User> call = userAPI.UpdateUser(displayedUser);
            call.enqueue(new Callback<User>()
            {
                @Override
                public void onResponse(Response<User> response,
                                       Retrofit retrofit)
                {
                    RetroBuilder.printResponse(response);
                    if(response.code() == 200)
                    {
                        System.out.println("Daten geaendert");
                    }
                }

                @Override
                public void onFailure(Throwable t)
                {
                    Log.d("Fehler beim aendern ", t.getMessage());
                }
            });
        }


    }

}
