package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wlg.ratewer.R;

/**
 * Created by Sabine on 26.12.2015.
 */
public class LogRegisterActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_register);

        //Register Button
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: check up if the input fields are correct filled out -> send to server
                if (true) {
                    //TODO: all is ok and user gets send to the start activity
                } else {
                    //TODO: some of the input is wrong and user gets notice
                }

                //TODO: delete this two lines later as they are only for now to send user no matter what to the startactivity
                final Intent firstIntent = new Intent(v.getContext(), StartActivity.class);
                startActivity(firstIntent);
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

    }

}
