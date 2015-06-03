package com.example.wlg.ratewer.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.wlg.ratewer.Controller.AIController;
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
    private static AIController m_AIController = new AIController();

    private static boolean isTurnOver = false;


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


        String usedCardset = "simpsons"; // "simpsons" OR "defaultset" possible
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String newCardSet = extras.getString("chosenCardSet");
            if (newCardSet != null)
            {
                usedCardset = newCardSet;
            }
        }

        // lIst with all cards and their attributes
        cardList = new CardList(ReturnCardJSONAsString(usedCardset));
        PlaceCardsOnField();
        // List with all attributes, unique, generated from cardlist
        m_Attribs = new AttributList(cardList);
        System.out.println("Cardlist2: " + cardList.m_List.size());
        System.out.println("Testwert: "+ cardList.m_List.get(2).attriList.get(2).attr);

        //MenuButton();
    }

    // called in oncreate
    private void PlaceCardsOnField()
    {
        // set cards
        if(cardList.GetSize()>0)
        {
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

    // ends players turn, do ai stuff, starts with player turn again
    private void EndTurn()
    {
        // change backgorund
        m_PlayerController.ChangeCurrentPlayer();
        TextView tv_title = (TextView) findViewById(R.id.tv_Title_Ingame);
        tv_title.setText("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + ": Mache deinen Zug!");
        // end of: change background

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity_board_question.this);

        // set title
        alertDialogBuilder.setTitle("Zug beendet");
        alertDialogBuilder
                .setMessage("Spieler" + m_PlayerController.GetCurrentPlayer().GetPlayerID() + " ist dran")
                        // not need to click ok to cancel alert, simply click outside the box
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if (m_PlayerController.GetCurrentPlayer().GetPlayerID() % 2 == 0)   // only call ai if it's ai's turn
                        {
                            AITurn();
                        }
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }


    private void AITurn()
    {
        String AIout = "Ich bin die Ki und bin jetzt dran!!!\n";
        int checkedId = m_AIController.CalculateCard();
        AIout+= "Ist es "+cardList.m_List.get(checkedId).name +" ?\n";


        if(m_PlayerController.GetNextPlayer().GetChosenCardId() == checkedId)
        {
            AIout+= "ja, ist es!!! Ich habe gewonnen!!!";
            final Intent lastIntent = new Intent(this, EndGameActivity.class);
            lastIntent.putExtra("msg","verloren");
            startActivity(lastIntent);
        }
        else
        {
            AIout+= "nein, leider nicht!!! Du bist!";
        }
        Toast.makeText(getApplicationContext(), AIout, Toast.LENGTH_SHORT).show();
        EndTurn();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_board_question, menu);

        SubMenu sm1 = menu.addSubMenu(100, -1,100, "Ist es?");
        for(int index = 0; index < cardList.m_List.size(); index ++)
        {
            sm1.add(100, 100 + index, 0, cardList.m_List.get(index).name);
        }
        menu.add(10000, 10000, 10000, "Zug beenden");   // end turn (option menu entry)

        // dynamically add menue item
        // first has to be menu, needed because java won't let you do this with if else, even if "if" is always the first
        int currGroupId = -1;
        // iterate all attributs
        for (int index = 0; index < m_Attribs.attriList.size();)
        {
            // at the beginning, set new group id, not necessary if stay the same (if below)
            currGroupId= m_Attribs.attriList.get(index).groupId;
            // if more than two -> submenu required, if one or two not (like bool)
            if(index + 2 < m_Attribs.attriList.size() && m_Attribs.attriList.get(index).groupId == m_Attribs.attriList.get(index + 2).groupId)
            {
                SubMenu sm = menu.addSubMenu(currGroupId, -1,0, m_Attribs.attriList.get(index).attr);
                //System.out.println("add: currGroupId "+currGroupId+ " index: -1 " + " eintrag:"+m_Attribs.attriList.get(index).attr);
                String que = m_Attribs.attriList.get(index).attr;

                while( index < m_Attribs.attriList.size() && currGroupId == m_Attribs.attriList.get(index).groupId) // it's a new menu item (kategory)
                {
                    sm.add(currGroupId, index, 0, m_Attribs.attriList.get(index).value);
                    //System.out.println("add: currGroupId "+currGroupId+ " index: "+index+ " eintrag:"+m_Attribs.attriList.get(index).value);
                    index++;
                }
            }
            else    // only bool attributes (like wearGlasses ...) -> no submenu required
            {
                menu.add(currGroupId, index, 0, m_Attribs.attriList.get(index).attr);
                System.out.println("add: currGroupId "+currGroupId+ "  eintrag:"+m_Attribs.attriList.get(index).attr);
                if(m_Attribs.attriList.get(index).groupId == m_Attribs.attriList.get(index + 1).groupId)
                {
                    index +=2;  // we don't want to print bool twice (has hair hair yes?, has hair no? -> only has hair?
                }
                else
                {
                    index++;    // not really necessary, because always min. yes or no in question, otherwhise not a question
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

        if(!HavePlayersSelectedWhoTheyAre)
        {
            Toast.makeText(getApplicationContext(), "Du musst erst einen Spieler auswählen!!", Toast.LENGTH_SHORT).show();
        }
        else // begin of -> first choose your character!!
        {


            if (itemId == 10000) // end turn
            {
                EndTurn();
                isTurnOver = false;
            }


            // print all Persons with same attribut (as klicked in view), useful for debugging
            else if (itemId >= 0 && itemId < 100)    // over 100 for additional entries (end turn, solve (choose person )...
            {
                if (isTurnOver == true)
                {
                    Toast.makeText(getApplicationContext(), "Dein Zug ist vorbei\n Du kannst kein weiteres Attribut erfragen", Toast.LENGTH_SHORT).show();
                } else
                {


                    // print of target person has this attribut:
                    boolean hasId = false;
                    // get playerCard (enemy)
                    Card cardEnemy = cardList.m_List.get(m_PlayerController.GetNextPlayer().GetChosenCardId());
                    System.out.println("Gegner ist: " + cardEnemy.name);
                    for (int index = 0; index < cardEnemy.attriList.size(); index++)
                    {
                        // look for attribut
                        if (m_Attribs.attriList.get(itemId).attr.equals(cardEnemy.attriList.get(index).attr))
                        {
                            // attribut (kategory) found, now compare value
                            if (m_Attribs.attriList.get(itemId).value.equals(cardEnemy.attriList.get(index).value))
                            {
                                hasId = true;
                                break;
                            }
                        }
                    }
                    if (hasId)
                    {
                        Toast.makeText(getApplicationContext(), "hat Attribut", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        Toast.makeText(getApplicationContext(), "hat Attribut nicht", Toast.LENGTH_SHORT).show();
                    }
                    // End of: print of target person has this attribut


                    String personsWithSameValue = "Folgende Personen kommen in Frage:\n";

                    // könnte ausgelagert werden in GetCardsWithThisAttribut(attributid)
                    for (int index1 = 0; index1 < cardList.m_List.size(); index1++)
                    {
                        //System.out.println("cardList.m_List.size() "+cardList.m_List.size());
                        //System.out.println("In for1:"+index1);
                        // look through all attributes to find the name of the clicked attributte  (hair, eyecolor ...), necessary because dynamic,
                        for (int index2 = 0; index2 < cardList.m_List.get(index1).attriList.size(); index2++)
                        {

                            if (cardList.m_List.get(index1).attriList.get(index2).attr.equals(m_Attribs.attriList.get(itemId).attr))
                            {
                                //System.out.println("Bin in if");
                                // hier muss unterschieden werden, ob die Person des Gegners das gewünschte Attribut hat oder nicht
                                // wenn ja:
                                if (hasId)
                                {
                                    // add persons to list who have this attribute (klicked)
                                    if (cardList.m_List.get(index1).attriList.get(index2).value.equals(m_Attribs.attriList.get(itemId).value))
                                    {
                                        //System.out.println("Bin in if2");
                                        personsWithSameValue += cardList.m_List.get(index1).name + "\n";
                                        //System.out.println("personsWithSameValue "+personsWithSameValue);
                                        break;
                                    } else
                                    {
                                        ImageButton btn = (ImageButton) findViewById(cardList.m_List.get(index1).viewID);
                                        btn.setAlpha(0.4f);
                                        btn.setClickable(false);
                                    }
                                } else
                                {
                                    // only add person to list (print) if they don't have this attribute
                                    if (cardList.m_List.get(index1).attriList.get(index2).value.equals(m_Attribs.attriList.get(itemId).value))
                                    {
                                        ImageButton btn = (ImageButton) findViewById(cardList.m_List.get(index1).viewID);
                                        btn.setAlpha(0.4f);
                                        btn.setClickable(false);
                                    } else    // only add person if not the same value (like other haircolor)
                                    {
                                        //System.out.println("Bin in if3");
                                        personsWithSameValue += cardList.m_List.get(index1).name + "\n";
                                        //System.out.println("personsWithSameValue "+personsWithSameValue);
                                        break;
                                    }

                                }

                            }
                        }
                    }
                    System.out.println("Personen: " + personsWithSameValue); //
                    Toast.makeText(getApplicationContext(), personsWithSameValue, Toast.LENGTH_LONG).show();

                    // turn is over !!!
                    isTurnOver = true;
                }
            } else if (itemId >= 100 && itemId < 200)
            {
                int curPlayerId = itemId - 100;
                System.out.println("itemid-100:" + curPlayerId + "  ChosenCardOfPlayer: " + m_PlayerController.GetCurrentPlayer().GetChosenCardId());
                if (m_PlayerController.GetCurrentPlayer().GetChosenCardId() == curPlayerId)
                {
                    final Intent lastIntent = new Intent(this, EndGameActivity.class);
                    lastIntent.putExtra("msg", "gewonnen");
                    startActivity(lastIntent);
                }
            }
        } // end of -> first choose your character!!

        else    // only for debugging, can be removed
        {

            System.out.println("komische item id :"+itemId);
        }



        // can be removed
        if (itemId == R.id.action_settings) // bug when clicking on options
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
                    System.out.println("id clicked: " + view.getId());  // needed for debugging

                    // map clicked id with card from cardlist -> iterate cardlist
                    Card currentCard = cardList.m_List.get(0);
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

                            // only funny:
                            /*
                            if(currentCard.id == m_PlayerController.GetNextPlayer().GetChosenCardId())
                            {
                                Toast.makeText(getApplicationContext(), "Du hast zufaelligerweise die Person angeklickt, die der Gegner ausgewaehlt hat!\n Aber psssst!!!!", Toast.LENGTH_SHORT).show();
                            }
                            */
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
    private String ReturnCardJSONAsString(String _cardsetName)
    {
        // outsourcing not recommended because memory leaks ... ( http://stackoverflow.com/questions/7666589/using-getresources-in-non-activity-class 08.05.15)
        int ressourceId =getResources().getIdentifier(_cardsetName, "raw",getPackageName());
        InputStream is = getResources().openRawResource(ressourceId);
        return FileToString.ReadTextFile(is);
    }

    /*  // now only ReturnCardJSONAsString
    private String ReturnCardAttributesAsString()
    {
        // outsourcing not recommended because memory leaks ... ( http://stackoverflow.com/questions/7666589/using-getresources-in-non-activity-class 08.05.15)
        InputStream is = getResources().openRawResource(R.raw.cardattributes);
        return FileToString.ReadTextFile(is);
    }
*/

    // ingame klick on card -> displays attributes of this card (from json, )
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

    // at the beginning of the game you have to select who you are
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


    private void DisplayCurrentPlayerInBox()
    {

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

