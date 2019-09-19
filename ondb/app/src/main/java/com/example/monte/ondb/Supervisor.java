package com.example.monte.ondb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Supervisor extends AppCompatActivity {
    Button register, personal;
    ImageView image;
    private Map<String,String> params;
    StringBuilder sbParams = new StringBuilder();

    String encoding1;
    EditText text1, text2, text3, text4, text5,text6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor);
        register = findViewById(R.id.regi);
        text1 = findViewById(R.id.name);
        text2 = findViewById(R.id.email);
        text3 = findViewById(R.id.password);
        text4 = findViewById(R.id.address);
        text5 = findViewById(R.id.phone);
        text6=findViewById(R.id.building);

        personal = findViewById(R.id.personal);
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent();
                x.setType("*/*");
                x.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(x, "choose your personal pic"), 1);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = text1.getText().toString();
                String email = text2.getText().toString();
                String password = text3.getText().toString();
                String address = text4.getText().toString();
                String phone = text5.getText().toString();
                String build=text6.getText().toString();
                if (name.equals("") || email.equals("") || password.equals("") || address.equals("") || phone.equals("")) {
                    Toast.makeText(Supervisor.this, "please enter supervisor's data", Toast.LENGTH_SHORT).show();
                } else {
                    params=new HashMap<>();
                    params.put("name",name);

                    params.put("email",email);
                    params.put("password",password);

                    params.put("phone",phone);
                    params.put("address",address);

                    params.put("image",encoding1);

                    params.put("building",build);

                    int i = 0;
                    for (String key : params.keySet()) {
                        try {
                            if (i != 0){
                                sbParams.append("&");
                            }
                            sbParams.append(key).append("=")
                                    .append(URLEncoder.encode(params.get(key), "UTF-8"));

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        i++;
                    }






                    new SendPostRequest().execute();


                }


            }
        });


    }

    public class SendPostRequest extends AsyncTask<String, Void, String>


    {
        protected void onPreExecute() {

        }

        protected String doInBackground(String... arg0) {
            try {
                URL url = new URL("http://10.0.3.2:8080/myproject/super.php" );

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Accept-Charset", "UTF-8");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.connect();

                String paramsString = sbParams.toString();
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;

                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                return buffer.toString();
            } catch (Exception e) {
                return new String ("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result)

        {

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK){
            image=findViewById(R.id.image);
            try {
                Bitmap y = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                image.setImageBitmap(y);
                Bitmap bimg=((BitmapDrawable)image.getDrawable()).getBitmap();

                ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
                bimg.compress(Bitmap.CompressFormat.JPEG,70,bytearray);
                encoding1= Base64.encodeToString(bytearray.toByteArray(),Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}}
