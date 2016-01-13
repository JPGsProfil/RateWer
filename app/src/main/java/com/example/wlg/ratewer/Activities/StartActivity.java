package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.wlg.ratewer.R;


public class StartActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);



        final Button bStartGame = (Button) findViewById(R.id.bStart);
        bStartGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), StartNewGameActivity.class);
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

        final Button bNetwork = (Button) findViewById(R.id.bNetworkTest);
        bNetwork.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                final Intent firstIntent = new Intent(v.getContext(), NetworkTest2Activity.class);
                startActivity(firstIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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

        if (id == R.id.aEnd || id == R.id.bEnd)
        {
            finish();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
