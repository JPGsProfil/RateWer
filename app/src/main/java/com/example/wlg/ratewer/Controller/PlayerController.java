package com.example.wlg.ratewer.Controller;

import com.example.wlg.ratewer.Model.CardList;
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

    public PlayerController(CardList _cardList)
    {
        // maybe later more than two player
        for (int i=0; i<2; i++)
        {
            PlayerInformation newPlayer = new PlayerInformation(_cardList);
            players.add(newPlayer);
            System.out.println("index "+ i + "= "+players.get(i).GetPlayerID());
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

    public PlayerInformation GetFirstPlayer()
    {
        return players.get(0);
    }

    public PlayerInformation GetSecondPlayer()
    {
        return players.get(1);
    }

    public PlayerInformation GetCurrentPlayer()
    {
        return players.get(currentPlayerIndex);
    }

    public PlayerInformation GetNextPlayer()
    {
        return players.get(GetIndexForNextPlayer());
    }



    // only working with two players
    public boolean ChangeCurrentPlayer()
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
