package com.example.azizrafsanjani.numericals.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.azizrafsanjani.numericals.R;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setTitle("About Mathemagi-Calc");
    }
}
