package com.example.wlg.ratewer.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wlg.ratewer.IO.FileToString;
import com.example.wlg.ratewer.IO.JSONCards;
import com.example.wlg.ratewer.Model.Board;
import com.example.wlg.ratewer.R;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class activity_board_question extends ActionBarActivity
{

    static List<JSONCards> cardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_board_question);
        System.out.println("Nach setContentView");
        GetCardobjectsAsArray(cardList);
        System.out.println("Nach GetCardobjectsAsArray()");
        if(cardList.size()>0)
        {
            System.out.println("cardList Groesse ist: "+cardList.size());
                    addButtonsDynamic(cardList);
        }
        else
        {
            // later replace with gui element, Karten sind addon
            System.out.println("Karten konnten nicht eingelesen werden");
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





    // maybe could be outsourced to Board.java
    public boolean addButtonsDynamic(final List<JSONCards> _cardList)
    {
        // nothing to add -> nothing else to do
        if(_cardList.size() < 1)
        {
            return false;
        }
        final int COLUMN = Board.COLUMN_PER_ROW;
        final int AMOUNT_OF_PERSON = _cardList.size();

        int rows = AMOUNT_OF_PERSON / COLUMN;
        if(rows < 1)
        {
            rows = 1;
        }

        // Set the Grid Layout where the cards will be placed in
        GridLayout gridCards = (GridLayout) findViewById(R.id.GridForCards);
        gridCards.setColumnCount(COLUMN);
        gridCards.setRowCount(rows);

        // create an instance of the board, needed for random, later outsourced
        //Board board = new Board(_cardList.size()); // later in controller and there a function displayBoard(Board _board)

        // loop for all cards, new row after 6 cards
        for (int currentCardID = 0; currentCardID < _cardList.size(); currentCardID++)
        {
            // create a new button
            ImageButton ib = new ImageButton(this);

            // android internal id to get access to image file in android
            int imageID;
            imageID = getResources().getIdentifier("drawable/" + _cardList.get(currentCardID).getImageName() , "drawable", getPackageName());
            //if(imageID == 0)  // not working because it's seems not be a normal int
            // {
            //    imageID = 1;    // should be changed to getIdentifier(noimage ...)
            //}
            System.out.println("Imageid " + currentCardID + " ist: " + imageID);
            ib.setClickable(true);
            //ib.setId(CurrentCardID); // not used anymore because of conflicts
            // @all: better solution, but needs api17
            //ib.generateViewId(); // needs api 17 would be better in my opinion
            ib.setId(ImageButton.generateViewId());
            ib.setImageResource(imageID);

            // set image id in class to find it later
            _cardList.get(currentCardID).setImageId(imageID);

            ib.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //System.out.println("Begin onclick");
                    JSONCards currentCard = null;
                    System.out.println("id clicked: " + view.getId());
                    System.out.println("KartenId2: " + cardList.get(view.getId()-1).getImageId() + " Name = " + cardList.get(view.getId()-1).getName());
                    Toast.makeText(getApplicationContext(), "Du hast "+cardList.get(view.getId()-1).getName()+" angeklickt!!!", Toast.LENGTH_SHORT).show();

                    /*
                    int currentIndex = 0;
                    while (currentIndex < cardList.size() && cardList.get(currentIndex).getImageId() != view.getId())
                    {
                        currentCard = cardList.get(currentIndex);
                        currentIndex++;
                    }
                    */
                    if (currentCard != null)
                    {
                       //System.out.println("KartenId: " + currentCard.getImageId() + " Name = " + currentCard.getName());
                    }


                }
            });
            // place the card at the next free position of the grid
            gridCards.addView(ib);
        }
        return true;
    }

    private void GetCardobjectsAsArray(List<JSONCards> _cardList)
    {

        Gson gson = new Gson();
        String jsonString = ReturnJSONAsString();
        JSONCards[] response = gson.fromJson(jsonString, JSONCards[].class);
        //System.out.println("Erster Name: " + response[0].getName());
        for (int index = 0; index < response.length; ++index)
        {
            _cardList.add(response[index]);
        }
        Collections.shuffle(_cardList);
        System.out.println("Erster Name: " + _cardList.get(0).getName());
        //return cardList;
    }


    // because later maybe different sets (chinese, latino, ... ) -> maybe different files
    private String ReturnJSONAsString()
    {
        // outsourcing not recommended because memory leaks ... ( http://stackoverflow.com/questions/7666589/using-getresources-in-non-activity-class 08.05.15)
        InputStream is = getResources().openRawResource(R.raw.cards);
        return FileToString.ReadTextFile(is);
    }





}
