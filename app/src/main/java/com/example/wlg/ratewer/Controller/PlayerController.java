package com.example.wlg.ratewer.Controller;

import com.example.wlg.ratewer.Model.PlayerInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jean on 21.04.2015.
 */
public class PlayerController
{
    private int currentPlayerIndex = 0;
    private static List<PlayerInformation> players = new ArrayList<>();

    public PlayerController()
    {
        // maybe later more than two player
        for (int i=0; i<2; i++)
        {
            PlayerInformation newPlayer = new PlayerInformation();
            players.add(newPlayer);
        }
        players.get(1).SetToAI();

    }

    public void ChooseQuestion()
    {

    }
    public void DisableCard()
    {

    }
    public void SelectCard()
    {

    }



    // only working with two players
    private boolean ChangeCurrentPlayer()
    {
        if(players.size()<2)
        {
            return false;
        }
        else
        {
            if(currentPlayerIndex == 0)
            {
                currentPlayerIndex = 1;
            }
            else
            {
                currentPlayerIndex = 0;
            }
        }
        return true;

    }



    private int GetIndexForNextPlayer()
    {
        if(currentPlayerIndex == 0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    private void SetIndexForNextPlayer()
    {
        if(currentPlayerIndex == 0)
        {
            currentPlayerIndex = 1;
        }
        else
        {
            currentPlayerIndex = 0;
        }
    }


}
