package com.example.wlg.ratewer.Model;

/**
 * used for normal ai
 */
public class IntInt implements Comparable<IntInt>
{
    public int index;
    public int value;
    public IntInt(int _index, int _value)
    {
        index = _index;
        value = _value;
    }

    @Override
    public int compareTo(IntInt _attr2)
    {
        return Double.compare(this.value, _attr2.value);
    }
}
