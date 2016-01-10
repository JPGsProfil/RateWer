package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.example.wlg.ratewer.R;

/**
 * Created by rofel on 16.12.2015.
 */
public class CreatorMenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_menu);

        final Button bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), StartActivity.class);
                startActivity(firstIntent);
            }
        });

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
    }
}
