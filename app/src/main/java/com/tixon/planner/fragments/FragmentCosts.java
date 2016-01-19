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

import com.tixon.planner.Cost;
import com.tixon.planner.R;
import com.tixon.planner.adapters.AdapterCosts;
import com.tixon.planner.database.DatabaseHelper;
import com.tixon.planner.dialogs.DialogAddCost;

import java.util.ArrayList;

/**
 * Created by tikhon on 18/01/16.
 */
public class FragmentCosts extends Fragment implements
        DialogAddCost.OnCostAddedListener {

    SQLiteDatabase db;
    DatabaseHelper helper;

    RecyclerView recyclerViewCosts;
    FloatingActionButton fab;
    AdapterCosts adapterCosts;

    ArrayList<Cost> costs = new ArrayList<>();

    public FragmentCosts() {}

    public static FragmentCosts newInstance() {
        FragmentCosts fragment = new FragmentCosts();
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
        inflater.inflate(R.menu.menu_fragment_costs, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCostsDelete:
                helper.deleteAllCosts(db);
                helper.getCosts(db, costs);
                adapterCosts.notifyDataSetChanged();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_costs, container, false);

        helper.getCosts(db, costs);

        fab = (FloatingActionButton) v.findViewById(R.id.fabCosts);
        recyclerViewCosts = (RecyclerView) v.findViewById(R.id.recyclerViewCosts);

        recyclerViewCosts.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterCosts = new AdapterCosts(costs);
        recyclerViewCosts.setAdapter(adapterCosts);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddCost dialog = DialogAddCost.newInstance();
                dialog.setOnCostAddedListener(FragmentCosts.this);
                dialog.showDialog(FragmentCosts.this);
            }
        });
        return v;
    }

    @Override
    public void onCostAdded(Cost cost) {
        helper.addCost(db, cost);
        helper.getLastCost(db, costs);
        adapterCosts.notifyDataSetChanged();
    }
}
