package com.example.taduros;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taduros.model.Category_expense;
import com.example.taduros.model.Expense;
import com.example.taduros.persistence.ExpenseDatabase;
import com.example.taduros.utils.UtilsDate;
import com.example.taduros.utils.UtilsAlert;
import com.example.taduros.utils.UtilsValue;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;


public class MerActivity extends AppCompatActivity {


    public static  final String KEY_ID = "ID";

    public static final String KEY_MOD = "MODO";

    public static final String KEY_PAYMENT_SUGESTION = "PAYMENT_SUGESTION";

    public static final String KEY_PAYMENT_VALUE = "PAYMENT_VALUE";
    public static final int MOD_NEW = 0;
    public static final int MOD_EDIT = 1;

    private EditText mer_ett_name, mer_ett_value, mer_ett_date;
    private CheckBox mer_cb_appellant;
    private RadioGroup mer_rg_category;
    private Spinner mer_sp_payment_method;

    private RadioButton mer_rb_essential;
    private RadioButton mer_rb_non_essential;
    private int mod;


    private boolean payment_sugestion = false;
    private int payment_value = 0;

    private Expense expense_origin;

    private LocalDate expense_date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mer);


        mer_cb_appellant        =   findViewById(R.id.mer_cb_appellant);
        mer_ett_date            =   findViewById(R.id.mer_ett_date);
        mer_ett_name            =   findViewById(R.id.mer_ett_name);
        mer_ett_value           =   findViewById(R.id.mer_ett_value);
        mer_rg_category         =   findViewById(R.id.mer_rg_category);
        mer_sp_payment_method   =   findViewById(R.id.mer_sp_payment_method);
        mer_rb_essential        =   findViewById(R.id.mer_rb_essential);
        mer_rb_non_essential    =   findViewById(R.id.mer_rb_non_essential);


        mer_ett_date.setFocusable(false);
        mer_ett_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatePickerDialog();
            }
        });


        readPreference();

        Intent intent_open = getIntent();

        Bundle bundle = intent_open.getExtras();

        // readPreference(); // chamada duplicada: as preferências já são lidas acima
        if(bundle != null){
            mod = bundle.getInt(KEY_MOD);

            if(mod == MOD_NEW){
                setTitle(getString(R.string.title_mer));

                if (payment_sugestion){
                    mer_sp_payment_method.setSelection(payment_value);
                }

                expense_date = LocalDate.now();


            }else{
                setTitle(getString(R.string.mer_edit_expense));

                long id = bundle.getLong(KEY_ID);

                ExpenseDatabase database = ExpenseDatabase.getInstance(this);

                expense_origin =database.getExpenseDao().queryForId(id);

                String name = expense_origin.getName();
                long value = expense_origin.getValue();
                expense_date = expense_origin.getDate();
                Category_expense category = expense_origin.getCategory();
                int payment_method = expense_origin.getPayment_method();
                boolean applellant = expense_origin.isAppellant();



                String str_value;

                str_value = UtilsValue.formatValue(value);


                mer_ett_name.setText(name);
                mer_ett_value.setText(str_value);

                mer_sp_payment_method.setSelection(payment_method);

                mer_cb_appellant.setChecked(applellant);

                if(category == Category_expense.essential){
                    mer_rb_essential.setChecked(true);
                }else if(category == Category_expense.non_essential){
                    mer_rb_non_essential.setChecked(true);
                }

                mer_ett_name.requestFocus();
                mer_ett_name.setSelection(mer_ett_name.getText().length());
            }
            mer_ett_date.setText(UtilsDate.formatDate(expense_date));
        }


    }

    private void displayDatePickerDialog(){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                expense_date = LocalDate.of(year,month +1,dayOfMonth);

                mer_ett_date.setText(UtilsDate.formatDate(expense_date));
            }
        };



        DatePickerDialog picker = new DatePickerDialog(this,listener, expense_date.getYear(),expense_date.getMonthValue()-1,expense_date.getDayOfMonth());

        picker.show();
    }

    public void clearFields(){

        final String name       = mer_ett_name.getText().toString();
        final String value      = mer_ett_value.getText().toString();
        final String date       = mer_ett_date.getText().toString();
        final boolean appellant  = mer_cb_appellant.isChecked();
        final int category_id   = mer_rg_category.getCheckedRadioButtonId();
        final int payment_method = mer_sp_payment_method.getSelectedItemPosition();


        final View view = findViewById(R.id.main);
        final View viewFocus =view.findFocus();


        mer_cb_appellant        .setChecked(false);
        mer_ett_date            .setText("");
        mer_ett_name            .setText("");
        mer_ett_value           .setText("");
        mer_rg_category         .clearCheck();
        mer_sp_payment_method   .setSelection(0);


        mer_ett_name.requestFocus();

        Snackbar snackbar = Snackbar.make(
                view,
                R.string.alert_clear_fields,
                Snackbar.LENGTH_LONG
        );

        snackbar.setAction(R.string.undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mer_cb_appellant        .setChecked(appellant);
                mer_ett_date            .setText(date);
                mer_ett_name            .setText(name);
                mer_ett_value           .setText(value);
                mer_sp_payment_method   .setSelection(payment_method);

                if(category_id == R.id.mer_rb_essential){
                    mer_rb_essential.setChecked(true);
                }else if(category_id == R.id.mer_rb_non_essential){
                    mer_rb_non_essential.setChecked(true);
                }

                if(viewFocus != null){
                    viewFocus.requestFocus();
                }
            }
        });

        snackbar.show();

    }

    public void submitForm(){
        String  name,
                str_value,
                str_date;
        int     goup_category,payment_method;
        long    value;
        boolean applellant;
        Category_expense category;

        //valiadar o nome
        name = mer_ett_name.getText().toString();

        if(name == null || name.trim().isEmpty()){

            UtilsAlert.showAlert(this,R.string.alert_name_empty );
            mer_ett_name.requestFocus();
            return;
        }
        name = name.trim();

        //validar e converter o valor
        str_value = mer_ett_value.getText().toString();
        if(str_value == null || str_value.trim().isEmpty()){
            UtilsAlert.showAlert(this, R.string.alert_empty_value);
            mer_ett_value.requestFocus();
            return;
        }

        try {
            value = UtilsValue.parseValue(str_value);
        }catch (NumberFormatException e){
             UtilsAlert.showAlert(this,
                    R.string.alert_value_invalid);
            mer_ett_value.requestFocus();
            return;
        }

        //validar e converter a data
        str_date = mer_ett_date.getText().toString();
        if(str_date == null || str_date.trim().isEmpty()){
             UtilsAlert.showAlert(this,
                    R.string.alert_date_empty);
            mer_ett_date.requestFocus();
            return;
        }

        //validar o raio buton
        goup_category = mer_rg_category.getCheckedRadioButtonId();

        if(goup_category == -1){
             UtilsAlert.showAlert(this,
                    R.string.alert_category_empty);
            return;
        }

        if(goup_category == R.id.mer_rb_essential){
            category = Category_expense.essential;
        } else if (goup_category == R.id.mer_rb_non_essential) {
            category = Category_expense.non_essential;
        }else{
             UtilsAlert.showAlert(this,
                    R.string.alert_type_invalid);
            return;
        }

        //recuperar o spiner
        payment_method = mer_sp_payment_method.getSelectedItemPosition();

        //recuperar o checbox
        applellant = mer_cb_appellant.isChecked();

        Expense expense =  new Expense(applellant, expense_date, name, value, category, payment_method);

        if (expense.equals(expense_origin)){
            setResult(MerActivity.RESULT_CANCELED);
            finish();
            return;
        }

        savePaymentPreference(payment_method);

        Intent intentResponse = new Intent();

        ExpenseDatabase database = ExpenseDatabase.getInstance(this);

        if(mod == MOD_NEW){
            long new_id = database.getExpenseDao().insert(expense);

            if(new_id <= 0){
                UtilsAlert.showAlert(this,getString(R.string.alert_saver_insert_error));
                return;
            }

            expense.setId(new_id);

        }else {
            expense.setId(expense_origin.getId());

            int row_affected = database.getExpenseDao().update(expense);

            if(row_affected != 1){
                UtilsAlert.showAlert(this, getString(R.string.alert_uptade_expense_error));
                return;
            }
        }

        intentResponse.putExtra(KEY_ID, expense.getId());
        setResult(MerActivity.RESULT_OK,intentResponse);

        finish();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mer_menu,menu );
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.mer_mni_sugestion_payment);

        item.setChecked(payment_sugestion);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menu_item = item.getItemId();

        if(menu_item == R.id.mer_mni_clear){
            clearFields();
            return  true;
        }else if(menu_item == R.id.mer_mni_save){
            submitForm();
            return true;
        }else if (menu_item == R.id.mer_mni_sugestion_payment){
            boolean value = !item.isChecked();
            savePaymentSugestion(value);

            item.setChecked(value);

            if (payment_sugestion){
                mer_sp_payment_method.setSelection(payment_value);
            }
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }


    public void readPreference(){
        SharedPreferences shared = getSharedPreferences(ListExpense.FILE_PREFERENCES, Context.MODE_PRIVATE);

        payment_sugestion = shared.getBoolean(KEY_PAYMENT_SUGESTION, false);
        payment_value = shared.getInt(KEY_PAYMENT_VALUE, 0);
    }

    public void savePaymentSugestion(boolean new_value){
        SharedPreferences shared = getSharedPreferences(ListExpense.FILE_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editer = shared.edit();

        editer.putBoolean(KEY_PAYMENT_SUGESTION,new_value);

        editer.commit();

        payment_sugestion = new_value;
    }

    public void savePaymentPreference(int new_value){
        SharedPreferences shared = getSharedPreferences(ListExpense.FILE_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editer = shared.edit();

        editer.putInt(KEY_PAYMENT_VALUE,new_value);

        editer.commit();

        payment_value = new_value;
    }

}