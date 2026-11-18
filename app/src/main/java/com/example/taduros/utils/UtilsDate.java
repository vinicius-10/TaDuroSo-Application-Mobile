package com.example.taduros.utils;

import android.content.Context;

import com.example.taduros.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class UtilsDate {

    // Retorna o formatador configurado para o idioma do app
    private static DateTimeFormatter getFormatter(Context context) {
        return DateTimeFormatter.ofPattern(context.getString(R.string.date_pattern))
                .withResolverStyle(ResolverStyle.STRICT);
    }


    public static String formatDate(Context context, LocalDate date) {
        if (date == null) return "";
        return date.format(getFormatter(context));
    }

    public static LocalDate parseDate(Context context, String dateStr) throws DateTimeParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        return LocalDate.parse(dateStr.trim(), getFormatter(context));
    }
}
