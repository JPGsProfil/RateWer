package com.example.wlg.ratewer.Controller;

/**
 * Created by Jean on 21.04.2015.
 */
public class GameController_New
{

    private enum EGamePhase {SelectCards, AskQuestions };

    private int amountOfPlayers;
    private int indexOfCurrentPlayer;
    private EGamePhase gamePhase;
    private PlayerControllerAbstract_New[] playerControllers;
    private PlayerControllerAbstract_New currentPlayerController;


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

    public void setPlayerController(PlayerControllerAbstract_New _playerController, int _index)
    {
        playerControllers[_index] = _playerController;
    }

    public PlayerControllerAbstract_New getPlayerController(int _index)
    {
        if (_index < amountOfPlayers) {
        return playerControllers[_index];
        }

        return null;
    }

    public int getIndexOfCurrentPlayerController()
    {
        return indexOfCurrentPlayer ;
    }

    public int getIndexOfNextPlayerController()
    {
        return (indexOfCurrentPlayer++ )% amountOfPlayers ;
    }



    private void setToNextPlayersTurn()
    {
        indexOfCurrentPlayer = indexOfCurrentPlayer++ % amountOfPlayers;
        currentPlayerController = playerControllers[indexOfCurrentPlayer];
    }

}




