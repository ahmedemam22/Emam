package com.example.monte.ondb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import static com.android.volley.Request.*;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    String url="http://10.0.3.2:8080/myproject/run.php";
LinearLayout open,close;
    EditText text2,text3 ;
    Button signin,register;
    private ArrayList<String>one=new ArrayList<String>();
    private ArrayList<String>two=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        text2=findViewById(R.id.email);
        text3=findViewById(R.id.password);
        signin=findViewById(R.id.signin);
        register=findViewById(R.id.register);
        requestQueue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonarray = response.getJSONArray("allstudents");
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject respons = jsonarray.getJSONObject(i);
                               // String id = respons.getString("id");
                               // String name = respons.getString("name");
                                String email = respons.getString("email");
                                one.add(email);
                                String pass = respons.getString("password");
                                two.add(pass);
                                //text.append("\n" + id + "-" + name + "-" + email + "-" + pass + "\n" + "--------" + "\n");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        //da l-byy48lha
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(MainActivity.this,Introduce.class);
                startActivity(x);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name=text1.getText().toString();
                String email=text2.getText().toString();
                String password=text3.getText().toString();
                //new SendPostRequest().execute(name, email,password);*/
                int count=0;
                for (int i=0;i<one.size();i++){
                    if(email.equals(one.get(i))&&password.equals(two.get(i))){
                        count++;
                        SharedPreferences share=getApplicationContext().getSharedPreferences("email",0);
                        SharedPreferences.Editor editor=share.edit();
                        editor.putString("email",email);
                        editor.putString("password",password);
                        editor.commit();
                        Intent x=new Intent(MainActivity.this,Document.class);
                        startActivity(x);
                    }



                }
                if (count==0){
                    Toast.makeText(MainActivity.this, "check email and password!", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

}