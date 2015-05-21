package com.example.david.listviewtrial;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends Activity {

    private ListView listView;
    private List<ListViewRowItem> itemList;
    private ListViewRowAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Random rand = new Random();
        itemList = new ArrayList<ListViewRowItem>();
        for(int i=0 ; i<20 ; i++)
        {
            int randomValue = rand.nextInt(2);
            ListViewRowItem item = new ListViewRowItem();
            if(0 == randomValue)
            {
                item.setValues("first");
                item.setIndex(i+1);
                itemList.add(item);
            }
            else
            {
                item.setValues("second");
                item.setIndex(i+1);
                itemList.add(item);
            }
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                refreshContent();
            }
        });

        final ListViewRowAdapter adapter = new ListViewRowAdapter(getBaseContext(), itemList);
        listView = (ListView)findViewById(R.id.mainListView);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id)
            {
                final ListViewRowItem item = itemList.get(position);
                int direction = rand.nextInt(2);
                ObjectAnimator anim;
                ObjectAnimator fadeAnim;
                if(0 == direction)
                {
                    anim = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0, 1000);
                }
                else
                {
                    anim = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0, -1000);
                }
                fadeAnim = ObjectAnimator.ofFloat(view, View.ALPHA, 0);
                anim.setDuration(2000);
                fadeAnim.setDuration(2000);
                view.setHasTransientState(true);
                anim.addListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        itemList.remove(item);
                        adapter.notifyDataSetChanged();
                        //view.setAlpha(1);
                        view.setHasTransientState(false);
                    }
                });

                fadeAnim.addListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        itemList.remove(item);
                        adapter.notifyDataSetChanged();
                        //view.setAlpha(1);
                        view.setHasTransientState(false);
                    }
                });
                fadeAnim.start();
                anim.start();

                return true;
            }
        });
    }

    private void refreshContent()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Random rand = new Random();
                itemList = new ArrayList<ListViewRowItem>();
                for(int i=0 ; i<20 ; i++)
                {
                    int randomValue = rand.nextInt(2);
                    ListViewRowItem item = new ListViewRowItem();
                    if(0 == randomValue)
                    {
                        item.setValues("first");
                        item.setIndex(i+1);
                        itemList.add(item);
                    }
                    else
                    {
                        item.setValues("second");
                        item.setIndex(i+1);
                        itemList.add(item);
                    }
                }

                adapter = new ListViewRowAdapter(getBaseContext(), itemList);
                listView = (ListView)findViewById(R.id.mainListView);
                listView.setAdapter(adapter);

                swipeRefreshLayout.setRefreshing(false);
            }
        },5000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
