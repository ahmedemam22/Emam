package com.example.monte.ondb;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class Welcome extends AppCompatActivity {
    RequestQueue requestQueue;
    private String pers,birth,email;
    ImageView image1,image2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SharedPreferences share=getApplicationContext().getSharedPreferences("email",0);
         email=share.getString("email","");
        String url=("http://192.168.43.95:8080/myproject/images.php?email="+email);

        requestQueue= Volley.newRequestQueue(Welcome.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonarray = response.getJSONArray("allstudents");
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject respons = jsonarray.getJSONObject(i);

                                 pers = respons.getString("imagepers");
                                //Toast.makeText(Welcome.this, pers, Toast.LENGTH_SHORT).show();
                                birth = respons.getString("imagebirth");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Welcome.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        Button d=findViewById(R.id.d);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image1=findViewById(R.id.personal);
                image2=findViewById(R.id.bir);
                Picasso.get().load("http://192.168.43.95:8080/myproject/imagepers/"+pers).into(image1);

                Picasso.get().load("http://192.168.43.95:8080/myproject/imagebirth/"+birth).into(image2);
            }
        });





    }
}
