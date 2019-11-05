package com.cmpe277.studentmarketplace;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class HomeFilters extends Fragment {
    private static final String TAG = "HomeFilters";
    HomeActivity parent;
    Database db;
    EditText searchName;
    Spinner searchCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parent = (HomeActivity) getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_filters, container, false);
        db = new Database(parent);
        searchName = view.findViewById(R.id.searchName);
        searchCategory = view.findViewById(R.id.searchCategory);

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
                MyAdapter m = (MyAdapter)parent.homePostsRecyclerView.getAdapter();
                if (s.length() != 0) {
                    searchCategory.setSelection(0);
                    m.setData(db.GetPostsByName(s.toString()));
                }
                else
                    m.setData(db.GetAllPosts());
            }
        });

        searchCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String category = searchCategory.getSelectedItem().toString();
                MyAdapter m = (MyAdapter)parent.homePostsRecyclerView.getAdapter();

                if (searchCategory.getSelectedItemPosition() != 0) {
                    searchName.setText(null);
                    m.setData(db.GetPostsByCategory(category));
                }
                else
                    m.setData(db.GetAllPosts());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return view;
    }
}
