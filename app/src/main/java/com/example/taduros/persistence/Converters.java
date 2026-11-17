package com.example.taduros.persistence;

import androidx.room3.ColumnTypeConverter;

import java.time.LocalDate;

public class Converters {

    // O SQLite não tem tipo de data, então o LocalDate é gravado como o número
    // de dias desde 01/01/1970 (epoch day). Isso mantém o ORDER BY date correto.
    @ColumnTypeConverter
    public static LocalDate fromEpochDay(Long value) {
        return value == null ? null : LocalDate.ofEpochDay(value);
    }

    @ColumnTypeConverter
    public static Long dateToEpochDay(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }
}
