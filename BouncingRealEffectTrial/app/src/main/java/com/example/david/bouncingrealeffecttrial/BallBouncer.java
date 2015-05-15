package com.example.david.bouncingrealeffecttrial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by david on 5/15/15.
 */
public class BallBouncer extends View
{
    private static final double ACCELERATION = 1.1;
    private static final int INITIAL_X_VELOCITY = 10;
    private static final int INITIAL_Y_VELOCITY = 5;
    private static final double RESITUTION_COEFISION = 0.8;
    private static final double FRICTION_COEFISION = 0.9;
    private static final int DEFAULT_WIDTH = 15;
    private static final int DEFAULT_HEIGHT = 15;
    private double currentXVelocity;
    private double currentYVelocity;
    private int currentXPosition;
    private int currentYPosition;
    private Paint paint = new Paint();

    public BallBouncer(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setupBall();
    }

    public BallBouncer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setupBall();
    }

    public BallBouncer(Context context)
    {
        super(context);
        setupBall();
    }

    private void setupBall()
    {
        this.currentXPosition = 0;
        this.currentYPosition = 0;
        this.currentXVelocity = INITIAL_X_VELOCITY;
        this.currentYVelocity = INITIAL_Y_VELOCITY;

        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startAnimation();
            }
        });
    }

    private void startAnimation()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                int counter = 300;
                while(counter > 0)
                {
                    moveObject();
                    postInvalidate();

                    try
                    {
                        Thread.sleep(10);
                    }
                    catch(Exception ex)
                    {

                    }

                    counter --;
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawCircle(currentXPosition,currentYPosition,DEFAULT_HEIGHT,paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {

    }

    private void moveObject()
    {
        double v2 = (ACCELERATION * 1) + currentYVelocity;
        currentYVelocity = v2;

        this.currentXPosition += currentXVelocity;
        this.currentYPosition += currentYVelocity;

        int maxY = getHeight() - (DEFAULT_HEIGHT/2);
        int maxX = getWidth() - (DEFAULT_WIDTH/2);
        int minX = 0 + DEFAULT_WIDTH/2;

        if(currentYPosition > maxY)
        {
            currentYPosition = maxY;
            currentYVelocity = (-RESITUTION_COEFISION * currentYVelocity);
        }
        else if(currentYPosition < 0)
        {
            currentYPosition = 0;
            currentYVelocity = (-RESITUTION_COEFISION * currentYVelocity);
        }

        if(currentXPosition > maxX)
        {
            currentXPosition = maxX;
            currentXVelocity = (-RESITUTION_COEFISION * currentXVelocity);
        }
        else if(currentXPosition < minX)
        {
            currentXPosition = minX;
            currentXVelocity = (-RESITUTION_COEFISION * currentXVelocity);
        }

        if(currentYPosition == maxY)
        {
            currentXPosition = (int)FRICTION_COEFISION * currentXPosition;
        }
    }

}
