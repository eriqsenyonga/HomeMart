package com.plexosysconsult.homemart;

import android.content.Context;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 * Created by senyer on 6/13/2016.
 */
public class BigDecimalClass {

    Context context;

    public BigDecimalClass(Context context) {

        this.context = context;

    }

    public Long addParameters(String parameter1, String parameter2) {

        BigDecimal bd1 = new BigDecimal(parameter1);

        BigDecimal bd2 = new BigDecimal(parameter2);

        BigDecimal bdSum = bd1.add(bd2);

        Long sum = bdSum.longValue();

        return sum;

    }

    public Long subtractParam2FromParam1(String parameter1, String parameter2) {

        BigDecimal bd1 = new BigDecimal(parameter1);

        BigDecimal bd2 = new BigDecimal(parameter2);

        BigDecimal bdDifference = bd1.subtract(bd2);

        Long difference = bdDifference.longValue();

        return difference;


    }


    public Long multiplyParameters(String itemUnitPrice, Long quantity) {

        BigDecimal bd1 = new BigDecimal(itemUnitPrice);
        BigDecimal bd2 = new BigDecimal(quantity);


        BigDecimal bdProduct = bd1.multiply(bd2);

        Long product = bdProduct.longValue();

        return product;

    }

    public Long multiplyParameters(String itemUnitPrice, String quantity) {

        BigDecimal bd1 = new BigDecimal(itemUnitPrice);
        BigDecimal bd2 = new BigDecimal(quantity);


        BigDecimal bdProduct = bd1.multiply(bd2);

        Long product = bdProduct.longValue();

        return product;

    }

    public String convertLongToDisplayCurrencyString(Long total) {

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("UGX");
        dfs.setGroupingSeparator(',');
        dfs.setMonetaryDecimalSeparator('.');

        numberFormat.setMaximumFractionDigits(0);
        numberFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        ((DecimalFormat) numberFormat).setDecimalFormatSymbols(dfs);

        String displayString = numberFormat.format(total);

        return displayString;

    }

    public Long addParameters(Long parameter1, Long parameter2) {

        BigDecimal bd1 = new BigDecimal(parameter1);

        BigDecimal bd2 = new BigDecimal(parameter2);

        BigDecimal bdSum = bd1.add(bd2);

        Long sum = bdSum.longValue();

        return sum;
    }

    public String convertStringToDisplayCurrencyString(String s) {

        BigDecimal bd = new BigDecimal(s);

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("UGX");
        dfs.setGroupingSeparator(',');
        dfs.setMonetaryDecimalSeparator('.');

        numberFormat.setMaximumFractionDigits(0);
        numberFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        ((DecimalFormat) numberFormat).setDecimalFormatSymbols(dfs);

        String displayString = numberFormat.format(bd.longValue());

        return displayString;
    }
}
