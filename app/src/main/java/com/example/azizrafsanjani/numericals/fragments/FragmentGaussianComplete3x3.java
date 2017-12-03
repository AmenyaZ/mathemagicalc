package com.example.azizrafsanjani.numericals.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentGaussianComplete3x3 extends Fragment implements View.OnClickListener, View.OnKeyListener, TextWatcher {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gaussian_complete3x3, container, false);
        MainActivity.setToolBarInfo("System of Equations", "Gaussian Elimination (Complete Pivoting)");

        initControls();
        return rootView;
    }

    private void initControls() {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/FallingSky.otf");
        TextView tvAnswer = (TextView) rootView.findViewById(R.id.textview_answer);
        tvAnswer.setTypeface(typeface);

        Button btnBack = (Button) rootView.findViewById(R.id.buttonBack);
        Button btnCalculate = (Button) rootView.findViewById(R.id.buttonCalculate);


        btnBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonBack:
                Utilities.replaceFragment(this, new FragmentEquationsMenu(), getFragmentManager(), R.id.fragmentContainer);
                break;

            case R.id.buttonCalculate:
                Log.i(Utilities.Log, "Solving gaussian with complete pivoting");
                onCalculate();
                break;
        }
    }

    private void onCalculate() {
        getMatrices();
    }


    private void getMatrices() {
        EditText[][] etA = new EditText[3][3];
        double a[][] = new double[3][3];

        EditText[] etB = new EditText[3];
        double b[] = new double[3];

        EditText[] etX = new EditText[3];


        etA[0][0] = (EditText) rootView.findViewById(R.id.a11);
        etA[0][1] = (EditText) rootView.findViewById(R.id.a12);
        etA[0][2] = (EditText) rootView.findViewById(R.id.a13);
        etA[1][0] = (EditText) rootView.findViewById(R.id.a21);
        etA[1][1] = (EditText) rootView.findViewById(R.id.a22);
        etA[1][2] = (EditText) rootView.findViewById(R.id.a23);
        etA[2][0] = (EditText) rootView.findViewById(R.id.a31);
        etA[2][1] = (EditText) rootView.findViewById(R.id.a32);
        etA[2][2] = (EditText) rootView.findViewById(R.id.a33);

        etB[0] = (EditText) rootView.findViewById(R.id.b1);
        etB[1] = (EditText) rootView.findViewById(R.id.b2);
        etB[2] = (EditText) rootView.findViewById(R.id.b3);

        etX[0] = (EditText) rootView.findViewById(R.id.x1);

        for (int i = 0; i < etA.length; i++) {
            for (int j = 0; j < etA.length; j++) {
                try {
                    a[i][j] = Double.parseDouble(etA[i][j].getText().toString());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                b[i] = Double.parseDouble(etB[i].getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }


        //get the solution matrix
        double[] solution = Numericals.GaussianWithCompletePivoting(a, b);


        //our previous matrices have been mutated so we can represent them on the textviews
        TextView[][] tvA = new TextView[3][3];
        tvA[0][0] = (TextView) rootView.findViewById(R.id.sa11);
        tvA[0][1] = (TextView) rootView.findViewById(R.id.sa12);
        tvA[0][2] = (TextView) rootView.findViewById(R.id.sa13);
        tvA[1][0] = (TextView) rootView.findViewById(R.id.sa21);
        tvA[1][1] = (TextView) rootView.findViewById(R.id.sa22);
        tvA[1][2] = (TextView) rootView.findViewById(R.id.sa23);
        tvA[2][0] = (TextView) rootView.findViewById(R.id.sa31);
        tvA[2][1] = (TextView) rootView.findViewById(R.id.sa32);
        tvA[2][2] = (TextView) rootView.findViewById(R.id.sa33);


        TextView[] tvX = new TextView[3];
        TextView[] tvB = new TextView[3];

        tvB[0] = (TextView) rootView.findViewById(R.id.sab1);
        tvB[1] = (TextView) rootView.findViewById(R.id.sab2);
        tvB[2] = (TextView) rootView.findViewById(R.id.sab3);

        tvX[0] = (TextView) rootView.findViewById(R.id.sax1);
        tvX[1] = (TextView) rootView.findViewById(R.id.sax2);
        tvX[2] = (TextView) rootView.findViewById(R.id.sax3);

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                tvA[i][j].setText(String.valueOf(a[i][j]));
            }
            tvX[i].setText(String.valueOf(solution[i]));
            tvB[i].setText(String.valueOf(b[i]));
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        Utilities.animateAnswer((TextView) rootView.findViewById(R.id.textview_answer),
                (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
    }
}
