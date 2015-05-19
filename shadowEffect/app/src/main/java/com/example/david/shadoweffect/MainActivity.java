package com.example.david.shadoweffect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                int degree = 0;
                final ImageView iv = (ImageView)findViewById(R.id.imageShadow);

                final Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.erreur);
                //final Bitmap shadow = addShadow2(src, src.getHeight(), src.getWidth(), Color.GRAY, 2, 50, 50);
                //iv.setImageBitmap(shadow);

                while(true)
                {
                    degree = (degree+4)%360;
                    int x = (int)(25*Math.cos(Math.toRadians(degree)));
                    int y = (int)(25*Math.sin(Math.toRadians(degree)));
                    final Bitmap shadow = addShadow2(src, src.getHeight(), src.getWidth(), Color.GRAY, 2, x, y);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(shadow);
                        }
                    });


                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (Exception ex)
                    {

                    }
                }

            }
        }).start();
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Bitmap addShadow(final Bitmap bm, final int dstHeight, final int dstWidth, int color, int size, float dx, float dy) {
        final int estimatedShadowHeight = dstHeight*2;
        final int estimatedShadowWidth = dstWidth*2;
        final Bitmap mask = Bitmap.createBitmap(estimatedShadowWidth, estimatedShadowHeight, Bitmap.Config.ALPHA_8);

        final Matrix scaleToFit = new Matrix();

        final RectF src = new RectF(0, 0, bm.getWidth(), bm.getHeight());
        final RectF dst = new RectF(0, 0, dstWidth - dx, dstHeight - dy);
        scaleToFit.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

        final Matrix dropShadow = new Matrix(scaleToFit);
        dropShadow.postTranslate(dx, dy);

        //setting mask
        final Canvas maskCanvas = new Canvas(mask);
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        maskCanvas.drawBitmap(bm, dropShadow, paint);

        //set blur to mask
        final BlurMaskFilter filter = new BlurMaskFilter(size, BlurMaskFilter.Blur.NORMAL);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setMaskFilter(filter);
        paint.setFilterBitmap(true);

        final Bitmap ret = Bitmap.createBitmap(estimatedShadowWidth, estimatedShadowHeight, Bitmap.Config.ARGB_8888);
        final Canvas retCanvas = new Canvas(ret);

        retCanvas.drawBitmap(mask, dx +dstWidth/2, dy + dstHeight/2, paint);

        mask.recycle();
        return ret;
    }

    public Bitmap addShadow2(final Bitmap bm, final int dstHeight, final int dstWidth, int color, int size, float dx, float dy) {
        final Bitmap mask = Bitmap.createBitmap(dstWidth*2, dstHeight*2, Bitmap.Config.ALPHA_8);

        final Matrix scaleToFit = new Matrix();
        final RectF src = new RectF(0, 0, bm.getWidth(), bm.getHeight());
        final RectF dst = new RectF(0, 0, dstWidth - dx, dstHeight - dy);
        scaleToFit.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

        final Matrix dropShadow = new Matrix(scaleToFit);
        dropShadow.postTranslate(dx, dy);

        final Canvas maskCanvas = new Canvas(mask);
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskCanvas.drawBitmap(bm, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        maskCanvas.drawBitmap(bm, dx, dy, paint);

        final BlurMaskFilter filter = new BlurMaskFilter(size, BlurMaskFilter.Blur.NORMAL);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setMaskFilter(filter);
        paint.setFilterBitmap(true);

        final Bitmap ret = Bitmap.createBitmap(dstWidth*2, dstHeight*2, Bitmap.Config.ARGB_8888);
        final Canvas retCanvas = new Canvas(ret);
        //retCanvas.translate(-dx,-dy);
        retCanvas.drawBitmap(mask, dstWidth/2+50,  dstHeight/2+50, paint);
        retCanvas.drawBitmap(bm, dstWidth/2+50, dstHeight/2+50, null);
        mask.recycle();
        return ret;
    }

}
