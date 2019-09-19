package com.example.monte.ondb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Sign extends AppCompatActivity {
    RequestQueue requestQueue;
    String url="http://10.0.3.2:8080/myproject/run.php";
    EditText text1,text2,text3,text4,text5,text6;
    String spin,encoding1,encoding2,email,relig;
    Button register,personal,birth;
    ImageView personal_pic,birth_pic;
    TextView invalid;
    Spinner spinner,religion;
    private ArrayList<String>one=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        text1=findViewById(R.id.fname);
        text2=findViewById(R.id.lname);
        text3=findViewById(R.id.email);
        text4=findViewById(R.id.password);
        religion=findViewById(R.id.religion);
        text5=findViewById(R.id.grade);
        text6=findViewById(R.id.mobile);
        personal=findViewById(R.id.personal);
        birth_pic=findViewById(R.id.birth_pic);
        birth=findViewById(R.id.birth);


        requestQueue= Volley.newRequestQueue(Sign.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonarray = response.getJSONArray("allstudents");
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject respons = jsonarray.getJSONObject(i);
                                String mail = respons.getString("email");
                                one.add(mail);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Sign.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

        spinner=findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin =spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       religion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                relig =religion.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent();
                x.setType("*/*");
                x.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(x,"choose your personal pic"),1);

            }
        });
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent();
                x.setType("*/*");
                x.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(x,"choose your birth certificate "),2);

            }
        });



        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String fname=text1.getText().toString();
                String lname=text2.getText().toString();
                email=text3.getText().toString();

                String password=text4.getText().toString();
                String grade=text5.getText().toString();
                String mobile=text6.getText().toString();



                    SharedPreferences share=getApplicationContext().getSharedPreferences("data",0);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("fname",fname);
                    editor.putString("lname",lname);
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.putString("grade",grade);
                    editor.putString("religion",relig);
                    editor.putString("mobile",mobile);
                    editor.putString("college",spin);
                    editor.putString("personal",encoding1);
                    editor.putString("birth",encoding2);
                    editor.commit();
                    //Toast.makeText(Sign.this, encoding, Toast.LENGTH_SHORT).show();
                    Intent x=new Intent(Sign.this,Choose_Country.class);
                    startActivity(x);





            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK){
            personal_pic=findViewById(R.id.personal_pic);
            try {
                Bitmap y = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                personal_pic.setImageBitmap(y);
                Bitmap bimg=((BitmapDrawable)personal_pic.getDrawable()).getBitmap();

                ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
                bimg.compress(Bitmap.CompressFormat.JPEG,70,bytearray);
                encoding1= Base64.encodeToString(bytearray.toByteArray(),Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        else if(requestCode==2&&resultCode==RESULT_OK){
            birth_pic=findViewById(R.id.birth_pic);
            try {
                 Bitmap y = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                birth_pic.setImageBitmap(y);
                Bitmap bimg=((BitmapDrawable)birth_pic.getDrawable()).getBitmap();

                ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
                bimg.compress(Bitmap.CompressFormat.JPEG,70,bytearray);
                encoding2= Base64.encodeToString(bytearray.toByteArray(),Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }



}
