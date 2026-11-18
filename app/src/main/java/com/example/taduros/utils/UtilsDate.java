package com.example.taduros.utils;

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

public class UtilsDate {

    /*// Retorna o formatador configurado para o idioma do app
    private static DateTimeFormatter getFormatter(Context context) {
        return DateTimeFormatter.ofPattern(context.getString(R.string.date_pattern))
                .withResolverStyle(ResolverStyle.STRICT);
    }*/

    public static String formatDate(LocalDate date) {
        if (date == null) return "";

        String formatPattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(FormatStyle.SHORT,
                                                                                    null,
                                                                                    IsoChronology.INSTANCE,
                                                                                    Locale.getDefault());

        formatPattern = formatPattern.replaceAll("\\byy\\b", "yyyy");
        formatPattern = formatPattern.replaceAll("\\bM\\b", "MM");
        formatPattern = formatPattern.replaceAll("\\bd\\b", "dd");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern, Locale.getDefault());

        return date.format(formatter);
    }

    /*public static LocalDate parseDate(Context context, String dateStr) throws DateTimeParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        return LocalDate.parse(dateStr.trim(), getFormatter(context));
    }*/
}
