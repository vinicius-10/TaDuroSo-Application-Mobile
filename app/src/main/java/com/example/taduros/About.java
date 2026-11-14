package com.example.taduros;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.ab_tv_about);
    }

    public void openSite(View view){
        open("https://www.linkedin.com/in/vinicius-dias-dev/");
    }

    private void open(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(Uri.parse(url));

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Toast.makeText(this,
                    R.string.alert_not_app_web,
                    Toast.LENGTH_LONG).show();
        }
    }
}