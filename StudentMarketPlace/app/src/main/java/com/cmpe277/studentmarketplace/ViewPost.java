package com.cmpe277.studentmarketplace;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ViewPost extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viewpost, container, false);
        final HomeActivity parent = (HomeActivity)getActivity();
        TextView t = (TextView)view.findViewById(R.id.view_post_text);
        TextView t2 = (TextView)view.findViewById(R.id.post_owner);
        final Post p = parent.getCurrentPost();
        t.setText("This is to display info for Post: "+p.getName());
        t2.setText("Posted By: "+p.getOwnerEmail());
        Button b = (Button)view.findViewById(R.id.view_owner);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.setViewProfileOf(p.getOwnerEmail()); // view profile of post owner
                NavController navController = Navigation.findNavController(parent,R.id.nav_host_fragment);
                navController.navigate(R.id.other_profile);
            }
        });
        ImageButton map = (ImageButton)view.findViewById(R.id.mapIcon);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                startActivity(intent);
            }
        });
        return view;
    }
}
