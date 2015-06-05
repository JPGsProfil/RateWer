package com.example.wlg.ratewer.Controller;

import com.example.wlg.ratewer.Model.AttribValue;
import com.example.wlg.ratewer.Model.AttributList;
import com.example.wlg.ratewer.Model.CardList;
import com.example.wlg.ratewer.Model.PlayerInformation;

/**
 * Created by Jean on 21.04.2015.
 */
public class AIController
{
    private static int CURRENTROUND = 0;

    private AttributList m_Attribs;
    // there is only one cardList, this is list two because a long time ago a gson list existed next to this
    private CardList m_cardList;

    private String m_difficulty;

    public AIController(String _difficulty)
    {
        m_difficulty = "einfach"; // needed for CalculateNextMove
        if(_difficulty != null)
        {
            m_difficulty = _difficulty;
        }
    }

    // spaeter attribValue vom Typ, da nach Eigenschaften gesucht, later
    // only for simple
    public int CalculateCard(PlayerInformation _curPlayer, PlayerInformation _nextPlayer)
    {
        CURRENTROUND += 1;  // start with card one, then card 2, ...    // only for simple and error (invalid difficulty)
        int calculatedMove = CURRENTROUND -1;   // because index of list starts at 0

        if(m_difficulty.equals("einfach"))
        {
            calculatedMove = CURRENTROUND -1;

        }
        else if(m_difficulty.equals("Hellseher"))
        {
            calculatedMove = _curPlayer.Hellseher(_curPlayer, _nextPlayer);
        }
        else if(m_difficulty.equals("normal"))
        {
            calculatedMove = _curPlayer.GoodEnemy(_curPlayer, _nextPlayer);
        }
       return calculatedMove;
    }


    public String GetDifficulty()
    {
        return m_difficulty;
    }

    public void SetDifficulty(String m_difficulty)
    {
        this.m_difficulty = m_difficulty;
    }


    /*
    public AttribValue CalculateNextMove(CardList _cardList, AttributList _attribs)
    {
        AttribValue bestquestion = new AttribValue("ja", "ja");

        return bestquestion;
    }
    */
}
