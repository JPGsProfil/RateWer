package com.example.wlg.ratewer.Model;

/**
 * all information a player of this game has
 * like CHosen card, isAi ..
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
    public boolean hasPlayersSelectedWhoHeIs;   // because the real game can start if both players selected their character


    /**
     * constructor of the player
     * each player has his / her own cardlist
     * @param _cardList own cardlist so that each player can remove cards
     */
    public PlayerInformation(CardList _cardList)
    {
        amountOfPlayers++;
        chosenCardId = -1;
        isAI = false;
        playerID = amountOfPlayers;
        aiDifficulty = "einfach";
        hasPlayersSelectedWhoHeIs = false;

        // make copy from orig list for user (each user has own carlist)

        cardListRemaining= new CardList(_cardList);    // call copy constructor
        //System.out.println("srcCardListSize:"+_cardList.GetSize() + "  dst size: "+cardListRemaining.GetSize());

        // generate own AttribList for optionsmenu:

        m_AttribsRemaining = new AttributList(cardListRemaining);
    }


    /**
     * Questions in optionmenu are generated from the cardList
     * each player has individual questions in option menu, displayed questions are generated from remaining cards in list
     */
    public void RecalculateRemainingAttributes()
    {
        m_AttribsRemaining = new AttributList(cardListRemaining);
    }


    /**
     * Getter
     * @return true if player is ai, else false
     */
    public boolean IsAI()
    {
        return isAI;
    }


    /**
     * Setter
     */
    public void SetToAI()
    {
        this.isAI = true;
        // for debugging
        this.chosenCardId = 10;
    }

    /**
     * id of the character the player selected
     * only useful at the beginning
     * if cards removed -> index change
     * use in combination with GetIndexFromCardId in Cardlist
     * @return int id
     */
    public int GetChosenCardId()
    {
        return chosenCardId;
    }

    /**
     * called at the beginning of the game
     * to choose a character
     * @param chosenCardId int id of the character
     */
    public void SetChosenCardId(int chosenCardId)
    {
        this.chosenCardId = chosenCardId;
    }

    /**
     * ID of the player, beginning by one
     * uesed to display whose turn
     * @return playernumber
     */
    public int GetPlayerID()
    {
        return playerID;
    }


    /**
     * Getter
     * @return String
     */
    public String GetAiDifficulty()
    {
        return aiDifficulty;
    }


    /**
     * Setter
     * @param aiDifficulty String
     */
    public void SetAiDifficulty(String aiDifficulty)
    {
        this.aiDifficulty = aiDifficulty;
    }



}
