package com.example.david.backgroundparallaxtrial;

/**
 * Created by david on 5/21/15.
 */
public class MyCircle
{
    protected float xPosition;
    protected float yPosition;
    protected float radius;
    protected int colorCode;

    public MyCircle(float xPos, float yPos, float radius, int colorCode)
    {
        this.xPosition = xPos;
        this.yPosition = yPos;
        this.radius = radius;
        this.colorCode = colorCode;
    }

    public float getXPosition()
    {
        return xPosition;
    }

    public void setxPosition(float xPosition)
    {
        this.xPosition = xPosition;
    }

    public float getYPosition()
    {
        return yPosition;
    }

    public void setyPosition(float yPosition)
    {
        this.yPosition = yPosition;
    }

    public float getRadius()
    {
        return radius;
    }

    public void setRadius(float radius)
    {
        this.radius = radius;
    }

    public int getColorCode()
    {
        return colorCode;
    }

    public void setColorCode(int colorCode)
    {
        this.colorCode = colorCode;
    }
}
