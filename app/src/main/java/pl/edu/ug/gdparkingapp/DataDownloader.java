package pl.edu.ug.gdparkingapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.edu.ug.gdparkingapp.interfaces.AsyncResponse;

public class DataDownloader extends AsyncTask<String, Void, String[]> implements AsyncResponse {

    public AsyncResponse delegate  = null;
    public String[] results;

    public DataDownloader(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String[] doInBackground(String... strings) {
        results = new String[strings.length];

        for(int i = 0; i < strings.length; i++) {
            try {
                URL url = new URL(strings[i]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                while (data != -1) {
                    char letter = (char) data;
                    results[i] += letter;
                    data = inputStreamReader.read();
                }

            }catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return results;
    }

    @Override
    protected void onPostExecute(String[] data) {
        JSONObject[] jsonObjects = new JSONObject[data.length];
        super.onPostExecute(data);

        for(int i = 0; i < data.length; i++) {
            JSONObject jsonObject = null;
            try {
                String crappyPrefix = "null";
                if(data[i].startsWith(crappyPrefix)){
                    data[i] = data[i].substring(crappyPrefix.length(), data[i].length());
                }
                jsonObject = new JSONObject(data[i]);
                jsonObjects[i] = jsonObject;
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }

        }
        delegate.onFinish(jsonObjects);
    }

    @Override
    public void onFinish(JSONObject[] results) {

    }
}
