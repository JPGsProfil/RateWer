package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 10.01.2016.
 */
public class User {

    private int id;
    private String email;
    private String name;
    private String password;
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
        this.email = email;
        this.name = name;
        this.password = password;
/*
        this.matchesLost = 0;
        this.matchesLost = 0;*/
        this.id = 17; // TODO:Handle this on the server
    }
}
