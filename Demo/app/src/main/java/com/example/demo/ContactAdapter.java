package com.example.demo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    ArrayList<Contact> datalist;

    public ContactAdapter(Context context, ArrayList<Contact> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullname,emailaddress,mobilenumber;
        ImageView profile;
        public ViewHolder(View view) {
            super(view);
            fullname=view.findViewById(R.id.fullname);
            emailaddress=view.findViewById(R.id.emailaddress);
            mobilenumber=view.findViewById(R.id.mobilenumber);
            profile=view.findViewById(R.id.profile);

        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.profile, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.fullname.setText(datalist.get(position).Fname);
        viewHolder.emailaddress.setText(datalist.get(position).Email);
        viewHolder.mobilenumber.setText(datalist.get(position).MobileNo);
        Glide.with(context).load(datalist.get(position).getProfileImage()).placeholder(R.drawable.usericon).into(viewHolder.profile);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return datalist.size();
    }
}