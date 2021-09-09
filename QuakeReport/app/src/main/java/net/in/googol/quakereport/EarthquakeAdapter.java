package net.in.googol.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private final static String LOCATION_SEPARATOR = "of";

    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Earthquake currentEarthquake = getItem(position);

        String locationOffset;
        String primaryLocation;
        // Initialization of views
        //mag
        TextView magnitude = listItemView.findViewById(R.id.magnitude);
        //Place
        TextView place = listItemView.findViewById(R.id.tv2);
        TextView pLocation = listItemView.findViewById(R.id.pLocation);
        //Time
        TextView date = listItemView.findViewById(R.id.date);
        TextView time = listItemView.findViewById(R.id.tv3);

      //Mag
        GradientDrawable magView  = (GradientDrawable) magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magView.setColor(magnitudeColor);
        DecimalFormat formatter = new DecimalFormat("0.0");
        String mag = formatter.format(currentEarthquake.getMagnitude());
        magnitude.setText(mag);
        //Place
        String location = currentEarthquake.getPlace();
        if(location.contains(LOCATION_SEPARATOR)){
            String[] parts = location.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }else {
            locationOffset = "Near the";
            primaryLocation = location;
        }
        place.setText(locationOffset);
        pLocation.setText(primaryLocation);

        //Time
        Date dateObj = new Date(currentEarthquake.getTimeInMillis());
        String formattedDate = formatDate(dateObj);
        date.setText(formattedDate);
        String formattedTime = formatTime(dateObj);
        time.setText(formattedTime);



        return listItemView;
    }

    private String formatDate(Date dateObj) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, YYYY");
        return dateFormat.format(dateObj);

    }

    private String formatTime(Date dateObj) {

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObj);

    }
    private int getMagnitudeColor(double magnitude){
        int magColorResourceId;
        int mag =(int) Math.floor(magnitude);
        switch (mag){
            case 1:
                magColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magColorResourceId = R.color.magnitude9;
                break;
            default:
                magColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magColorResourceId);
    }
}
