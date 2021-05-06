package pl.edu.ug.gdparkingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import pl.edu.ug.gdparkingapp.Parking;
import pl.edu.ug.gdparkingapp.R;
import pl.edu.ug.gdparkingapp.SlideAdapter;
import pl.edu.ug.gdparkingapp.models.ParkingValues;

public class ParkingSliderActivity extends AppCompatActivity {
    private Parking parking;
    private ParkingValues parkingValues;

    private ViewPager viewPager;
    private SlideAdapter slideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_slider);
        parking = (Parking) getIntent().getSerializableExtra("parking");
        parkingValues = (ParkingValues) getIntent().getSerializableExtra("currentParkingValues");

        parking.setCurrent(parkingValues); // Slider zacznie działać od tej pozycji.

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        slideAdapter = new SlideAdapter(this, parking);

        viewPager.setAdapter(slideAdapter);
        viewPager.setCurrentItem(parking.getCurrentParkingId());

    }
}


