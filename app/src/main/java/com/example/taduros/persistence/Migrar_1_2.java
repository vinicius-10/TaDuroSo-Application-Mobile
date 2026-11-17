package com.example.taduros.persistence;

import androidx.annotation.NonNull;
import androidx.room3.migration.Migration;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

public class Migrar_1_2 extends Migration {

    public Migrar_1_2() {
        super(1, 2);
    }

    @Override
    public Object migrate(@NonNull SQLiteConnection connection, @NonNull Continuation<? super Unit> continuation) {

        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `Expense_provisorio` (" +
                                   " `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                                   " `appellant` INTEGER NOT NULL," +
                                   " `date` INTEGER NOT NULL," +
                                   " `name` TEXT NOT NULL," +
                                   " `value` INTEGER NOT NULL," +
                                   " `category` INTEGER NOT NULL," +
                                   " `payment_method` INTEGER NOT NULL)");

        SQLite.execSQL(connection, "INSERT INTO Expense_provisorio (id, appellant, date, name, value, category, payment_method) " +
                                   "SELECT id, appellant, date, name, value, " +
                                   "CASE " +
                                   "WHEN category = 'essential'     THEN 0 " +
                                   "WHEN category = 'non_essential' THEN 1 " +
                                   "ELSE 0 " +
                                   "END, " +
                                   "payment_method " +
                                   "FROM Expense");

        SQLite.execSQL(connection, "DROP TABLE Expense");

        SQLite.execSQL(connection, "ALTER TABLE Expense_provisorio RENAME TO Expense");

        return Unit.INSTANCE;
    }
}
