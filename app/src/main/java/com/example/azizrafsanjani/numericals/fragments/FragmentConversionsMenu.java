package com.example.azizrafsanjani.numericals.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentConversionsMenu extends Fragment implements View.OnClickListener {


    private View rootView;
    static TextView header;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_conversions_menu, container, false);
        initControls();
        MainActivity.setToolBarInfo(getResources().getString(R.string.app_name), getResources().getString(R.string.app_description));

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initControls() {
        //items = rootView.findViewById(R.id.listItems);
        //items.setOnItemClickListener(this);

        //items.setLayoutManager(new LinearLayoutManager(getContext()));

        header = rootView.findViewById(R.id.Header);
        header.setVisibility(View.VISIBLE);

        rootView.findViewById(R.id.btn_dec_to_binary_integer).setOnClickListener(this);
        rootView.findViewById(R.id.btn_dec_to_binary_fraction).setOnClickListener(this);
        rootView.findViewById(R.id.btn_dec_to_binary_any_number).setOnClickListener(this);
        rootView.findViewById(R.id.btn_bin_to_decimal).setOnClickListener(this);
        rootView.findViewById(R.id.btn_decimal_to_octal).setOnClickListener(this);
        rootView.findViewById(R.id.btn_decimal_to_hexadecimal).setOnClickListener(this);

        // TextView footer = rootView.findViewById(R.id.footer);
        Typeface footerTf = Typeface.createFromAsset(getContext().getAssets(), "fonts/WorkSans-Regular.ttf");


        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lobster-Regular.ttf");
        header.setTypeface(typeface);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.btn_dec_to_binary_integer:
                fragment = new FragmentDecToBinInt();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer, false);
                break;

            case R.id.btn_dec_to_binary_fraction:
                fragment = new FragmentDecToBinFrac();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer, false);
                break;

            case R.id.btn_dec_to_binary_any_number:
                fragment = new FragmentDecToBin();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer, false);
                break;
        }
    }
}
