package com.example.taduros.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.taduros.R;

public final class UtilsAlert {

    private UtilsAlert(){
    }


    public static  void showAlert(Context context, String menssage, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.alert_warning);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(menssage);

        builder.setNeutralButton("OK", listener);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static  void showAlert(Context context, int id, DialogInterface.OnClickListener listener){
        showAlert(context,context.getString(id),listener);
    }

    public static  void showAlert(Context context, int id){
        showAlert(context,context.getString(id),null);
    }

    public static  void showAlert(Context context,String menssage){
        showAlert(context,menssage,null);
    }

    public static void confirmAction(Context context, String menssage, DialogInterface.OnClickListener listenerYes ,DialogInterface.OnClickListener listenerNot){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.alert_confirmation);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(menssage);

        builder.setPositiveButton(context.getString(R.string.yes), listenerYes);
        builder.setNegativeButton(context.getString(R.string.no), listenerNot);



        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void confirmAction(Context context,int id, DialogInterface.OnClickListener listenerYes ,DialogInterface.OnClickListener listenerNot){
        confirmAction(context,context.getString(id), listenerYes,listenerNot);
    }
}
