package com.example.wlg.ratewer.Controller;

/**
 * Created by Jean on 21.04.2015.
 */
public class GameManagerController
{

    void game()
    {
        PlayerController player1 = new PlayerController();
        PlayerController player2 = new PlayerController();
        PlayerController currentPlayer = player1;
        player2.SetToCPU();

        boolean isGameOver = false;
        while(isGameOver == false)
        {

            MakeMove();



            if(currentPlayer == player1)
            {
                currentPlayer = player2;
            }
            else
            {
                currentPlayer = player1;
            }
        }
    }


    public boolean MakeMove()
    {
        boolean wasSucc = false;
        // do something

        return wasSucc;
    }





}
