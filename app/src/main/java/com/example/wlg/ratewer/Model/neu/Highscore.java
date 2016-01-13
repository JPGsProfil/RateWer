package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 10.01.2016.
 */
public class Highscore {

    private int ID;
    private int MatchesWon;
    private int MatchesLost;
    private int User_ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMatchesWon() {
        return MatchesWon;
    }

    public void setMatchesWon(int matchesWon) {
        MatchesWon = matchesWon;
    }

    public int getMatchesLost() {
        return MatchesLost;
    }

    public void setMatchesLost(int matchesLost) {
        MatchesLost = matchesLost;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }


}
