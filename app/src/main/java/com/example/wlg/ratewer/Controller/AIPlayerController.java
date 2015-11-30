package com.example.wlg.ratewer.Controller;

import com.example.wlg.ratewer.Model.AttribValue;
import com.example.wlg.ratewer.Model.AttributList;
import com.example.wlg.ratewer.Model.AttributeContainer_New;
import com.example.wlg.ratewer.Model.Attribute_New;
import com.example.wlg.ratewer.Model.CardList;
import com.example.wlg.ratewer.Model.IntInt;
import com.example.wlg.ratewer.Model.PlayerInformation;
import com.example.wlg.ratewer.Model.PlayerInformation_New;
import com.example.wlg.ratewer.Model.Question_New;
import com.example.wlg.ratewer.Model.StringsToDisplayAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by RareHue on 30.11.2015.
 */
public class AIPlayerController extends PlayerControllerAbstract_New
{


    public Question_New getNextQuestion()
    {
        return calculateNextQuestion();
    }

    public  Question_New calculateNextQuestion()
    {
        Question_New result;

        switch (getPlayerInformation().getAiDifficulty()){
            case EASY:      result = easyQuestion(); break;
            case NORMAL:    result = normalQuestion();break;
            case HARD:      result = hardQuestion();break;
            case VERYHARD:  result = veryhardQuestion();break;
            default:        result = easyQuestion();
        }

        return  result;
    }

    private Question_New easyQuestion()
    {
        return null;
    }

    private Question_New normalQuestion()
    {

        getPlayerInformation().refreshAttributes();

        //TODO just ajusting
/*
        if(getPlayerInformation().getAmountOfCardsRemaining() <= 2) // only to cards left, no need for attributs -> choose first of them
        {
            return getPlayerInformation().getCardsRemaining(); // only to players, ask for the first one
        }


        int lowestRemainingCards = 50000;
        List<IntInt> amountList = new ArrayList<IntInt>();
        for(int index=0; index < attrList.attributList.size(); index ++)
        {
            int amount = 0;
            StringsToDisplayAttributes curAttribVal = attrList.attributList.get(index);
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
            AttribValReturn.attr  = attrList.attributList.get(amountList.get(bestIndex).index).attr;
            AttribValReturn.value = attrList.attributList.get(amountList.get(bestIndex).index).value;
        }
        return AttribValReturn;

*/
        return null;
    }

    private Question_New hardQuestion()
    {
        return null;
    }

    private Question_New veryhardQuestion()
    {
        return null;
    }
}
