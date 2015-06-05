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





    public int GoodEnemy(PlayerInformation _curPlayer, PlayerInformation _enemy)
    {
        CardList _cardListRemaining = _curPlayer.cardListRemaining;

        int bestMoveId = 0;
        if(_cardListRemaining.GetSize()<=2) // only to cards left, no need for attributs -> choose first of them
        {
            return 0;
        }
        int targetId = _enemy.GetChosenCardId();
        AttributList attrList = new AttributList(_cardListRemaining);
        int lowestRemainingCards = 50000;
        for(int index=0; index < attrList.attriList.size(); index ++)
        {
            //System.out.println("deb: Index ist: "+index);
            CardList cardListCpy = new CardList(_cardListRemaining); // make copy, because each way has it's own attributlist
            int clcpysize = cardListCpy.GetSize();
            System.out.println("cardListCpy Groesse: "+clcpysize+ " OrigListSize: "+_cardListRemaining.GetSize()); // wenn veraendert, dann nicht kopiert, sondern referenz
            // if enemy card has the target attribute -> remove all cards wich have not
            if(cardListCpy.m_List.get(cardListCpy.GetIndexFromCardId(targetId)).DoesCardContainAttrValue(attrList.attriList.get(index).attr,attrList.attriList.get(index).value))
            {
                cardListCpy.RemoveCardsWithoutAttriValue(attrList.attriList.get(index).attr,attrList.attriList.get(index).value);
            }
            else    // target card has not asked attrivalue -> remove all cards which have have asked attrivalue
            {
                cardListCpy.RemoveCardsWithAttriValue(attrList.attriList.get(index).attr, attrList.attriList.get(index).value);
            }

            int remainingCardsNew = cardListCpy.GetSize();
            //System.out.println("nach remove: cardListCpy Groesse: "+clcpysize+ " OrigListSize: "+_cardListRemaining.GetSize()); // wenn veraendert, dann nicht kopiert, sondern referenz

            if(lowestRemainingCards > remainingCardsNew)
            {
                lowestRemainingCards = remainingCardsNew;
                bestMoveId = index;
            }
        }
        System.out.println("geschafft");
        return bestMoveId;
    }


    public int Hellseher(PlayerInformation _curPlayer, PlayerInformation _enemy)
    {
        CardList _cardListRemaining = _curPlayer.cardListRemaining;
        CardList cardListcpy = new CardList(_cardListRemaining);
        AIReturn bestMove = Deepsearch(cardListcpy,0,_enemy.GetChosenCardId());
        return bestMove.attrValId;
    }

    // used by Hellseher
    private AIReturn Deepsearch(CardList _cardListRemaining, int _curDeep, int _targetCardId)
    {
        _curDeep++;
        AIReturn bestAIReturn = new AIReturn();

        int remainingCards = _cardListRemaining.GetSize();
        // if something changed:    // look at next deep

        AttributList attrList = new AttributList(_cardListRemaining);
        for(int index=0; index < attrList.attriList.size(); index ++)
        {
            //System.out.println("Deep: "+_curDeep+ "  index: "+index);
            CardList cardListCpy = new CardList(_cardListRemaining); // make copy, because each way has it's own attributlist
            // get current index of the target card id:
            int targetId = cardListCpy.GetIndexFromCardId(_targetCardId);

            // if enemy card has the target attribute -> remove all cards wich have not
            if(cardListCpy.m_List.get(targetId).DoesCardContainAttrValue(attrList.attriList.get(index).attr,attrList.attriList.get(index).value))
            {
                cardListCpy.RemoveCardsWithoutAttriValue(attrList.attriList.get(index).attr,attrList.attriList.get(index).value);
            }
            else    // target card has not asked attrivalue -> remove all cards which have have asked attrivalue
            {
                cardListCpy.RemoveCardsWithAttriValue(attrList.attriList.get(index).attr, attrList.attriList.get(index).value);
            }

            int remainingCardsNew = cardListCpy.GetSize();
            if(remainingCards != remainingCardsNew && _curDeep < 4)
            {
                AIReturn tempReturn = Deepsearch(cardListCpy, _curDeep, _targetCardId);
                if(tempReturn.rating < bestAIReturn.rating)
                {
                    bestAIReturn = tempReturn;
                }
            }
            else
            {
                bestAIReturn.rating = (_curDeep * 1000) + cardListCpy.GetSize() * 600;
                bestAIReturn.attrValId = index;     // todo -> should be saved into list because it won't change -> you don't have to calculate the best move each round
                // rating = remaining cards * 600, 5 cards left -> worse rating
            }
        }
        System.out.println("bestAIReturn:"+bestAIReturn);
        return bestAIReturn;
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
