package com.chee.util;

import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class DoubleUtil implements Serializable {
    private static final long serialVersionUID = -3345205828566485102L;
    private static final Integer DEF_DIV_SCALE = Integer.valueOf(2);

    public static Double add(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return Double.valueOf(b1.add(b2).doubleValue());
    }

    public static double sub(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.subtract(b2).doubleValue();
    }

    public static Double mul(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return Double.valueOf(b1.multiply(b2).doubleValue());
    }

    public static Double div(Double dividend, Double divisor) {
        return div(dividend, divisor, DEF_DIV_SCALE);
    }

    public static Double div(Double dividend, Double divisor, Integer scale) {
        if (scale.intValue() < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor.doubleValue()));
        return Double.valueOf(b1.divide(b2, scale.intValue(), 4).doubleValue());
    }

    public static Double round(Double value, Integer scale) {
        if (scale.intValue() < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(value.doubleValue()));
        BigDecimal one = new BigDecimal("1");
        return Double.valueOf(b.divide(one, scale.intValue(), 4).doubleValue());
    }
}
