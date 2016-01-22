package com.tixon.planner.fragments;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tixon.planner.R;
import com.tixon.planner.database.DatabaseHelper;

/**
 * Created by tikhon on 19/01/16.
 */
public class FragmentMain extends Fragment {

    SQLiteDatabase db;
    DatabaseHelper helper;

    TextView tvCosts, tvIncomes, tvSavings, tvRemainingAmount;

    double costs, incomes, savings, remainingAmount = 0;

    public FragmentMain() {}

    public static FragmentMain newInstance() {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DatabaseHelper(getActivity());
        db = helper.getWritableDatabase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        tvCosts = (TextView) v.findViewById(R.id.tvFragmentMainCosts);
        tvIncomes = (TextView) v.findViewById(R.id.tvFragmentMainIncomes);
        tvSavings = (TextView) v.findViewById(R.id.tvFragmentMainSavings);
        tvRemainingAmount = (TextView) v.findViewById(R.id.tvFragmentMainRemaining);

        costs = helper.getCostsTotalValue(db);
        incomes = helper.getIncomesTotalValue(db);
        savings = helper.getSavingsTotalValue(db);
        remainingAmount = incomes - costs - savings;

        tvCosts.setText(String.valueOf(costs));
        tvIncomes.setText(String.valueOf(incomes));
        tvSavings.setText(String.valueOf(savings));
        tvRemainingAmount.setText(String.valueOf(remainingAmount));
        return v;
    }
}
