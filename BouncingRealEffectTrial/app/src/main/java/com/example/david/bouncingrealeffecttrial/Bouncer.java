package com.example.david.bouncingrealeffecttrial;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by david on 5/13/15.
 */
public class Bouncer extends View
{
    private static final int MOVEMENT_SPEED_INCREMENT = 5;
    private static final int DEFAULT_MOVEMENT_SPEED = 50;
    private int movementSpeed = DEFAULT_MOVEMENT_SPEED;
    private int maxHeight;
    private Bitmap image;
    private Paint paint = new Paint();
    private int imagePosX=1, imagePosY=1, imageWidth, imageHeight;
    private int objectXMovement, objectYMovement;
    private int direction;

    public Bouncer(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setupShape();
    }

    public Bouncer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setupShape();
    }

    public Bouncer(Context context)
    {
        super(context);
        setupShape();
    }

    private void setupShape()
    {
        image = BitmapFactory.decodeResource(getResources(), R.drawable.electricsheep);
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        direction = 1;
        maxHeight = 0;
        objectXMovement = 0;
        objectYMovement = movementSpeed;

        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startAnimation();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(image, imagePosX, imagePosY, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {

    }

    private void startAnimation()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(maxHeight <= getHeight() - imageHeight + 100) {
                    //Log.d("VARIABLES : ", objectYMovement + " " + (getHeight()-imageHeight));
                    moveObject();
                    postInvalidate();
                    try {
                        Thread.sleep(100);
                    }
                    catch(Exception ex)
                    {

                    }
                }
            }
        }).start();

    }

    private void moveObject()
    {
        checkCollision();
        recalculateObjectCoordination();
    }

    private void recalculateObjectCoordination()
    {
        imagePosX += objectXMovement;
        imagePosY += objectYMovement * direction;
        objectYMovement += MOVEMENT_SPEED_INCREMENT * direction;
    }

    private void checkCollision()
    {
        if(hitTopBorder())
        {
            direction = 1;
        }
        else if(hitBottomBorder())
        {
            direction = -1;
            maxHeight += 100;

        }
    }

    private boolean hitTopBorder()
    {
        if(imagePosY <= maxHeight)
        {
            return true;
        }
        return false;
    }

    private boolean hitBottomBorder()
    {
        if(imagePosY + imageHeight >= getHeight())
        {
            return true;
        }
        return false;
    }
}
