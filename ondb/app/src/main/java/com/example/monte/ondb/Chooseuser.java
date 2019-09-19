package com.example.monte.ondb;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Chooseuser extends AppCompatActivity {
    LinearLayout open,close;

    Button student,supervisor,admin,agency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseuser);
        open=findViewById(R.id.open);
        close=findViewById(R.id.close);
        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                close.setVisibility(View.GONE);
                open.setVisibility(View.VISIBLE);

            }
        }.start();
        student=findViewById(R.id.student);
        supervisor=findViewById(R.id.supervisor);
        admin=findViewById(R.id.admin);
        agency=findViewById(R.id.agency);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(Chooseuser.this,MainActivity.class);
                startActivity(x);
            }
        });
        supervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getApplicationContext(),Superlog.class);
                startActivity(x);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getApplicationContext(),Admin.class);
                startActivity(x);
            }
        });
        agency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getApplicationContext(),Agencylog.class);
                startActivity(x);
            }
        });

    }
}
