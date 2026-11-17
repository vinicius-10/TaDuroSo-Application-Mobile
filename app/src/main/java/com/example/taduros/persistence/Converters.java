package com.example.taduros.persistence;

import androidx.room3.ColumnTypeConverter;

import com.example.taduros.model.Category_expense;

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

    public static Category_expense[] categories = Category_expense.values();

    @ColumnTypeConverter
    public static int fromEnumToInt(Category_expense category) {

        if (category == null) {
            return -1;
        }

        return category.ordinal();
    }

    @ColumnTypeConverter
    public static Category_expense fromIntToEnum(int ordinal) {

        if (ordinal < 0) {
            return null;
        }

        return categories[ordinal];
    }
}
