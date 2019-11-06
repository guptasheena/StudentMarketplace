package com.cmpe277.studentmarketplace;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class Profile extends Fragment {

    // widgets
    TextView email;
    TextView firstName;
    TextView lastName;
    TextView password;
    TextView address;
    TextView phone;

    // vars
    boolean flag;

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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final HomeActivity parent = (HomeActivity) getActivity();
        db = new Database(parent);

        email = view.findViewById(R.id.email);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        password = view.findViewById(R.id.password);
        address = view.findViewById(R.id.address);
        phone = view.findViewById(R.id.phone);

        User u = parent.getUser();

        firstName.setText(u.getFirst_name());
        lastName.setText(u.getLast_name());
        password.setText(u.getPassword());
        address.setText(u.getAddress());
        phone.setText(u.getPhone());

        flag = false;

        email.setText(parent.getViewProfileOf()); // get email of user whose profile needs to be viewed
//        Button b = (Button) view.findViewById(R.id.btn_main);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(parent.findViewById(R.id.nav_host_fragment), "This is the profile of: " + parent.getViewProfileOf(), Snackbar.LENGTH_LONG).show();
//            }
//        });

        final Button b = (Button) view.findViewById(R.id.btn_main);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag) {
                    firstName.setEnabled(true);
                    lastName.setEnabled(true);
                    password.setEnabled(true);
                    address.setEnabled(true);
                    phone.setEnabled(true);
                    b.setText("Save Changes");
                    flag = true;
                } else {
                    parent.updateUser(
                            email.getText().toString(),
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            password.getText().toString(),
                            address.getText().toString(),
                            phone.getText().toString()
                            );
                    firstName.setEnabled(false);
                    lastName.setEnabled(false);
                    password.setEnabled(false);
                    address.setEnabled(false);
                    phone.setEnabled(false);
                    b.setText("Edit Profile");
                    flag = false;
                    Snackbar.make(
                            parent.findViewById(R.id.nav_host_fragment), "Your profile has been updated" ,
                            Snackbar.LENGTH_LONG
                    ).show();
                }
            }
        });
        return view;
    }
}
