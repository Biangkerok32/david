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
    private final int CIRCLES_NUMBER = 300;
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

    public void updateCircleList(float currentYPosition, float currentXPosition)
    {
        moveCircles(currentYPosition,currentXPosition);
    }

    private void moveCircles(float currentYPosition, float currentXPosition)
    {
        for(int i=0 ; i<circleList.size() ; i++)
        {
            MyCircle circle = circleList.get(i);
            if(checkIfCircleNotHitScreen(circle, currentXPosition, currentYPosition))
            {
                circle.setyPosition(circle.getYPosition() + currentYPosition / circle.getDecelerationRatio());
                circle.setxPosition(circle.getXPosition() - currentXPosition / circle.getDecelerationRatio());
            }

        }

        invalidate();
    }

    private boolean checkIfCircleNotHitScreen(MyCircle circle, float currentXPos, float currentYPos)
    {
        if(circle.getYPosition()+currentYPos < 0 || circle.getXPosition()-currentXPos < 0)
        {
            return false;
        }
        else if(circle.getYPosition()+currentYPos > getHeight() || circle.getXPosition()-currentXPos > getWidth())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    protected void onSizeChanged (int width, int height, int oldWidth, int oldHeight)
    {
        for(int i=0 ; i<CIRCLES_NUMBER ; i++)
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
        int clr = Color.argb(ALPHA, r, g, b);
        int speed = rand.nextInt(50)+1;
        MyCircle newCircle = new MyCircle(xPosition, yPosition, radius, clr, speed);

        return newCircle;
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
