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

public class LoanAmountEMI extends Fragment {
    private static final String TAG;

    static {
        TAG = "LoanAmountEMI";
    }

    private final BigDecimal TWELVE = new BigDecimal("12");
    private final BigDecimal HUNDRED = new BigDecimal("100");
    private final TextWatcher textWatcher;
    View view;
    private boolean isInvoiceShowroom;
    private boolean isDownPayment;
    private boolean isPCVariable;
    private boolean isPCFixed;
    private boolean isInterestRate;
    private boolean isFlatTenure;
    private boolean isAdvanceEMI;
    private boolean isLoanAmount;
    private boolean isInvoiceCIPL;
    private boolean isLTV;
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
                    case (R.id.invoiceShowroom):
                        isInvoiceShowroom = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.downPayment):
                        isDownPayment = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.pcPercent):
                        isPCVariable = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.pcFixed):
                        isPCFixed = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.interestRate):
                        isInterestRate = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.flatTenure):
                        isFlatTenure = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.advanceEmi):
                        isAdvanceEMI = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.invoiceCIPL):
                        isInvoiceCIPL = editable.length() > 0;
                        calculateValues();
                        break;
                    case (R.id.shortEMI):
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
        view = inflater.inflate(R.layout.la_emi_layout, container, false);
        setFontGothicBody(view.findViewById(R.id.la_emi_table));
        addTextWatchers(view);
        return view;
    }

    private void addTextWatchers(View view) {
        ((EditText) view.findViewById(R.id.invoiceShowroom)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.downPayment)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.pcPercent)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.pcFixed)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.interestRate)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.flatTenure)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.advanceEmi)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.invoiceCIPL)).addTextChangedListener(textWatcher);
        ((EditText) view.findViewById(R.id.shortEMI)).addTextChangedListener(textWatcher);
    }

    private void calculateValues() {
        isLoanAmount = isAdvanceEMI && isInvoiceShowroom && isDownPayment && isPCVariable && isPCFixed && isInterestRate && isFlatTenure ? calculateLoanAmount() : isLoanAmount ? setEmpty(R.id.loanAmount) : isLoanAmount;
        isLTV = isInvoiceCIPL && isLoanAmount ? calculateLTV() : isLTV ? setEmpty(R.id.ltv) : isLTV;
        isPMTTenure = isAdvanceEMI && isFlatTenure ? calculatePMTTenure() : isPMTTenure ? setEmpty(R.id.pmtTenure) : isPMTTenure;
        isEMI = isInterestRate && isLoanAmount && isFlatTenure ? calculateEMI() : isEMI ? setEmpty(R.id.emi) : isEMI;
    }

    private boolean calculateEMI() {
        BigDecimal denominator = new BigDecimal(((EditText) view.findViewById(R.id.flatTenure)).getText().toString());
        BigDecimal loanAmount = new BigDecimal(((EditText) view.findViewById(R.id.loanAmount)).getText().toString());
        BigDecimal interestRate = new BigDecimal(((EditText) view.findViewById(R.id.interestRate)).getText().toString());
        BigDecimal flatTenure = new BigDecimal(((EditText) view.findViewById(R.id.flatTenure)).getText().toString());
        if (isShortEMI) {
            BigDecimal shortEMI = new BigDecimal(((EditText) view.findViewById(R.id.shortEMI)).getText().toString());
            denominator = denominator.subtract(shortEMI);
        }
        BigDecimal numerator = loanAmount.multiply(BigDecimal.ONE.add(interestRate.divide(HUNDRED, 10, BigDecimal.ROUND_HALF_EVEN).multiply(flatTenure.divide(TWELVE, 10, BigDecimal.ROUND_HALF_EVEN))));
        BigDecimal emi = numerator.divide(denominator, 10, BigDecimal.ROUND_HALF_EVEN);
        ((EditText) view.findViewById(R.id.emi)).setText(String.valueOf((int) Math.ceil(emi.doubleValue())));
        return true;
    }

    private boolean setEmpty(int viewId) {
        ((EditText) view.findViewById(viewId)).setText("");
        return false;
    }

    private boolean calculateLTV() {
        BigDecimal invoiceCIPL = new BigDecimal(((EditText) view.findViewById(R.id.invoiceCIPL)).getText().toString());
        BigDecimal loanAmount = new BigDecimal(((EditText) view.findViewById(R.id.loanAmount)).getText().toString());
        BigDecimal ltv = loanAmount.divide(invoiceCIPL, 10, BigDecimal.ROUND_HALF_EVEN).multiply(HUNDRED);
        ((EditText) view.findViewById(R.id.ltv)).setText(String.valueOf((int) Math.ceil(ltv.doubleValue())));
        return true;
    }

    private boolean calculatePMTTenure() {
        Integer advanceEmi = Integer.valueOf(((EditText) view.findViewById(R.id.advanceEmi)).getText().toString());
        Integer tenure = Integer.valueOf(((EditText) view.findViewById(R.id.flatTenure)).getText().toString());
        if (isShortEMI) {
            Integer shortEMI = Integer.valueOf(((EditText) view.findViewById(R.id.shortEMI)).getText().toString());
            ((EditText) view.findViewById(R.id.pmtTenure)).setText(String.valueOf(tenure - advanceEmi - shortEMI));
        } else {
            ((EditText) view.findViewById(R.id.pmtTenure)).setText(String.valueOf(tenure - advanceEmi));
        }
        return true;
    }

    private boolean calculateLoanAmount() {
        BigDecimal advanceEmi = new BigDecimal(((EditText) view.findViewById(R.id.advanceEmi)).getText().toString());
        BigDecimal invoiceShowRoom = new BigDecimal(((EditText) view.findViewById(R.id.invoiceShowroom)).getText().toString());
        BigDecimal downPayment = new BigDecimal(((EditText) view.findViewById(R.id.downPayment)).getText().toString());
        BigDecimal pcFixed = new BigDecimal(((EditText) view.findViewById(R.id.pcFixed)).getText().toString());
        BigDecimal pcPercent = new BigDecimal(((EditText) view.findViewById(R.id.pcPercent)).getText().toString());
        BigDecimal intRate = new BigDecimal(((EditText) view.findViewById(R.id.interestRate)).getText().toString());
        BigDecimal tenure = new BigDecimal(((EditText) view.findViewById(R.id.flatTenure)).getText().toString());
        BigDecimal numerator = invoiceShowRoom.subtract(downPayment.subtract(pcFixed));
        BigDecimal denomPart1 = BigDecimal.ONE.add(intRate.multiply(tenure).divide(TWELVE.multiply(HUNDRED), 10, BigDecimal.ROUND_HALF_EVEN));
        denomPart1 = denomPart1.multiply(advanceEmi).divide(tenure, 10, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal denominator = BigDecimal.ONE.subtract(pcPercent.divide(HUNDRED, 10, BigDecimal.ROUND_HALF_EVEN)).subtract(denomPart1);
        BigDecimal loanAmount = numerator.divide(denominator, 10, BigDecimal.ROUND_HALF_EVEN);
        loanAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        ((EditText) view.findViewById(R.id.loanAmount)).setText(String.valueOf((int) Math.ceil(loanAmount.doubleValue())));
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
