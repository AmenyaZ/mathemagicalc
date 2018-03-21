package com.example.azizrafsanjani.numericals.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.Numericals;
import com.example.azizrafsanjani.numericals.utils.Utilities;

public class FragmentDecToBinInt extends Fragment implements Button.OnClickListener, View.OnKeyListener, TextWatcher {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dec_to_bin_int, container, false);
        MainActivity.setToolBarInfo("Decimal Calculator", "Convert decimals to binary");

        initControls();
        return rootView;
    }

    private void initControls() {
        Button button = rootView.findViewById(R.id.buttonBack);
        Button btnCalculate = rootView.findViewById(R.id.buttonCalculate);
        EditText etInput = rootView.findViewById(R.id.text_user_input);

        etInput.addTextChangedListener(this);

        etInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    onCalculate();
                }
                return true;
            }
        });

        etInput.setOnKeyListener(this);

        button.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonBack:
                Utilities.replaceFragment(this, new FragmentMainMenu(), getFragmentManager(), R.id.fragmentContainer);

                break;

            case R.id.buttonCalculate:

                onCalculate();
                break;
        }
    }

    public void onCalculate() {
        EditText etInput = rootView.findViewById(R.id.text_user_input);
        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);


        String decimal = etInput.getText().toString();
        if (decimal.isEmpty()) {
            Toast.makeText(getContext(), "Input field is empty", Toast.LENGTH_LONG).show();
            return;
        }


        try {
            int decInt = Integer.parseInt(decimal);

            if (decInt < 1) {
                Toast.makeText(getContext(), "Number should be greater than 1", Toast.LENGTH_LONG).show();
                return;
            }


            String binary = Numericals.DecimalIntToBinary(decInt);

            if (binary.length() >= 21) {
                binary = binary.substring(0, 20);
                Toast.makeText(getContext(), "Answer truncated to 20 significant figures", Toast.LENGTH_LONG).show();
            }

            tvAnswer.setText(binary);
            Utilities.animateAnswer(tvAnswer, (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);

        } catch (NumberFormatException ex) {
            Log.i(Utilities.Log, "cannot parse " + decimal + " to a double value");
        } finally {
            MainActivity.hideKeyboard(etInput);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            onCalculate();
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 0) {
            Utilities.animateAnswer(rootView.findViewById(R.id.textview_answer),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        }
    }
}
