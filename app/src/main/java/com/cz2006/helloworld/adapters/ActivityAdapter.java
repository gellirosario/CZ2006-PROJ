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

        // Data population
        ActivityViewHolder activityViewHolder = createViewHolderFrom(convertView);
        convertView.setTag(activityViewHolder);


        // Populate the data into the template view using the data object
        String pointTitle = "";

        if(points.getPointType() != null)
        {
            if(points.getPointType().matches("QR Code"))
            {
                pointTitle = "Scanned QR Code";
                activityViewHolder.tvDate.setText(points.getPointDate());
            }
            else
            {
                String type = points.getPointType().substring(0,7);
                pointTitle = "Added Utility Usage";
                activityViewHolder.tvDate.setText(points.getPointDate() + " - " + type);
            }
            activityViewHolder.tvMessage.setText("");
            activityViewHolder.tvTitle.setText(pointTitle);
            activityViewHolder.tvPoints.setText("+ " + String.valueOf(points.getPoints()));
        }
        else
        {
            activityViewHolder.tvMessage.setText("No Recent Activity");
            activityViewHolder.tvTitle.setText("");
            activityViewHolder.tvDate.setText("");
            activityViewHolder.tvPoints.setText("");
        }
        // Return the completed view to render on screen
        return convertView;
    }

    private ActivityViewHolder createViewHolderFrom(View view) {
        TextView tvTitle = view.findViewById(R.id.titleTV);
        TextView tvDate = view.findViewById(R.id.dateTV);
        AppCompatTextView tvMessage = view.findViewById(R.id.msgTV);
        AppCompatTextView tvPoints = view.findViewById(R.id.pointsTV);

        return new ActivityViewHolder(tvTitle, tvDate, tvMessage, tvPoints);
    }

    private class ActivityViewHolder {
        TextView tvTitle;
        TextView tvDate;
        AppCompatTextView tvMessage;
        AppCompatTextView tvPoints;

        ActivityViewHolder(TextView tvTitle, TextView tvDate, AppCompatTextView tvMessage, AppCompatTextView tvPoints) {
            this.tvTitle = tvTitle;
            this.tvDate = tvDate;
            this.tvMessage = tvMessage;
            this.tvPoints = tvPoints;
        }
    }
}
