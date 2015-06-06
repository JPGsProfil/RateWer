package com.example.wlg.ratewer.Model;

/**
 * needed for deep search algorithm
 * attrValId is the question id
 * rating is needed to find out best move (in deep)
 * deepestDeep is needed by "Hellseher" algorithm
 */
public class AIReturn
{
    public int attrValId;
    public int rating;
    public int deepestDeep;
    public AIReturn()
    {
        attrValId = 0;
        rating = 500000;    // smaller is better
    }
}
