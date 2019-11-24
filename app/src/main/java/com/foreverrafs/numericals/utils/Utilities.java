package com.foreverrafs.numericals.utils;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionManager;

import com.foreverrafs.numericals.fragments.FragmentShowAlgorithm;

import java.util.List;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public final class Utilities {
    public static void replaceFragment(Fragment next, FragmentManager fragmentManager,
                                       int container) {
        String name = fragmentManager.getClass().getSimpleName();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.replace(container, next)
                .addToBackStack(name)
                .commit();
    }

    public static void animateAnswer(View answerView, View rootView, DisplayMode displayMode) {
        switch (displayMode) {
            case SHOW:
                TransitionManager.beginDelayedTransition((ViewGroup) rootView);
                answerView.setVisibility(View.VISIBLE);
                hideKeyboard(rootView);
                break;

            case HIDE:
                TransitionManager.beginDelayedTransition((ViewGroup) rootView);
                answerView.setVisibility(View.GONE);
                break;
        }

    }

    public static double[] toPrimitiveDouble(List<Double> list) {
        double[] intArray = new double[list.size()];
        for (int i = 0; i < list.size(); i++)
            intArray[i] = list.get(i);
        return intArray;
    }

    public static void showAlgorithmScreen(Context c, String algoName) {
        if (algoName.isEmpty())
            algoName = "index";
        Bundle bundle = new Bundle();
        bundle.putString("algorithm_name", algoName);

        c.startActivity(new Intent(c, FragmentShowAlgorithm.class).putExtras(bundle));
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public enum DisplayMode {
        SHOW,
        HIDE
    }
}

/*
    LATEX TEXTS
    JACOBI'S METHOD
    $$x^{(k)}_{1} = \frac{1}{a_{11}}[b_{1} - (a_{12}x^{(k-1)}_{2})+a_{13}x^{(k-1)}_{3}] \newline\newline

    x^{(k)}_{2} = \frac{1}{a_{22}}[b_{2} - (a_{21}x^{(k-1)}_{1})+a_{23}x^{(k-1)}_{3}] \newline\newline

    x^{(k)}_{3} = \frac{1}{a_{33}}[b_{3} - (a_{31}x^{(k-1)}_{1})+a_{32}x^{(k-1)}_{2}]

    GAUSS SEIDEL'S METHOD
 */
