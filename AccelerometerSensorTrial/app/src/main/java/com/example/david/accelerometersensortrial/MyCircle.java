package com.example.david.accelerometersensortrial;

/**
 * Created by david on 5/21/15.
 */
public class MyCircle
{
    protected float xPosition;
    protected float yPosition;
    protected float radius;
    protected int colorCode;
    protected int decelerationRatio;

    public MyCircle(float xPos, float yPos, float radius, int colorCode, int decelerationRatio)
    {
        this.xPosition = xPos;
        this.yPosition = yPos;
        this.radius = radius;
        this.colorCode = colorCode;
        this.decelerationRatio = decelerationRatio;

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

    public int getDecelerationRatio()
    {
        return decelerationRatio;
    }

    public void setDecelerationRatio(int decelerationRatio)
    {
        this.decelerationRatio = decelerationRatio;
    }
}
