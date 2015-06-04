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


    public PlayerInformation(CardList _cardList)
    {
        amountOfPlayers++;
        chosenCardId = -1;
        isAI = false;
        playerID = amountOfPlayers;

        // make copy from orig list for user (each user has own carlist)

        cardListRemaining= new CardList(_cardList);    // call copy constructor
        //System.out.println("srcCardListSize:"+_cardList.GetSize() + "  dst size: "+cardListRemaining.GetSize());

        // generate own AttribList for optionsmenu:

        m_AttribsRemaining = new AttributList(cardListRemaining);
    }

    public void RecalculateRemainingAttributes()
    {
        m_AttribsRemaining = new AttributList(cardListRemaining);
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
