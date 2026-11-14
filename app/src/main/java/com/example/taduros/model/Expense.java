package com.example.taduros.model;

import androidx.annotation.NonNull;
import androidx.room3.Entity;
import androidx.room3.PrimaryKey;

import com.example.taduros.utils.UtilsDate;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;


@Entity
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private boolean appellant;


    @NonNull
    private Date date;

    @NonNull
    private String name;

    @NonNull
    private Float value;
    @NonNull
    private Category_expense category;
    private int payment_method;



    public Expense(boolean appellant, @NonNull Date date, @NonNull String name, @NonNull Float value, @NonNull Category_expense category, int payment_method) {
        this.appellant = appellant;
        this.date = date;
        this.name = name;
        this.value = value;
        this.category = category;
        this.payment_method = payment_method;
    }

    public boolean isAppellant() {
        return appellant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAppellant(boolean appellant) {
        this.appellant = appellant;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Float getValue() {
        return value;
    }

    @NonNull
    public void setValue( @NonNull Float value) {
        this.value = value;
    }

    @NonNull
    public Category_expense getCategory() {
        return category;
    }
    @NonNull
    public void setCategory( @NonNull Category_expense category) {
        this.category = category;
    }

    public int getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(int payment_method) {
        this.payment_method = payment_method;
    }

    @NonNull
    @Override
    public String toString() {
        return  name  + '\n' +
                value + '\n' +
                UtilsDate.formatDate(date) + '\n' +
                category;

    }

    public static Comparator<Expense> increasing_order_date = new Comparator<Expense>() {
        @Override
        public int compare(Expense expense1, Expense expense2) {

            if(expense1.getDate().equals(expense2.getDate())) {
                return 0;
            }else if (expense1.getDate().before(expense2.getDate())) {
                return -1;
            }else {
                return  1;
            }
        }
    };

    public static Comparator<Expense> decreasing_order_date = new Comparator<Expense>() {
        @Override
        public int compare(Expense expense1, Expense expense2) {

            if(expense1.getDate().equals(expense2.getDate())) {
                return 0;
            }else if (expense1.getDate().before(expense2.getDate())) {
                return 1;
            }else {
                return  -1;
            }
        }
    };

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return appellant == expense.appellant &&
                payment_method == expense.payment_method &&
                date.equals(expense.date) &&
                name.equals(expense.name) &&
                value.equals(expense.value) &&
                category == expense.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(appellant, date, name, value, category, payment_method);
    }
}
