package com.cmpe277.studentmarketplace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

public class Profile extends Fragment {
    TextView currentUser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        HomeActivity parent = (HomeActivity)getActivity();
        currentUser = view.findViewById(R.id.user_email);
        currentUser.setText(parent.getCurrentUserEmail());
        Geocoder geocoder = new Geocoder(parent);
        LatLng l = geocoder.getLatLong("1600 Amphitheatre Pkwy", "Mountain View", "CA", 94043, "USA");
        return view;
    }
}
