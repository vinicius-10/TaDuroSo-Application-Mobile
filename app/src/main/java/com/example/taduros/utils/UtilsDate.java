package com.example.taduros.utils;

import android.content.Context;

import com.example.taduros.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/*
 * LocalDate (java.time) representa só uma data do calendário (dia, mês e ano),
 * sem hora e sem fuso horário. Assim a data digitada pelo usuário não muda
 * quando o fuso do aparelho muda, o que acontecia com java.util.Date
 * (que guarda um instante no tempo, em milissegundos).
 *
 * O java.time só existe nativamente a partir da API 26. Como o minSdk é 24,
 * ele é liberado pelo "core library desugaring" configurado no build.gradle.
 */
public class UtilsDate {

    // Retorna o formatador usando o padrão do idioma do app (res/values*/strings.xml)
    private static DateTimeFormatter getFormatter(Context context) {
        // STRICT: exige o texto inteiro e rejeita datas inexistentes (ex: 31/02).
        // No modo STRICT o ano deve usar "uuuu" no padrão, pois "yyyy" exige a era.
        return DateTimeFormatter.ofPattern(context.getString(R.string.date_pattern))
                .withResolverStyle(ResolverStyle.STRICT);
    }

    /**
     * Converte de LocalDate (sistema) para String (exibição do usuário)
     */
    public static String formatDate(Context context, LocalDate date) {
        if (date == null) return "";
        return date.format(getFormatter(context));
    }

    /**
     * Converte de String (digitado pelo usuário) para LocalDate (sistema)
     * Lança DateTimeParseException se o texto estiver fora do formato do idioma do app
     */
    public static LocalDate parseDate(Context context, String dateStr) throws DateTimeParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        return LocalDate.parse(dateStr.trim(), getFormatter(context));
    }
}
