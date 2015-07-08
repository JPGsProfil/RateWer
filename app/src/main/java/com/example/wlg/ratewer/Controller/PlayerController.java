package com.example.wlg.ratewer.Controller;

import com.example.wlg.ratewer.Model.AIReturn;
import com.example.wlg.ratewer.Model.AttribValue;
import com.example.wlg.ratewer.Model.AttributList;
import com.example.wlg.ratewer.Model.CardList;
import com.example.wlg.ratewer.Model.IntInt;
import com.example.wlg.ratewer.Model.PlayerInformation;
import com.example.wlg.ratewer.Model.StringsToDisplayAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jean on 21.04.2015.
 * handles the players (access)
 * initializes two players at the beginning
 * more than 2 players possible in future
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
        //
    }


    /**
     * Get first player
     * not needed, but second player is
     * @return playernumber
     */
    public PlayerInformation GetFirstPlayer()
    {
        return players.get(0);
    }

    /**
     * Get second player
     * used to set the second player to ai
     * @return playernumber
     */
    public PlayerInformation GetSecondPlayer()
    {
        return players.get(1);
    }


    /**
     *returns the player who's turn it is
     * could be player one, two, ...
     * @return the current player
     */
    public PlayerInformation GetCurrentPlayer()
    {
        return players.get(currentPlayerIndex);
    }

    /**
     * get next player
     * e.g. used to get enemy card
     * @return the next player
     */
    public PlayerInformation GetNextPlayer()
    {
        return players.get(GetIndexForNextPlayer());
    }

    /**
     * // set to current player to next player, working with more than 2 players
     * bool used for future, but could be changed to void
     * @return always true, later false if no players e.g. possible
     */
    public boolean ChangeCurrentPlayer()
    {
        //System.out.println("Changed from "+ currentPlayerIndex + " to "+GetIndexForNextPlayer());
        currentPlayerIndex = GetIndexForNextPlayer();
        return true;    // always true, if one player, next is player 1 ..
    }

    /**
     * game can only start if all player have selected the character they want to be
     * @return true if all have, else false
     */
    public boolean HaveAllPlayersSelectedWhoTheyAre()
    {
        for(int index = 0; index < players.size(); index++)
        {
            if(!players.get(index).hasPlayersSelectedWhoHeIs)
            {
                return false;
            }
        }
        return true;
    }


    /**
     * used to set next player
     * @return index of the next player
     */
    private int GetIndexForNextPlayer()
    {
        if(players.size()<2)
        {
            System.out.println("players.size()<2");
            return 0;
        }
        else
        {
            //int plsmi1 = +players.size()-1;
            //System.out.println(""+ currentPlayerIndex +" < " + plsmi1);
            if(currentPlayerIndex < players.size()-1)
            {
                return currentPlayerIndex + 1;
            }
            else
            {
                return 0;
            }
        }
    }

    /**
     * called by both player
     * but only for ai, because human is making his /her move by using option menu and button
     * @return the move the ai calculated (String type, String move), type could be "is it" or category
     */
    public AttribValue MakeMove()
    {
        AttribValue attribValueReturn = new AttribValue("aHumanPlayer","-1");
        if(GetCurrentPlayer().IsAI())
        {
            attribValueReturn = AIMove( GetCurrentPlayer(),GetNextPlayer());
        }
        return attribValueReturn;

        // later here -> change player ...
    }


    ////////////////////////////////////////////////
    //////////////////////////////////////////////77
    // AI move:

    // used by AIMove (e.g. if difficulty is easy or invalid, ask for card index)
    private static int MAKEMOVECALLED = 0;

    /**
     * move of the ai
     * depending on difficulty
     * if invalid difficulty, get next card index, ask is it ... (card 1, card 2, card 3)
     */
    private AttribValue AIMove(PlayerInformation _curPlayer, PlayerInformation _nextPlayer)
    {
        System.out.println("Bin in AIMove");
        AttribValue attribValueReturn = new AttribValue("IsIt",Integer.toString(MAKEMOVECALLED));   // if chosen easy or invalid KI, go through index
        MAKEMOVECALLED += 1;  // start with card one, then card 2, ...    // only for simple and error (invalid difficulty)
        /*
        if(_curPlayer.GetAiDifficulty().equals("einfach"))
        {
            calculatedMove = MAKEMOVECALLED -1;
        }else */
        //if(_curPlayer.GetAiDifficulty().equals("Hellseher"))
        //{
        //    attribValueReturn = Hellseher(_curPlayer, _nextPlayer);
        //}
        if(_curPlayer.GetAiDifficulty().equals("normaler Gegner"))
        {
            System.out.println("normale KI");
            attribValueReturn = NormalEnemy(_curPlayer, _nextPlayer);
        }
        else if(_curPlayer.GetAiDifficulty().equals("schwerer Gegner"))
        {
            System.out.println("schwere KI");
            attribValueReturn = GoodEnemy(_curPlayer, _nextPlayer);
        }
        return attribValueReturn;
    }



    //normal enemy
    private AttribValue NormalEnemy(PlayerInformation _curPlayer, PlayerInformation _enemy)
    {
        AttribValue AttribValReturn = new AttribValue("IsIt","0");
        CardList _cardListRemaining = _curPlayer.cardListRemaining;
        AttributList attrList = new AttributList(_cardListRemaining);
        if(_cardListRemaining.GetSize()<=2) // only to cards left, no need for attributs -> choose first of them
        {
            return AttribValReturn; // only to players, ask for the first one
        }


        int lowestRemainingCards = 50000;
        List<IntInt> amountList = new ArrayList<IntInt>();
        for(int index=0; index < attrList.attriList.size(); index ++)
        {
            int amount = 0;
            StringsToDisplayAttributes curAttribVal = attrList.attriList.get(index);
            String debug = "verbleibende Pers: ";
            for(int cardIndex=0; cardIndex < _cardListRemaining.GetSize(); cardIndex++)
            {
                debug += _cardListRemaining.Get(cardIndex).name + " ";
                if(_cardListRemaining.Get(cardIndex).DoesCardContainAttrValue(curAttribVal.attr, curAttribVal.value))
                {
                    amount++;
                }
            }
            System.out.println(debug);
            System.out.println("attr "+curAttribVal.attr+ " value: "+curAttribVal.value + " = "+amount + " cardlist "+_cardListRemaining.GetSize());
            if(amount != _cardListRemaining.GetSize())    // else this question wouldn't matter
            {
                amountList.add(new IntInt(index, amount));
            }
        }

        if(amountList.size()>0)
        {
            Collections.sort(amountList);
            int bestIndex = (_cardListRemaining.GetSize()-1)/2;
            AttribValReturn.attr  = attrList.attriList.get(amountList.get(bestIndex).index).attr;
            AttribValReturn.value = attrList.attriList.get(amountList.get(bestIndex).index).value;
        }
        return AttribValReturn;
    }




    /**
     * Best choice for an enemy,
     * could be renamed to hard
     * @param _curPlayer needed to get cardList and possible quesions
     * @param _enemy e.g. needed for "Hellseher" for deep search
     * @return calculated move (String type, String move)
     * type could be "is it" or question
     */
    private AttribValue GoodEnemy(PlayerInformation _curPlayer, PlayerInformation _enemy)
    {
        AttribValue AttribValReturn = new AttribValue("IsIt","0");
        CardList _cardListRemaining = _curPlayer.cardListRemaining;

        int bestMoveId = 0;
        if(_cardListRemaining.GetSize()<=2) // only to cards left, no need for attributs -> choose first of them
        {
            return AttribValReturn; // only to players, ask for the first one
        }
        int targetId = _enemy.GetChosenCardId();
        AttributList attrList = new AttributList(_cardListRemaining);
        int lowestRemainingCards = 50000;
        for(int index=0; index < attrList.attriList.size(); index ++)
        {
            //System.out.println("deb: Index ist: "+index);
            CardList cardListCpy = new CardList(_cardListRemaining); // make copy, because each way has it's own attributlist
            int clcpysize = cardListCpy.GetSize();
            //System.out.println("cardListCpy Groesse: "+clcpysize+ " OrigListSize: "+_cardListRemaining.GetSize()); // wenn veraendert, dann nicht kopiert, sondern referenz
            // if enemy card has the target attribute -> remove all cards wich have not
            if (cardListCpy.Get(cardListCpy.GetIndexFromCardId(targetId)).DoesCardContainAttrValue(attrList.attriList.get(index).attr,attrList.attriList.get(index).value))
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
        //System.out.println("geschafft");

        // get Attrib and Value from list id
        AttributList attrListOrig = new AttributList(_cardListRemaining);
        AttribValReturn.attr  = attrListOrig.attriList.get(bestMoveId).attr;
        AttribValReturn.value = attrListOrig.attriList.get(bestMoveId).value;
        return AttribValReturn;
    }

//
//    // used for statistic and debugging
//    static double CheckedWays = 0;
//    /**
//     * like normal enemy, but with deep search
//     * needs a lot of time in first round
//     * not much better than hard enemy
    // * only useful if many attributes and values (deep search)
//     * @param _curPlayer
//     * @param _enemy
//     * @return calculated move (String type, String move)
//     */
//    private AttribValue Hellseher(PlayerInformation _curPlayer, PlayerInformation _enemy)
//    {
//        CardList _cardListRemaining = _curPlayer.cardListRemaining;
//        CardList cardListcpy = new CardList(_cardListRemaining);
//        AIReturn bestMove = DeepSearch(cardListcpy, 0, _enemy.GetChosenCardId());
//
//        AttributList attrListOrig = new AttributList(_cardListRemaining);
//        AttribValue AttribValReturn = new AttribValue("IsIt","0");
//        if(bestMove.attrValId != -1)
//        {
//            AttribValReturn.attr  = attrListOrig.attriList.get(bestMove.attrValId).attr;
//            AttribValReturn.value = attrListOrig.attriList.get(bestMove.attrValId).value;
//        }
//        // else use isIt instead of looking after attributes
//        System.out.println("checked ways: "+CheckedWays);
//        return AttribValReturn;
//    }
//
//
//    /**
//     * used by Hellseher, don't call it otherwise
//     * @param _cardListRemaining one card of this list is the card the enemy has chosen
//     * @param _curDeep  // current deep, needed because end game in 3 steps is better than 5 steps and to avoid overflow by calculating
//     * @param _targetCardId // if of enemy card, needed to find perfect question
//     * @return calculated move (String type, String move)
//     */
//    private AIReturn DeepSearch(CardList _cardListRemaining, int _curDeep, int _targetCardId)
//    {
//        _curDeep++;
//        AIReturn bestAIReturn = new AIReturn();
//        int remainingCards = _cardListRemaining.GetSize();
//        // if something changed:    // look at next deep
//
//        AttributList attrList = new AttributList(_cardListRemaining);
//        for(int index=0; index < attrList.attriList.size(); index ++)
//        {
//            CheckedWays +=1;
//            //System.out.println("Deep: "+_curDeep+ "  index: "+index);
//            CardList cardListCpy = new CardList(_cardListRemaining); // make copy, because each way has it's own attributlist
//            // get current index of the target card id:
//            int targetId = cardListCpy.GetIndexFromCardId(_targetCardId);
//
//            String debug = "Ueberpruefe "+attrList.attriList.get(index).attr+ "  "+attrList.attriList.get(index).value;
//
//            // if enemy card has the target attribute -> remove all cards wich have not
//            if(cardListCpy.Get(targetId).DoesCardContainAttrValue(attrList.attriList.get(index).attr,attrList.attriList.get(index).value))
//            {
//                cardListCpy.RemoveCardsWithoutAttriValue(attrList.attriList.get(index).attr,attrList.attriList.get(index).value);
//            }
//            else    // target card has not asked attrivalue -> remove all cards which have have asked attrivalue
//            {
//                cardListCpy.RemoveCardsWithAttriValue(attrList.attriList.get(index).attr, attrList.attriList.get(index).value);
//            }
//
//            int remainingCardsNew = cardListCpy.GetSize();
//            AIReturn tempReturn = new AIReturn();
//            if(remainingCards != remainingCardsNew && _curDeep < 4)
//            {
//                tempReturn = DeepSearch(cardListCpy, _curDeep, _targetCardId);
//            }
//            else
//            {
//                if(remainingCards == remainingCardsNew) // nothing changed //maybe not enough attributes
//                {
//                    tempReturn.deepestDeep = _curDeep;
//                    tempReturn.rating = 1000000;
//                    tempReturn.attrValId = -1;
//                }
//                else
//                {
//                    tempReturn.deepestDeep = _curDeep;
//                    tempReturn.rating = (_curDeep * 100) + cardListCpy.GetSize() * 15;
//                    tempReturn.attrValId = index;     // todo -> should be saved into list because it won't change -> you don't have to calculate the best move each round
//                }
//
//                // rating = remaining cards * 600, 5 cards left -> worse rating
//
//                if(_curDeep == 1 && remainingCardsNew <= 3)
//                {
//                    bestAIReturn.deepestDeep = _curDeep;
//                    bestAIReturn.rating = 5;
//                    bestAIReturn.attrValId = -1;
//                    return bestAIReturn;    // game could end now, ask for person, not for attribute
//                }
//
//            }
//
//
//                    System.out.println("Ueberpruefe "+attrList.attriList.get(index).attr+ "  "+attrList.attriList.get(index).value);
//            if(tempReturn.rating < bestAIReturn.rating)
//            {
//                bestAIReturn = tempReturn;
//                bestAIReturn.attrValId = index;     // because we want the next move, move in 4 rounds doesn't matter
//            }
//            debug += "  akt Rat: "+tempReturn.rating;
//            debug += "  bestR "+bestAIReturn.rating;
//            System.out.println("debug: "+debug);
//        }
//        System.out.println("bestAIReturn:"+bestAIReturn);
//        return bestAIReturn;
//    }

}
