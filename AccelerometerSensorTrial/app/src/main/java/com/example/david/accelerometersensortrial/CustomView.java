package com.example.david.accelerometersensortrial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by david on 5/21/15.
 */
public class CustomView extends View
{
    private final int ALPHA = 120;
    private final int MINIMAL_CIRCLE_RADIUS = 1;
    private final int MAXIMAL_CIRCLE_RADIUS = 50;
    private ArrayList<MyCircle> circleList;
    private Paint paint;
    private Random rand;

    public CustomView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initialize();
    }

    public CustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize();
    }

    public CustomView(Context context)
    {
        super(context);
        initialize();
    }

    private void initialize()
    {
        this.circleList = new ArrayList<MyCircle>();
        this.paint = new Paint();
        rand = new Random();
    }

    public void updateCircleList(float currentYPosition, float oldYPosition, float currentXPosition, float oldXPosition)
    {
        /*
        if(isScreenGoingDown(currentYPosition, oldYPosition))
        {
            moveCirclesDown(currentYPosition, oldYPosition);
            addNewCircle(rand.nextInt(getWidth()), getHeight()-rand.nextInt(10));
        }
        else if(isScreenGoingUp(currentYPosition, oldYPosition))
        {
            moveCirclesUp(currentYPosition, oldYPosition);
            addNewCircle(rand.nextInt(getWidth()), rand.nextInt(10));
        }
        else if(isScreenGoingRight(currentXPosition, oldXPosition))
        {
            moveCirclesLeft(currentXPosition,oldXPosition);
            addNewCircle(getWidth() - rand.nextInt(10), rand.nextInt(getHeight()));
        }
        else if(isScreenGoingLeft(currentXPosition, oldXPosition))
        {
            moveCirclesRight(currentXPosition,oldXPosition);
            addNewCircle(rand.nextInt(10), rand.nextInt(getHeight()));
        }
        */
        moveCircles(currentYPosition,oldYPosition,currentXPosition,oldXPosition);
    }

    private void moveCircles(float currentYPosition, float oldYPosition, float currentXPosition, float oldXPosition)
    {
        for(int i=0 ; i<circleList.size() ; i++)
        {
            MyCircle circle = circleList.get(i);
            if(circle.getYPosition()+circle.getRadius() < 0 || circle.getXPosition()+circle.getRadius() < 0)
            {
                float newY = circle.getXPosition();
                float newX = circle.getXPosition();
                if(newX < 0)
                {
                    newX = 1;
                }

                if(newY < 0)
                {
                    newY = 1;
                }
                addNewCircle(getWidth() - (int)newX + rand.nextInt(40)-20, getHeight () - (int)newY + rand.nextInt(40)-20);
                circleList.remove(i);
                i--;
            }
            else if(circle.getYPosition()-circle.getRadius() > getHeight() || circle.getXPosition()-circle.getRadius() > getWidth())
            {
                float newY = circle.getXPosition();
                float newX = circle.getXPosition();
                if(newX > getHeight())
                {
                    newX = getHeight() - 1;
                }

                if(newY > getWidth())
                {
                    newY = getWidth() - 1;
                }
                addNewCircle(getWidth() - (int)newX + rand.nextInt(40)-20, getHeight() - (int)newY + rand.nextInt(40)-20);

                circleList.remove(i);
                i--;
            }
            else
            {
                circle.setyPosition(circle.getYPosition() + currentYPosition / 2);
                circle.setxPosition(circle.getXPosition() - currentXPosition / 2);
            }
        }

        invalidate();
        //addNewCircle(rand.nextInt(getWidth() - ((int) (getWidth() * currentXPosition))), rand.nextInt(getHeight() - (int) (getHeight() * currentYPosition)));
    }


    private boolean isScreenGoingDown(float currentScreenY, float oldScreenY)
    {
        if(currentScreenY - oldScreenY < 0)
        {
            return true;
        }
        return false;
    }

    private boolean isScreenGoingUp(float currentScreenY, float oldScreenY)
    {
        if(currentScreenY - oldScreenY > 0)
        {
            return true;
        }
        return false;
    }

    private boolean isScreenGoingRight(float currentScreenX, float oldScreenX)
    {
        if(currentScreenX - oldScreenX > 0)
        {
            return true;
        }
        return false;
    }
    private boolean isScreenGoingLeft(float currentScreenX, float oldScreenX)
    {
        if(currentScreenX - oldScreenX < 0)
        {
            return true;
        }
        return false;
    }

    private void moveCirclesDown(float currentYPosition, float oldYPosition)
    {
        for(int i=0 ; i<circleList.size() ; i++)
        {
            MyCircle circle = circleList.get(i);
            if (checkIfCircleOutOfBottomScreen(circle)) {
                circleList.remove(i);
                i--;
            } else {
                circle.setyPosition((circle.getYPosition() - oldYPosition - currentYPosition));
            }
        }
    }

    private void moveCirclesUp(float currentYPosition, float oldYPosition)
    {
        for(int i=0 ; i<circleList.size() ; i++)
        {
            MyCircle circle = circleList.get(i);
            if(checkIfCircleOutOfTopScreen(circle))
            {
                this.circleList.remove(i);
                i--;
            }
            else
            {
                circle.setyPosition((circle.getYPosition() - oldYPosition - currentYPosition));
            }
        }
    }

    private void moveCirclesRight(float currentXPosition, float oldXPosition)
    {
        for(int i=0 ; i<circleList.size() ; i++)
        {
            MyCircle circle = circleList.get(i);
            if(checkIfCircleOutOfRightScreen(circle))
            {
                this.circleList.remove(i);
                i--;
            }
            else
            {
                circle.setxPosition((circle.getXPosition() - oldXPosition - currentXPosition));
            }
        }
    }

    private void moveCirclesLeft(float currentXPosition, float oldXPosition)
    {
        for(int i=0 ; i<circleList.size() ; i++)
        {
            MyCircle circle = circleList.get(i);
            if(checkIfCircleOutOfLeftScreen(circle))
            {
                this.circleList.remove(i);
                i--;
            }
            else
            {
                circle.setxPosition(circle.getXPosition() - oldXPosition - currentXPosition);
            }
        }
    }

    private boolean checkIfCircleOutOfBottomScreen(MyCircle circle)
    {
        if(circle.getYPosition() - circle.getRadius() > getHeight())
        {
            return true;
        }
        return false;
    }

    private boolean checkIfCircleOutOfTopScreen(MyCircle circle)
    {
        if(circle.getYPosition() + circle.getRadius() < 0)
        {
            return true;
        }
        return false;
    }

    private boolean checkIfCircleOutOfRightScreen(MyCircle circle)
    {
        if(circle.getXPosition() - circle.getRadius() > getWidth())
        {
            return true;
        }
        return false;
    }

    private boolean checkIfCircleOutOfLeftScreen(MyCircle circle)
    {
        if(circle.getXPosition() - circle.getRadius() < 0)
        {
            return true;
        }
        return false;
    }

    private void addNewCircle(int xPos, int yPos)
    {
        MyCircle newCircle = createMyCircle(xPos, yPos, rand.nextInt(MAXIMAL_CIRCLE_RADIUS)+MINIMAL_CIRCLE_RADIUS);
        this.circleList.add(newCircle);
    }

    private MyCircle createMyCircle(int xPosition, int yPosition, int radius)
    {
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        int clr = Color.argb(ALPHA, r, g, b);
        MyCircle newCircle = new MyCircle(xPosition, yPosition, radius, clr);

        return newCircle;
    }

    @Override
    protected void onSizeChanged (int width, int height, int oldWidth, int oldHeight)
    {
        for(int i=0 ; i<100 ; i++)
        {
            MyCircle newCircle = createMyCircle(rand.nextInt(width), rand.nextInt(height), rand.nextInt(MAXIMAL_CIRCLE_RADIUS)+MINIMAL_CIRCLE_RADIUS);
            circleList.add(newCircle);
        }

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        for(int i=0 ; i<circleList.size() ; i++)
        {
            MyCircle circle = circleList.get(i);
            paint.setColor(circle.getColorCode());
            canvas.drawCircle(circle.getXPosition(), circle.getYPosition(), circle.getRadius(), paint);
        }
    };
}
