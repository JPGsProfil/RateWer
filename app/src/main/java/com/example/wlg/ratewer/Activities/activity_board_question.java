package com.example.wlg.ratewer.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.wlg.ratewer.Controller.AIController;
import com.example.wlg.ratewer.Controller.PlayerController;
import com.example.wlg.ratewer.IO.FileToString;
import com.example.wlg.ratewer.Model.AttribValue;
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
import java.util.Random;

public class activity_board_question extends ActionBarActivity
{

    private static AttributList m_Attribs;  // attribs of the current player, only reference
    // there is only one cardList, this is list two because a long time ago a gson list existed next to this
    private static CardList cardList;  // list where the cards will be saved (name and attributtes)
    private static PlayerController m_PlayerController ;    // initialize two players, accessable via list or Get
    private static boolean s_isTurnOver = false;  // needed for human (can't ask two question in one turn)
    //private static List<JSONCards> cardList = new ArrayList<>();  // for GSON implementation
    //private static boolean HavePlayersSelectedWhoTheyAre = false;   // to handle onclick -> first select player, later onlick to view details
    private static boolean s_TurnCardsAuto = false;



    /**
     * fill member variables
     * set players
     * cardlist (used to display all cards, not only remaining)
     * set ai, read from last Intent ...
     * @param savedInstanceState get data from last Intent ...
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_board_question);


        String usedCardset = "simpsons"; // "simpsons" OR "defaultset" possible
        String difficulty = "einfach";
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            // get chosen cardset from prev. activity:
            String newCardSet = extras.getString("chosenCardSet");
            if (newCardSet != null)
            {
                usedCardset = newCardSet;
            }

            int hasBeenChecked = extras.getInt("hasBeenChecked");
            if (hasBeenChecked == 1)
            {
                System.out.println("hasBeenChecked");
                s_TurnCardsAuto = true;
            }



            // get difficulty from prev. activity:
            String diff = extras.getString("difficulty");
            if (diff != null)
            {
                if(diff.equals("Hellseher"))
                {
                    String msg = "Ein Hellseher braucht beim ersten ANlauf etwas Laenger. Geduld, wenn Spieler 2 dran ist!";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }

                difficulty = diff;
            }
        }
        //m_AIController = new AIController(difficulty);
        //  m_PlayerController.GetSecondPlayer().SetAiDifficulty(difficulty);   // not used at the moment
        // lIst with all cards and their attributes
        cardList = new CardList(ReturnCardJSONAsString(usedCardset));
        System.out.println("Ein leichter Gegner haette ausgewaehlt: " + cardList.Get(10).name);
        PlaceCardsOnField();
        m_PlayerController = new PlayerController(cardList);
        if(!difficulty.equals("Mensch"))
        {
            m_PlayerController.GetSecondPlayer().SetToAI();
        }
        if(m_PlayerController.GetSecondPlayer().IsAI())
        {
            m_PlayerController.GetSecondPlayer().SetAiDifficulty(difficulty);
        }
        m_Attribs = m_PlayerController.GetCurrentPlayer().m_AttribsRemaining;

        InitializeFinishButton();   // place finish button, make it invisible at beginning

    }   // end of OnCreate



    private void InitializeFinishButton()
    {
        // make finish button:
        Button bt = (Button)findViewById(R.id.bFinished);
        bt.setOnClickListener(
                new ImageButton.OnClickListener() {
                    public void onClick(View v)
                    {
                        BeginNewTurn();
                    }
                }
        );
        bt.setClickable(false); // only visible if turn is over
        bt.setAlpha(0);
    }

    private void SetFinishBTVisibility(boolean _displayIt)
    {
        Button bt = (Button)findViewById(R.id.bFinished);
        if(!_displayIt)
        {
            bt.setClickable(false); // only visible if turn is over
            bt.setAlpha(0);
        }
        else
        {
            bt.setAlpha(1);
            bt.setClickable(true); // only visible if turn is over
        }

    }


    /**
     * call function to fill gridview
     * displays error msg if cardlist empty (json invalid ...)
     * called in oncreate
     */
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


