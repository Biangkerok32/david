package com.example.david.backgroundparallaxtrial;

/**
 * Created by david on 5/21/15.
 */
public class MyRect
{
    protected int topPosition;
    protected int leftPositon;
    protected int rectWidth;
    protected int rectHeight;

    public MyRect(int topPosition, int leftPositon, int rectHeight, int rectWidth)
    {
        this.topPosition = topPosition;
        this.leftPositon = leftPositon;
        this.rectHeight = rectHeight;
        this.rectWidth = rectWidth;
    }

    public int getTopPosition()
    {
        return topPosition;
    }

    public void setTopPosition(int topPosition)
    {
        this.topPosition = topPosition;
    }

    public int getLeftPositon()
    {
        return leftPositon;
    }

    public void setLeftPositon(int leftPositon)
    {
        this.leftPositon = leftPositon;
    }

    public int getRectWidth()
    {
        return rectWidth;
    }

    public void setRectWidth(int rectWidth)
    {
        this.rectWidth = rectWidth;
    }

    public int getRectHeight()
    {
        return rectHeight;
    }

    public void setRectHeight(int rectHeight)
    {
        this.rectHeight = rectHeight;
    }
}
