package com.cmpe277.studentmarketplace;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Geocoder {
    private HomeActivity currentContext;

    public Geocoder(HomeActivity h) {
        currentContext = h;
    }

    public LatLng getLatLong(String strAddress) {
        //String strAddress = addr12 + ", " + city + ", " + state + " " + zip + ", " + country;
        android.location.Geocoder coder = new android.location.Geocoder(currentContext);
        List<Address> address;


        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);

            return new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
