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
public class LogInActivity extends AppCompatActivity {

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

    }


}
