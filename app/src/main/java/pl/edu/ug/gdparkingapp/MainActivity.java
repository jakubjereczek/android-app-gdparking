package pl.edu.ug.gdparkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.edu.ug.gdparkingapp.activities.ParkingsListActivity;
import pl.edu.ug.gdparkingapp.interfaces.AsyncResponse;
import pl.edu.ug.gdparkingapp.models.ParkingName;
import pl.edu.ug.gdparkingapp.models.ParkingValues;

public class MainActivity extends AppCompatActivity {

    public Parking parkingList;

    private ProgressBar spinner;
    private TextView errorMessage;
    private Button exitButton;

    private boolean isLoading = true;
    private boolean isError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        errorMessage = findViewById(R.id.errorMessage);
        fetchData();
    }

    private void fetchData() {
        String[] urls = new String[] {
                "https://ckan2.multimediagdansk.pl/parkingLots",
                "https://ckan.multimediagdansk.pl/dataset/cb1e2708-aec1-4b21-9c8c-db2626ae31a6/resource/d361dff3-202b-402d-92a5-445d8ba6fd7f/download/parking-lots.json"
        };
        DataDownloader dataDownloader = new DataDownloader(new AsyncResponse() {

            private ArrayList<ParkingName> parkingNameList = new ArrayList<>();
            private ArrayList<ParkingValues> parkings = new ArrayList<>();

            @Override
            public void onFinish(JSONObject[] results) {

                // results[0] => ParkingValues
                JSONObject parkingValuesJSONObject = results[0];
                // results[1] => ParkingName
                JSONObject parkingNameJSONObject = results[1];
                try {
                    JSONArray parkingNameJSONArray = parkingNameJSONObject.getJSONArray("parkingLots");
                    for(int i=0;i<parkingNameJSONArray.length();i++){
                        JSONObject object=parkingNameJSONArray.getJSONObject(i);

                        int id = Integer.parseInt(object.getString("id"));
                        String name = object.getString("name");
                        String shortName = object.getString("shortName");
                        String address = object.getString("address");
                        String streetEntrance = object.getString("streetEntrance");
                        JSONObject locationObject = object.getJSONObject("location");
                        MyLocationSerializable location = new MyLocationSerializable();
                        location.setLatitude(Double.parseDouble(locationObject.getString("latitude")));
                        location.setLongitude(Double.parseDouble(locationObject.getString("longitude")));
                        ParkingName parkingName = new ParkingName(id, name, shortName, address, streetEntrance, location);
                        parkingNameList.add(parkingName);
                    }

                    JSONArray parkingValuesJSONArray = parkingValuesJSONObject.getJSONArray("parkingLots");
                    for(int i=0;i<parkingValuesJSONArray.length();i++) {
                        JSONObject object=parkingValuesJSONArray.getJSONObject(i);
                        int id = Integer.parseInt(object.getString("parkingId"));
                        ParkingName parkingName = ParkingName.findParkingNameById(parkingNameList, id);
                        int availableSpots = object.getInt("availableSpots");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
                        Date lastUpdate = formatter.parse(object.getString("lastUpdate"));
                        ParkingValues parkingValues = new ParkingValues(parkingName, availableSpots, lastUpdate);
                        parkings.add(parkingValues);
                    }
                } catch (Exception e) {
                    isError = true;
                    e.printStackTrace();
                }

                if (isError) {
                    errorMessage.setVisibility(View.VISIBLE);
                    exitButton.setVisibility(View.VISIBLE);
                }else{
                    parkingList = new Parking(parkings);
                    spinner.setVisibility(View.GONE);
                    startParkingListActivity();
                }
                isLoading = false;

            }
        });
        dataDownloader.execute(urls);
    }

    private void startParkingListActivity() {
        Intent intent = new Intent(MainActivity.this, ParkingsListActivity.class);
        intent.putExtra("parking", (Serializable) parkingList);
        startActivity(intent);
    }

}

