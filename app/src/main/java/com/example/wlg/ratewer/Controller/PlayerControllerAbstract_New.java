package com.example.wlg.ratewer.Controller;

/**
 * Created by RareHue on 26.11.2015.
 * handles one Players access to its PlayerInformation  (access)
 * more than 2 players possible in future
 */

import com.example.wlg.ratewer.Model.to_erase.PlayerInformation_New;
import com.example.wlg.ratewer.Model.to_erase.Question_New;


public abstract  class PlayerControllerAbstract_New {

   private PlayerInformation_New playerInformation;

   public void setPlayerInformation(PlayerInformation_New _playerInformation)
   {
       playerInformation = _playerInformation;
   }

    public PlayerInformation_New getPlayerInformation() {
        return playerInformation;
    }

    public abstract Question_New getNextQuestion();


}