    /**
     * ends players turn, do ai stuff, starts with player turn again
     */
    private void BeginNewTurn()
    {
        s_isTurnOver = false;

        SetFinishBTVisibility(false);    // make finish button invisible (first do turn)

        // change backgorund
        m_PlayerController.ChangeCurrentPlayer();
        UpdateFieldV2(); // draw new grid with cards of the current player
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
                        System.out.println("Spielerid:" + m_PlayerController.GetCurrentPlayer().GetPlayerID());
                        if (m_PlayerController.GetCurrentPlayer().IsAI())   // only call ai if it's ai's turn
                        {
                            AITurn();
                        }
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }




    /**
     * Ai's turn
     * call makemove
     * display result
     */
    private void AITurn()
    {
        //SetCurrentCardList();  // because now each player has it's own

        // now update option menu list:
        //m_PlayerController.GetCurrentPlayer().RecalculateRemainingAttributes();   // later

        String AIout = "";

        // KI has to select the a person
        if(!m_PlayerController.GetCurrentPlayer().hasPlayersSelectedWhoHeIs)
        {
            Random rand = new Random();
            int randCardId = rand.nextInt(m_PlayerController.GetCurrentPlayer().cardListRemaining.GetSize());
            m_PlayerController.GetCurrentPlayer().SetChosenCardId(randCardId);
            m_PlayerController.GetCurrentPlayer().hasPlayersSelectedWhoHeIs = true;
            invalidateOptionsMenu(); // because ai doens't use the options menu
            String msg = "Ich habe mich entschieden!!!";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            String name = m_PlayerController.GetCurrentPlayer().cardListRemaining.Get(randCardId).name;
            System.out.println("Ki waehlte: " + name);
            String debug = "Reihenfolge ids: \n";
            for (int i=0; i<m_PlayerController.GetCurrentPlayer().cardListRemaining.GetSize(); i++)
            {
                debug+=" "+ m_PlayerController.GetCurrentPlayer().cardListRemaining.Get(i).id;
            }
            System.out.println(debug);
        }
        // both players had selected one person -> ai can now make a move
        else
        {
            AttribValue move = m_PlayerController.MakeMove();
            if(move.attr.equals("aHumanPlayer"))
            {
                AIout = "Ich bin die Ki und Spieler "+m_PlayerController.GetCurrentPlayer().GetPlayerID() +" und bin jetzt dran!!!\n";
                // do nothing special
            }
            else if(move.attr.equals("IsIt"))
            {
                // move value is int here
                int ivalue = Integer.parseInt(move.value);
                AIout+= "Ist es "+m_PlayerController.GetCurrentPlayer().cardListRemaining.Get(ivalue).name +" ?\n";

                int idEnemy = m_PlayerController.GetNextPlayer().GetChosenCardId(); // first get Id of enemy card, can't be used now because dynamic
                int indexEnemyCard = m_PlayerController.GetCurrentPlayer().cardListRemaining.GetIndexFromCardId(idEnemy);
                if(indexEnemyCard == ivalue)
                {
                    AIout+= "ja, ist es!!! Ich habe gewonnen!!!";
                    final Intent lastIntent = new Intent(this, EndGameActivity.class);
                    String msg = "Ich habe gewonnen,\n du hast verloren!!!";
                    lastIntent.putExtra("msg",msg);
                    startActivity(lastIntent);
                }
                else
                {
                    m_PlayerController.GetCurrentPlayer().cardListRemaining.Remove(ivalue);
                    AIout+= "nein, leider nicht!!! \n Du bist!";

                }
            }
            else    // reduce cardlist by attrib and value
            {
                AIout+= "Meine Wahl: "+move.attr + ": "+move.value;
                int idEnemy = m_PlayerController.GetNextPlayer().GetChosenCardId(); // first get Id of enemy card, can't be used now because dynamic
                int indexEnemyCard = m_PlayerController.GetCurrentPlayer().cardListRemaining.GetIndexFromCardId(idEnemy);   // because dynamic list, index can be different
                Card cardEnemy = m_PlayerController.GetCurrentPlayer().cardListRemaining.Get(indexEnemyCard);
                if(cardEnemy.DoesCardContainAttrValue(move.attr,move.value))
                {
                    AIout+= "\n Juhu,deine Person hat die Eigenschaft";
                    m_PlayerController.GetCurrentPlayer().cardListRemaining.RemoveCardsWithoutAttriValue(move.attr, move.value);
                }
                else
                {
                    AIout+= "\n Schade";
                    m_PlayerController.GetCurrentPlayer().cardListRemaining.RemoveCardsWithAttriValue(move.attr, move.value);
                }
                System.out.println("verbleibende Karten: "+m_PlayerController.GetCurrentPlayer().cardListRemaining.GetSize());
            }

            Toast.makeText(getApplicationContext(), AIout, Toast.LENGTH_SHORT).show();
            //BeginNewTurn();
        }
        s_isTurnOver = true;
        UpdateFieldV2();
        TextView tv_title = (TextView) findViewById(R.id.tv_Title_Ingame);
        tv_title.setText("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + ": Hat den Zug beendet!");

        SetFinishBTVisibility(true);    // make finish bt visible

    }


    /**
     * to interact
     * ask question or "is it.."
     * diplayed values depending on game state (players turn, turn over) and remaining cards ...
     * @param menu interact by using option menu
     * @return always true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        System.out.println("aktueller Spieler "+ m_PlayerController.GetCurrentPlayer().GetPlayerID());
        CardList curCardList = m_PlayerController.GetCurrentPlayer().cardListRemaining;
        //SetCurrentCardList();   // make sure you use the the correct cardlist (of the current player)
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_board_question, menu);
        menu.clear();   // delete all entries, only add remaining, chose this way instead of visibility because if human vs human -> need to display 2 independend menus
        if(m_PlayerController.GetCurrentPlayer().hasPlayersSelectedWhoHeIs)
        {
            // menu entry to end own turn, only possible if player already selected who he /she want to be
            menu.add(10000, 10000, 10000, "Zug beenden");   // end turn (option menu entry))
        }


        if(m_PlayerController.HaveAllPlayersSelectedWhoTheyAre())
        {
            if (!s_isTurnOver)
            {
                // submenu to choose "is it ...?"
                SubMenu sm1 = menu.addSubMenu(100, -1, 100, "Ist es?");
                for (int index = 0; index < curCardList.GetSize(); index++)
                {
                    // add the names of the card (person)
                    int newItemId = 100 + curCardList.Get(index).id;
                    sm1.add(100, newItemId, 0, curCardList.Get(index).name);
                }

                // part 2 : add questions
                m_Attribs = m_PlayerController.GetCurrentPlayer().m_AttribsRemaining;
                System.out.println("playercontroller cardRem: "+m_PlayerController.GetCurrentPlayer().cardListRemaining.GetSize());
                System.out.println("playercontroller attribremaining: "+m_PlayerController.GetCurrentPlayer().m_AttribsRemaining.attriList.size());
                // dynamically add menue item
                int currGroupId = -1;
                // iterate all attributs
                for (int index = 0; index < m_Attribs.attriList.size(); )
                {
                    // at the beginning, set new group id, not necessary if stay the same (if below)
                    currGroupId = m_Attribs.attriList.get(index).groupId;

                    // if more than two -> submenu required, if one or two not (like bool)
                    if (index + 2 < m_Attribs.attriList.size() && m_Attribs.attriList.get(index).groupId == m_Attribs.attriList.get(index + 2).groupId)
                    {
                        SubMenu sm = menu.addSubMenu(currGroupId, -1, 0, m_Attribs.attriList.get(index).attr);
                        //System.out.println("add: currGroupId "+currGroupId+ " index: -1 " + " eintrag:"+m_Attribs.attriList.get(index).attr);
                        String que = m_Attribs.attriList.get(index).attr;

                        while (index < m_Attribs.attriList.size() && currGroupId == m_Attribs.attriList.get(index).groupId) // it's a new menu item (kategory)
                        {
                            sm.add(currGroupId, index, index, m_Attribs.attriList.get(index).value);
                            //itemId++;
                            //System.out.println("add: currGroupId "+currGroupId+ " index: "+index+ " eintrag:"+m_Attribs.attriList.get(index).value);
                            index++;
                        }
                    }
                    else    // only bool attributes (like wearGlasses ...) -> no submenu required
                    {

                        if (index + 1 < m_Attribs.attriList.size() && m_Attribs.attriList.get(index).groupId == m_Attribs.attriList.get(index + 1).groupId)
                        {
                            String attriValStr = m_Attribs.attriList.get(index).attr + ": "+m_Attribs.attriList.get(index).value;
                            menu.add(currGroupId, index, 0, attriValStr);
                            //itemId ++;
                            //System.out.println("add: currGroupId " + currGroupId + "  eintrag:" + m_Attribs.attriList.get(index).attr);
                            index += 2;  // we don't want to print bool twice (has hair hair yes?, has hair no? -> only has hair?
                        } else // only one value for this attribut (eg all cards have haircolor brown -> have to be at least two values (brown, black, ..)
                        {
                            index++;    // not really necessary, because always min. yes or no in question, otherwhise not a question
                        }
                    }
                }
            }   // end of: only display categories if players turn isn't over
        }   // end of: option menu only visible if both players have chosen their character
        return true;
    }


    /**
     * Check what human player has done
     * if asked question -> compare question with enemy target and own cardlist
     * switch gamestate : players turn, turn over ...
     *
     * @param item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        CardList curCardList = m_PlayerController.GetCurrentPlayer().cardListRemaining;
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();
        System.out.println("itemId ist: " + itemId);
        // game can't start if player hasn't chosen his card, maybe extra activity later
        if(!m_PlayerController.GetCurrentPlayer().hasPlayersSelectedWhoHeIs)
        {
            Toast.makeText(getApplicationContext(), "Du musst erst einen Spieler auswählen!!", Toast.LENGTH_SHORT).show();
        }
        else // begin of -> first choose your character!!
        {
            if (itemId == 10000) // end turn
            {
                BeginNewTurn();
            }

            // handle selected question
            else if (itemId >= 0 && itemId < 100)    // over 100 for additional entries (end turn, solve (choose person )...
            {
                if (s_isTurnOver)
                {
                    Toast.makeText(getApplicationContext(), "Dein Zug ist vorbei\n Du kannst kein weiteres Attribut erfragen", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    m_Attribs = m_PlayerController.GetCurrentPlayer().m_AttribsRemaining;
                    // print of target person has this attribut:
                    boolean hasId = false;
                    // get playerCard (enemy)
                    //int enemyCardPos = curCardList.GetIndexFromCardId(m_PlayerController.GetNextPlayer().GetChosenCardId());  // not used because can't make sure it still exist
                    Card cardEnemy = curCardList.Get(0);

                    for (int i = 0; i < curCardList.GetSize(); i++)
                    {
                        // because index not working anymore (because most cards have been deleted)
                        if (curCardList.Get(i).id == m_PlayerController.GetNextPlayer().GetChosenCardId())
                        {
                            cardEnemy = curCardList.Get(i);
                            break;
                        }
                    }
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

                    List<Integer> CardsToRemove = new ArrayList<>();

                    String personsWithSameValue = "Folgende Personen kommen in Frage:\n";

                    // könnte ausgelagert werden in GetCardsWithThisAttribut(attributid)
                    for (int index1 = 0; index1 < curCardList.GetSize(); index1++)
                    {
                        //System.out.println("curCardList.m_List.size() "+curCardList.m_List.size());
                        //System.out.println("In for1:"+index1);
                        // look through all attributes to find the name of the clicked attributte  (hair, eyecolor ...), necessary because dynamic,
                        for (int index2 = 0; index2 < curCardList.Get(index1).attriList.size(); index2++)
                        {
                            if (curCardList.Get(index1).attriList.get(index2).attr.equals(m_Attribs.attriList.get(itemId).attr))
                            {
                                // hier muss unterschieden werden, ob die Person des Gegners das gewünschte Attribut hat oder nicht
                                // wenn ja:
                                if (hasId)
                                {
                                    // add persons to list who have this attribute (klicked)
                                    if (curCardList.Get(index1).attriList.get(index2).value.equals(m_Attribs.attriList.get(itemId).value))
                                    {
                                        //System.out.println("Bin in if2");
                                        personsWithSameValue += curCardList.Get(index1).name + "\n";
                                        //System.out.println("personsWithSameValue "+personsWithSameValue);
                                        //break; // because we want to delete all cards exept those with this attribute / value
                                    } else
                                    {
                                        if(s_TurnCardsAuto)
                                        {
                                            ImageButton btn = (ImageButton) findViewById(curCardList.Get(index1).viewID);
                                            btn.setAlpha(0.4f);
                                            btn.setClickable(false);
                                            CardsToRemove.add(index1);  // because we want to delete entries at the end, not now
                                            System.out.println("zu loeschende Person: " + curCardList.Get(index1).name + " index = " + index1);
                                        }
                                    }
                                } else
                                {
                                    // only add person to list (print) if they don't have this attribute
                                    if (curCardList.Get(index1).attriList.get(index2).value.equals(m_Attribs.attriList.get(itemId).value))
                                    {
                                        if(s_TurnCardsAuto)
                                        {
                                            ImageButton btn = (ImageButton) findViewById(curCardList.Get(index1).viewID);
                                            btn.setAlpha(0.4f);
                                            btn.setClickable(false);
                                            CardsToRemove.add(index1);  // because we want to delete entries at the end, not now
                                            System.out.println("zu loeschende Person: " + curCardList.Get(index1).name + "index = " + index1);
                                        }
                                    } else    // only add person if not the same value (like other haircolor)
                                    {
                                        //System.out.println("Bin in if3");
                                        personsWithSameValue += curCardList.Get(index1).name + "\n";
                                        //System.out.println("personsWithSameValue "+personsWithSameValue);
                                        //break;
                                    }
                                }

                            }
                        }
                    }
                    //System.out.println("Personen: " + personsWithSameValue); //
                    if(s_TurnCardsAuto && !m_PlayerController.GetCurrentPlayer().IsAI())   // only display persons if cards turned by pc, else player want to find out by himself /herself
                    {
                        Toast.makeText(getApplicationContext(), personsWithSameValue, Toast.LENGTH_LONG).show();
                    }


                    // turn is over !!!
                    s_isTurnOver = true;
                    TextView tv_title = (TextView) findViewById(R.id.tv_Title_Ingame);
                    String txt_display = ": Beende den Zug!";
                    if(!s_TurnCardsAuto && !m_PlayerController.GetCurrentPlayer().IsAI())
                    {
                        txt_display = "  Drehe die Karten um!";
                    }
                    tv_title.setText("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + txt_display);

                    SetFinishBTVisibility(true);    // make finish button visible


                    //System.out.println("Anz Karten: " + curCardList.GetSize());
                    //System.out.println("cardsToRemove: " + CardsToRemove.size());
                    // remove unwanted cards:
                    //debug

                    ///// auslagern!!!!
                    if(s_TurnCardsAuto)
                    {
                        System.out.println();
                        for (int index = CardsToRemove.size() - 1; index >= 0; index--)
                        {
                            //String pers = "Personen: ";
                            //for (int i = 0; i < curCardList.GetSize(); i++)
                            //{
                            //    pers += curCardList.Get(i).name + "  ";
                            //}
                            //System.out.println(pers);

                            // set visibility of imagebutton
                            ImageButton btn = (ImageButton) findViewById(curCardList.Get(CardsToRemove.get(index)).viewID);
                            btn.setAlpha(0.4f);
                            btn.setClickable(false);

                            // remove from option menu
                            //System.out.println("Remove element: " + CardsToRemove.get(index) + "  "+curCardList.Get(CardsToRemove.get(index)).name);
                            curCardList.Remove(curCardList.Get(CardsToRemove.get(index)));// int list of  all cards wich should be deleted // with index not working
                        }
                    }


                    // now update option menu list:
                    m_PlayerController.GetCurrentPlayer().RecalculateRemainingAttributes();
                    // Ende auslagern!!!!

                }
            }
            // player selected "is it ..?"
            else if (itemId >= 100 && itemId < 200)
            {
                int curPlayerId = itemId - 100;
                System.out.println("itemId "+curPlayerId+ " entspricht "+cardList.Get(cardList.GetIndexFromCardId(curPlayerId)).name);
                System.out.println("itemid-100:" + curPlayerId + "  ChosenCardOfPlayer: " + m_PlayerController.GetNextPlayer().GetChosenCardId());
                System.out.println("Gegner wählte:" + m_PlayerController.GetNextPlayer().GetChosenCardId() + " = "+cardList.Get(m_PlayerController.GetNextPlayer().GetChosenCardId()).name);
                s_isTurnOver = true;
                TextView tv_title = (TextView) findViewById(R.id.tv_Title_Ingame);

                SetFinishBTVisibility(true);    // make finish button visible

                String txt_display = ": Beende den Zug!";
                if(!s_TurnCardsAuto && !m_PlayerController.GetCurrentPlayer().IsAI())
                {
                    txt_display = "  Drehe die Karten um!";
                }
                tv_title.setText("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + txt_display);
                //int cardId = curCardList.GetIndexFromCardId(curPlayerId);
                if (curPlayerId == m_PlayerController.GetNextPlayer().GetChosenCardId())
                {
                    final Intent lastIntent = new Intent(this, EndGameActivity.class);
                    String msg = "Spieler "+m_PlayerController.GetCurrentPlayer().GetPlayerID()+" hat gewonnen !!! ";
                    lastIntent.putExtra("msg", msg);
                    startActivity(lastIntent);
                } else
                {
                    String playerName = curCardList.Get(curPlayerId).name;

                    ImageButton btn = (ImageButton) findViewById(curCardList.Get(curPlayerId).viewID);
                    btn.setAlpha(0.4f);
                    btn.setClickable(false);

                    String msg = "Es ist nicht " + playerName;
                    if(s_TurnCardsAuto)
                    {
                        curCardList.Remove(curCardList.Get(curPlayerId));
                    }
                    m_PlayerController.GetCurrentPlayer().RecalculateRemainingAttributes();
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    System.out.println("msg: " + msg);
                }
            }
            else    // only for debugging, can be removed
            {
                System.out.println("komische item id :" + itemId); // called if no clicked menu has a submenu
            }

        } // end of -> first choose your character!!

        // can be removed
        if (itemId == R.id.action_settings) // bug when clicking on options
        {
            Toast.makeText(getApplicationContext(), "Hier könnte später evtl. einmal die Sprache geändert werden", Toast.LENGTH_LONG).show();
            return true;
        }

        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }


    /**
     * to scale pictures depending on resolution
     * @return with in px (also height, because square)
     */
    private int GetImageWithHeight()
    {
        // get Picture with:
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeigth = size.y;
        float imageWidthHeightfloat = screenHeigth / 9.2f;   // 5 because 4 elements each row + border, padding ...
        int imageWidthHeight = Math.round(imageWidthHeightfloat);
        return imageWidthHeight;
    }


    /**
     * fill gridView with cards, called at the beginning of the game
     * 1. set Gridview
     * 2. map cardattributes to class member (cardlist)
     * 3. map attributes like id to button
     * 4. add onclick listener (e.g. to display all card attributs for clicked card)
     * maybe could be outsourced to Board.java, called at the beginning of the game
     * @return false if no cards available, else true
     */
    public boolean addButtonsDynamic()
    {
        // nothing to add -> nothing else to do
        if(cardList.GetSize() < 1)
        {
            return false;
        }
        final int COLUMN = Board.COLUMN_PER_ROW;
        final int AMOUNT_OF_PERSON = cardList.GetSize();
        /*
        int rows = AMOUNT_OF_PERSON / COLUMN;
        if(rows < 1)
        {
            rows = 1;   // otherwhise exception
        }*/
        // Set the Grid Layout where the cards will be placed in
        GridLayout gridCards = (GridLayout) findViewById(R.id.GridForCards);
        gridCards.setColumnCount(COLUMN);
        //gridCards.setRowCount(rows);

        int imageWidthHeight = GetImageWithHeight();    // could be called in function, but would be calculated 24 times

        // margin:
        //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = 10;
        //lp.setMargins(margin, margin, margin, margin);


        // create an instance of the board, needed for random, later outsourced
        //Board board = new Board(_cardList.size()); // later in controller and there a function displayBoard(Board _board)

        // loop for all cards, new row after 6 cards
        for (int currentCardID = 0; currentCardID < cardList.GetSize(); currentCardID++)
        {
            // create a new button
            ImageButton ib = new ImageButton(this);
            ib.setPadding(0,0,5,5); // border between images


            //ib.setLayoutParams(lp);

            // android internal id to get access to image file in android
            int imageID;
            imageID = getResources().getIdentifier("drawable/" + cardList.Get(currentCardID).image , "drawable", getPackageName());

            // add scaled image
            Bitmap bm = BitmapFactory.decodeResource(getResources(), imageID );
            Bitmap resized = Bitmap.createScaledBitmap(bm, imageWidthHeight, imageWidthHeight, true);
            ib.setImageBitmap(resized);

            System.out.println("Imageid " + currentCardID + " ist: " + imageID);
            ib.setClickable(true);

            //ib.generateViewId(); // needs api 17 would be better in my opinion
            int viewId = ImageButton.generateViewId();
            ib.setId(viewId);
            cardList.Get(currentCardID).viewID = viewId;






            //ib.setImageResource(imageID);
            // set image id in class to find it later
            cardList.Get(currentCardID).imageID = imageID;

            ib.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    System.out.println("id clicked: " + view.getId());  // needed for debugging

                    // map clicked id with card from cardlist -> iterate cardlist
                    Card currentCard = cardList.Get(0);  // = get(0) not needed
                    int currentIndex = 0;

                    while (currentIndex < cardList.GetSize()-1 && cardList.Get(currentIndex).viewID != view.getId())
                    {
                        // stops at this index
                        currentIndex++; // because we already checked index 0 in while loop
                        currentCard = cardList.Get(currentIndex);

                    }

                    if (currentCard != null)
                    {
                        // at the beginning choose which character you want to be, could be outsourced, but nearly same code
                        if (!m_PlayerController.GetCurrentPlayer().hasPlayersSelectedWhoHeIs)
                        {
                            SelectWhoYouAre(cardList.Get(currentIndex));
                        }
                        else    // player selected who he want to be -> now onclick to view details
                        {
                            System.out.println("KartenViewId: " + currentCard.viewID + " Name = " + currentCard.name + " ViewID: " + view.getId());
                            DisplayAttributes(cardList.Get(currentIndex));
                        }
                    }
                }
            });
            // place the card at the next free position of the grid
            gridCards.addView(ib);
        }
        return true;
    }


    /**
     * Version 2
     * display cards on screen
     * uses gridView
     * displays all cards (incl. removed cards with less alpha)
     * Version 1 removes unwanted cards from screen instead of alpha
     * @return false if no cards to display, should never happen
     */
    public boolean UpdateFieldV2()
    {
        // nothing to add -> nothing else to do
        if(cardList.GetSize() < 1)
        {
            return false;
        }
        GridLayout gridCards = (GridLayout) findViewById(R.id.GridForCards);
        gridCards.removeAllViews();

        int imageWidthHeight = GetImageWithHeight();    // could be called in function, but would be calculated 24 times

        for (int currentCardID = 0; currentCardID < cardList.GetSize(); currentCardID++)
        {
            // create a new button
            ImageButton ib = new ImageButton(this);
            ib.setPadding(0,0,5,5); // border between images
            ib.setClickable(true);
            ib.setId(cardList.Get(currentCardID).viewID);

            // add scaled image
            Bitmap bm = BitmapFactory.decodeResource(getResources(), cardList.Get(currentCardID).imageID );
            Bitmap resized = Bitmap.createScaledBitmap(bm, imageWidthHeight, imageWidthHeight, true);
            ib.setImageBitmap(resized);
            // ib.setImageResource(cardList.Get(currentCardID).imageID);  // obsolete, no scaled bitmap

            if(!m_PlayerController.GetCurrentPlayer().cardListRemaining.DoesCardIdExist(currentCardID))
            {
                ib.setAlpha(0.4f);
                ib.setClickable(false);
            }

            ib.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    System.out.println("id clicked: " + view.getId());  // needed for debugging

                    // map clicked id with card from cardlist -> iterate cardlist
                    Card currentCard = cardList.Get(0);  // = get(0) not needed
                    int currentIndex = 0;

                    while (currentIndex < cardList.GetSize() - 1 && cardList.Get(currentIndex).viewID != view.getId())
                    {
                        currentIndex++; // because we already checked index 0 in while loop
                        currentCard = cardList.Get(currentIndex);

                    }

                    if (currentCard != null)
                    {
                        if (!m_PlayerController.GetCurrentPlayer().hasPlayersSelectedWhoHeIs)
                        {
                            SelectWhoYouAre(cardList.Get(currentIndex));    // //bug, darf nicht currentIndex sein, da index für viewId
                        }
                        else if(s_TurnCardsAuto == false && s_isTurnOver && !m_PlayerController.GetCurrentPlayer().IsAI())
                        {
                            System.out.println("debug: bin in manuell umdrehen!");
                            ImageButton btn = (ImageButton) findViewById(view.getId());
                            btn.setAlpha(0.4f);
                            btn.setClickable(false);
                            m_PlayerController.GetCurrentPlayer().cardListRemaining.RemoveCardByViewId(view.getId());
                        }
                        else
                        {
                            System.out.println("KartenViewId: " + currentCard.viewID + " Name = " + currentCard.name + " ViewID: " + view.getId());
                            DisplayAttributes(cardList.Get(currentIndex));
                        }

                    }
                }
            });
            // place the card at the next free position of the grid
            gridCards.addView(ib);
        }
        return true;
    }


    /**
     * reads cardsets from json file in raw folder
     * put them into string
     * because later maybe different sets (chinese, latino, ... ) -> maybe different files
     * @param _cardsetName file name of the cardset
     * @return String of extracted cardset json
     */
    private String ReturnCardJSONAsString(String _cardsetName)
    {
        // outsourcing not recommended because memory leaks ... ( http://stackoverflow.com/questions/7666589/using-getresources-in-non-activity-class 08.05.15)
        int ressourceId = getResources().getIdentifier(_cardsetName, "raw",getPackageName());
        InputStream is  = getResources().openRawResource(ressourceId);
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

    /**
     * ingame klick on card -> displays attributes of this card (from json, )
     * @param currentCard needed to display card information in box
     */
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
                        m_PlayerController.GetCurrentPlayer().hasPlayersSelectedWhoHeIs = true;
                        //if (m_PlayerController.HaveAllPlayersSelectedWhoTheyAre())
                        //{
                        //    HavePlayersSelectedWhoTheyAre = true;
                        //}


                        m_PlayerController.GetCurrentPlayer().SetChosenCardId(_currentCard.id);
                        System.out.println("Du hast " + _currentCard.name + " ausgewaehlt. ID = " + _currentCard.id);
                        dialog.cancel();
                        TextView tv_title = (TextView) findViewById(R.id.tv_Title_Ingame);
                        tv_title.setText("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + ": Beende deinen Zug!");
                        invalidateOptionsMenu();    // because now we have entries

                        SetFinishBTVisibility(true);    // make finish button visible
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


    /**
     * alternative field, working, but not used anymore, can be activated
     * removes all cards wich are irrelevant instead of decreasing alpha
     * @return false if field doesn't contain cards
     */
    /*
    public boolean UpdateField()
    {
        final CardList curCardList = m_PlayerController.GetCurrentPlayer().cardListRemaining;
        // nothing to add -> nothing else to do
        if(curCardList.GetSize() < 1)
        {
            return false;
        }
        GridLayout gridCards = (GridLayout) findViewById(R.id.GridForCards);
        gridCards.removeAllViews();

        for (int currentCardID = 0; currentCardID < curCardList.GetSize(); currentCardID++)
        {
            // create a new button
            ImageButton ib = new ImageButton(this);
            ib.setClickable(true);
            ib.setId(curCardList.Get(currentCardID).viewID);
            ib.setImageResource(curCardList.Get(currentCardID).imageID);

            ib.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    System.out.println("id clicked: " + view.getId());  // needed for debugging


                    // map clicked id with card from cardlist -> iterate cardlist
                    Card currentCard = curCardList.Get(0);  // = get(0) not needed
                    int currentIndex = 0;

                    while (currentIndex < curCardList.GetSize() - 1 && curCardList.Get(currentIndex).viewID != view.getId())
                    {
                        currentIndex++; // because we already checked index 0 in while loop
                        currentCard = curCardList.Get(currentIndex);
                    }


                    if (currentCard != null)
                    {
                        if (!m_PlayerController.GetCurrentPlayer().hasPlayersSelectedWhoHeIs)
                        {
                            SelectWhoYouAre(cardList.Get(currentIndex));    // //bug, darf nicht currentIndex sein, da index für viewId
                        }
                        else
                        {
                            System.out.println("KartenViewId: " + currentCard.viewID + " Name = " + currentCard.name + " ViewID: " + view.getId());
                            DisplayAttributes(curCardList.Get(currentIndex));
                        }

                    }
                }
            });
            // place the card at the next free position of the grid
            gridCards.addView(ib);
        }
        return true;
    }
*/

}




/*
    // later outdated, is in extra json
    private void SetUniqueEyeAndHairColors()
    {
    CardList curCardList = m_PlayerController.GetCurrentPlayer().cardListRemaining;
        HashSet <String> eyeColors = new HashSet<String>();
        for(int index=0; index < curCardList.size(); index++)
        {
            eyeColors.add(curCardList.get(index).GetEye());
        }
        eyeColorsUnique.addAll(eyeColors);

        HashSet <String> hairColors = new HashSet<String>();
        for(int index=0; index < curCardList.size(); index++)
        {
            hairColors.add(curCardList.get(index).GetHair());
        }
        hairColorsUnique.addAll(hairColors);


    }
*/

