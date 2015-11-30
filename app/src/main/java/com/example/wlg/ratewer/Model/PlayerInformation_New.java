package com.example.wlg.ratewer.Model;

/**
 * Created by RareHue on 26.11.2015.
 */
public class PlayerInformation_New {

    public enum EAIDifficulty {EASY,NORMAL, HARD, VERYHARD};

    private int selectedCardID;
    private boolean isAI;
    private int playerID;
    private EAIDifficulty aiDifficulty;
    public AttributList attributesRemaining; // each player has his/her own attributlist(each player can see individual option menu)
    public CardList cardsRemaining;  // same as attriblist
    public boolean hasSelectedACard;   // because the real game can start if both players selected their character

    public PlayerInformation_New()
    {
    }

    public void onStart(CardList _cardlist)
    {
        cardsRemaining = new CardList(_cardlist); // copy since all players need their own
        attributesRemaining = new AttributList(_cardlist);
        hasSelectedACard = false;
    }

    public void refreshAttributs()
    {
        attributesRemaining = new AttributList(cardsRemaining);
    }

    public boolean getIsAi()
    {
        return isAI;
    }

    public void  setAi (EAIDifficulty _aidifficulty)
    {
        aiDifficulty = _aidifficulty;
        isAI = true;
        selectRandomCardID();
    }

    private void selectRandomCardID()
    {
        setSelectedCardID(10); // todo: make it some real random
    }

    public int setSelectedCardID()
    {
        return selectedCardID;
    }

    public void setSelectedCardID(int _cardID)
    {
        selectedCardID = _cardID;
    }

    public int getPlayerID()
    {
        return  playerID;
    }

    public EAIDifficulty getAiDifficulty()
    {
        return aiDifficulty;
    }
}
