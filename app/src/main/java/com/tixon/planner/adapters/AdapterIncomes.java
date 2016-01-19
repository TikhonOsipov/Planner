package com.tixon.planner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tixon.planner.Income;
import com.tixon.planner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tikhon on 19/01/16.
 */
public class AdapterIncomes extends RecyclerView.Adapter<AdapterIncomes.ViewHolder> {

    public ArrayList<Income> incomes;

    public AdapterIncomes(ArrayList<Income> incomes) {
        this.incomes = incomes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_incomes,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(incomes.get(position).getTime());

        holder.textViewName.setText(incomes.get(position).getName());
        holder.textViewValue.setText(String.valueOf(incomes.get(position).getValue()));
        holder.textViewTime.setText(String.valueOf((new SimpleDateFormat("dd.MM.yyyy HH:mm"))
                .format(c.getTime())));
    }

    @Override
    public int getItemCount() {
        return incomes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName, textViewValue, textViewTime;

        public ViewHolder(View v) {
            super(v);
            textViewName = (TextView) v.findViewById(R.id.incomesItemName);
            textViewValue = (TextView) v.findViewById(R.id.incomesItemValue);
            textViewTime = (TextView) v.findViewById(R.id.incomesItemTime);
        }
    }
}
