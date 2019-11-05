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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;

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
                MyAdapter m = (MyAdapter) parent.homePostsRecyclerView.getAdapter();
                if (s.length() != 0) {
                    searchCategory.setSelection(0);
                    final ArrayList<Post> filteredPosts = db.GetPostsByName(s.toString());
                    View.OnClickListener clickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = parent.homePostsRecyclerView.indexOfChild(v);
                            parent.setCurrentPost(filteredPosts.get(pos));
                            NavController navController = Navigation.findNavController(parent, R.id.nav_host_fragment);
                            navController.navigate(R.id.viewpost);
                        }
                    };
                    m.setData(filteredPosts, clickListener);
                } else {
                    View.OnClickListener clickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = parent.homePostsRecyclerView.indexOfChild(v);
                            parent.setCurrentPost(parent.allPostList.get(pos));
                            NavController navController = Navigation.findNavController(parent, R.id.nav_host_fragment);
                            navController.navigate(R.id.viewpost);
                        }
                    };
                    m.setData(parent.allPostList, clickListener);
                }
            }
        });


        searchCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String category = searchCategory.getSelectedItem().toString();
                MyAdapter m = (MyAdapter) parent.homePostsRecyclerView.getAdapter();

                if (searchCategory.getSelectedItemPosition() != 0) {
                    searchName.setText(null);
                    final ArrayList<Post> filteredPosts = db.GetPostsByCategory(category);
                    View.OnClickListener clickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = parent.homePostsRecyclerView.indexOfChild(v);
                            parent.setCurrentPost(filteredPosts.get(pos));
                            NavController navController = Navigation.findNavController(parent, R.id.nav_host_fragment);
                            navController.navigate(R.id.viewpost);
                        }
                    };
                    m.setData(filteredPosts, clickListener);
                } else {
                    View.OnClickListener clickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = parent.homePostsRecyclerView.indexOfChild(v);
                            parent.setCurrentPost(parent.allPostList.get(pos));
                            NavController navController = Navigation.findNavController(parent, R.id.nav_host_fragment);
                            navController.navigate(R.id.viewpost);
                        }
                    };
                    m.setData(db.GetAllPosts(), clickListener);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        return view;
    }
}
