package com.example.taduros.persistence;

import android.content.Context;

import androidx.room3.ColumnTypeConverters;
import androidx.room3.Database;
import androidx.room3.Room;
import androidx.room3.RoomDatabase;

import com.example.taduros.model.Expense;



@Database(entities = {Expense.class}, version =  1, exportSchema = false)
@ColumnTypeConverters(Converters.class)
public abstract class ExpenseDatabase extends RoomDatabase {

    public abstract ExpenseDao getExpenseDao();
    private static ExpenseDatabase INSTANCE;

    protected ExpenseDatabase() {
    }

    public static ExpenseDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (ExpenseDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,
                                                    ExpenseDatabase.class,
                                                    "expense.db"
                    ).allowMainThreadQueries().build();
                }
            }
        }

        return  INSTANCE;
    }


}
