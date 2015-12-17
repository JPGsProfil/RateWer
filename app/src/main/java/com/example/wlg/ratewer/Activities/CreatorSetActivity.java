package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.example.wlg.ratewer.R;

/**
 * Created by Sabine on 16.12.2015.
 */
public class CreatorSetActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_set);

        final Button bAddCard = (Button) findViewById(R.id.bAddCard);
        bAddCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorCardActivity.class);
                startActivity(firstIntent);
            }
        });

            final Button bDelSet = (Button) findViewById(R.id.bDeleteSet);
            bDelSet.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final Intent firstIntent = new Intent(v.getContext(), CreatorMenuActivity.class);
                    startActivity(firstIntent);
                }
            });


            final Button bSave = (Button) findViewById(R.id.bSave);
            bSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final Intent firstIntent = new Intent(v.getContext(), CreatorMenuActivity.class);
                    startActivity(firstIntent);
                }
            });

        final Button bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), OnlineGameActivity.class);
                startActivity(firstIntent);
            }
        });


    }

}