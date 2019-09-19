package com.example.monte.ondb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;

public class Agencylog extends AppCompatActivity {

    EditText text2, text3;
    Button signin;
    RequestQueue requestQueue;
    String url = "http://10.0.3.2:8080/myproject/agencylog.php";
    private ArrayList<String> one = new ArrayList<String>();
    private ArrayList<String> two = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agencylog);
        text2 = findViewById(R.id.email);
        text3 = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        requestQueue = Volley.newRequestQueue(Agencylog.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonarray = response.getJSONArray("allstudents");
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject respons = jsonarray.getJSONObject(i);

                                String email = respons.getString("email");
                                one.add(email);
                                String pass = respons.getString("password");
                                two.add(pass);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Agencylog.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);//da l-byy48lha
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name=text1.getText().toString();
                String email = text2.getText().toString();
                String password = text3.getText().toString();
                //new SendPostRequest().execute(name, email,password);*/
                int count = 0;
                for (int i = 0; i < one.size(); i++) {
                    if (email.equals(one.get(i)) && password.equals(two.get(i))) {
                        count++;
                        Intent x = new Intent(Agencylog.this, Agency_prof.class);
                        startActivity(x);
                    }


                }
                if (count == 0) {
                    Toast.makeText(Agencylog.this, "check email and password!", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }
}


