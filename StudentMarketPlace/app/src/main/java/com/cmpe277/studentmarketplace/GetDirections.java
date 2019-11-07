package com.cmpe277.studentmarketplace;
import android.os.AsyncTask;
import android.util.Log;

import com.cmpe277.studentmarketplace.DownloadURL;
import com.cmpe277.studentmarketplace.HomeActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.List;

public class GetDirections extends AsyncTask<Object, String, String> {

    GoogleMap mMap;
    String url;
    String data= "";

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetDirections", "doInBackground entered");
            mMap = (GoogleMap) params[0];
            url = (String) params[1];
            DownloadURL download = new DownloadURL();
            data = download.downloadUrl(url);
            Log.d("GetDirectionsReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GetDirectionsReadTask", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GetDirectionsReadTask", "onPostExecute Entered");
        PolylineOptions lines = null;
        DataParser dataParser = new DataParser();
        lines =  dataParser.parse(result);
        if(lines != null)
            mMap.addPolyline(lines);
        Log.d("GetDirectionsReadTask", "onPostExecute Exit");
    }


}
