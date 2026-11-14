package com.example.taduros;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import com.example.taduros.model.Category_expense;
import com.example.taduros.model.Expense;
import com.example.taduros.persistence.ExpenseDatabase;
import com.example.taduros.utils.UtilsDate;
import com.example.taduros.utils.UtilsAlert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;

public class ListExpense extends AppCompatActivity {

    private ListView le_listView;

    private List<Expense> ls_expense;

    private ExpenseAdapter expenseAdapter;


    private int selected_expense = -1;

    private View view_selected;
    private Drawable background_drawable;
    private ActionMode actionMode;

    private boolean order_increasing = true;

    private MenuItem menuItemOrder;

    public static final String FILE_PREFERENCES = "com.example.taduros.PREFERENCES";
    public static final String KEY_ORDER_PREFERENCE = "ORDER_LIST";


    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.ls_expense_selected, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int menu_iten = item.getItemId();
            AdapterView.AdapterContextMenuInfo info;

            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            if(menu_iten == R.id.le_mni_delet){
                delet_expense();

                return  true;
            }else if(menu_iten == R.id.lle_mni_edit){
                edit_expense();

                return true;
            }else{
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(view_selected != null){
                view_selected.setBackground(background_drawable);

            }

            actionMode = null;
            view_selected = null;
            background_drawable = null;

            le_listView.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expense);

        setTitle(getString(R.string.title_lisExpense));

        le_listView = findViewById(R.id.le_listView);

        readPreference();

        le_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Expense expense = (Expense) le_listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                                getString(R.string.alert_save_name) + expense.getName() + "\n" +
                                getString(R.string.alert_save_value) + expense.getValue() + "\n" +
                                getString(R.string.alert_save_date) + UtilsDate.formatDate(expense.getDate()),
                            Toast.LENGTH_LONG).show();
            }
        });
        populate_list();

        //orderList();
        //registerForContextMenu(le_listView);
    }

    private void populate_list(){


        ExpenseDatabase database = ExpenseDatabase.getInstance(this);


        if(order_increasing){
            ls_expense = database.getExpenseDao().queryAllDateAscending();
        }else{
            ls_expense = database.getExpenseDao().queryAllDateDownward();
        }




        expenseAdapter = new ExpenseAdapter(this, ls_expense);

        le_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(actionMode != null){
                    return false;
                }
                selected_expense = position;

                view_selected = view;
                background_drawable = view.getBackground();

                view.setBackgroundColor(getColor(R.color.ListSelected));

                le_listView.setEnabled(false);

                actionMode = startSupportActionMode(callback);

                return true;
            }
        });

        le_listView.setAdapter(expenseAdapter);
    }

    ActivityResultLauncher<Intent> laucherNewExtense = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == MerActivity.RESULT_OK){
                    Intent itent = result.getData();

                    Bundle bundle = itent.getExtras();

                    if(bundle != null) {
                        long id = bundle.getLong(MerActivity.KEY_ID);

                        ExpenseDatabase database = ExpenseDatabase.getInstance(ListExpense.this);

                        Expense expense = database.getExpenseDao().queryForId(id);

                        ls_expense.add(expense);

                        orderList();
                    }
                }
            }
        });

    public void aboutOpen(){
        Intent intentAbout = new Intent(this, About.class);

        startActivity(intentAbout);
    }

    public void merOpen(){
        Intent intentOpen = new Intent(this, MerActivity.class);
        intentOpen.putExtra(MerActivity.KEY_MOD, MerActivity.MOD_NEW);
        laucherNewExtense.launch(intentOpen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expense_menu,menu);

        menuItemOrder = menu.findItem(R.id.mng_order_list);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        updateIconOrder();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id_menu = item.getItemId();

        if(id_menu == R.id.mng_about){
            aboutOpen();
            return true;
        }else if(id_menu == R.id.mng_register){
            merOpen();
            return true;
        }else if(id_menu == R.id.mng_order_list){
            saveOrder(!order_increasing);
            orderList();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    public void delet_expense(){

        final Expense expense = ls_expense.get(selected_expense);

        String menssage = getString(R.string.alert_expense_delet) + getString(R.string.mer_tv_name) + expense.getName() + getString(R.string.mer_tv_date) + UtilsDate.formatDate(expense.getDate());

        DialogInterface.OnClickListener listenerYes = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ExpenseDatabase database = ExpenseDatabase.getInstance(ListExpense.this);

                int row_affected = database.getExpenseDao().delet(expense);

                if(row_affected != 1){
                    UtilsAlert.showAlert(ListExpense.this, getString(R.string.alert_delet_expense_error));
                    return;
                }

                ls_expense.remove(selected_expense);
                expenseAdapter.notifyDataSetChanged();
                actionMode.finish();

                Toast.makeText(getApplicationContext(),
                        getString(R.string.alert_deleted),
                        Toast.LENGTH_LONG).show();
            }
        };

        UtilsAlert.confirmAction(this,menssage, listenerYes, null);


    }

    ActivityResultLauncher<Intent> laucherEditExtense = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == MerActivity.RESULT_OK){
                        Intent itent = result.getData();

                        Bundle bundle = itent.getExtras();

                        if(bundle != null) {
                            long id = bundle.getLong(MerActivity.KEY_ID);

                            ExpenseDatabase database = ExpenseDatabase.getInstance(ListExpense.this);

                            Expense expense = database.getExpenseDao().queryForId(id);

                            // Procura pelo id: selected_expense se perde ao girar a tela
                            for (int i = 0; i < ls_expense.size(); i++) {
                                if (ls_expense.get(i).getId() == id) {
                                    ls_expense.set(i, expense);
                                    break;
                                }
                            }
                            orderList();
                        }
                    }

                    selected_expense = -1;

                    if(actionMode != null){
                        actionMode.finish();
                    }

                }
            });

    public void edit_expense(){
        Expense expense = ls_expense.get(selected_expense);

        Intent intentOpen = new Intent(this, MerActivity.class);
        intentOpen.putExtra(MerActivity.KEY_MOD, MerActivity.MOD_EDIT);

        intentOpen.putExtra(MerActivity.KEY_ID, expense.getId());

        laucherEditExtense.launch(intentOpen);
    }

    public void readPreference(){
        SharedPreferences shared = getSharedPreferences(FILE_PREFERENCES, Context.MODE_PRIVATE);

        order_increasing = shared.getBoolean(KEY_ORDER_PREFERENCE, order_increasing);
    }
    public void saveOrder(boolean new_value){
        SharedPreferences shared = getSharedPreferences(FILE_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editer = shared.edit();

        editer.putBoolean(KEY_ORDER_PREFERENCE,new_value);

        editer.commit();

        order_increasing = new_value;
    }

    private  void orderList(){
        if(order_increasing){
            Collections.sort(ls_expense,Expense.increasing_order_date);
        }else{
            Collections.sort(ls_expense,Expense.decreasing_order_date);
        }
        updateIconOrder();
        expenseAdapter.notifyDataSetChanged();
    }

    private void updateIconOrder(){
        if(menuItemOrder == null){
            return; // menu ainda não criado; onPrepareOptionsMenu atualiza depois
        }
        if(order_increasing){
            menuItemOrder.setIcon(R.drawable.ic_increasing_order);
        }else{
            menuItemOrder.setIcon(R.drawable.ic_decreasing_order);
        }
    }

}