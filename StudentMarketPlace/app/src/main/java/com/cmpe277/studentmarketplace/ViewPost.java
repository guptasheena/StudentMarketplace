package com.cmpe277.studentmarketplace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
        HomeActivity parent = (HomeActivity)getActivity();
        TextView t = (TextView)view.findViewById(R.id.view_post_text);
        Post p = parent.getCurrentPost();
        t.setText("This is to display info for Post: "+p.getName());
        return view;
    }
}
