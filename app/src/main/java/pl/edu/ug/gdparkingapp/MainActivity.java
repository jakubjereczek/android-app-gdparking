package pl.edu.ug.gdparkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.edu.ug.gdparkingapp.interfaces.AsyncResponse;
import pl.edu.ug.gdparkingapp.models.ParkingName;
import pl.edu.ug.gdparkingapp.models.ParkingValues;

public class MainActivity extends AppCompatActivity {

    public Parking parkingList;

    private boolean isLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchData();
    }

    private void fetchData() {
        String[] urls = new String[] {
                "https://ckan2.multimediagdansk.pl/parkingLots",
                "https://ckan.multimediagdansk.pl/dataset/cb1e2708-aec1-4b21-9c8c-db2626ae31a6/resource/d361dff3-202b-402d-92a5-445d8ba6fd7f/download/parking-lots.json"
        };
        DataDownloader dataDownloader = new DataDownloader(new AsyncResponse() {

            private List<ParkingName> parkingNameList = new ArrayList<>();
            private List<ParkingValues> parkings = new ArrayList<>();

            @Override
            public void onFinish(JSONObject[] results) {
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
                        Location location = new Location("Parking location");
                        location.setLatitude(Double.parseDouble(locationObject.getString("latitude")));
                        location.setLongitude(Double.parseDouble(locationObject.getString("longitude")));
                        ParkingName parkingName = new ParkingName(id, name, shortName, address, streetEntrance, location);
                        parkingNameList.add(parkingName);
                    }

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

                // results[0] => ParkingValues
                JSONObject parkingValuesJSONObject = results[0];
                try {
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
                        Log.i("XXX", "Name: "+parkingName.getName() + ", slotow: "+parkingValues.getAvailableSpots());
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                isLoading = false;
                parkingList = new Parking(parkings);
                Log.i("XXX", "Ilosć załadowanych parkingow: "+parkingList.getParking());
            }
        });
        dataDownloader.execute(urls);
    }
}

