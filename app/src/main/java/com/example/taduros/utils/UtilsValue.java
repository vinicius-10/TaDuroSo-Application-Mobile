package com.example.taduros.utils;

import java.math.BigDecimal;

public final class UtilsValue {

    /* Os valores em dinheiro são guardados em centavos (long), por exemplo R$ 12,50 = 1250.
       Assim não há perda de precisão, como acontece com float e double. */

    private UtilsValue(){
    }

    /**
     * Converte o texto digitado pelo usuário (ex: "12,50") para centavos (ex: 1250)
     * Throws NumberFormatException se o texto não for um valor válido com até 2 casas decimais
     */
    public static long parseValue(String str_value) throws NumberFormatException {
        BigDecimal value = new BigDecimal(str_value.trim().replace(",", "."));

        try {
            return value.movePointRight(2).longValueExact();
        } catch (ArithmeticException e) {
            throw new NumberFormatException(str_value);
        }
    }

    /**
     * Converte de centavos (ex: 1250) para texto de exibição (ex: "12.50")
     */
    public static String formatValue(long value) {
        return BigDecimal.valueOf(value, 2).toPlainString();
    }
}
