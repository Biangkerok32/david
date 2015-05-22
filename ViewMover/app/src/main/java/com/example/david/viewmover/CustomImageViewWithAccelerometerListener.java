package com.example.david.viewmover;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by david on 5/22/15.
 */
public class CustomImageViewWithAccelerometerListener extends ImageView implements SensorEventListener
{
    private int MOVEMENT_RATIO = 50;
    private float movementSpeed;


    public CustomImageViewWithAccelerometerListener(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setUpMovementSpeed();
    }

    public CustomImageViewWithAccelerometerListener(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setUpMovementSpeed();
    }

    public CustomImageViewWithAccelerometerListener(Context context)
    {
        super(context);
        setUpMovementSpeed();
    }

    private void setUpMovementSpeed()
    {
        Random rand = new Random();
        movementSpeed = (float)(rand.nextInt(MOVEMENT_RATIO) + 1) / (float)MOVEMENT_RATIO * 10;
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            if(chekIfObjectNotHitBound(x*movementSpeed, y*movementSpeed))
            {
                setX(getX() - x * movementSpeed);
                setY(getY() + y * movementSpeed);
            }
        }
    }

    private boolean chekIfObjectNotHitBound(float nextXPosition, float nextYPosition)
    {
        View parentView = (View)getParent();
        if(getX() - nextXPosition < 0 || getY() + nextYPosition < 0 )
        {
            return false;
        }
        else if(getX() - nextXPosition + getWidth() > parentView.getWidth() || getY() + nextYPosition + getHeight() > parentView.getHeight())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
