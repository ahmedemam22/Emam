package com.example.monte.ondb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Show_rate extends AppCompatActivity {
    TextView text1,text2,text3;
    Button ok;
    String url="http://10.0.3.2:8080/myproject/show_rate.php";
    RequestQueue requestQueue;
    public float fod,servic,al;

    public ArrayList<Integer>food=new ArrayList<Integer>();
    public ArrayList<Integer>service=new ArrayList<Integer>();
    public ArrayList<Integer>all=new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rate);
        ok=findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getApplicationContext(),Adminfirst.class);
                startActivity(x);
            }
        });
        text1=findViewById(R.id.food);
        text2=findViewById(R.id.service);
        text3=findViewById(R.id.all);

        requestQueue= Volley.newRequestQueue(Show_rate.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(com.android.volley.Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonarray = response.getJSONArray("allstudents");
                            for (int i = 0; i <jsonarray.length(); i++) {
                                JSONObject respons = jsonarray.getJSONObject(i);
                                // String id = respons.getString("id");
                                if(respons.getInt("food_rate")!=0){
                                    food.add(respons.getInt("food_rate"));

                                }
                                if(respons.getInt("system")!=0){
                                    all.add(respons.getInt("system"));

                                }
                                if(respons.getInt("service")!=0){
                                    service.add(respons.getInt("service"));

                                }










                            }
                            for (int i=0;i<all.size();i++){
                                fod+=food.get(i);
                                servic+=service.get(i);
                                al+=all.get(i);

                            }
                            text1.setText((fod/food.size())+"");
                            text2.setText((servic/service.size())+"");
                            text3.setText((al/all.size())+"");


                            Toast.makeText(Show_rate.this, food.get(0)+"", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}
