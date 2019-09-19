package com.example.monte.ondb;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Request extends AppCompatActivity {
    TextView text1,text2,text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        text1=findViewById(R.id.supervisorId);
        text2=findViewById(R.id.buildingnum);
        text3=findViewById(R.id.servicetype);
        SharedPreferences share=getApplicationContext().getSharedPreferences("request",0);
        SharedPreferences shared=getApplicationContext().getSharedPreferences("super_data",0);
        text1.setText("supervisor id:"+shared.getString("id",""));
        text2.setText("buildin num:"+shared.getString("buildnum",""));
        text3.setText("request type"+share.getString("type",""));


    }
}
