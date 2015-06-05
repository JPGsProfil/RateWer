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
    private String aiDifficulty;
    public AttributList m_AttribsRemaining; // each player has his/her own attributlist(each player can see individual option menu)
    public CardList cardListRemaining;  // same as attriblist


    public PlayerInformation(CardList _cardList)
    {
        amountOfPlayers++;
        chosenCardId = -1;
        isAI = false;
        playerID = amountOfPlayers;
        aiDifficulty = "einfach";

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


    public boolean IsAI()
    {
        return isAI;
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








    public String GetAiDifficulty()
    {
        return aiDifficulty;
    }

    public void SetAiDifficulty(String aiDifficulty)
    {
        this.aiDifficulty = aiDifficulty;
    }

    public void debug(PlayerInformation _curPlayer)
    {

        // debug
        Card card1 = _curPlayer.cardListRemaining.m_List.get(0);
        Card card2 = new Card(card1);
        System.out.println("Karte 1 Attri0: "+card1.attriList.get(0).attr);
        System.out.println("Karte 2 Name: "+card2.attriList.get(0).attr);
        card1.attriList.get(0).attr = "jens";
        System.out.println("Karte 1 Name: "+card1.attriList.get(0).attr);
        System.out.println("Karte 2 Name: "+card2.attriList.get(0).attr);
        // debug end
    }
}
