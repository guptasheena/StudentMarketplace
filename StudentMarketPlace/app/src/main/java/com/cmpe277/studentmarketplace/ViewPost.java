package com.cmpe277.studentmarketplace;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class ViewPost extends Fragment {
    ViewPager viewPager;
    //int images[] = {R.drawable.ic_launcher_background, R.drawable.no_image, R.drawable.home_icon, R.drawable.logo};
    ImageSliderAdapter imageSliderAdapter;
    HomeActivity parent;

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
        parent = (HomeActivity) getActivity();
        TextView t = view.findViewById(R.id.view_post_text);
        TextView t2 = view.findViewById(R.id.post_owner);
        final Post p = parent.getCurrentPost();
        t.setText("This is to display info for Post: " + p.getName());
        t2.setText("Posted By: " + p.getOwnerEmail());
        Button b = (Button) view.findViewById(R.id.view_owner);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.setViewProfileOf(p.getOwnerEmail()); // view profile of post owner
                NavController navController = Navigation.findNavController(parent, R.id.nav_host_fragment);
                navController.navigate(R.id.other_profile);
            }
        });

        //display directions
        ImageButton map = view.findViewById(R.id.mapIcon);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(parent, R.id.nav_host_fragment);
                navController.navigate(R.id.direction);
            }
        });

        //display images
        viewPager = view.findViewById(R.id.viewPager);
        ArrayList<Bitmap> images = p.getAllImages();
        if (images.size() == 0)
            images.add(((BitmapDrawable) getResources().getDrawable(R.drawable.no_image)).getBitmap());
        imageSliderAdapter = new ImageSliderAdapter(parent, images);
        viewPager.setAdapter(imageSliderAdapter);

        //display option to mark sold if the current user is the owner
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //only owner can mark an item as sold
        if (parent.getCurrentPost().getOwnerEmail().equals(parent.getCurrentUserEmail())) {
            MenuItem menuItem = menu.findItem(R.id.sold);
            menuItem.setVisible(true);
        }
    }

}
