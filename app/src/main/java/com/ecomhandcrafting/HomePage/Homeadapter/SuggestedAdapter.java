package com.ecomhandcrafting.HomePage.Homeadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecomhandcrafting.R;

import java.util.ArrayList;

public class SuggestedAdapter extends RecyclerView.Adapter<SuggestedAdapter.SuggestedViewHolder> {

    ArrayList<SuggestedHelperClass> suggestedLocations;

    //Constructor
    public SuggestedAdapter(ArrayList<SuggestedHelperClass> suggestedLocations) {
        this.suggestedLocations = suggestedLocations;
    }

    @NonNull
    @Override
    public SuggestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggested_viewed_card_design,parent, false);
        SuggestedViewHolder suggestedViewHolder = new SuggestedViewHolder(view);
        return suggestedViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedViewHolder holder, int position) {

        SuggestedHelperClass suggestedHelperClass = suggestedLocations.get(position);

        holder.image.setImageResource(suggestedHelperClass.getImage());
        holder.title.setText(suggestedHelperClass.getTitle());
    }

    @Override
    public int getItemCount() {
        return suggestedLocations.size();
    }

    //design
    public static class SuggestedViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;

        public SuggestedViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.sv_image);
            title = itemView.findViewById(R.id.sv_title);
        }
    }
}
