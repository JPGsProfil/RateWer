package com.example.wlg.ratewer.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.wlg.ratewer.Controller.AIController;
import com.example.wlg.ratewer.Adapter.ExpandableListAdapter;
import com.example.wlg.ratewer.Builder.CustomAlertDialogBuilder;
import com.example.wlg.ratewer.Controller.PlayerController;
import com.example.wlg.ratewer.IO.FileToString;
import com.example.wlg.ratewer.Model.AttribValue;
import com.example.wlg.ratewer.Model.AttributList;
import com.example.wlg.ratewer.Model.Board;
import com.example.wlg.ratewer.Model.Card;
import com.example.wlg.ratewer.Model.CardList;
import com.example.wlg.ratewer.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameBoardActivity extends AppCompatActivity
{

    private AttributList m_Attribs;  // attribs of the current player, only reference
    // there is only one cardList, this is list two because a long time ago a gson list existed next to this
    private CardList cardList;  // list where the cards will be saved (name and attributtes)
    private PlayerController m_PlayerController ;    // initialize two players, accessable via list or Get
    private boolean s_isTurnOver = false;  // needed for human (can't ask two question in one turn)
    //private static List<JSONCards> cardList = new ArrayList<>();  // for GSON implementation
    //private static boolean HavePlayersSelectedWhoTheyAre = false;   // to handle onclick -> first select player, later onlick to view details
    private boolean s_TurnCardsAuto = false;

    int panelHeigth = 0;

    private Animation animation_card_part_1;
    private Animation animation_card_part_2;

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


        String usedCardset = getString(R.string.default_card_set_name);
        String difficulty = getString(R.string.difficulty_easy);
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
//                if(diff.equals("Hellseher"))
//                {
//                    //String msg = "Ein Hellseher braucht beim ersten Anlauf etwas Laenger. Geduld, wenn Spieler 2 dran ist!";
//                    //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//                    String txt = "Ein Hellseher braucht beim ersten Anlauf etwas Laenger. Geduld, wenn Spieler 2 dran ist!";
//                    showSimpleDialog("Hellseher gewählt",txt,"OK");
//                }

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

        expListView = (ExpandableListView) findViewById(R.id.abq_lvExp);


        // read height of expandable list because we need the dp value
        com.sothree.slidinguppanel.SlidingUpPanelLayout lay = (com.sothree.slidinguppanel.SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        panelHeigth = lay.getPanelHeight();

        SetExpandableListVisibility(false);

        animation_card_part_1 = AnimationUtils.loadAnimation(this, R.anim.anim_to_middle);
        animation_card_part_2 = AnimationUtils.loadAnimation(this, R.anim.anim_from_middle);




    }   // end of OnCreate



    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader = new ArrayList<String>();
    private HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
    //listDataChild = new HashMap<String, List<String>>();











    /**
     * called by click on expandable list item
     * @param _groupPosition id from expandable list
     * @param _childPosition id from expandable list
     * @return
     */
    private boolean OnclickAttributes(int _groupPosition, int _childPosition)
    {

        final String clickedAttrib = listDataHeader.get(_groupPosition);
        final String clickedValue = listDataChild.get( listDataHeader.get(_groupPosition)).get(_childPosition);
        System.out.println("clickedAttrib "+clickedAttrib+ "  clickedValue "+clickedValue);
        boolean returnVal = ExpandableListClick(clickedAttrib,clickedValue);
        return returnVal;
    }


    /**
     * called by click on expandable list item
     * @param clickedAttrib
     * @param clickedValue
     * @return
     */
    private boolean ExpandableListClick(String clickedAttrib, String clickedValue)
    {
        if(s_isTurnOver)
        {
            String msg = "Dein Zug ist beendet!\n Du kannst keine weiteren Attribute erfragen!";
            showSimpleDialog("Hinweis", msg, "OK");
            return false;
        }
        CardList curCardList = m_PlayerController.GetCurrentPlayer().cardListRemaining;
        m_Attribs = m_PlayerController.GetCurrentPlayer().m_AttribsRemaining;
        // print of target person has this attribut:
        boolean hasId = false;
        // get playerCard (enemy)

        Card cardEnemy = curCardList.GetCardByCardID(m_PlayerController.GetNextPlayer().GetChosenCardId());

        System.out.println("Gegner wähle: " + cardEnemy.name);

        String isItQuestion = getString(R.string.txt_activity_board_question_isIt);
        if(clickedAttrib.equals(isItQuestion) )
        {
            if(cardEnemy.name.equals(clickedValue))
            {
                final Intent lastIntent = new Intent(this, EndGameActivity.class);
                String msg = "Spieler "+m_PlayerController.GetCurrentPlayer().GetPlayerID()+ " hat gewonnen";
                lastIntent.putExtra("msg",msg);
                startActivity(lastIntent);
            }

        }



        hasId = cardEnemy.DoesCardContainAttrValue(clickedAttrib, clickedValue);

        if (hasId)
        {
            showSimpleDialog(clickedAttrib +" : "+ clickedValue, "Gut Geraten" , "Yay");

        } else
        {
            showSimpleDialog(clickedAttrib +" : "+ clickedValue,"leider Falsch", "Nagut");
        }
        // End of: print of target person has this attribut

        List<Integer> CardsToRemove = new ArrayList<>();

        String personsWithSameValue = "Folgende Personen kommen in Frage:\n";

        // könnte ausgelagert werden in GetCardsWithThisAttribut(attributid)
        for (int curCardIndex = 0; curCardIndex < curCardList.GetSize(); curCardIndex++)
        {
            Card curCard = curCardList.Get(curCardIndex);
            boolean doesCardContainAttrVal = curCard.DoesCardContainAttrValue(clickedAttrib, clickedValue);
            //System.out.println("Bin aktuell bei: "+curCard.name+ " ; hasId = "+hasId + " doesContain: "+doesCardContainAttrVal);
            if (hasId)
            {
                if(doesCardContainAttrVal)
                {
                    personsWithSameValue += curCardList.Get(curCardIndex).name + "\n";
                }
                else
                {
                    if (s_TurnCardsAuto)
                    {
                        CardsToRemove.add(curCardIndex);  // because we want to delete entries at the end, not now
                        System.out.println("zu loeschende Person: " + curCardList.Get(curCardIndex).name + " index = " + curCardIndex);
                    }
                }

            }
            else
            {
                if(doesCardContainAttrVal)
                {
                    if (s_TurnCardsAuto)
                    {
                        CardsToRemove.add(curCardIndex);  // because we want to delete entries at the end, not now
                        System.out.println("zu loeschende Person: " + curCardList.Get(curCardIndex).name + " index = " + curCardIndex);
                    }
                }
                else
                {
                    personsWithSameValue += curCardList.Get(curCardIndex).name + "\n";
                }
            }
        }
        System.out.println("Personen: " + personsWithSameValue); //

        // turn is over !!!
        s_isTurnOver = true;
        SetExpandableListVisibility(false);

        String txt_display = ": Beende den Zug!";
        if(!s_TurnCardsAuto && !m_PlayerController.GetCurrentPlayer().IsAI())
        {
            txt_display = "  Drehe die Karten um!";
        }
        SetTitle("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + " : " + txt_display);

        SetFinishButtonVisibility(true);    // make finish button visible


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
                ChangeButtonVisibility(curCardList.Get(CardsToRemove.get(index)).viewID, false);

                // remove from option menu
                //System.out.println("Remove element: " + CardsToRemove.get(index) + "  "+curCardList.Get(CardsToRemove.get(index)).name);
                curCardList.Remove(curCardList.Get(CardsToRemove.get(index)));// int list of  all cards wich should be deleted // with index not working
            }
        }


        // now update option menu list:
        m_PlayerController.GetCurrentPlayer().RecalculateRemainingAttributes();
        // Ende auslagern!!!!
        return true;    // only false if turn is already over
    }





    /**
     * Update expandable list
     * set listeners
     * add question and answers to list ( FillExpandableList() )
     *
     */
    private void SetExpandableList()
    {
        FillExpandableList();

        
        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id)
            {

                //if(listDataHeader.get(groupPosition).equals(getString(R.string.txt_activity_board_question_isIt)))
                //{
                //    System.out.println("Wow, du hast ist es angeklickt!!!");
                //    System.out.println("Group0: "+listDataHeader.get(groupPosition));
                //}

                m_Attribs = m_PlayerController.GetCurrentPlayer().m_AttribsRemaining;
                OnclickAttributes(groupPosition, childPosition);

                //com.sothree.slidinguppanel.SlidingUpPanelLayout slidingUpPanelLayout = (com.sothree.slidinguppanel.SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
                //slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                return true;


            }
        });
    }


    /**
     * only call from SetExpandableList !!!!
     * // fill list with remaining question and answers
     */
    private void FillExpandableList()
    {
        //listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<String>>();
        listDataHeader.clear();
        listDataChild.clear();

        CardList curCardList = m_PlayerController.GetCurrentPlayer().cardListRemaining;
        if( m_PlayerController.HaveAllPlayersSelectedWhoTheyAre()
                && !m_PlayerController.GetCurrentPlayer().IsAI()
                && curCardList.GetSize() > 0 )
        {
            if (!s_isTurnOver)
            {
                String question = getString(R.string.txt_activity_board_question_isIt); // is it?
                // submenu to choose "is it ...?"

                // Adding child data
                listDataHeader.add(question);
                List<String> submenu = new ArrayList<String>();
                for (int index = 0; index < curCardList.GetSize(); index++)
                {
                    // Adding child data
                    submenu.add(curCardList.Get(index).name);
                }
                System.out.println("question: "+question+"  submenu: "+submenu.get(0));
                listDataChild.put(question, submenu); // Header, Child data


                // part 2 : add questions
                m_Attribs = m_PlayerController.GetCurrentPlayer().m_AttribsRemaining;

                int currGroupId = -1;
                // iterate all attributs
                for ( int index = 0; index < m_Attribs.attriList.size(); )
                {
                    // at the beginning, set new group id, not necessary if stay the same (if below)
                    currGroupId = m_Attribs.attriList.get(index).groupId;


                    // if more than two -> submenu required, if one or two not (like bool)
                    if (index + 2 < m_Attribs.attriList.size() && m_Attribs.attriList.get(index).groupId == m_Attribs.attriList.get(index + 2).groupId)
                    {
                        question = m_Attribs.attriList.get(index).attr;
                        listDataHeader.add(question);
                        List<String> curSubmenu = new ArrayList<String>();

                        while (index < m_Attribs.attriList.size() && currGroupId == m_Attribs.attriList.get(index).groupId) // it's a new menu item (kategory)
                        {
                            //sm.add(currGroupId, index, index, m_Attribs.attriList.get(index).value);
                            curSubmenu.add(m_Attribs.attriList.get(index).value);
                            //itemId++;
                            //System.out.println("add: currGroupId "+currGroupId+ " index: "+index+ " eintrag:"+m_Attribs.attriList.get(index).value);
                            index++;
                        }
                        listDataChild.put(question, curSubmenu); // Header, Child data
                    }
                    else    // only bool attributes (like wearGlasses ...) -> no submenu required
                    {

                        if (index + 1 < m_Attribs.attriList.size() && m_Attribs.attriList.get(index).groupId == m_Attribs.attriList.get(index + 1).groupId)
                        {

                            //String attriValStr = m_Attribs.attriList.get(index).attr + ": "+m_Attribs.attriList.get(index).value;

                            question = m_Attribs.attriList.get(index).attr;
                            String curVal = m_Attribs.attriList.get(index).value;
                            listDataHeader.add(question);
                            List<String> curSubmenu = new ArrayList<String>();
                            curSubmenu.add(curVal);
                            listDataChild.put(question, curSubmenu); // Header, Child data

                            index += 2;  // we don't want to print bool twice (has hair hair yes?, has hair no? -> only has hair?
                        } else // only one value for this attribut (eg all cards have haircolor brown -> have to be at least two values (brown, black, ..)
                        {
                            index++;    // not really necessary, because always min. yes or no in question, otherwhise not a question
                        }
                    }
                }
            }   // end of: only display categories if players turn isn't over

            System.out.println("listDataHeader.size() " + listDataHeader.size());
            if(listDataHeader.size() > 0)
            {
                listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

                // setting list adapter
                expListView.setAdapter(listAdapter);
            }

        }   // end of: option menu only visible if both players have chosen their character

    }



    ///////////////////////////////////////////////////
    //////////////////////////Button Visibility
    ////////////////////////////////////////


    /**
     * initialize finish button
     * makes new turn begin after click
     */
    private void InitializeFinishButton()
    {
        // make finish button:
        Button bt = (Button)findViewById(R.id.bFinished);
        bt.setOnClickListener(
                new ImageButton.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        BeginNewTurn();
                    }
                }
        );
        bt.setClickable(false); // only visible if turn is over
        bt.setAlpha(0);
    }


    /**
     * after finish button is initialized
     * it could be changed to visible or invisible depending on wether the turn is over or not
     * @param _displayIt if true button is visible, else invisible
     */
    private void SetFinishButtonVisibility(boolean _displayIt)
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
     * used to change visibility of the cards
     * changes clickable
     * @param _viewId
     * @param isVisible
     */
    private void ChangeButtonVisibility(int _viewId, boolean isVisible)
    {
        ImageButton btn = (ImageButton) findViewById(_viewId);
        if(isVisible)
        {
            btn.setAlpha(1.0f);
            btn.setClickable(true);
        }
        else
        {
            //btn.setAlpha(0.4f);
            btn.setClickable(false);
            TurnCard(btn);
        }
    }


    /**
     * hide expandable eg. if asking a question isn't allowed (eg. turn is over)
     * @param _isVisible boolean if true, list is visible and clickable, else not
     */
    private void SetExpandableListVisibility(boolean _isVisible)
    {
        com.sothree.slidinguppanel.SlidingUpPanelLayout slidingUpPanelLayout = (com.sothree.slidinguppanel.SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.abq_lvExp);
        TextView textView = (TextView) findViewById(R.id.SlideUp_Heading);


        if(_isVisible)
        {
            //System.out.println("panelHeigth "+panelHeigth);
            slidingUpPanelLayout.setPanelHeight(panelHeigth);
            expandableListView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
        else
        {
            slidingUpPanelLayout.setPanelHeight(0);
            expandableListView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }

        boolean oposite = !_isVisible;
        slidingUpPanelLayout.setOverlayed(oposite);

        System.out.println("Sichtbarkeit geaendert in: " + _isVisible);

    }




    ///////////////////////////////////////////////////
    ////////////////////////// End of: Button Visibility
    ////////////////////////////////////////



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
            String error = "Die Karten konnten nicht eingelesen werden";
            System.out.println(error);

            String Title ="Oh Weh...";
            String Message =error;
            String ButtonText ="OK";
            showSimpleDialog(Title, Message, ButtonText);
        }
        // end of: set cards
    }


    public void SetTitle(String _txt)
    {
        TextView tv_title = (TextView) findViewById(R.id.tv_Title_Ingame);
        tv_title.setText(_txt);
    }


    /**
     * ends players turn, do ai stuff, starts with player turn again
     */
    private void BeginNewTurn()
    {
        // change backgorund
        m_PlayerController.ChangeCurrentPlayer();

        SetFinishButtonVisibility(false);    // make finish button invisible (first do turn)
        s_isTurnOver = false;


        System.out.println("Bin in begin new Turn");

        if(!m_PlayerController.GetCurrentPlayer().IsAI())
        {
            System.out.println("Der aktuelle Spieler ist ein Mensch");
            SetExpandableListVisibility(true);
        }
        else
        {
            System.out.println("Der aktuelle Spieler ist KI. var isAI "+m_PlayerController.GetCurrentPlayer().IsAI());
        }




        UpdateField(); // draw new grid with cards of the current player

        System.out.println("hasPlayersSelectedWhoHeIs "+m_PlayerController.GetCurrentPlayer().hasPlayersSelectedWhoHeIs);
        if(!m_PlayerController.GetCurrentPlayer().hasPlayersSelectedWhoHeIs)
        {
            SetTitle("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + ": Wähle deinen Charakter!");
            SetExpandableListVisibility(false);
        }
        else
        {
            SetTitle("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + ": Fragestunde!");
        }

        // end of: change background


        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.MyDialogTheme );
        CustomAlertDialogBuilder alertDialogBuilder = new CustomAlertDialogBuilder(ctw);

        // set title
        alertDialogBuilder.setTitle("Zug beendet");
        alertDialogBuilder
                .setMessage("Spieler" + m_PlayerController.GetCurrentPlayer().GetPlayerID() + " ist dran")
                        // not need to click ok to cancel alert, simply click outside the box
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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


            String Title ="Computer: ";
            String Message =msg;
            String ButtonText ="OK";
            showSimpleDialog(Title, Message, ButtonText);



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
                    AIout+= "Richtig geraten";
                    final Intent lastIntent = new Intent(this, EndGameActivity.class);
                    String msg = "Computer: Ich habe gewonnen,\n du hast verloren!!!";
                    lastIntent.putExtra("msg",msg);
                    startActivity(lastIntent);
                }
                else
                {
                    m_PlayerController.GetCurrentPlayer().cardListRemaining.Remove(ivalue);
                    AIout+= "Computer: nein, leider nicht!!! \n Du bist!";

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


            String Title ="Computer: ";
            String Message =AIout;
            String ButtonText ="OK";
            showSimpleDialog(Title, Message, ButtonText);

            //BeginNewTurn();
        }
        s_isTurnOver = true;
        //SetExpandableListVisibility(false);
        UpdateField();

        SetTitle("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + ": Hat den Zug beendet!");

        SetFinishButtonVisibility(true);    // make finish bt visible

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
            String Title ="Achtung!";
            String Message ="Du musst erst einen Spieler auswählen!!";
            String ButtonText ="OK";
            showSimpleDialog(Title, Message, ButtonText);

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
                    String Title ="Nur eine Frage pro Zug";
                    String Message ="Dein Zug ist vorbei\n" +" Du kannst kein weiteres Attribut erfragen";
                    String ButtonText ="OK";
                    showSimpleDialog(Title, Message, ButtonText);

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

                        String Title ="Gut geraten";
                        String Message ="";
                        String ButtonText ="Weiter";
                        showSimpleDialog(Title, Message, ButtonText);

                    } else
                    {
                        String Title ="Falsch geraten";
                        String Message ="";
                        String ButtonText ="Weiter";
                        showSimpleDialog(Title, Message, ButtonText);
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
                                            //btn.setAlpha(0.4f);
                                            btn.setClickable(false);

                                            TurnCard(btn);
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
                                            //btn.setAlpha(0.4f);
                                            btn.setClickable(false);

                                            TurnCard(btn);

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
                        String Title ="Nur eine Frage pro Zug";
                        String Message =personsWithSameValue;
                        String ButtonText ="OK";
                        showSimpleDialog(Title, Message, ButtonText);
                    }


                    // turn is over !!!
                    s_isTurnOver = true;
                    SetExpandableListVisibility(false);

                    String txt_display = " Beende den Zug!";
                    if(!s_TurnCardsAuto && !m_PlayerController.GetCurrentPlayer().IsAI())
                    {
                        txt_display = "  Drehe die Karten um!";
                    }
                    SetTitle("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + txt_display);

                    SetFinishButtonVisibility(true);    // make finish button visible


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
                            //btn.setAlpha(0.4f);
                            btn.setClickable(false);

                            TurnCard(btn);

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
                SetExpandableListVisibility(false);

                SetFinishButtonVisibility(true);    // make finish button visible

                String txt_display = ": Beende den Zug!";
                if(!s_TurnCardsAuto && !m_PlayerController.GetCurrentPlayer().IsAI())
                {
                    txt_display = "  Drehe die Karten um!";
                }

                SetTitle("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + txt_display);

                //int cardId = curCardList.GetIndexFromCardId(curPlayerId);
                if (curPlayerId == m_PlayerController.GetNextPlayer().GetChosenCardId())
                {
                    final Intent lastIntent = new Intent(this, EndGameActivity.class);
                    String msg = "Spieler "+m_PlayerController.GetCurrentPlayer().GetPlayerID()+ " hat gewonnen !!! ";
                    lastIntent.putExtra("msg", msg);
                    startActivity(lastIntent);
                } else
                {
                    String playerName = curCardList.Get(curPlayerId).name;

                    ImageButton btn = (ImageButton) findViewById(curCardList.Get(curPlayerId).viewID);
                    //btn.setAlpha(0.4f);
                    btn.setClickable(false);

                    TurnCard(btn);


                    String msg = "Es ist leider nicht " + playerName;
                    if(s_TurnCardsAuto)
                    {
                        curCardList.Remove(curCardList.Get(curPlayerId));
                    }
                    m_PlayerController.GetCurrentPlayer().RecalculateRemainingAttributes();

                    String Title ="Schade";
                    String Message =msg;
                    String ButtonText ="OK";
                    showSimpleDialog(Title, Message, ButtonText);

                    System.out.println("msg: " + msg);
                }
            } else    // only for debugging, can be removed
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
            ib.setPadding(0,0,2,2); // border between images
            //ib.setStateListAnimator(null); API 21...


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
                            selectTheSecretCard(cardList.Get(currentIndex));
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
    public boolean UpdateField()
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
                //ib.setAlpha(0.4f);
                ib.setClickable(false);

                TurnCard(ib);

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
                            selectTheSecretCard(cardList.Get(currentIndex));    // //bug, darf nicht currentIndex sein, da index für viewId
                        }
                        else if(s_TurnCardsAuto == false && s_isTurnOver && !m_PlayerController.GetCurrentPlayer().IsAI())
                        {
                            System.out.println("debug: bin in manuell umdrehen!");
                            ImageButton btn = (ImageButton) findViewById(view.getId());
                            //btn.setAlpha(0.4f);
                            btn.setClickable(false);

                            TurnCard(btn);

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

        if(!m_PlayerController.GetCurrentPlayer().IsAI())
        {
            // initialize expandableList
            SetExpandableList();
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
        int ressourceId = getResources().getIdentifier(_cardsetName, "raw", getPackageName());
        InputStream is = getResources().openRawResource(ressourceId);
        return FileToString.ReadTextFile(is);
    }


    /**
     * ingame klick on card -> displays attributes of this card (from json, )
     * @param currentCard needed to display card information in box
     */
    private void DisplayAttributes(Card currentCard)
    {
        String title = currentCard.name;
        String attributes = "Eigenschaften\n\n";
        for(int index = 0; index <currentCard.attriList.size(); index ++)
        {
            attributes += currentCard.attriList.get(index).attr + ":  "+ currentCard.attriList.get(index).value + "\n";
        }

        showSimpleDialog(title, attributes, "OK");
    }


    // at the beginning of the game you have to select who you are
    private void selectTheSecretCard(final Card _currentCard)
    {
        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.MyDialogTheme );
        CustomAlertDialogBuilder alertDialogBuilder = new CustomAlertDialogBuilder(ctw);
        //AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameBoardActivity.this);

        // set title
        alertDialogBuilder.setTitle("Auswahl:");

        //System.out.println("Bin in Display");
        // set dialog message
        alertDialogBuilder
                .setMessage("Möchtest du "+_currentCard.name+" wählen?")
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
                        SetTitle("Spieler " + m_PlayerController.GetCurrentPlayer().GetPlayerID() + " : Beende deinen Zug!");
                        invalidateOptionsMenu();    // because now we have entries

                        SetFinishButtonVisibility(true);    // make finish button visible
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


    private void showSimpleDialog(String _Title, String _Message, String _ButtonText)
    {
        showSimpleDialog(_Title, _Message, _ButtonText, false);
    }

    private void showSimpleDialog(String _Title, String _Message, String _ButtonText, boolean _IsQuestion)
    {
        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.MyDialogTheme );
        CustomAlertDialogBuilder alertDialogBuilder = new CustomAlertDialogBuilder(ctw);
        alertDialogBuilder.setTitle(_Title);

        if(_IsQuestion)
        {
            alertDialogBuilder.setIcon(R.drawable.icon_question);
        }
        else
        {
            alertDialogBuilder.setIcon(R.drawable.icon_exclamation);
        }

        alertDialogBuilder
                .setMessage(_Message)
                .setCancelable(false)
                .setPositiveButton(_ButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    private void TurnCard(ImageButton _ImageButton)
    {
        int imageWidthHeight = GetImageWithHeight();
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.icon_x_big );
        Bitmap resized = Bitmap.createScaledBitmap(bm, imageWidthHeight, imageWidthHeight, true);

        _ImageButton.setImageBitmap(resized);

        AnimationSet as = new AnimationSet(true);
        as.addAnimation(animation_card_part_1);
        as.addAnimation(animation_card_part_2);
        _ImageButton.startAnimation(as);
        _ImageButton.setPadding(0,0,2,2);

    }

}



