package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 10.01.2016.
 */
public class Highscore {

    private int ID;
    private String MatchesWon;
    private String MatchesLost;
    private int User_ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMatchesWon() {
        return MatchesWon;
    }

    public void setMatchesWon(String matchesWon) {
        MatchesWon = matchesWon;
    }

    public String getMatchesLost() {
        return MatchesLost;
    }

    public void setMatchesLost(String matchesLost) {
        MatchesLost = matchesLost;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }
}
