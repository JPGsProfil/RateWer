package com.example.wlg.ratewer.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.example.wlg.ratewer.R;

public class activity_board_question extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_board_question);
        addButtonsDynamic();
    }

    private void addButtonsDynamic()
    {
        GridLayout gridCards = (GridLayout) findViewById(R.id.GridForCards);
        gridCards.setColumnCount(3);
        gridCards.setRowCount(3);

        // Schleife für Reihe
        for(int CurrentCard=0; CurrentCard<3; CurrentCard++)
        {
            // Schleife für Spalte fehlt noch
            //GridLayout.Spec row = GridLayout.spec(0, 1);
            //GridLayout.Spec colspan = GridLayout.spec(CurrentCard, 1);
            //GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(row, colspan);

            // create a new button
            ImageButton ib = new ImageButton(this);

            int imageid;
            imageid = getResources().getIdentifier("drawable/lego50x50_"+CurrentCard,"drawable", getPackageName());
            //if(imageid == 0)
           // {
            //    imageid = 1;    // should be changed to getIdentifier(noimage ...)
            //}
            System.out.println("Imageid "+CurrentCard+" ist: "+imageid);
            ib.setClickable(true);
            ib.setId(CurrentCard); // not used anymore because of conflicts
            // @all: better solution, but needs api17
            //ib.generateViewId(); // needs api 17 would be better in my opinion
            ib.setImageResource(imageid);

            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("id clicked: " + view.getId());
                }
            });

            gridCards.addView(ib);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_board_question, menu);
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
