package com.tixon.planner.fragments;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tixon.planner.Income;
import com.tixon.planner.R;
import com.tixon.planner.adapters.AdapterIncomes;
import com.tixon.planner.database.DatabaseHelper;
import com.tixon.planner.dialogs.DialogAddIncome;

import java.util.ArrayList;

/**
 * Created by tikhon on 19/01/16.
 */
public class FragmentIncomes extends Fragment implements
        DialogAddIncome.OnIncomeAddedListener {

    ArrayList<Income> incomes = new ArrayList<>();

    SQLiteDatabase db;
    DatabaseHelper helper;

    RecyclerView recyclerViewIncomes;
    FloatingActionButton fab;
    AdapterIncomes adapterIncomes;

    public static FragmentIncomes newInstance() {
        FragmentIncomes fragment = new FragmentIncomes();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DatabaseHelper(getActivity());
        db = helper.getWritableDatabase();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_incomes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuIncomesDelete:
                helper.deleteAllIncomes(db);
                helper.getIncomes(db, incomes);
                adapterIncomes.notifyDataSetChanged();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_incomes, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.fabIncomes);
        recyclerViewIncomes = (RecyclerView) v.findViewById(R.id.recyclerViewIncomes);

        helper.getIncomes(db, incomes);

        recyclerViewIncomes.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterIncomes = new AdapterIncomes(incomes);
        recyclerViewIncomes.setAdapter(adapterIncomes);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddIncome dialogAddIncome = DialogAddIncome.newInstance();
                dialogAddIncome.setOnIncomeAddedListener(FragmentIncomes.this);
                dialogAddIncome.showDialog(FragmentIncomes.this);
            }
        });
        return v;
    }

    @Override
    public void onIncomeAdded(Income income) {
        helper.addIncome(db, income);
        helper.getLastIncome(db, incomes);
        adapterIncomes.notifyDataSetChanged();
    }
}
