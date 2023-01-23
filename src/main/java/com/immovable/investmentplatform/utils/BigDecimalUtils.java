package com.immovable.investmentplatform.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class BigDecimalUtils {
    public static boolean checkIf(BigDecimal firstNum, String operator, BigDecimal secondNum) {
        final BigDecimal fstNumRounded = firstNum.setScale(2, RoundingMode.HALF_UP);
        final BigDecimal secondNumRounded = secondNum.setScale(2, RoundingMode.HALF_UP);
         boolean isValid = false;
         switch (operator) {
            case "EQUALS" :
            	   isValid =fstNumRounded.compareTo(secondNumRounded) == 0;
            	break;
            case "NOTEQUALS" :
                 isValid =fstNumRounded.compareTo(secondNumRounded) != 0;
                 break;
            case "LESS_THAN" :
            isValid = fstNumRounded.compareTo(secondNumRounded) < 0;
            break;
            case "LESS_THAN_OR_EQUALS":
            isValid = fstNumRounded.compareTo(secondNumRounded) <= 0;
            break;
            case "GREATER_THAN" :
            isValid = fstNumRounded.compareTo(secondNumRounded) > 0;
            break;
            case  "GREATER_THAN_OR_EQUALS" :
            isValid = fstNumRounded.compareTo(secondNumRounded) >= 0;
            break;
        };
        
        return isValid;
    }

    public static boolean checkIfPositiveNonZero(BigDecimal num) {
        return checkNotNull(num) && checkIf(num, "GREATER_THAN", BigDecimal.ZERO);
    }

    public static boolean checkNotNull(BigDecimal num) {
        return num != null;
    }

}
