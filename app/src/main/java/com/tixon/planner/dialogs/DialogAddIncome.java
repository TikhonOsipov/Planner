package com.tixon.planner.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.tixon.planner.Cost;
import com.tixon.planner.Income;
import com.tixon.planner.R;

/**
 * Created by tikhon on 19/01/16.
 */
public class DialogAddIncome extends DialogFragment {

    public interface OnIncomeAddedListener {
        void onIncomeAdded(Income income);
    }

    private OnIncomeAddedListener onIncomeAddedListener;

    public void setOnIncomeAddedListener(OnIncomeAddedListener listener) {
        this.onIncomeAddedListener = listener;
    }

    public static final String TAG_ADD_INCOME_DIALOG = "add_income_dialog";

    public DialogAddIncome() {}

    public static DialogAddIncome newInstance() {
        DialogAddIncome dialog = new DialogAddIncome();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        return dialog;
    }

    public void showDialog(Fragment fragment) {
        this.show(fragment.getChildFragmentManager().beginTransaction(), TAG_ADD_INCOME_DIALOG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_income, null);

        final EditText etName = (EditText) v.findViewById(R.id.incomeName);
        final EditText etValue = (EditText) v.findViewById(R.id.incomeValue);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Новый доход")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onIncomeAddedListener.onIncomeAdded(new Income(etName.getText().toString(),
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
