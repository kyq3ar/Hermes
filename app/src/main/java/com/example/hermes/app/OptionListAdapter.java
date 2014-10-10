package com.example.hermes.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jinhyun on 6/10/14.
 */
public class OptionListAdapter extends ArrayAdapter<OptionHeader>{

    Context context;
    int layoutResourceId;
    ArrayList<OptionHeader> optionList;

    public OptionListAdapter(Context context, int layoutResourceId, ArrayList<OptionHeader> optionList) {
        super(context, layoutResourceId, optionList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.optionList = optionList;
    }

    //Custom View
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if (currentView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            currentView = inflater.inflate(R.layout.header_option, null);
        }

        OptionHeader currentItem = optionList.get(position);

        if (currentItem != null) {
            TextView text = (TextView) currentView.findViewById(R.id.text);

            if (text != null) {
                text.setText(currentItem.getText());
            }
        }
        return currentView;
    }

}
