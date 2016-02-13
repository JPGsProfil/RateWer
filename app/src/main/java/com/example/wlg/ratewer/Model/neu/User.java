package com.example.wlg.ratewer.Model.neu;

import java.util.List;

/**
 * Created by RareHue on 10.01.2016.
 */
public class User {

    private Integer id;
    private String email;
    private String name;
    private String password;
    private List<Highscore> highscores;
   /* private int matchesWon;
    private int matchesLost;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<Highscore> getHighscores()
    {
        return highscores;
    }

    public void setHighscores(List<Highscore> highscores)
    {
        this.highscores = highscores;
    }
/*
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
    }*/

    public boolean isValid()
    {
        return (this.id >= 0);
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password +
                '}';
    }

    public User(String email, String name, String password) {
        this.id = 0; // TODO:Handle this on the server */
        this.name = name;
        this.email = email;
        this.password = password;
/*
        this.matchesLost = 0;
        this.matchesLost = 0;*/

    }
}
