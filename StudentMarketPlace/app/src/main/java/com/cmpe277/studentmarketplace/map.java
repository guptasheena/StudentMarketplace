package com.cmpe277.studentmarketplace;


import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class map extends Fragment implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    HomeActivity parent;
    Database db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        parent = (HomeActivity) getActivity();
        db = new Database(parent);
        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        Geocoder g = new Geocoder(parent);
        String strAddress = db.getUserAddress(parent.getCurrentPost().getOwnerEmail());
        LatLng ownerLoc= g.getLatLong(strAddress);
        String errMsg="";
        String strAddress2 = db.getUserAddress(parent.getCurrentUserEmail());

        mMap.moveCamera(CameraUpdateFactory.newLatLng(ownerLoc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        markerOptions.position(ownerLoc);
        markerOptions.title("Destination: "+strAddress);
        mMap.addMarker(markerOptions);
        if(strAddress2.equals("") || strAddress2 == null){
            errMsg = "Looks like you havent  doesn't have an address!";
        }
        else{
            LatLng currentUserLoc= g.getLatLong(strAddress2);
            if (ownerLoc == null) {
                errMsg = "You could not be located!Please check if your address is correct";
            }
            else {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                markerOptions.position(currentUserLoc);
                markerOptions.title("Source: "+strAddress2);
                mMap.addMarker(markerOptions);
                mMap.addPolyline(new PolylineOptions()
                        .add(ownerLoc, currentUserLoc)
                        .width(5)
                        .color(Color.RED));
            }
        }
        mapFragment.onResume();
        if(errMsg != ""){
            Toast.makeText(parent,errMsg,Toast.LENGTH_SHORT).show();
        }
    }

}
