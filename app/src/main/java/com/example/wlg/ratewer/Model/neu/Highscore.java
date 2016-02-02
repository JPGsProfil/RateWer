package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 10.01.2016.
 */
public class Highscore {

    private int id;
    private int matchesWon;
    private int matchesLost;
    private int user_ID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public void setMatchesWon(int matchesWon) {
        this.matchesWon = matchesWon;
    }

    public int getMatchesLost() {
        return matchesLost;
    }

    public void setMatchesLost(int matchesLost) {
        this.matchesLost = matchesLost;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    @Override
    public String toString() {
        return "Highscore{" +
                "id=" + id +
                ", matchesWon=" + matchesWon +
                ", matchesLost=" + matchesLost +
                ", user_ID=" + user_ID +
                '}';
    }
}
