package com.example.wlg.ratewer.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wlg.ratewer.IO.LocalStorage;
import com.example.wlg.ratewer.Model.neu.Login;
import com.example.wlg.ratewer.Model.neu.User;
import com.example.wlg.ratewer.Network.LoginAPI;
import com.example.wlg.ratewer.R;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Sabine on 26.12.2015.
 */
public class LogInActivity extends AppCompatActivity implements Callback<User> {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // auto-login:
        int userId = LocalStorage.getUserId(this.getApplicationContext());
        System.out.println("letzte User_ID: "+userId);
        if(userId != 0)
        {
            final Intent firstIntent = new Intent(this.getApplicationContext(), StartActivity.class);
            startActivity(firstIntent);
        }


        setContentView(R.layout.activity_log_in);
        //TODO: set Text of button according to the login status (Button) findViewById(R.id.btnOptionLogin)

        //Login User Button
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClickLogin();
            }
        });



        //Register User Button
        final Button bLogReg = (Button) findViewById(R.id.bRegister);
        bLogReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: User gets send to the register activity page
                final Intent firstIntent = new Intent(v.getContext(), LogRegisterActivity.class);
                startActivity(firstIntent);
            }
        });

        //Jumpover Login Button
        final Button bJumpToStart = (Button) findViewById(R.id.bjumpToStart);
        bJumpToStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: User gets send to the start activity page
                final Intent firstIntent = new Intent(v.getContext(), StartActivity.class);
                startActivity(firstIntent);
            }
        });

        context = this.getApplicationContext();

    }


    private void ClickLogin ()
    {

        EditText nameEditText = (EditText) findViewById(R.id.loginName);
        String name = nameEditText.getText().toString();

        EditText passwordEditText = (EditText) findViewById(R.id.loginPassword);
        String password = passwordEditText.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://isit-fhemc2.rhcloud.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI loginAPI = retrofit.create(LoginAPI.class);

        Login login = new Login(name,password);
        Call<User> call = loginAPI.GetLoginResult(login);
        call.enqueue(this);



    }

    @Override
    public void onResponse(Response<User> response, Retrofit retrofit) {


        //System.out.println("Username uebermittelt: " + response.body().getName());
       // System.out.println("Password uebermittelt: " + response.body().getPassword());
        System.out.println("Response body " + response.body());
        System.out.println("Response message " + response.message());
        System.out.println("Response code " + response.code());
        System.out.println("Response ERROR :" +response.errorBody());


        if (response.code() == 200 )
        {
            LocalStorage.updateUserId( context, response.body().getId());
            final Intent firstIntent = new Intent(LogInActivity.this, StartActivity.class);
            startActivity(firstIntent);
        }
        else
        {
            System.out.println("WRONG PASSWORD USER COMBINATION" );
        }
    }

    @Override
    public void onFailure(Throwable t) {
        System.out.println("Retrieving User Failed");
    }
}

