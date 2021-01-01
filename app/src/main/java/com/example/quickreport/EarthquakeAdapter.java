package com.example.quickreport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter {

    private static final String stringSeparator = "of";

    public EarthquakeAdapter(Context context, List<EarthquakeData> earthquakeData) {
        super(context, 0, earthquakeData);
    }

    /* Return the color for the magnitude circle based on the intensity of the earthquake.
     *
     * @param magnitude of the earthquake
     */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;

        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        EarthquakeData itemPosition = (EarthquakeData) getItem(position);

        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.custom_earthquake_layout, parent, false);
        }

        TextView dateTextView = (TextView) listView.findViewById(R.id.dateText);
        dateTextView.setText(itemPosition.getDate());

        TextView timeTextView = (TextView) listView.findViewById(R.id.timeText);
        timeTextView.setText(itemPosition.getTime());


        String primaryLocation;
        String offsetLocation;


        if (itemPosition.getCity().contains(stringSeparator)) {
            String[] parts = itemPosition.getCity().split(stringSeparator);
            offsetLocation = parts[0] + stringSeparator;
            primaryLocation = parts[1];
        } else {
            offsetLocation = "near_the";
            primaryLocation = itemPosition.getCity();
        }

        TextView offsetLocationTextView = (TextView) listView.findViewById(R.id.offsetLocation);
        offsetLocationTextView.setText(offsetLocation);

        TextView primaryLocationTextView = (TextView) listView.findViewById(R.id.primaryLocation);
        primaryLocationTextView.setText(primaryLocation);


        TextView magnitudeTextView = (TextView) listView.findViewById(R.id.magnitudeText);
        String formattedMagnitude = formatMagnitude(itemPosition.getMagnitude());
        magnitudeTextView.setText(formattedMagnitude);


        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(itemPosition.getMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        return listView;
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

}
