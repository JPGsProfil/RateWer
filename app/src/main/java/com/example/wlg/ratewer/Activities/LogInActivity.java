package com.example.wlg.ratewer.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wlg.ratewer.Model.neu.User;
import com.example.wlg.ratewer.Network.UserAPI;
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

        EditText passwordEditText = (EditText) findViewById(R.id.passwordInput);
        String password = passwordEditText.getText().toString();

        // TODO wir checken immernoch mit der id....die der nutzer niemals eingibt --> beim get serverseitig den nutzernamen übergeben

        if (response.body().getPassword().equals(password)) {
            final Intent firstIntent = new Intent(context, StartActivity.class);
            startActivity(firstIntent);
        } else {
            System.out.println("WRONG PASSWORD : " + response.body().getPassword()+" is not " + password );
        }
    }

    @Override
    public void onFailure(Throwable t) {
        System.out.println("Retrieving User Failed");
    }
}

