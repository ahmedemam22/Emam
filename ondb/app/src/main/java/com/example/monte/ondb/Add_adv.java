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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_adv extends AppCompatActivity {
    Button upload,publish;
    private Map<String,String> params;
    StringBuilder sbParams = new StringBuilder();
    ImageView image;
    String encoding1;
    EditText text1,text2,text3;
    Spinner spin1,spin2;
    String floor,room,price,start,end;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adv);
        upload=findViewById(R.id.uploadphotos);
        publish=findViewById(R.id.publish);
        text1=findViewById(R.id.floorno);
        text2=findViewById(R.id.numrooms);
        text3=findViewById(R.id.price);
        spin1=findViewById(R.id.from);
        spin2=findViewById(R.id.to);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent();
                x.setType("*/*");
                x.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(x,"choose your  pic"),1);
            }
        });
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               start =position+"".toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                end =position+"".toString();
                Toast.makeText(Add_adv.this, position+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                room=text2.getText().toString();
                floor=text1.getText().toString();
                price=text3.getText().toString();

                params=new HashMap<>();
               params.put("room",room);
               params.put("floor",floor);

                params.put("price",price);
               params.put("image",encoding1);
                params.put("start",start);
                params.put("end",end);


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
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK ){
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
}
    public class SendPostRequest extends AsyncTask<String, Void, String>


    {
        protected void onPreExecute()
        {

        }

        protected String doInBackground(String... arg0)
        {
            try {
                URL url = new URL("http://10.0.3.2:8080/myproject/add.php");//=" + arg0[0] + "&lname=" + arg0[1]+"&email="+arg0[2]+"&password="+arg0[3]+"&grade="+arg0[4]+"&college="+college+"&mobile="+arg0[6]+"&distance="+arg0[7]);


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

}
