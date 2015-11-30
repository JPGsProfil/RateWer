package com.example.wlg.ratewer.Controller;

import com.example.wlg.ratewer.Model.AttribValue;
import com.example.wlg.ratewer.Model.AttributList;
import com.example.wlg.ratewer.Model.CardList;
import com.example.wlg.ratewer.Model.PlayerInformation;
import com.example.wlg.ratewer.Model.PlayerInformation_New;
import com.example.wlg.ratewer.Model.Question_New;

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
