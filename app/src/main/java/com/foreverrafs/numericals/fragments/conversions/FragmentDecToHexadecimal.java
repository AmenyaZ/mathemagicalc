package com.foreverrafs.numericals.fragments.conversions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.utils.Utilities;
//import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentDecToHexadecimal extends ConversionsBase {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initControls();
    }

    @Override
    protected void initControls() {
        super.initControls();

        setHeader(getString(R.string.dec_to_hexadecimal));
        setDescription(getString(R.string.decimal_to_hexadecimal_desc));
        setInputHint(getString(R.string.decimal_int_input_hint));
        setMethodName("dectohexa");

    }

    @Override
    protected void onCalculate() {
        String decimal = etInput.getText().toString();
        if (decimal.isEmpty()) {
            showErrorMessage("Input cannot be empty", false);
            return;
        }

        try {
            double decLong = Double.parseDouble(decimal);

            if (decLong <= 0) {
                showErrorMessage("Input must be greater than 0!", false);
                return;
            }

            String hexadecimal = Numericals.decimalToHexadecimal(decimal);

            tvAnswer.setText(hexadecimal);

           displayAnswer();


        } catch (NumberFormatException ex) {
            Log.e(Utilities.LOG_TAG, "cannot parse " + decimal + " to an integer value");
        } catch (Exception ex) {
            Log.e(Utilities.LOG_TAG, ex.getMessage());
        }
    }
}
