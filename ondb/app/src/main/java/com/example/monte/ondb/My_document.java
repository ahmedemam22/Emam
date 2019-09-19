package com.example.monte.ondb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class My_document extends AppCompatActivity {
    ImageView image1,image2;
    RequestQueue requestQueue;
String email,pers,birth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_document);
        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        SharedPreferences share=getApplicationContext().getSharedPreferences("email",0);
        email=share.getString("email","");
        String url=("http://10.0.3.2:8080/myproject/document.php?email="+email);

        requestQueue= Volley.newRequestQueue(My_document.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(com.android.volley.Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonarray = response.getJSONArray("allstudents");
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject respons = jsonarray.getJSONObject(i);

                                pers=respons.getString("imagepers");
                                birth=respons.getString("imagebirth");

                                Picasso.get().load("http://10.0.3.2:8080/myproject/imagepers/"+pers).into(image1);
                                Picasso.get().load("http://10.0.3.2:8080/myproject/imagebirth/"+birth).into(image2);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(My_document.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getApplicationContext(),Image.class);
                x.putExtra("pers",pers);
                x.putExtra("link","http://192.168.43.95:8080/myproject/imagepers/");
                startActivity(x);
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getApplicationContext(),Image.class);
                x.putExtra("pers",birth);
                x.putExtra("link","http://192.168.43.95:8080/myproject/imagebirth/");
                startActivity(x);
            }
        });

    }
}
