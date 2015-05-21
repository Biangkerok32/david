package com.example.david.backgroundparallaxtrial;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by david on 5/21/15.
 */
public class MyCustomScrollView extends ScrollView
{

    private ScrollViewListener scrollViewListener = null;

    public MyCustomScrollView(Context context)
    {
        super(context);
    }

    public MyCustomScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public MyCustomScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener)
    {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy)
    {
        super.onScrollChanged(x, y, oldx, oldy);
        if(scrollViewListener != null)
        {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
