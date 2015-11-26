package com.example.wlg.ratewer.Controller;

import com.example.wlg.ratewer.Model.PlayerInformation;

/**
 * Created by Jean on 21.04.2015.
 */
public class GameController
{

    private enum EGamePhase {SelectCards, AskQuestions };

    private int amountOfPlayers;
    private int indexOfCurrentPlayer;
    private EGamePhase gamePhase;
    private PlayerController_New [] playerControllers;
    private PlayerController_New currentPlayerController;


    public void onStart()
    {
        amountOfPlayers = 2;
        indexOfCurrentPlayer = 0;
    }

        public void onUpdate()
    {
        switch (gamePhase)
        {
            case SelectCards:break;
            case AskQuestions:break;
            default:;
        }

    }

    public void setPlayerController(PlayerController_New _playerController, int _index)
    {
        playerControllers[_index] = _playerController;
    }

    public PlayerController_New getPlayerController(int _index)
    {
        if (_index < amountOfPlayers) {
        return playerControllers[_index];
        }

        return null;
    }

    public int getIndexofCurrentPlayerController()
    {
        return indexOfCurrentPlayer ;
    }

    public int getIndexofNextPlayerController()
    {
        return (indexOfCurrentPlayer++ )% amountOfPlayers ;
    }



    private void setToNextPlayersTurn()
    {
        indexOfCurrentPlayer = indexOfCurrentPlayer++ % amountOfPlayers;
        currentPlayerController = playerControllers[indexOfCurrentPlayer];
    }

}




