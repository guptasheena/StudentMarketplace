package com.cmpe277.studentmarketplace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Posted extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posted, container, false);
        // Inflate the layout for this fragment
        HomeActivity parent = (HomeActivity) getActivity();
        recyclerView = view.findViewById(R.id.posted_list_recycler);

        // use a linear layout manager
        layoutManager = new GridLayoutManager(parent, 3);
        recyclerView.setLayoutManager(layoutManager);
        parent.setPostedRecyclerview(recyclerView);
        parent.displayPostedPosts();
        return view;
    }
}
