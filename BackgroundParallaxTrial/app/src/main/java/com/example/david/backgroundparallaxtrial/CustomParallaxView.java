package com.example.david.backgroundparallaxtrial;

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
public class CustomParallaxView extends View
{
    private final int ALPHA = 120;
    private final int INITIALIZATION_CIRCLE_NUMBER = 100;
    private final int MINIMAL_CIRCLE_RADIUS = 1;
    private final int MAXIMAL_CIRCLE_RADIUS = 50;

    private ArrayList<MyCircle> circleList;
    private Paint paint;
    private Random rand;

    public CustomParallaxView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initialize();
    }

    public CustomParallaxView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize();
    }

    public CustomParallaxView(Context context)
    {
        super(context);
        initialize();
    }

    private void initialize()
    {
        this.circleList = new ArrayList<MyCircle>();
        this.paint = new Paint();
        this.rand = new Random();
    }

    public void updateCircleList(float currentYPosition, float oldYPosition)
    {
        if(isScreenGoingDown(currentYPosition, oldYPosition))
        {
            moveCirclesDown(currentYPosition, oldYPosition);
        }
        else
        {
            moveCirclesUp(currentYPosition, oldYPosition);
        }
        invalidate();
    }

    private void moveCirclesDown(float currentYPosition, float oldYPosition)
    {
        for(int i=0 ; i<circleList.size() ; i++)
        {
            MyCircle circle = circleList.get(i);
            if(checkIfCircleOutOfBottomScreen(circle))
            {
                circleList.remove(i);
                i--;
            }
            else
            {
                circle.setyPosition(circle.getYPosition() + oldYPosition - currentYPosition);
            }
        }
        addNewCircle(rand.nextInt(getWidth()), rand.nextInt(10));
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
                circle.setyPosition(circle.getYPosition() + oldYPosition - currentYPosition);
            }
        }
        addNewCircle(rand.nextInt(getWidth()), getHeight()-rand.nextInt(10));
    }

    private void addNewCircle(int xPos, int yPos)
    {
        MyCircle newCircle = createMyCircle(xPos, yPos, rand.nextInt(MAXIMAL_CIRCLE_RADIUS)+MINIMAL_CIRCLE_RADIUS);
        this.circleList.add(newCircle);
    }

    private boolean isScreenGoingDown(float currentScreenY, float oldScreenY)
    {
        if(currentScreenY - oldScreenY <= 0)
        {
            return true;
        }
        return false;
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

    @Override
    protected void onSizeChanged (int width, int height, int oldWidth, int oldHeight)
    {
        for(int i=0 ; i<INITIALIZATION_CIRCLE_NUMBER ; i++)
        {
            MyCircle newCircle = createMyCircle(rand.nextInt(width), rand.nextInt(height), rand.nextInt(MAXIMAL_CIRCLE_RADIUS)+MINIMAL_CIRCLE_RADIUS);
            circleList.add(newCircle);
        }
    }

    private MyCircle createMyCircle(int xPosition, int yPosition, int radius)
    {
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        int clr = Color.argb(ALPHA,r,g,b);
        MyCircle newCircle = new MyCircle(xPosition, yPosition, radius, clr);

        return newCircle;
    }
}
