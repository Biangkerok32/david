package com.example.david.shadoweffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by david on 5/18/15.
 */
public class shadowContainer extends RelativeLayout
{
    private final float defaultXOffset = 24.0f;
    private final float defaultYOffset = 24.0f;
    private float xOffset = defaultXOffset, yOffset = defaultYOffset;
    private int state = 1;

    public shadowContainer(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setupTest();
    }

    public shadowContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setupTest();
    }

    public shadowContainer(Context context)
    {
        super(context);
        setupTest();
    }

    private void setupTest()
    {
        setWillNotDraw(false);
        startAnimation();
        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                invalidate();
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint mShadow = createShadow();
        //Paint paint = new Paint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            setLayerType(LAYER_TYPE_SOFTWARE, mShadow);
        }

        ImageView imgvw = (ImageView)getChildAt(0);
        Bitmap bmp = ((BitmapDrawable)(imgvw.getDrawable())).getBitmap();

        canvas.drawBitmap(bmp,(int)imgvw.getX(), (int)imgvw.getY(),mShadow);

        //Rect rect = new Rect((int)imgvw.getX(), (int)imgvw.getY(), bmp.getWidth()+(int)imgvw.getX(), bmp.getHeight()+(int)imgvw.getY());
        //canvas.drawRect(rect, mShadow);

    };

    private Paint createShadow()
    {
        Paint mShadow = new Paint();

        float radius = 5.0f;

        // color=black
        int color = Color.BLACK;
        mShadow.setAntiAlias(true);
        mShadow.setShadowLayer(radius, xOffset, yOffset, color);
        //mShadow.setStyle(Paint.Style.STROKE);

        return mShadow;
    }

    private void startAnimation()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true) {
                    if (state == 1) {
                        yOffset -= 2.0f;
                    } else if (state == 2) {
                        xOffset -= 2.0f;
                    } else if (state == 3) {
                        yOffset += 2.0f;
                    } else {
                        xOffset += 2.0f;
                    }

                    postInvalidate();

                    if (state == 1) {
                        if (yOffset <= -defaultYOffset) {
                            state = 2;
                        }
                    } else if (state == 2) {
                        if (xOffset <= -defaultXOffset) {
                            state = 3;
                        }
                    } else if (state == 3) {
                        if (yOffset >= defaultYOffset) {
                            state = 4;
                        }
                    } else if (state == 4) {
                        if (xOffset >= defaultXOffset) {
                            state = 1;
                        }
                    }

                    try {
                        Thread.sleep(100);
                    } catch (Exception ex) {

                    }
                }
            }
        }).start();
    }

}
