package com.cmpe277.studentmarketplace;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class AddPost extends Fragment {
    int i = 0;
    HomeActivity parent;
    ImageView targetImage;
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    Database db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addpost, container, false);
        Button buttonLoadImage = view.findViewById(R.id.buttonLoadImage);
        Button save = view.findViewById(R.id.savepost);
        final Spinner category_input = view.findViewById(R.id.category);
        final EditText name_input = view.findViewById(R.id.add_name);
        final EditText desc_input = view.findViewById(R.id.add_desc);
        parent = (HomeActivity) getActivity();
        targetImage = view.findViewById(R.id.targetImage);
        db = new Database(parent);
        buttonLoadImage.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View arg0) {
                if (ContextCompat.checkSelfPermission(parent, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, i);
                } else {
                    // Show rationale and request permission.
                    ActivityCompat.requestPermissions(parent,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 99);
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post post = new Post(0, name_input.getText().toString(), desc_input.getText().toString(), parent.getCurrentUserEmail(), category_input.getSelectedItem().toString(), 0.0, images);
                DbResult result = db.createNewPost(post);
                NavigationView navView = parent.findViewById(R.id.nav_view);
                parent.allPostList = db.GetAllPosts(); //to refresh home on adding new
                parent.onNavigationItemSelected(navView.getMenu().getItem(0));
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 0:
                if (resultCode == RESULT_OK) {
                    Uri targetUri = data.getData();
                    //             textTargetUri.setText(targetUri.toString());
                    Bitmap bitmap;
                    try {
                        bitmap = BitmapFactory.decodeStream(parent.getContentResolver().openInputStream(targetUri));
                        targetImage.setImageBitmap(bitmap);
                        targetImage.setVisibility(View.VISIBLE);
                        images.add(bitmap);

                    } catch (FileNotFoundException e) {

                    }
                }
                break;

        }

    }
}
