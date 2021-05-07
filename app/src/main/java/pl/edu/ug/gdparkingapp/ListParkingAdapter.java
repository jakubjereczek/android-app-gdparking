package pl.edu.ug.gdparkingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import pl.edu.ug.gdparkingapp.activities.ParkingSliderActivity;
import pl.edu.ug.gdparkingapp.models.ParkingValues;

public class ListParkingAdapter extends ArrayAdapter<ParkingValues> {

    private Parking parking;
    private ArrayList<ParkingValues> parkings;
    Context context;

    public ListParkingAdapter(@NonNull Context context, int resource, @NonNull Parking parking) {
        super(context, resource, parking.getParkingsList());
        this.parking = parking;
        this.parkings = (ArrayList<ParkingValues>) parking.getParkingsList();
        this.context = context;
    }

    @Nullable
    @Override
    public ParkingValues getItem(int position) {
        return parkings.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.container_box,parent,false);
        }

        ParkingValues current = getItem(position);
        TextView info_name = listItem.findViewById(R.id.info_name);
        info_name.setText(current.getParkingName().getName());
        TextView info_address = listItem.findViewById(R.id.info_address);
        info_address.setText(current.getParkingName().getAddress());
        TextView info_availableSpots = listItem.findViewById(R.id.info_availableSpots);
        String pattern = "hh:mm dd.MM"; // dd.MM.yyyy hh:mm:ss
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        String date = simpleDateFormat.format(current.getLastUpdate());
        info_availableSpots.setText("Wolnych miejsc parkingowych: " + current.getAvailableSpots());
        TextView info_lastUpdate = listItem.findViewById(R.id.info_lastUpdate);
        info_lastUpdate.setText( "("+date+")");

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, ParkingSliderActivity.class);
                intent.putExtra("parking", (Serializable) parking);
                intent.putExtra("currentParkingValues", (Serializable) current);
                context.startActivity(intent);
            }
        });

        return listItem;
    }
}
