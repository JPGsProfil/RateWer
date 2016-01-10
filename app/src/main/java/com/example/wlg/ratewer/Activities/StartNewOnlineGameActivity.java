package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.wlg.ratewer.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabine on 16.12.2015.
 */
public class StartNewOnlineGameActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_new_game);
        FillCardSetSpinner();


        final Button bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), StartActivity.class);
                startActivity(firstIntent);
            }
        });

        /*Spinner sp_cardsets = (Spinner)findViewById(R.id.sp_cardsets);
        String cardSet = sp_cardsets.getSelectedItem().toString();
        firstIntent.putExtra("chosenCardSet", cardSet);
*/
/*                CheckBox cb_TurnAuto = (CheckBox)findViewById(R.id.tv_sng_FlipCardsAuto);
                int hasBeenChecked = 0;
                if(cb_TurnAuto.isChecked())
                {
                    hasBeenChecked = 1;
                }
                firstIntent.putExtra("hasBeenChecked", hasBeenChecked);
*/

/*
        final Button bCreateOnlineGame = (Button) findViewById(R.id.bCreateOnlineGame);
        bCreateOnlineGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), StartNewOnlineGameActivity.class);
                startActivity(firstIntent);
            }
        });

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

    /**
     * players can select between different card sets
     * available cardsets read from raw folder, only put valid json file there
     * put them into spinner
     */
    private void FillCardSetSpinner()
    {
        Spinner sp_cardsets = (Spinner)findViewById(R.id.sp_cardsets);

        List<String> cardsets = new ArrayList<>();

        Field[] fields=R.raw.class.getFields();

        for(int count=0; count < fields.length; count++)
        {
            cardsets.add(fields[count].getName());
            //System.out.println("Raw Asset: " + fields[count].getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cardsets);
        sp_cardsets.setAdapter(adapter);
    }
}
