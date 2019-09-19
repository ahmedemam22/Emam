package com.example.monte.ondb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class service extends AppCompatActivity {
    RadioGroup group;
    Button request;
    RadioButton radio;
     public int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        group=findViewById(R.id.group);
        count=0;
        final SharedPreferences share=getApplicationContext().getSharedPreferences("request",0);
        final SharedPreferences.Editor edit=share.edit();
        edit.putInt("count",count);
        edit.commit();



        request=findViewById(R.id.request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=1;

                int selectedId = group.getCheckedRadioButtonId();
                radio = (RadioButton) findViewById(selectedId);
                edit.putString("type",radio.getText().toString());
                edit.putInt("count",count).apply();
                Intent x=new Intent(getApplicationContext(),Adminfirst.class);
                startActivity(x);







            }
        });

    }
}
