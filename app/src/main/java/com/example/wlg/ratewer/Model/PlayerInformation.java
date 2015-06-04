package com.example.wlg.ratewer.Model;

/**
 * Created by Jean on 21.04.2015.
 */
public class PlayerInformation
{
    private int chosenCardId;
    private boolean isAI;
    private int playerID;
    private static int amountOfPlayers = 0;
    public AttributList m_AttribsRemaining; // each player has his/her own attributlist(each player can see individual option menu)
    public CardList cardListRemaining;  // same as attriblist


    public PlayerInformation()
    {
        amountOfPlayers++;
        chosenCardId = -1;
        isAI = false;
        playerID = amountOfPlayers;
    }


    public void SetToAI()
    {
        this.isAI = true;
        // for debugging
        this.chosenCardId = 10;
    }

    public int GetChosenCardId()
    {
        return chosenCardId;
    }

    public void SetChosenCardId(int chosenCardId)
    {
        this.chosenCardId = chosenCardId;
    }

    public int GetPlayerID()
    {
        return playerID;
    }


}
