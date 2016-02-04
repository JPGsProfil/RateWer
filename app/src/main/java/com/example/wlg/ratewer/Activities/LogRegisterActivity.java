package com.example.wlg.ratewer.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
public class LogRegisterActivity extends AppCompatActivity implements Callback<User>
{
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_register);

        final Button bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClickRegister();
            }
        });

        //Cancel or goback Button sends user to the login page
        final Button bCancel = (Button) findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: User gets send to the register activity page
                final Intent firstIntent = new Intent(v.getContext(), LogInActivity.class);
                startActivity(firstIntent);
            }
        });

        context = this.getApplicationContext();
    }

    public void ClickRegister()
    {
        //TODO: check up if the input fields are correct filled out -> send to server

        EditText emailEditText = (EditText) findViewById(R.id.userMail);
        String email = emailEditText.getText().toString();

        EditText nameEditText = (EditText) findViewById(R.id.userName);
        String name = nameEditText.getText().toString();

        EditText passwordEditText1 = (EditText) findViewById(R.id.userPassword1);
        String password1 = passwordEditText1.getText().toString();

        EditText passwordEditText2 = (EditText) findViewById(R.id.userPassword2);
        String password2 = passwordEditText2.getText().toString();

        //TODO : Check for same usernames, the way we use get forces us to do so
        boolean inputCheck = (password1.equals(password2));

        if (inputCheck) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://isit-fhemc2.rhcloud.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            UserAPI userAPI = retrofit.create(UserAPI.class);
            User user = new User(email,name,password1);

            System.out.println(user);

            Call<User> call = userAPI.CreateUser(user);
            call.enqueue(this);

        } else {

            //TODO: some of the input is wrong and user gets notice
            System.out.println("Fehler bei der Eingabe");
        }


    }

    @Override
    public void onResponse(Response<User> response, Retrofit retrofit) {

        System.out.println(response.body());
        System.out.println(response.message());
        System.out.println("Error : " + response.errorBody());
        System.out.println(response);

       /* final Intent firstIntent = new Intent(context, StartActivity.class);
        startActivity(firstIntent);*/
    }

    @Override
    public void onFailure(Throwable t) {

        System.out.println("Creating User Failed");

    }
}
