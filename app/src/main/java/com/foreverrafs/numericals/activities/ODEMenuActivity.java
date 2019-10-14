package com.foreverrafs.numericals.activities;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter;
import com.foreverrafs.numericals.fragments.ordinary_differential_eqns.FragmentEuler;
import com.foreverrafs.numericals.model.OperationMenu;
import com.foreverrafs.numericals.utils.Constants;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ODEMenuActivity extends AppCompatActivity implements OperationsMenuAdapter.MenuItemClickListenener {

    @BindView(R.id.list_main_menu)
    RecyclerView mainMenuItems;

    @BindView(R.id.tvHeader)
    TextView header;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_menu);

        ButterKnife.bind(this);

        mainMenuItems.setLayoutManager(new LinearLayoutManager(this));

        List<OperationMenu> operations = new ArrayList<>();
        operations.add(new OperationMenu("Euler's Forward Method", R.drawable.button_ode_euler, Constants.ODE_EULER));


        header.setText(getString(R.string.ode_header));

        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);

        adapter.setOnItemClickListenener(this);

        mainMenuItems.setHasFixedSize(true);
        mainMenuItems.setAdapter(adapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void OnMenuItemClicked(int menuItemType) {
        if (menuItemType == Constants.ODE_EULER) {
            Fragment fragment = new FragmentEuler();
            Utilities.replaceFragment(fragment, getSupportFragmentManager(), R.id.fragmentContainer);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
