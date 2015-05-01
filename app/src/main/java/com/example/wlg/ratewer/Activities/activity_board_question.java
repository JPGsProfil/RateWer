package com.example.wlg.ratewer.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.example.wlg.ratewer.Model.Board;
import com.example.wlg.ratewer.R;

public class activity_board_question extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_board_question);
        addButtonsDynamic();
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





    // maybe could be outsourced to Board.java
    public void addButtonsDynamic()
    {
        final int COLUMN = Board.COLUMN_PER_ROW;
        final int AMOUNT_OF_PERSON = Board.AMOUNT_OF_PERSON;
        int rows = AMOUNT_OF_PERSON / COLUMN;

        // Set the Grid Layout where the cards will be placed in
        GridLayout gridCards = (GridLayout) findViewById(R.id.GridForCards);
        gridCards.setColumnCount(COLUMN);
        gridCards.setRowCount(rows);

        // create an instance of the board, needed for random, later outsourced
        Board board = new Board(); // later in controller and there a function displayBoard(Board _board)

        // loop for all cards, new row after 6 cards
        for (int CurrentCard = 0; CurrentCard < board.GetAmountOfCards(); CurrentCard++)
        {
            // create a new button
            ImageButton ib = new ImageButton(this);

            int imageID;
            imageID = getResources().getIdentifier("drawable/lego50x50_" + board.GetCardAtIndex(CurrentCard) , "drawable", getPackageName());
            //if(imageID == 0)  // not working because it's seems not be a normal int
            // {
            //    imageID = 1;    // should be changed to getIdentifier(noimage ...)
            //}
            System.out.println("Imageid " + CurrentCard + " ist: " + imageID);
            ib.setClickable(true);
            //ib.setId(CurrentCard); // not used anymore because of conflicts
            // @all: better solution, but needs api17
            //ib.generateViewId(); // needs api 17 would be better in my opinion
            ib.setId(ImageButton.generateViewId());
            ib.setImageResource(imageID);

            ib.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    System.out.println("id clicked: " + view.getId());
                }
            });
            // place the card at the next free position of the grid
            gridCards.addView(ib);
        }

    }



}
