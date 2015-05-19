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


    public PlayerInformation()
    {
        amountOfPlayers++;
        chosenCardId = -1;
        isAI = false;
        amountOfPlayers++;
        playerID = amountOfPlayers;
    }


    public void SetToAI()
    {
        this.isAI = true;
        // for debugging
        this.chosenCardId = 10;
    }
}
