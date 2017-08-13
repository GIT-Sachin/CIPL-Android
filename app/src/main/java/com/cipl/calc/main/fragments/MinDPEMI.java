package com.cipl.calc.main.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cipl.calc.main.R;

import java.math.BigDecimal;

/**
 * Created by sachin on 30/07/17.
 */

public class MinDPEMI extends Fragment {
    private static final String TAG;

    static {
        TAG = "MinDPEMI";
    }

    private final BigDecimal TWELVE = new BigDecimal("12");
    private final BigDecimal HUNDRED = new BigDecimal("100");
    private final TextWatcher textWatcher;
    View view;
    private boolean isInvoiceShowroom;
    private boolean isInvoiceCIPL;
    private boolean isMaxLTV;
    private boolean isPCVariable;
    private boolean isPCFixed;
    private boolean isInterestRate;
    private boolean isFlatTenure;
    private boolean isAdvanceEMI;
    private boolean isLoanAmount;
    private boolean isMinDP;
    private boolean isShortEMI;
    private boolean isEMI;
    private boolean isPMTTenure;

    {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                EditText currentView = (EditText) getActivity().getCurrentFocus();
                switch (currentView.getId()) {
                    case (R.id.invoiceshowroom1):
                        isInvoiceShowroom = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.maxltv):
                        isMaxLTV = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.pcvariable1):
                        isPCVariable = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.pcfixed1):
                        isPCFixed = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.interestrate1):
                        isInterestRate = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.flattenure1):
                        isFlatTenure = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.advanceemi1):
                        isAdvanceEMI = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.invoicecipl1):
                        isInvoiceCIPL = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.shortemi1):
                        isShortEMI = editable.length() > 0;
                        calculateValues();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mindp_emi_layout, container, false);
        setFontGothicBody(view.findViewById(R.id.mindp_emi_table));
        addTextWatchers(view);
        return view;
    }

    private void addTextWatchers(View view) {
        ((EditText) view.findViewById(R.id.invoiceshowroom1)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.invoicecipl1)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.pcvariable1)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.pcfixed1)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.interestrate1)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.flattenure1)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.advanceemi1)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.maxltv)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.shortemi1)).addTextChangedListener(textWatcher);
    }

    private void calculateValues() {
        isLoanAmount = isInvoiceCIPL && isMaxLTV ? calculateLoanAmount() : isLoanAmount ? setEmpty(R.id.loanamount1) : isLoanAmount;
        isEMI = isInterestRate && isLoanAmount && isFlatTenure ? calculateEMI() : isEMI ? setEmpty(R.id.emi1) : isEMI;
        isMinDP = isInvoiceShowroom && isLoanAmount && isPCVariable && isEMI && isAdvanceEMI && isPCFixed ? calculateMinDP() : isMinDP ? setEmpty(R.id.mindp) : isMinDP;
        isPMTTenure = isAdvanceEMI && isFlatTenure ? calculatePMTTenure() : isPMTTenure ? setEmpty(R.id.pmtTenure1) : isPMTTenure;
    }

    private boolean calculateMinDP() {
        BigDecimal invoiceShowroom = new BigDecimal(((EditText) view.findViewById(R.id.invoiceshowroom1)).getText().toString());
        BigDecimal loanAmount = new BigDecimal(((EditText) view.findViewById(R.id.loanamount1)).getText().toString());
        BigDecimal pcv = new BigDecimal(((EditText) view.findViewById(R.id.pcvariable1)).getText().toString());
        BigDecimal pcf = new BigDecimal(((EditText) view.findViewById(R.id.pcfixed1)).getText().toString());
        BigDecimal emi = new BigDecimal(((EditText) view.findViewById(R.id.emi1)).getText().toString());
        BigDecimal advemi;
        if (isAdvanceEMI) {
            advemi = new BigDecimal(((EditText) view.findViewById(R.id.advanceemi1)).getText().toString());
        } else {
            advemi = BigDecimal.ZERO;
        }
        BigDecimal minDP = invoiceShowroom.subtract(loanAmount).add(loanAmount.multiply(pcv.divide(HUNDRED, 10, BigDecimal.ROUND_HALF_EVEN))).add(emi.multiply(advemi)).add(pcf);
        ((EditText) view.findViewById(R.id.mindp)).setText(String.valueOf((int) Math.ceil(minDP.doubleValue())));
        return true;

    }

    private boolean calculateLoanAmount() {
        BigDecimal invoiceCIPL = new BigDecimal(((EditText) view.findViewById(R.id.invoicecipl1)).getText().toString());
        BigDecimal maxLTV = new BigDecimal(((EditText) view.findViewById(R.id.maxltv)).getText().toString());
        BigDecimal loanAmount = invoiceCIPL.multiply(maxLTV).divide(HUNDRED, 10, BigDecimal.ROUND_HALF_EVEN);
        ((EditText) view.findViewById(R.id.loanamount1)).setText(String.valueOf((int) Math.ceil(loanAmount.doubleValue())));
        return true;

    }

    private boolean calculateEMI() {
        BigDecimal denominator = new BigDecimal(((EditText) view.findViewById(R.id.flattenure1)).getText().toString());
        BigDecimal loanAmount = new BigDecimal(((EditText) view.findViewById(R.id.loanamount1)).getText().toString());
        BigDecimal interestRate = new BigDecimal(((EditText) view.findViewById(R.id.interestrate1)).getText().toString());
        BigDecimal flatTenure = new BigDecimal(((EditText) view.findViewById(R.id.flattenure1)).getText().toString());
        if (isShortEMI) {
            BigDecimal shortEMI = new BigDecimal(((EditText) view.findViewById(R.id.shortemi1)).getText().toString());
            denominator = denominator.subtract(shortEMI);
        }
        BigDecimal numerator = loanAmount.multiply(BigDecimal.ONE.add(interestRate.divide(HUNDRED, 10, BigDecimal.ROUND_HALF_EVEN).multiply(flatTenure.divide(TWELVE, 10, BigDecimal.ROUND_HALF_EVEN))));
        BigDecimal emi = numerator.divide(denominator, 10, BigDecimal.ROUND_HALF_EVEN);
        ((EditText) view.findViewById(R.id.emi1)).setText(String.valueOf((int) Math.ceil(emi.doubleValue())));
        return true;
    }

    private boolean setEmpty(int viewId) {
        ((EditText) view.findViewById(viewId)).setText("");
        return false;
    }

    private boolean calculatePMTTenure() {
        Integer advanceEmi = Integer.valueOf(((EditText) view.findViewById(R.id.advanceemi1)).getText().toString());
        Integer tenure = Integer.valueOf(((EditText) view.findViewById(R.id.flattenure1)).getText().toString());
        if (isShortEMI) {
            Integer shortEMI = Integer.valueOf(((EditText) view.findViewById(R.id.shortemi1)).getText().toString());
            ((EditText) view.findViewById(R.id.pmtTenure1)).setText(String.valueOf(tenure - advanceEmi - shortEMI));
        } else {
            ((EditText) view.findViewById(R.id.pmtTenure1)).setText(String.valueOf(tenure - advanceEmi));
        }
        return true;
    }


    private void setFontGothicBody(View viewById) {
        Typeface gothic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/centurygothic.TTF");
        if (viewById instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) viewById;
            int children = vg.getChildCount();
            for (int i = 0; i < children; i++) {
                setFontGothicBody(vg.getChildAt(i));
            }
        } else if (viewById instanceof TextView) {
            TextView tv = (TextView) viewById;
            tv.setTypeface(gothic);
        }
    }
}
