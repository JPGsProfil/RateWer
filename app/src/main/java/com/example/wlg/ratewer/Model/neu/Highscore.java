package com.example.wlg.ratewer.Model.neu;

/**
 * Created by Jean on 12.02.2016.
 */
public class Highscore
{

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Integer getMatchesWon()
    {
        return matchesWon;
    }

    public void setMatchesWon(Integer matchesWon)
    {
        this.matchesWon = matchesWon;
    }

    public Integer getMatchesLost()
    {
        return matchesLost;
    }

    public void setMatchesLost(Integer matchesLost)
    {
        this.matchesLost = matchesLost;
    }

    private Integer id;
    private User user;
    private Integer matchesWon;
    private Integer matchesLost;
}
