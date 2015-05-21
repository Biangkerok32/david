package com.example.david.listviewtrial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by david on 5/19/15.
 */
public class ListViewRowAdapter extends ArrayAdapter
{
    private Context context;
    private List<ListViewRowItem> values;
    private Bitmap bmp1;
    private Bitmap bmp2;

    public ListViewRowAdapter(Context context, List<ListViewRowItem> values)
    {
        super(context, R.layout.list_view_row, values);
        this.context = context;
        this.values = values;
        bmp1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.images);
        bmp2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_thing);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_row, parent, false);

        ImageView imgVw = (ImageView)rowView.findViewById(R.id.rowImage);
        TextView txtVw = (TextView)rowView.findViewById(R.id.rowText);

        if(values.get(position).getValues().equals("first"))
        {
            imgVw.setImageBitmap(bmp1);
        }
        else
        {
            imgVw.setImageBitmap(bmp2);
        }

        txtVw.setText("Row Text...." + values.get(position).getIndex());

        return rowView;
    }
}
