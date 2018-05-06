package com.example.azizrafsanjani.numericals.fragments.roots;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.Numericals;
import com.example.azizrafsanjani.numericals.utils.Utilities;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentSecante extends Fragment implements View.OnClickListener, TextWatcher {

    View rootView;
    ViewGroup viewGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_secante, container, false);
        initControls();
        return rootView;
    }

    public void initControls() {
        Button btnCalculate = rootView.findViewById(R.id.buttonCalculate);
        Button btnBack = rootView.findViewById(R.id.buttonBack);
        EditText etEquation = rootView.findViewById(R.id.text_equation);
        EditText etX0 = rootView.findViewById(R.id.x0);
        EditText etX1 = rootView.findViewById(R.id.x1);
        EditText etIterations = rootView.findViewById(R.id.text_iterations);
        EditText etEpsilon = rootView.findViewById(R.id.text_epsilon);


        Utilities.setLobsterTypeface(rootView.findViewById(R.id.headerText), getContext());
        Utilities.setItalicTypeface(etEquation, getContext());

        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        etEquation.addTextChangedListener(this);
        etX0.addTextChangedListener(this);
        etX1.addTextChangedListener(this);
        etEpsilon.addTextChangedListener(this);
        etIterations.addTextChangedListener(this);


        viewGroup = (LinearLayout) rootView.findViewById(R.id.parentContainer);
        MainActivity.setToolBarInfo("Location of Roots", "Secante Method");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonBack:
                Utilities.replaceFragment(new FragmentLocationOfRootsMenu(), getFragmentManager(), R.id.fragmentContainer, true);
                break;

            case R.id.buttonCalculate:
                Log.i(Utilities.Log, "performing bisection calculation");
                onCalculate();
                break;

        }
    }

    private void onCalculate() {
        EditText etEquation = rootView.findViewById(R.id.text_equation);
        EditText etX0 = rootView.findViewById(R.id.x0);
        EditText etX1 = rootView.findViewById(R.id.x1);
        EditText etEpsilon = rootView.findViewById(R.id.text_epsilon);
        EditText etIterations = rootView.findViewById(R.id.text_iterations);

        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);

        try {
            String eqn = etEquation.getText().toString();
            Double x0 = Double.valueOf(etX0.getText().toString());
            Double x1 = Double.valueOf(etX1.getText().toString());
            // Double tol = Double.valueOf(etEpsilon.getText().toString());
            int iter = Integer.valueOf(etIterations.getText().toString());

            if (eqn.trim().contains("=0")) {
                Toast.makeText(getContext(), "Equation malformed: Please take out the  = 0", Toast.LENGTH_LONG).show();
                Log.i(Utilities.Log, "Equation is malformed");
                return;
            }
            double root = Numericals.Secante(eqn, x0, x1, iter);

            if (Double.isNaN(root) || Double.isInfinite(root)) {
                Toast.makeText(getContext(), "Syntax Error: Please check equation", Toast.LENGTH_LONG).show();
                Log.i(Utilities.Log, "Syntax error, unable to evaluate expression");
                return;
            }

            tvAnswer.setText(String.valueOf(root));

            Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.SHOW);

        } catch (NumberFormatException ex) {
            Toast.makeText(getContext(), "One or more of the input values are invalid", Toast.LENGTH_LONG).show();
            Log.i(Utilities.Log, "One or more of the input values are invalid");
        } finally {
            MainActivity.hideKeyboard(etEquation);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onEquationChanged();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onEquationChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        onEquationChanged();
    }

    private void onEquationChanged() {
        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);

        Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.HIDE);
    }
}
