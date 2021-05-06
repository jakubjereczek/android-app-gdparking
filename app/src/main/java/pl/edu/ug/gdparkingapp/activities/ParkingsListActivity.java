package pl.edu.ug.gdparkingapp.activities;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.edu.ug.gdparkingapp.ListParkingAdapter;
import pl.edu.ug.gdparkingapp.Parking;
import pl.edu.ug.gdparkingapp.R;
import pl.edu.ug.gdparkingapp.models.ParkingValues;

public class ParkingsListActivity extends AppCompatActivity {


    Parking parking;
    ArrayList<ParkingValues> parkingList;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkings_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        toolBarLayout.setTitle("Miejsca parkingowe");
        listView = findViewById(R.id.listView);
        ViewCompat.setNestedScrollingEnabled(listView, true);

        parking = (Parking) getIntent().getSerializableExtra("parking");
        parkingList = (ArrayList<ParkingValues>) parking.getParkingsList();

        ListParkingAdapter listParkingAdapter = new ListParkingAdapter(this,0, parking);
        listView.setAdapter(listParkingAdapter);
    }
}