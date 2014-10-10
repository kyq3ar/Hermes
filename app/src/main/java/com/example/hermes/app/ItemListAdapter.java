package com.example.hermes.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Jinhyun on 6/5/14.
 */
public class ItemListAdapter extends ArrayAdapter<ItemHeader> {

    Context context;
    int layoutResourceId;
    ArrayList<ItemHeader> itemList;

    public ItemListAdapter(Context context, int layoutResourceId, ArrayList<ItemHeader> itemList) {
        super(context, layoutResourceId, itemList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.itemList = itemList;
    }

    //Custom View
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if (currentView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            currentView = inflater.inflate(R.layout.header_item, null);
        }

        ItemHeader currentItem = itemList.get(position);

        if (currentItem != null) {
            TextView titleText = (TextView) currentView.findViewById(R.id.text_title);
            TextView detailText = (TextView) currentView.findViewById(R.id.text_detail);

            //=================TODO: CHANGED BY KAREN==================================//
            ImageView image = (ImageView) currentView.findViewById(R.id.icon_image);

            if (titleText != null) {
                titleText.setText(currentItem.getTitle());
            }
            if (detailText != null) {
                detailText.setText(currentItem.getDescription());
            }

            //=================TODO: CHANGED BY KAREN==================================//
            if(image!=null){
                String img = currentItem.getImage();
                if (img.equals(""))
                {
                    image.setImageResource(R.drawable.cnn);
                }
                else {
                    Bitmap mIcon11 = null;
                    try {
                        InputStream in = new java.net.URL(img).openStream();
                        mIcon11 = BitmapFactory.decodeStream(in);
                        image.setImageBitmap(mIcon11);
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
        return currentView;
    }
}
