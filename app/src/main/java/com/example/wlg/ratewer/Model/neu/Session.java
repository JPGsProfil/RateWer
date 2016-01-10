package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 10.01.2016.
 */
public class Session {

    private int ID;
    private int Player1ID;
    private int Player2ID;
    private int Set_ID;
    private String HasStarted; // TODO bool
    private String HasEnded; //TODO bool


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPlayer1ID() {
        return Player1ID;
    }

    public void setPlayer1ID(int player1ID) {
        Player1ID = player1ID;
    }

    public int getPlayer2ID() {
        return Player2ID;
    }

    public void setPlayer2ID(int player2ID) {
        Player2ID = player2ID;
    }

    public int getSet_ID() {
        return Set_ID;
    }

    public void setSet_ID(int set_ID) {
        Set_ID = set_ID;
    }

    public String getHasStarted() {
        return HasStarted;
    }

    public void setHasStarted(String hasStarted) {
        HasStarted = hasStarted;
    }

    public String getHasEnded() {
        return HasEnded;
    }

    public void setHasEnded(String hasEnded) {
        HasEnded = hasEnded;
    }
}
