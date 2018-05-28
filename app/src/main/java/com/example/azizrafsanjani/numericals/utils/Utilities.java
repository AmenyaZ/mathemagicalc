package com.example.azizrafsanjani.numericals.utils;


import android.content.Context;
import android.graphics.Typeface;
import android.support.transition.Fade;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public final class Utilities {

    public static final String Log = "TAG";

    public static void replaceFragment(Fragment next, FragmentManager fragmentManager, int containerViewId, boolean isGoingBack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!isGoingBack)
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        else
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        //Fade enterFade = new Fade();
        //enterFade.setDuration(500);

        //next.setEnterTransition(enterFade);
        transaction.replace(containerViewId, next);
        transaction.commit();
    }

    public static void setLobsterTypeface(View view, Context mCtx) {
        //cast the view to a TextView
        try {
            TextView tv = (TextView) view;
            tv.setTypeface(Typeface.createFromAsset(mCtx.getAssets(), "fonts/Lobster-Regular.ttf"));

        } catch (ClassCastException ex) {
            EditText editText = (EditText) view;
            editText.setTypeface(Typeface.createFromAsset(mCtx.getAssets(), "fonts/Lobster-Regular.ttf"));
        }

    }

    public static void setItalicTypeface(View view, Context mCtx) {
        try {
            TextView tv = (TextView) view;
            tv.setTypeface(Typeface.createFromAsset(mCtx.getAssets(), "fonts/Bitter-Italic.ttf"));
        } catch (ClassCastException ex) {
            EditText editText = (EditText) view;
            editText.setTypeface(Typeface.createFromAsset(mCtx.getAssets(), "fonts/Bitter-Italic.ttf"));
        }
    }

    public static void replaceFragment(Fragment next, FragmentManager fragmentManager, int containerViewId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fade enterFade = new Fade();
        enterFade.setDuration(300);
        //Fade enterFade = new Fade();
        //enterFade.setDuration(500);

        next.setEnterTransition(enterFade);
        transaction.replace(containerViewId, next);
        transaction.commit();
    }

    public static void loadFragment(Fragment fragment, FragmentManager fragmentManager, int containerViewId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }


    public static void animateAnswer(View answerView, ViewGroup viewGroup, DisplayMode displayMode) {

        switch (displayMode) {
            case SHOW:
                TransitionSet set = new TransitionSet()
                        .addTransition(new Scale(0.7f))
                        .addTransition(new com.transitionseverywhere.Fade())
                        .setInterpolator(new LinearOutSlowInInterpolator());

                TransitionManager.beginDelayedTransition(viewGroup);
                answerView.setVisibility(View.VISIBLE);
                //  TransitionManager.beginDelayedTransition(viewGroup, set);

                break;

            case HIDE:
                TransitionManager.beginDelayedTransition(viewGroup);
                answerView.setVisibility(View.GONE);
                break;
        }
    }


    public enum DisplayMode {
        SHOW,
        HIDE
    }
}
