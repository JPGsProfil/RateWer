package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.wlg.ratewer.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabine on 16.12.2015.
 */
public class OnlineGameActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_board);


        final Button bCreateOnlineGame = (Button) findViewById(R.id.bCreateOnlineGame);
        bCreateOnlineGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Intent firstIntent = new Intent(v.getContext(), StartNewOnlineGameActivity.class);

                startActivity(firstIntent);
            }
        });

/*        final Button bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), StartActivity.class);
                startActivity(firstIntent);
            }
        });
*/
/*
        final Button bOnlineGame = (Button) findViewById(R.id.bOnline);
        bOnlineGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                final Intent firstIntent = new Intent(v.getContext(), OnlineGameActivity.class);
                startActivity(firstIntent);
            }
        });


        final Button bOptions = (Button) findViewById(R.id.bOptions);
        bOptions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                final Intent firstIntent = new Intent(v.getContext(), OptionsActivity.class);
                startActivity(firstIntent);
            }
        });


        final Button bStatistics = (Button) findViewById(R.id.bStatistic);
        bStatistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                final Intent firstIntent = new Intent(v.getContext(), StatisticActivity.class);
                startActivity(firstIntent);
            }
        });

        final Button bEnd = (Button) findViewById(R.id.bEnd);
        bEnd.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
*/
    }

}


