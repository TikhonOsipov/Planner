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

import com.tixon.planner.R;
import com.tixon.planner.Saving;
import com.tixon.planner.adapters.AdapterSavings;
import com.tixon.planner.database.DatabaseHelper;
import com.tixon.planner.dialogs.DialogAddSaving;

import java.util.ArrayList;

/**
 * Created by tikhon on 22/01/16.
 */
public class FragmentSavings extends Fragment implements DialogAddSaving.OnSavingAddedListener {

    ArrayList<Saving> savings = new ArrayList<>();
    SQLiteDatabase db;
    DatabaseHelper helper;
    RecyclerView recyclerViewSavings;
    AdapterSavings adapter;
    LinearLayoutManager layoutManager;
    FloatingActionButton fab;

    public FragmentSavings() {}

    public static FragmentSavings newInstance() {
        FragmentSavings fragment = new FragmentSavings();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_savings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSavingsDelete:
                helper.deleteAllSavings(db);
                helper.getSavings(db, savings);
                adapter.notifyDataSetChanged();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DatabaseHelper(getActivity());
        db = helper.getWritableDatabase();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_savings, container, false);
        helper.getSavings(db, savings);
        fab = (FloatingActionButton) v.findViewById(R.id.fabSavings);
        recyclerViewSavings = (RecyclerView) v.findViewById(R.id.recyclerViewSavings);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new AdapterSavings(savings);
        recyclerViewSavings.setLayoutManager(layoutManager);
        recyclerViewSavings.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddSaving dialogAddSaving = DialogAddSaving.newInstance();
                dialogAddSaving.setOnSavingAddedListener(FragmentSavings.this);
                dialogAddSaving.showDialog(FragmentSavings.this);
            }
        });
        return v;
    }

    @Override
    public void onSavingAdded(Saving saving) {
        helper.addSaving(db, saving);
        helper.getLastSaving(db, savings);
        adapter.notifyDataSetChanged();
    }
}
