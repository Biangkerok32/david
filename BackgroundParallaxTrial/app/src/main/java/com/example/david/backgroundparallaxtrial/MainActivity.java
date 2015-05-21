package com.example.david.backgroundparallaxtrial;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements ScrollViewListener  {

    //private MyCustomScrollView mBgScrollView;
    private MyCustomScrollView mContentScrollView;
    private CustomParallaxView parallaxView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mBgScrollView = (MyCustomScrollView)findViewById(R.id.background_sv);
        mContentScrollView = (MyCustomScrollView)findViewById(R.id.content_sv);
        parallaxView = (CustomParallaxView)findViewById(R.id.parallaxView);


        mContentScrollView.setScrollViewListener(this);
    }

    @Override
    public void onScrollChanged(MyCustomScrollView scrollView, int x, int y, int oldx, int oldy)
    {
        mContentScrollView.scrollTo(0,y);
        // when the content scrollview will scroll by say 100px,
        // the background scrollview will scroll by 50px. It will
        // look like a parallax effect where the background is
        // scrolling with a different speed then the content scrollview.
        parallaxView.updateCircleList(y,oldy);
        //mBgScrollView.scrollTo(0, (int)(y / 2f));
    }
}
