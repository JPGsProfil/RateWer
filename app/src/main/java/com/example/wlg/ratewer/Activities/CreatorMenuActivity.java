package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import com.example.wlg.ratewer.R;

/**
 * Created by rofel on 16.12.2015.
 */
public class CreatorMenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_menu);

        Log.d("TEST", "oncreate started");

       /* final Button bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), StartActivity.class);
                startActivity(firstIntent);
            }
        });
*/
        final Button bNewSet = (Button) findViewById(R.id.bNewSet);
        bNewSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                startActivity(firstIntent);
            }
        });

        final Button bEditSet = (Button) findViewById(R.id.bEditSet);
        bEditSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                startActivity(firstIntent);
            }
        });

        /*final Button bDelSet = (Button) findViewById(R.id.bDeleteSet);
        bDelSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

            }
        });*/

/*        final TableRow rSelectItem1 = (TableRow)findViewById(R.id.tableRow5);
        final TableRow rSelectItem2 = (TableRow)findViewById(R.id.tableRow6);
        final TableRow rSelectItem3 = (TableRow)findViewById(R.id.tableRow7);

        rSelectItem1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                EditCardSetFromList(firstIntent, 1);
            }
        });

        rSelectItem2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                EditCardSetFromList(firstIntent, 2);
            }
        });

        rSelectItem3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                EditCardSetFromList(firstIntent, 2);
            }
        });
*/

    }
/*
    public void EditCardSetFromList(Intent firstIntent, int id)
    {
        startActivity(firstIntent);
    }
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }*/
}
