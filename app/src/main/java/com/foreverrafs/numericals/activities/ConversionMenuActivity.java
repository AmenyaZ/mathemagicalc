package com.foreverrafs.numericals.activities;


import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter;
import com.foreverrafs.numericals.custom_views.RafsTextView;
import com.foreverrafs.numericals.fragments.conversions.FragmentAllInOne;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToBin;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToBinFrac;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToBinInt;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToHexadecimal;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToOctal;
import com.foreverrafs.numericals.model.OperationMenu;
import com.foreverrafs.numericals.utils.Constants;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ConversionMenuActivity extends AppCompatActivity implements OperationsMenuAdapter.MenuItemClickListenener {

    @BindView(R.id.list_main_menu)
    RecyclerView mainMenuItems;

    @BindView(R.id.headerTextView)
    RafsTextView header;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        mainMenuItems.setLayoutManager(new GridLayoutManager(this, 2));

        List<OperationMenu> operations = new ArrayList<>();
        operations.add(new OperationMenu("Dec to Bin(Fraction)", R.drawable.button_location_of_roots, Constants.CONVERSION_DEC_TO_BIN_FRACTION));
        operations.add(new OperationMenu("Dec to Bin(Integer)", R.drawable.button_location_of_roots, Constants.CONVERSION_DEC_TO_BIN_INT));
        operations.add(new OperationMenu("Dec to Bin(Any Number)", R.drawable.button_location_of_roots, Constants.CONVERSION_DEC_TO_BIN_ALL));
        operations.add(new OperationMenu("Bin to Decimal", R.drawable.button_location_of_roots, Constants.CONVERSION_BIN_TO_DEC));
        operations.add(new OperationMenu("Dec to Hexa", R.drawable.button_location_of_roots, Constants.CONVERSION_DEC_TO_HEXA));
        operations.add(new OperationMenu("Dec to Octal", R.drawable.button_location_of_roots, Constants.CONVERSION_DEC_TO_OCTAL));
        operations.add(new OperationMenu("All in One Conversion", R.drawable.button_location_of_roots, Constants.CONVERSION_ALL_IN_ONE));


        header.setText("Numerical Conversion");

        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);

        adapter.setOnItemClickListenener(this);

        mainMenuItems.setHasFixedSize(true);
        mainMenuItems.setAdapter(adapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void OnMenuItemClicked(int menuItemType) {

        switch (menuItemType) {
            case Constants.CONVERSION_DEC_TO_BIN_FRACTION:
                Utilities.replaceFragment(new FragmentDecToBinFrac(), getSupportFragmentManager(), R.id.fragmentContainer);
                break;

            case Constants.CONVERSION_DEC_TO_BIN_INT:
                Utilities.replaceFragment(new FragmentDecToBinInt(), getSupportFragmentManager(), R.id.fragmentContainer);
                break;

            case Constants.CONVERSION_ALL_IN_ONE:
                Utilities.replaceFragment(new FragmentAllInOne(), getSupportFragmentManager(), R.id.fragmentContainer);
                break;

            case Constants.CONVERSION_DEC_TO_BIN_ALL:
                Utilities.replaceFragment(new FragmentDecToBin(), getSupportFragmentManager(), R.id.fragmentContainer);
                break;

            case Constants.CONVERSION_DEC_TO_HEXA:
                Utilities.replaceFragment(new FragmentDecToHexadecimal(), getSupportFragmentManager(), R.id.fragmentContainer);
                break;

            case Constants.CONVERSION_DEC_TO_OCTAL:
                Utilities.replaceFragment(new FragmentDecToOctal(), getSupportFragmentManager(), R.id.fragmentContainer);
                break;
        }
    }
}
