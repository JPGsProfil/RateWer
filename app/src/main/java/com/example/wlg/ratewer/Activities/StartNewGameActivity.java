package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wlg.ratewer.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class StartNewGameActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_game);
        FillCardSetSpinner();
        FillDifficicultySpinner();

        final Button bStartGame = (Button) findViewById(R.id.b_sng_start);
        bStartGame.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                final Intent firstIntent = new Intent(v.getContext(), GameBoardActivity.class);

                Spinner sp_cardsets = (Spinner)findViewById(R.id.sp_cardsets);
                String cardSet = sp_cardsets.getSelectedItem().toString();
                firstIntent.putExtra("chosenCardSet", cardSet);

                Spinner sp_difficulty = (Spinner)findViewById(R.id.sp_sng_difficulty);
                String diff = sp_difficulty.getSelectedItem().toString();
                firstIntent.putExtra("difficulty", diff);

                CheckBox cb_TurnAuto = (CheckBox)findViewById(R.id.tv_sng_FlipCardsAuto);
                int hasBeenChecked = 0;
                if(cb_TurnAuto.isChecked())
                {
                    hasBeenChecked = 1;
                }
                firstIntent.putExtra("hasBeenChecked", hasBeenChecked);

                startActivity(firstIntent);
            }
        });
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
                R.layout.spinner_item, cardsets);
        sp_cardsets.setAdapter(adapter);
    }


    /**
     * available difficulties
     * placed in spinner
     * should be replaced be @string/ ...
     */
    private void FillDifficicultySpinner()
    {
        Spinner sp_diff = (Spinner)findViewById(R.id.sp_sng_difficulty);

        List<String> difflist = new ArrayList<>();
        difflist.add("Mensch");
        difflist.add("leichter Gegner");
        difflist.add("normaler Gegner");
        difflist.add("schwerer Gegner");
        //difflist.add("Hellseher");    // not needed anymore, "schwerer Gegner " nearly same result


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, difflist);
        sp_diff.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_new_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
