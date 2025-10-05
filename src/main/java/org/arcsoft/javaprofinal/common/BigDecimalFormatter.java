package org.arcsoft.javaprofinal.common;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@UtilityClass
public class BigDecimalFormatter {

    private static final DecimalFormat FORMAT;

    static {
        FORMAT = new DecimalFormat();

        FORMAT.setMaximumFractionDigits(2);

        FORMAT.setMinimumFractionDigits(0);

        FORMAT.setGroupingUsed(false);
    }

    public static String format(BigDecimal amount) {
        return FORMAT.format(amount);
    }
}
