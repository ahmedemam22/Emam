package com.example.monte.ondb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

public class super_profile extends AppCompatActivity {
    RequestQueue requestQueue;
    ImageView image;
    String url="";
    TextView name,email,phone,Building,ids;
    public  String   mail="",img="",nam="",emai="",phon="",build="",id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_profile);
        Toolbar t=findViewById(R.id.toolbar);
        setSupportActionBar(t);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        Building=findViewById(R.id.buildingnumber);
        ids=findViewById(R.id.id);
        image=findViewById(R.id.imagesuper);
        SharedPreferences share=getApplicationContext().getSharedPreferences("super",0);
        mail=share.getString("email","");
         url=("http://10.0.3.2:8080/myproject/super_prof.php?email="+mail);

        requestQueue= Volley.newRequestQueue(super_profile.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonarray = response.getJSONArray("allstudents");
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject respons = jsonarray.getJSONObject(i);
                                img=respons.getString("image");
                                id=respons.getString("id");
                                nam=respons.getString("name");
                                emai=respons.getString("email");
                                phon=respons.getString("phone");

                                build=respons.getString("building")+"";
                                SharedPreferences share=getApplicationContext().getSharedPreferences("super_data",0);
                                SharedPreferences.Editor edit=share.edit();
                                edit.putString("buildnum",build);
                                edit.putString("id",id);
                                edit.commit();
                                Picasso.get().load("http://10.0.3.2:8080/myproject/imagesuper/"+img).into(image);
                                name.setText(nam);
                                email.setText(emai);
                                phone.setText(phon);
                                Building.setText(build);
                                ids.setText(id);






                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(super_profile.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.supervisor,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.services) {
            Intent x=new Intent(getApplicationContext(),service.class);
            startActivity(x);


        }
        return super.onOptionsItemSelected(item);

    }
}
