package pl.edu.ug.gdparkingapp.interfaces;

import org.json.JSONObject;

public interface AsyncResponse {

    void onFinish(JSONObject[] results);
}
