package com.example.taduros.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class UtilsDate {

    // Retorna o formatador configurado para o idioma/região atual do dispositivo
    private static DateFormat getFormatter() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        df.setLenient(false); // Garante validação estrita (ex: rejeita 31/02)
        return df;
    }

    /**
     * Converte de Date (sistema) para String (exibição do usuário)
     */
    public static String formatDate(Date date) {
        if (date == null) return "";
        return getFormatter().format(date);
    }

    /**
     * Converte de String (digitado pelo usuário) para Date (sistema)
     * Throws ParseException se o texto estiver fora do formato do idioma do usuário
     */
    public static Date parseDate(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        return getFormatter().parse(dateStr);
    }
}