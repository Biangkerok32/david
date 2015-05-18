package com.example.david.shadoweffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by david on 5/18/15.
 */
public class shadow extends ImageView
{
    private float xOffset, yOffset;
    private int state;

    public shadow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        xOffset = 50.0f;
        yOffset = 50.0f;
        state = 1;
        //startAnimation();
    }

    public shadow(Context context, AttributeSet attrs) {
        super(context, attrs);
        xOffset = 50.0f;
        yOffset = 50.0f;
        state = 1;
        //startAnimation();
    }

    public shadow(Context context) {
        super(context);
        xOffset = 50.0f;
        yOffset = 50.0f;
        state = 1;
        //startAnimation();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint mShadow = new Paint();
        Drawable d = getDrawable();
        if (d != null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            {
                setLayerType(LAYER_TYPE_SOFTWARE, mShadow);
            }
            Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();

            canvas.drawBitmap(bitmap, 0, 0, null);
        } else
        {
            super.onDraw(canvas);
        }

    };

    private Paint createShadow()
    {
        Paint mShadow = new Paint();

        float radius = 5.0f;

        // color=black
        int color = Color.BLACK;
        mShadow.setColor(Color.BLACK);
        mShadow.setAntiAlias(true);
        mShadow.setShadowLayer(radius, xOffset, yOffset, color);


        return mShadow;
    }

    public Bitmap addShadow(final Bitmap bm, final int dstHeight, final int dstWidth, int color, int size, float dx, float dy) {
        final Bitmap mask = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ALPHA_8);

        final Matrix scaleToFit = new Matrix();
        final RectF src = new RectF(0, 0, bm.getWidth(), bm.getHeight());
        final RectF dst = new RectF(0, 0, dstWidth - dx, dstHeight - dy);
        scaleToFit.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

        final Matrix dropShadow = new Matrix(scaleToFit);
        dropShadow.postTranslate(dx, dy);

        final Canvas maskCanvas = new Canvas(mask);
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskCanvas.drawBitmap(bm, scaleToFit, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        maskCanvas.drawBitmap(bm, dropShadow, paint);

        final BlurMaskFilter filter = new BlurMaskFilter(size, BlurMaskFilter.Blur.NORMAL);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setMaskFilter(filter);
        paint.setFilterBitmap(true);

        final Bitmap ret = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
        final Canvas retCanvas = new Canvas(ret);
        retCanvas.drawBitmap(mask, 0,  0, paint);
        retCanvas.drawBitmap(bm, scaleToFit, null);
        mask.recycle();
        return ret;
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
                        if (yOffset <= -50.0f) {
                            state = 2;
                        }
                    } else if (state == 2) {
                        if (xOffset <= -50.0f) {
                            state = 3;
                        }
                    } else if (state == 3) {
                        if (yOffset >= 50.0f) {
                            state = 4;
                        }
                    } else if (state == 4) {
                        if (xOffset >= 50.0f) {
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
