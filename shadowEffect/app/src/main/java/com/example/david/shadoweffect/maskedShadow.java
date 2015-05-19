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
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by david on 5/19/15.
 */
public class maskedShadow extends ImageView
{
    private Bitmap mainImage = null;
    private Bitmap imageShadow = null;
    private int dx,dy;

    public maskedShadow(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        startAnimation();
    }

    public maskedShadow(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        startAnimation();
    }

    public maskedShadow(Context context)
    {
        super(context);
        startAnimation();
    }

    private void startAnimation()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                int degree = 0;
                while(true)
                {
                    degree = (degree + 4)%360;
                    dx = (int)(25*Math.cos(Math.toRadians(degree)));
                    dy = (int)(25*Math.sin(Math.toRadians(degree)));

                    postInvalidate();

                    try
                    {
                        Thread.sleep(100);
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
        Paint paint = new Paint();

        if(mainImage == null)
        {
            mainImage = ((BitmapDrawable)getDrawable()).getBitmap();
        }

        if(imageShadow == null)
        {
            imageShadow = getShadow(mainImage,mainImage.getHeight(), mainImage.getWidth(), Color.GRAY, 3);
        }


        canvas.drawBitmap(imageShadow, canvas.getWidth()/2-imageShadow.getWidth()/2+dx, canvas.getHeight()/2-imageShadow.getHeight()/2+dy, paint);
        //canvas.drawBitmap(mainImage, canvas.getWidth()/2-mainImage.getWidth()/2, canvas.getHeight()/2-mainImage.getHeight()/2, paint);
    };

    public Bitmap getShadow(final Bitmap bm, final int dstHeight, final int dstWidth, int color, int size) {
        final Bitmap mask = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ALPHA_8);

        final Matrix scaleToFit = new Matrix();
        final RectF src = new RectF(0, 0, bm.getWidth(), bm.getHeight());
        final RectF dst = new RectF(0, 0, dstWidth, dstHeight);
        scaleToFit.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

        final Matrix dropShadow = new Matrix(scaleToFit);

        final Canvas maskCanvas = new Canvas(mask);
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //maskCanvas.drawBitmap(bm, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        maskCanvas.drawBitmap(bm, 0, 0, paint);

        final BlurMaskFilter filter = new BlurMaskFilter(size, BlurMaskFilter.Blur.NORMAL);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setMaskFilter(filter);
        paint.setFilterBitmap(true);

        final Bitmap ret = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
        final Canvas retCanvas = new Canvas(ret);

        //retCanvas.drawCircle(0,0,50,paint);
        retCanvas.drawBitmap(mask, 0,  0, paint);

        mask.recycle();
        return ret;
    }
}
