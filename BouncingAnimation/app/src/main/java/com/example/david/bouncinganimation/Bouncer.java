package com.example.david.bouncinganimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Interpolator;
import android.graphics.Paint;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

/**
 * Created by david on 5/13/15.
 */
public class Bouncer extends View
{

    Bitmap bouncingImage;
    Paint paint = new Paint();
    int imagePositionX, imagePositionY, imageWidth, imageHeight;

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
        bouncingImage = BitmapFactory.decodeResource(getResources(), R.drawable.electricsheep);
        imageHeight = bouncingImage.getHeight();
        imageWidth = bouncingImage.getWidth();
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
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bouncingImage, imagePositionX, imagePositionY, paint);
    }

    private void setupShapeY(int shapePositionY)
    {
        int minY, maxY;
        minY = imagePositionY;
        maxY = imagePositionY + imageHeight;
        imagePositionY = shapePositionY;
        minY = Math.min(minY,imagePositionY);
        maxY = Math.max(maxY, imagePositionY + imageHeight);
        Log.d("Y Position ", imagePositionY + " " + getHeight());
        invalidate(imagePositionX, minY, imagePositionX + imageWidth, maxY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        imagePositionX = (w - bouncingImage.getWidth()) / 2;
        imagePositionY = 0;

    }

    private ValueAnimator getAnimator()
    {
        final ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new AccelerateInterpolator());
        //animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                //Log.d("fraction : ", animator.getAnimatedFraction() + " ");
                setupShapeY((int) (animator.getAnimatedFraction() * (getHeight() - imageHeight)));
            }
        });

        return animator;
    }

    private void startAnimation()
    {
        ValueAnimator animator = getAnimator();
        animator.start();
    }

}
