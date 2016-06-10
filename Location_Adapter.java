package com.android.project.travelbook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by hp on 6/10/2016.
 */
public class Location_Adapter extends ArrayAdapter<Location_Item> {
    ArrayList<Location_Item> location_list = null;
    Context context;

    public Location_Adapter(Context context, ArrayList<Location_Item> resource) {
        super(context,R.layout.location_item,resource);

        this.context = context;
        this.location_list = resource;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.location_item, parent, false);

        TextView location_address = (TextView) convertView.findViewById(R.id.address);
        TextView location_date = (TextView) convertView.findViewById(R.id.date);
        TextView location_category = (TextView) convertView.findViewById(R.id.category);
        TextView location_note = (TextView) convertView.findViewById(R.id.notes);

        location_address.setText(location_list.get(position).getAddress());
        location_date.setText(location_list.get(position).getVisit_date());
        location_category.setText(location_list.get(position).getCategory());
        location_note.setText(String.valueOf(location_list.get(position).getNotes()));

        return convertView;
    }
}
