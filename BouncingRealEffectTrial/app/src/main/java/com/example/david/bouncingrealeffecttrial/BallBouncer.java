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
    private double currentXPosition;
    private double currentYPosition;
    private Paint paint = new Paint();
    private boolean canContinue;

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
        this.currentXPosition = DEFAULT_WIDTH;
        this.currentYPosition = DEFAULT_HEIGHT;
        this.currentXVelocity = INITIAL_X_VELOCITY;
        this.currentYVelocity = INITIAL_Y_VELOCITY;
        canContinue = true;

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
            while(canContinue)
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
            }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawCircle((int)currentXPosition,(int)currentYPosition,DEFAULT_HEIGHT,paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {

    }

    private void moveObject()
    {
        double v2 = (ACCELERATION * 1) + currentYVelocity;
        currentYVelocity = v2;
        double oldXPosition = currentXPosition;
        this.currentXPosition += currentXVelocity;
        this.currentYPosition += currentYVelocity;

        int maxY = getHeight() - (DEFAULT_HEIGHT);
        int maxX = getWidth() - (DEFAULT_WIDTH);
        int minX = 0;

        if(currentYPosition > maxY)
        {
            setupYPositionAndVelocity(maxY, (-RESITUTION_COEFISION * currentYVelocity));
        }
        else if(currentYPosition < 0)
        {
            setupYPositionAndVelocity(0, (-RESITUTION_COEFISION * currentYVelocity));
        }

        if(currentXPosition > maxX)
        {
            setupXPositionAndVelocity(maxX, (-RESITUTION_COEFISION * currentXVelocity));
        }
        else if(currentXPosition < minX)
        {
            setupXPositionAndVelocity(minX, (-RESITUTION_COEFISION * currentXVelocity));
        }

        if(isBallRollingOnTheFloor(maxY))
        {
            currentXVelocity = FRICTION_COEFISION * currentXVelocity;
        }

        canContinue = canAnimationContinue(oldXPosition,currentXPosition);
    }

    private void setupYPositionAndVelocity(double newYPosition, double newYVelocity)
    {
        currentYPosition = newYPosition;
        currentYVelocity = newYVelocity;
    }

    private void setupXPositionAndVelocity(double newXPosition, double newXVelocity)
    {
        currentXPosition = newXPosition;
        currentXVelocity = newXVelocity;
    }

    private boolean isBallRollingOnTheFloor(int floorYPosition)
    {
        if(currentYPosition == floorYPosition)
        {
            return true;
        }
        return  false;
    }

    private boolean canAnimationContinue(double oldObjectXPosition, double currentObjectXPosition)
    {
        if(oldObjectXPosition == currentObjectXPosition)
        {
            return false;
        }
        return true;
    }

}
