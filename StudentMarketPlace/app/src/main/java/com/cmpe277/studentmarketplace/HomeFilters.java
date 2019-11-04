package com.cmpe277.studentmarketplace;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFilters extends Fragment {
    HomeActivity parent;
    Database db;
    EditText searchName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parent = (HomeActivity) getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_filters, container, false);
        db = new Database(parent);
        searchName = view.findViewById(R.id.searchName);
        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    MyAdapter m = (MyAdapter)parent.homePostsRecyclerView.getAdapter();
                    m.setData(db.GetPostsByName(s.toString()));
                }
            }
        });
        return view;
    }
}
