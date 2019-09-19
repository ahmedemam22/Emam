package com.example.monte.ondb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Choose_Country extends AppCompatActivity {
    Button open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__country);

        open=findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(x);
            }
        });
        EditText text=findViewById(R.id.encod);
        SharedPreferences share=getApplicationContext().getSharedPreferences("data",0);
        text.setText(share.getString("pic",""));
    }
}
