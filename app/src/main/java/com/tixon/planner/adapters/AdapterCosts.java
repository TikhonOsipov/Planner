package com.tixon.planner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tixon.planner.Cost;
import com.tixon.planner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tikhon on 18/01/16.
 */
public class AdapterCosts extends RecyclerView.Adapter<AdapterCosts.ViewHolder> {

    private ArrayList<Cost> costs;

    public AdapterCosts(ArrayList<Cost> costs) {
        this.costs = costs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_costs,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(costs.get(position).getTime());

        holder.textViewName.setText(costs.get(position).getName());
        holder.textViewValue.setText(String.valueOf(costs.get(position).getValue()));
        holder.textViewTime.setText(String.valueOf((new SimpleDateFormat("dd.MM.yyyy HH:mm"))
                .format(c.getTime())));
    }

    @Override
    public int getItemCount() {
        return costs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName, textViewValue, textViewTime;

        public ViewHolder(View v) {
            super(v);
            textViewName = (TextView) v.findViewById(R.id.costsItemName);
            textViewValue = (TextView) v.findViewById(R.id.costsItemValue);
            textViewTime = (TextView) v.findViewById(R.id.costsItemTime);
        }
    }
}
