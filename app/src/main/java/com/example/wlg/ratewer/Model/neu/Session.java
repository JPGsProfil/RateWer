package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 10.01.2016.
 */
public class Session {

    private int ID;
    private int Player1_ID;
    private int Player2_ID;
    private int Set_ID;
    private boolean HasStarted; // TODO bool
    private boolean HasEnded; //TODO bool


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPlayer1ID() {
        return Player1_ID;
    }

    public void setPlayer1ID(int player1ID) {
        Player1_ID = player1ID;
    }

    public int getPlayer2ID() {
        return Player2_ID;
    }

    public void setPlayer2ID(int player2ID) {
        Player2_ID = player2ID;
    }

    public int getSet_ID() {
        return Set_ID;
    }

    public void setSet_ID(int set_ID) {
        Set_ID = set_ID;
    }

    public boolean getHasStarted() {
        return HasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        HasStarted = hasStarted;
    }

    public boolean getHasEnded() {
        return HasEnded;
    }

    public void setHasEnded(boolean hasEnded) {
        HasEnded = hasEnded;
    }
}
