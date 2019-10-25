package com.cmpe277.studentmarketplace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;

public class Profile extends Fragment {
    TextView profileUser;
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
        final HomeActivity parent = (HomeActivity)getActivity();
        profileUser = view.findViewById(R.id.user_email);
        profileUser.setText(parent.getViewProfileOf()); //display profile of user whose profile needs to be viewed
        Button b = (Button) view.findViewById(R.id.snack_msg);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent.findViewById(R.id.nav_host_fragment),"This is the profile of: "+parent.getViewProfileOf(),Snackbar.LENGTH_LONG).show();
            }
        });
         return view;
    }
}
