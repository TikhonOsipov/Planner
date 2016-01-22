package com.tixon.planner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tixon.planner.Saving;
import com.tixon.planner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tikhon on 22/01/16.
 */
public class AdapterSavings extends RecyclerView.Adapter<AdapterSavings.ViewHolder> {
    
    private ArrayList<Saving> saving;
    
    public AdapterSavings(ArrayList<Saving> saving) {
        this.saving = saving;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_savings,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(saving.get(position).getTime());
        holder.textViewName.setText(saving.get(position).getName());
        holder.textViewValue.setText(String.valueOf(saving.get(position).getValue()));
        holder.textViewTime.setText(String.valueOf((new SimpleDateFormat("dd.MM.yyyy HH:mm"))
                .format(c.getTime())));
    }

    @Override
    public int getItemCount() {
        return saving.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName, textViewValue, textViewTime;
        public ViewHolder(View v) {
            super(v);
            textViewName = (TextView) v.findViewById(R.id.savingsItemName);
            textViewValue = (TextView) v.findViewById(R.id.savingsItemValue);
            textViewTime = (TextView) v.findViewById(R.id.savingsItemTime);
        }
    }
}
