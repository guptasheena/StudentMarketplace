package com.cmpe277.studentmarketplace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Post> mDataset;
    View.OnClickListener mClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;

        public MyViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Post> myDataset, View.OnClickListener callback) {
        mDataset = myDataset;
        mClickListener = callback;
    }

    public void setData(ArrayList<Post> data, View.OnClickListener callback){
        mDataset = data;
        mClickListener = callback;
        notifyDataSetChanged();
        // where this.data is the recyclerView's dataset you are
        // setting in adapter=new Adapter(this,db.getData());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView text1 = holder.cardView.findViewById(R.id.post_name);
        text1.setText(mDataset.get(position).getName());
        ImageView image = holder.cardView.findViewById(R.id.post_image);
        if (mDataset.get(position).getMainImage() != null) {
            image.setImageBitmap(mDataset.get(position).getMainImage());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}