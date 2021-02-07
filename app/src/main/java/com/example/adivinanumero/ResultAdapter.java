package com.example.adivinanumero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ResultAdapter extends ArrayAdapter<Result> {
    public ResultAdapter(Context context, ArrayList<Result> results){
        super(context, 0, results);
    }
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        Result result = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view_layout, parent, false);

        }

        // Lookup view for data population

        ImageView imgView = (ImageView) convertView.findViewById(R.id.photo);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

        TextView tvTries = (TextView) convertView.findViewById(R.id.tvTries);

        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);

        // Populate the data into the template view using the data object

        imgView.setImageBitmap(result.photo);

        tvName.setText(result.getName());

        tvTries.setText(result.getTries());

        tvTime.setText(result.getTime());

        // Return the completed view to render on screen

        return convertView;

    }
}
