package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.wlg.ratewer.R;

/**
 * Created by Sabine on 16.12.2015.
 */



public class CreatorCardActivity extends ActionBarActivity {

    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_card);

        //save progress of new card to the set and clean cache of the activity for new card
        final Button bNewCard = (Button) findViewById(R.id.bNextNewCard);
        bNewCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorCardActivity.class);
                startActivity(firstIntent);
            }
        });

        //Save progress
        final Button bDone = (Button) findViewById(R.id.bDoneCard);
        bDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                startActivity(firstIntent);
            }
        });

        //Cancel Card creation
        final Button bCancel = (Button) findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                startActivity(firstIntent);
            }
        });

        findViewById(R.id.iBNewCard)
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                        final Intent firstIntent = new Intent(arg0.getContext(), CreatorCardActivity.class);
                        startActivity(firstIntent);
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        final Intent firstIntent = new Intent(this.findViewById(android.R.id.content).getContext(), CreatorSetActivity.class);
        startActivity(firstIntent);
    }
}