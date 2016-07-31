package com.gamik.pastify.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gamik.pastify.model.DataItem;
import com.gamik.pastify.util.Date;
import com.gamik.pastify.callback.DialogCallback;
import com.gamik.pastify.R;
import com.gamik.pastify.store.Store;

public class AddDialog extends DialogFragment {

    private AlertDialog dialog;

    private TextView placeHolder;

    private TextView value;

    private TextView label;

    private Button cancelButton;

    private Button addButton;

    private DialogCallback callback;

    private TextInputLayout inputLayoutPlaceHolder;

    private TextInputLayout inputLayoutValue;

    private int operation = 0;

    private int id;

    private Store store;

    private String placeHolderText;

    public void setCallback(DialogCallback callback) {
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.add_dialog, null);
        /* Inflate and set the layout for the rootView */
        /* Pass null as the parent view because its going in the rootView layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        submitForm();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        store = new Store(getContext());
        initViews(rootView);
        dialog = builder.create();
        return dialog;
    }

    private void initViews(View rootView) {
        placeHolderText = "";
        String valueText = "";
        try {
            placeHolderText = getArguments().getString("placeholder");
            valueText = getArguments().getString("value");
            id = getArguments().getInt("id");
        } catch (Exception e) {

        }
        inputLayoutPlaceHolder = (TextInputLayout) rootView.findViewById(R.id.input_layout_name);
        inputLayoutValue = (TextInputLayout) rootView.findViewById(R.id.input_layout_description);
        placeHolder = (TextView) rootView.findViewById(R.id.placeholder);
        value = (TextView) rootView.findViewById(R.id.value);
        label = (TextView) rootView.findViewById(R.id.label);
        //cancelButton = (Button) rootView.findViewById(R.id.btn_cancel_purchase);
        placeHolder.addTextChangedListener(new MyTextWatcher(placeHolder));
        value.addTextChangedListener(new MyTextWatcher(value));
        //addButton = (Button) rootView.findViewById(R.id.btn_add_purchase);
        if (!valueText.equals("")) {
            operation = 1;
            placeHolder.setText(placeHolderText);
            value.setText(valueText);
            label.setText("EDIT ITEM");
        }
    }

    private DataItem getDataFromViews() {
        DataItem item = new DataItem(placeHolder.getText().toString(), value.getText().toString(), id, 0, Date.getDate());
        return item;
    }

    private boolean validateName() {
        if (placeHolder.getText().toString().trim().isEmpty()) {
            inputLayoutPlaceHolder.setError("Item name cannot be empty");
            return false;
        } else {
            if (operation == 1 && !placeHolderText.equals(placeHolder.getText().toString())) {
                Cursor cursor = store.getData(placeHolder.getText().toString().trim());
                if (cursor.getCount() > 0) {
                    inputLayoutPlaceHolder.setError("PlaceHolder already exist");
                    return false;
                }
            } else if (operation == 0) {
                Cursor cursor = store.getData(placeHolder.getText().toString().trim());
                if (cursor.getCount() > 0) {
                    inputLayoutPlaceHolder.setError("PlaceHolder already exist");
                    return false;
                }
            } else
                inputLayoutValue.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateDescription() {
        String description = value.getText().toString().trim();
        if (description.isEmpty()) {
            inputLayoutValue.setError("Item description cannot be empty");
            return false;
        } else {
            inputLayoutValue.setErrorEnabled(false);
        }
        return true;
    }

    private void submitForm() {
        int check = 0;
        if (validateName()) {
            check++;
        }
        if (validateDescription()) {
            check++;
        }
        if (check == 2) {
            Store store = new Store(getContext());
            DataItem dataItem = getDataFromViews();
            if (operation == 1) {
                store.updateDataById(dataItem.getPlaceHolder(), dataItem.getValue(), id);
            } else {
                store.saveData(dataItem.getPlaceHolder(), dataItem.getValue(), dataItem.getDate());
            }
            callback.onItemAdded();

        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.placeholder:
                    validateName();
                    break;
                case R.id.value:
                    validateDescription();
                    break;
            }
        }
    }
}

