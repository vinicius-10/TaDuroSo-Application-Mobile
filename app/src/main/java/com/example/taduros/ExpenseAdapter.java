package com.example.taduros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.taduros.model.Expense;
import com.example.taduros.utils.UtilsDate;

import java.util.List;


public class ExpenseAdapter  extends BaseAdapter {

    private Context context;
    private List<Expense> ls_expense;

    private static class ExpenseHolder{
        public TextView lle_tv_field_name, lle_tv_field_value, lle_tv_field_essential, lle_tv_field_date;


    }

    public ExpenseAdapter(Context context, List<Expense> ls_expense) {
        this.context = context;
        this.ls_expense = ls_expense;
    }

    @Override
    public int getCount() {
        return ls_expense.size();
    }

    @Override
    public Object getItem(int position) {
        return ls_expense.get((position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ExpenseHolder holder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_list_expense, parent, false);

            holder = new ExpenseHolder();

            holder.lle_tv_field_name = convertView.findViewById(R.id.lle_tv_field_name);
            holder.lle_tv_field_date = convertView.findViewById(R.id.lle_tv_field_date);
            holder.lle_tv_field_essential = convertView.findViewById(R.id.lle_tv_field_essential);
            holder.lle_tv_field_value = convertView.findViewById(R.id.lle_tv_field_value);

            convertView.setTag(holder);

        }else{
            holder = (ExpenseHolder) convertView.getTag();
        }

        Expense expense = ls_expense.get(position);

        holder.lle_tv_field_name.setText(expense.getName());

        String str_value = Float.toString(expense.getValue());

        holder.lle_tv_field_value.setText( str_value);

        holder.lle_tv_field_date.setText(UtilsDate.formatDate(expense.getDate()));

        switch (expense.getCategory()){
            case essential:
                holder.lle_tv_field_essential.setText(R.string.mer_rb_essential);
                break;

            case non_essential:
                holder.lle_tv_field_essential.setText(R.string.mer_rb_non_essential);
                break;
        }

        return convertView;
    }
}
