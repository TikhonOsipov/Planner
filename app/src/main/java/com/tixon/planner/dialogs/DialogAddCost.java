package com.tixon.planner.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.tixon.planner.Cost;
import com.tixon.planner.R;
import com.tixon.planner.adapters.AdapterCosts;
import com.tixon.planner.database.DatabaseHelper;

/**
 * Created by tikhon on 19/01/16.
 */
public class DialogAddCost extends DialogFragment {

    public interface OnCostAddedListener {
        void onCostAdded(Cost cost);
    }

    private OnCostAddedListener onCostAddedListener;

    public void setOnCostAddedListener(OnCostAddedListener listener) {
        this.onCostAddedListener = listener;
    }

    public static final String TAG_ADD_COST_DIALOG = "add_cost_dialog";

    public DialogAddCost() {}

    public static DialogAddCost newInstance() {
        DialogAddCost dialog = new DialogAddCost();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        return dialog;
    }

    public void showDialog(Fragment fragment) {
        this.show(fragment.getChildFragmentManager().beginTransaction(),
                TAG_ADD_COST_DIALOG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_cost, null);

        final EditText etName = (EditText) v.findViewById(R.id.costName);
        final EditText etValue = (EditText) v.findViewById(R.id.costValue);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Новый расход")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onCostAddedListener.onCostAdded(new Cost(etName.getText().toString(),
                                Double.parseDouble(etValue.getText().toString()), System.currentTimeMillis()));
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        builder.setView(v);
        return builder.create();
    }
}
