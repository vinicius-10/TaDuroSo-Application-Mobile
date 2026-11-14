package com.example.taduros.persistence;

import androidx.room3.Dao;
import androidx.room3.Delete;
import androidx.room3.Insert;
import androidx.room3.Query;
import androidx.room3.Update;

import com.example.taduros.model.Expense;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    long  insert(Expense expense);

    @Delete
    int delet(Expense expense);

    @Update
    int update(Expense expense);


    @Query("SELECT * FROM expense ORDER BY date ASC")
    List<Expense> queryAllDateAscending();

    @Query("SELECT * FROM expense ORDER BY date DESC")
    List<Expense> queryAllDateDownward();

    @Query("SELECT * FROM expense WHERE id=:id")
    Expense queryForId(long id);


}
