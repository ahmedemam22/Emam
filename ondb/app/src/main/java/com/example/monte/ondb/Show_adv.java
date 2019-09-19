package com.example.monte.ondb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Show_adv extends AppCompatActivity {
  private RecyclerView recycler;
  private RecyclerView_dAdapter recyclerView_dAdapter;
  private List<List_Item> list=new ArrayList<List_Item>();
  Button search;
  Spinner spin1,spin2;
  String start,end="";
    RequestQueue requestQueue;
    String url="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_adv);
        search=findViewById(R.id.search);
        spin1=findViewById(R.id.From);
        recycler=findViewById(R.id.recycler);

        spin2=findViewById(R.id.To);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        requestQueue= Volley.newRequestQueue(Show_adv.this);

       search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                // clear list
               // recyclerView_dAdapter.notifyDataSetChanged();


                url=("http://10.0.3.2:8080/myproject/show_adv.php?start="+start+ "&end=" + end);
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
list.clear();
                                    JSONArray jsonarray = response.getJSONArray("allstudents");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject respons = jsonarray.getJSONObject(i);
                                        // String id = respons.getString("id");
                                        list.add(new List_Item("room:"+respons.getString("room"),"floor:"+respons.getString("floor"),"price:"+respons.getString("price"),"http://192.168.43.95:8080/myproject/adv/"+respons.getString("image")));


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        list.clear();
                        Toast.makeText(Show_adv.this, "no room availlable", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);

                recycler.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1);
                recycler.setLayoutManager(gridLayoutManager);
                recyclerView_dAdapter=new RecyclerView_dAdapter(list, Show_adv.this);
                recycler.setAdapter(recyclerView_dAdapter);
            }
        });
            }




    }

