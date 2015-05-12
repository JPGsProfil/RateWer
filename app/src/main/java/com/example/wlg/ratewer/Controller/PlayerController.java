package com.example.wlg.ratewer.Controller;

/**
 * Created by Jean on 21.04.2015.
 */
public class PlayerController
{
    private int ChosencardId;
    private boolean isCPU;

    public PlayerController()
    {
        ChosencardId = 1;
        isCPU = false;
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

    public void SetToCPU()
    {
        this.isCPU = true;
        // for debugging
        this.ChosencardId = 10;
    }


    public int GetCardId()
    {
        return ChosencardId;
    }

    public void SetCardId(int cardId)
    {
        this.ChosencardId = cardId;
    }


    public boolean IsCPU()
    {
        return isCPU;
    }

    public void SetIsCPU(boolean isCPU)
    {
        this.isCPU = isCPU;
    }
}
