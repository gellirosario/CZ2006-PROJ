package com.cz2006.helloworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.models.Points;
import com.cz2006.helloworld.models.User;

import java.util.ArrayList;

public class ActivityAdapter extends ArrayAdapter<Points> {
    public ActivityAdapter(Context context, ArrayList<Points> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Points points = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_activity, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.titleTV);
        AppCompatTextView tvMessage = convertView.findViewById(R.id.messageTV);
        TextView tvDate = (TextView) convertView.findViewById(R.id.dateTV);
        AppCompatTextView tvPoints = (AppCompatTextView) convertView.findViewById(R.id.pointsTV);

        // Populate the data into the template view using the data object
        String pointTitle = "";

        if(points.getPointType() != null)
        {
            if(points.getPointType().matches("QR Code"))
            {
                pointTitle = "Scanned QR Code";
            }
            else
            {
                pointTitle = "Added Utility Usage";
            }

            tvMessage.setText("");
            tvTitle.setText(pointTitle);
            tvDate.setText(points.getPointDate());
            tvPoints.setText("+ " + String.valueOf(points.getPoints()));
        }
        else
        {
            tvMessage.setText("No Recent Activity");
            tvTitle.setText("");
            tvDate.setText("");
            tvPoints.setText("");
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
