package com.cmpe277.studentmarketplace;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class HomePosts extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
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
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        parent = (HomeActivity) getActivity();


        recyclerView = view.findViewById(R.id.post_list_recycler);

        // use a linear layout manager
        layoutManager = new GridLayoutManager(parent,3);
        recyclerView.setLayoutManager(layoutManager);
        parent.setHomePostsRecyclerview(recyclerView);
        parent.displayHomePosts();
        parent.setViewProfileOf(parent.getCurrentUserEmail()); // from home page we can only view current users profile on clicking my stuff
        return view;
    }
}
