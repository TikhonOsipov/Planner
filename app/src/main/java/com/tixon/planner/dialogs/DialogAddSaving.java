package com.tixon.planner.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.tixon.planner.R;
import com.tixon.planner.Saving;

/**
 * Created by tikhon on 22/01/16.
 */
public class DialogAddSaving extends DialogFragment {

    public interface OnSavingAddedListener {
        void onSavingAdded(Saving saving);
    }

    private OnSavingAddedListener onSavingAddedListener;

    public void setOnSavingAddedListener(OnSavingAddedListener listener) {
        this.onSavingAddedListener = listener;
    }

    private static final String TAG_ADD_SAVING_DIALOG = "add_saving_dialog";

    public DialogAddSaving() {}

    public static DialogAddSaving newInstance() {
        DialogAddSaving dialog = new DialogAddSaving();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        return dialog;
    }

    public void showDialog(Fragment fragment) {
        this.show(fragment.getChildFragmentManager().beginTransaction(), TAG_ADD_SAVING_DIALOG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_saving, null);

        final EditText etName = (EditText) v.findViewById(R.id.savingName);
        final EditText etValue = (EditText) v.findViewById(R.id.savingValue);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Новое сбережение")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onSavingAddedListener.onSavingAdded(new Saving(etName.getText().toString(),
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

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }
}
