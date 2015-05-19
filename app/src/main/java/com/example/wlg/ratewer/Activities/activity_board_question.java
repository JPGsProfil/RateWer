package com.example.wlg.ratewer.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.Animation;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wlg.ratewer.Controller.PlayerController;
import com.example.wlg.ratewer.IO.FileToString;
import com.example.wlg.ratewer.Model.AttributList;
import com.example.wlg.ratewer.Model.Board;
import com.example.wlg.ratewer.Model.Card;
import com.example.wlg.ratewer.Model.CardList;
import com.example.wlg.ratewer.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class activity_board_question extends ActionBarActivity
{

    //private static List<JSONCards> cardList = new ArrayList<>();  // for GSON implementation

    private static boolean HavePlayersSelectedWhoTheyAre = false;   // to handle onclick -> first select player, later onlick to view details
    private static AttributList m_Attribs;
    // there is only one cardList, this is list two because a long time ago a gson list existed next to this
    private static CardList cardList;  // list where the cards will be saved (name and attributtes)
    private static PlayerController m_PlayerController = new PlayerController();    // initialize two players, accessable via list or Get


    // menu:
    //private Animation animUp;
    //private Animation animDown;
    //private RelativeLayout rl;
    //private boolean isMenuVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_board_question);


        cardList = new CardList(ReturnCardJSONAsString());
        PlaceCardsOnField();
        m_Attribs = new AttributList(ReturnCardAttributesAsString());
        System.out.println("Cardlist2: " + cardList.m_List.size());
        System.out.println("Testwert: "+ cardList.m_List.get(2).attriList.get(2).attr);

        //MenuButton();


    }

    // called at beginning of increate
    private void PlaceCardsOnField()
    {
        // set cards
        if(cardList.GetSize()>0)
        {
            //System.out.println("cardList Groesse ist: "+cardList.size());
            addButtonsDynamic();
        }
        else
        {
            // later replace with gui element, Karten sind addon
            String error = "Karten konnten nicht eingelesen werden";
            System.out.println(error);
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
        // end of: set cards
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_board_question, menu);

        // dynamically add menue item
        // first has to be menu, needed because java won't let you do this with if else, even if "if" is always the first
        int currGroupId = -1;
        for (int index = 0; index < m_Attribs.attriList.size();)
        {
            // at the beginning, set new group id, not necessary if stay the same (if below)
            currGroupId= m_Attribs.attriList.get(index).groupId;
            // if more than two -> submenu required, if one or two not (like bool)
            if(index + 2 < m_Attribs.attriList.size() && m_Attribs.attriList.get(index).groupId == m_Attribs.attriList.get(index + 2).groupId)
            {
                SubMenu sm = menu.addSubMenu(currGroupId, -1,0, m_Attribs.attriList.get(index).question);
                String que = m_Attribs.attriList.get(index).question;

                while( index < m_Attribs.attriList.size() && currGroupId == m_Attribs.attriList.get(index).groupId) // it's a new menu item (kategory)
                {
                    //sm.add(m_Attribs.attriList.get(index).attribute);
                    sm.add(currGroupId, index, 0, m_Attribs.attriList.get(index).attribute);
                    index++;
                }
            }
            else
            {
                menu.add(currGroupId, index, 0, m_Attribs.attriList.get(index).question);
                if(m_Attribs.attriList.get(index).groupId == m_Attribs.attriList.get(index + 1).groupId)
                {
                    index +=2;  // we don't want to print bool twice (has hair hair yes?, has hair no? -> only has hair?
                }
                else
                {
                    index++;    // not really necesarry, because always min. yes or no in question, otherwhise not a question
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();
        String personsWithSameValue = "Folgende Personen haben die angeklickte Eigenschaft:\n";
        if(itemId >= 0)
        {
            for (int index1=0; index1 < cardList.m_List.size()-1; index1++)
            {
                //System.out.println("cardList.m_List.size() "+cardList.m_List.size());
                 //System.out.println("In for1:"+index1);
                for(int index2=0; index2 < cardList.m_List.get(index1).attriList.size()-1; index2++)
                {
                    if(cardList.m_List.get(index1).attriList.get(index2).attr.equals( m_Attribs.attriList.get(itemId).kategory))
                    {
                        System.out.println("Bin in if");
                        if(cardList.m_List.get(index1).attriList.get(index2).value.equals( m_Attribs.attriList.get(itemId).attribute))
                        {
                            System.out.println("Bin in if2");
                            personsWithSameValue +=cardList.m_List.get(index1).name+"\n";
                            System.out.println("personsWithSameValue "+personsWithSameValue);
                            // only funny:
                            if(m_Attribs.attriList.get(itemId).id == m_PlayerController.GetNextPlayer().GetChosenCardId())
                            {
                                Toast.makeText(getApplicationContext(), "Du hast zufaelligerweise die Person angeklickt, die der Gegner ausgewaehlt hat!\n Aber psssst!!!!", Toast.LENGTH_LONG).show();
                            }
                            break;
                        }
                    }
                }
            }
            System.out.println("Personen: "+personsWithSameValue);
            Toast.makeText(getApplicationContext(), personsWithSameValue, Toast.LENGTH_LONG).show();
        }



        //noinspection SimplifiableIfStatement
        if (itemId == R.id.action_settings)
        {
            Toast.makeText(getApplicationContext(), "Hier könnte später evtl. einmal die Sprache geändert werden", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    // maybe could be outsourced to Board.java
    public boolean addButtonsDynamic()
    {
        // nothing to add -> nothing else to do
        if(cardList.GetSize() < 1)
        {
            return false;
        }
        final int COLUMN = Board.COLUMN_PER_ROW;
        final int AMOUNT_OF_PERSON = cardList.GetSize();

        int rows = AMOUNT_OF_PERSON / COLUMN;
        if(rows < 1)
        {
            rows = 1;   // otherwhise exception
        }
        // Set the Grid Layout where the cards will be placed in
        GridLayout gridCards = (GridLayout) findViewById(R.id.GridForCards);
        gridCards.setColumnCount(COLUMN);
        gridCards.setRowCount(rows);

        // create an instance of the board, needed for random, later outsourced
        //Board board = new Board(_cardList.size()); // later in controller and there a function displayBoard(Board _board)

        // loop for all cards, new row after 6 cards
        for (int currentCardID = 0; currentCardID < cardList.GetSize(); currentCardID++)
        {
            // create a new button
            ImageButton ib = new ImageButton(this);

            // android internal id to get access to image file in android
            int imageID;
            imageID = getResources().getIdentifier("drawable/" + cardList.m_List.get(currentCardID).image , "drawable", getPackageName());

            System.out.println("Imageid " + currentCardID + " ist: " + imageID);
            ib.setClickable(true);

            //ib.generateViewId(); // needs api 17 would be better in my opinion
            int viewId = ImageButton.generateViewId();
            ib.setId(viewId);
            cardList.m_List.get(currentCardID).viewID = viewId;

            ib.setImageResource(imageID);
            // set image id in class to find it later
            cardList.m_List.get(currentCardID).imageID = imageID;

            ib.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // map clicked id with card from cardlist
                    Card currentCard = null;
                    System.out.println("id clicked: " + view.getId());

                    // map clicked id with card from cardlist -> iterate cardlist

                    currentCard = cardList.m_List.get(0);
                    int currentIndex = 0;
                    while (currentIndex < cardList.GetSize() && cardList.m_List.get(currentIndex).viewID != view.getId())
                    {
                        currentCard = cardList.m_List.get(currentIndex);
                        currentIndex++;
                    }

                    if (currentCard != null)
                    {
                        // at the beginning choose which character you want to be, could be outsourced, but nearly same code
                        if (!HavePlayersSelectedWhoTheyAre)
                        {
                            SelectWhoYouAre(cardList.m_List.get(currentIndex));    // if say
                        }
                        else    // player selected who he want to be -> now onclick to view details
                        {
                            System.out.println("KartenViewId: " + currentCard.viewID + " Name = " + currentCard.name + " ViewID: " + view.getId());
                            DisplayAttributes(cardList.m_List.get(currentIndex));
                        }
                    }
                }
            });
            // place the card at the next free position of the grid
            gridCards.addView(ib);
        }
        return true;
    }


    // because later maybe different sets (chinese, latino, ... ) -> maybe different files
    private String ReturnCardJSONAsString()
    {
        // outsourcing not recommended because memory leaks ... ( http://stackoverflow.com/questions/7666589/using-getresources-in-non-activity-class 08.05.15)
        InputStream is = getResources().openRawResource(R.raw.cards);
        return FileToString.ReadTextFile(is);
    }

    private String ReturnCardAttributesAsString()
    {
        // outsourcing not recommended because memory leaks ... ( http://stackoverflow.com/questions/7666589/using-getresources-in-non-activity-class 08.05.15)
        InputStream is = getResources().openRawResource(R.raw.cardattributes);
        return FileToString.ReadTextFile(is);
    }


    private void DisplayAttributes(Card currentCard)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity_board_question.this);

        // set title
        alertDialogBuilder.setTitle(currentCard.name);

        //System.out.println("Bin in Display");
        // set dialog message
        String attributes = "Eigenschaften\n";
        for(int index = 0; index <currentCard.attriList.size(); index ++)
        {
            attributes += currentCard.attriList.get(index).attr + ":  "+ currentCard.attriList.get(index).value + "\n";
        }

        alertDialogBuilder
                .setMessage(attributes)
                // not need to click ok to cancel alert, simply click outside the box
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // if this button is clicked, close
                        // current activity
                        //activity_board_question.this.finish();
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

    private void SelectWhoYouAre(final Card _currentCard)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity_board_question.this);

        // set title
        alertDialogBuilder.setTitle("Wer möchtest du sein?");

        //System.out.println("Bin in Display");
        // set dialog message
        alertDialogBuilder
                .setMessage("Möchtest du "+_currentCard.name+" sein?")
                        // choose yes or no
                .setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        HavePlayersSelectedWhoTheyAre = true;
                        m_PlayerController.GetCurrentPlayer().SetChosenCardId(_currentCard.id);
                        System.out.println("Du hast " + _currentCard.name + " ausgewaehlt");
                        dialog.cancel();
                        TextView tv_title = (TextView) findViewById(R.id.tv_Title_Ingame);
                        tv_title.setText("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + ": Mache deinen Zug!");
                    }
                })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                })
        ;

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }


}




/*
    // later outdated, is in extra json
    private void SetUniqueEyeAndHairColors()
    {
        HashSet <String> eyeColors = new HashSet<String>();
        for(int index=0; index < cardList.size(); index++)
        {
            eyeColors.add(cardList.get(index).GetEye());
        }
        eyeColorsUnique.addAll(eyeColors);

        HashSet <String> hairColors = new HashSet<String>();
        for(int index=0; index < cardList.size(); index++)
        {
            hairColors.add(cardList.get(index).GetHair());
        }
        hairColorsUnique.addAll(hairColors);


    }
*/

